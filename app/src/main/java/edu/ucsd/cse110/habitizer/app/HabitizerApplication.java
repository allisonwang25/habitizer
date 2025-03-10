package edu.ucsd.cse110.habitizer.app;

import android.app.Application;

import androidx.room.Room;

import java.util.List;

import edu.ucsd.cse110.habitizer.app.data.db.HabitizerDatabase;
import edu.ucsd.cse110.habitizer.app.data.db.RoomRoutineRepository;
import edu.ucsd.cse110.habitizer.app.data.db.RoomTaskRepository;
import edu.ucsd.cse110.habitizer.lib.data.InMemoryDataSource;
import edu.ucsd.cse110.habitizer.lib.domain.RoutineRepository;
import edu.ucsd.cse110.habitizer.lib.domain.Task;
import edu.ucsd.cse110.habitizer.lib.domain.TaskRepository;

public class HabitizerApplication extends Application {
    private RoutineRepository routineRepository;
    private TaskRepository taskRepository;

    @Override
    public void onCreate() {
        super.onCreate();

        var database = Room.databaseBuilder(
            getApplicationContext(),
            HabitizerDatabase.class,
            "habitizer-database"
        )
            .allowMainThreadQueries()
            .build();

        this.taskRepository = new RoomTaskRepository(database.taskDao());
        this.routineRepository = new RoomRoutineRepository(database.routineDao());

        var sharedPref = getSharedPreferences("habitizer", MODE_PRIVATE);
        var isFirstRun = sharedPref.getBoolean("isFirstRun", true);

        if (isFirstRun && database.routineDao().count() == 0) {
            routineRepository.saveAll(InMemoryDataSource.DEFAULT_ROUTINES);

            taskRepository.saveAll(InMemoryDataSource.DEFAULT_MORNING_TASKS);
            taskRepository.saveAll(InMemoryDataSource.DEFAULT_EVENING_TASKS);

            sharedPref.edit()
                .putBoolean("isFirstRun", false)
                .apply();
        }
    }
    public RoutineRepository getRoutineRepository(){
        return routineRepository;
    }

    public TaskRepository getTaskRepository(){
        return taskRepository;
    }
}