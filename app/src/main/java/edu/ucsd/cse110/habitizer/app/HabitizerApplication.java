package edu.ucsd.cse110.habitizer.app;

import android.app.Application;

import androidx.room.Room;

import edu.ucsd.cse110.habitizer.app.data.db.HabitizerDatabase;
import edu.ucsd.cse110.habitizer.app.data.db.RoomRoutineRepository;
import edu.ucsd.cse110.habitizer.app.data.db.RoomTaskRepository;
import edu.ucsd.cse110.habitizer.lib.data.InMemoryDataSource;
import edu.ucsd.cse110.habitizer.lib.domain.RoutineRepository;
import edu.ucsd.cse110.habitizer.lib.domain.TaskRepository;

public class HabitizerApplication extends Application {
    InMemoryDataSource dataSource;
    private RoutineRepository routineRepository;
    private TaskRepository mTaskRepository;
//    private TaskRepository eTaskRepository;

    @Override
    public void onCreate() {
        super.onCreate();

        //init datasource
//        this.dataSource = InMemoryDataSource.fromDefault();
//        this.routineRepository = new RoutineRepository(dataSource);
//        this.mTaskRepository = new TaskRepository(dataSource);
//        this.eTaskRepository = new TaskRepository(dataSource);

        var database = Room.databaseBuilder(
            getApplicationContext(),
            HabitizerDatabase.class,
            "habitizer-database"
        )
            .allowMainThreadQueries()
            .build();

        // TODO: oop
        this.mTaskRepository = new RoomTaskRepository(database.taskDao());
        this.routineRepository = new RoomRoutineRepository(database.routineDao());

        var sharedPref = getSharedPreferences("habitizer", MODE_PRIVATE);
        var isFirstRun = sharedPref.getBoolean("isFirstRun", true);

        //TODO : Our Routines has tasks on init?
        if (isFirstRun && database.routineDao().count() == 0) {
//            TaskRepository.save(InMemoryDataSource.DEFAULT_CARDS);

            sharedPref.edit()
                .putBoolean("isFirstRun", false)
                .apply();
        }
    }
    public RoutineRepository getRoutineRepository(){
        return routineRepository;
    }
//    public TaskRepository getETaskRepository(){
//        return eTaskRepository;
//    }
    public TaskRepository getMTaskRepository(){
        return mTaskRepository;
    }
}