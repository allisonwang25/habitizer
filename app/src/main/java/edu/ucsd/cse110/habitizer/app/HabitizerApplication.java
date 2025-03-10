package edu.ucsd.cse110.habitizer.app;

import android.app.Application;

import edu.ucsd.cse110.habitizer.lib.data.InMemoryDataSource;
import edu.ucsd.cse110.habitizer.lib.domain.RoutineRepository;
import edu.ucsd.cse110.habitizer.lib.domain.TaskRepository;

public class HabitizerApplication extends Application {
    InMemoryDataSource dataSource;
    private RoutineRepository routineRepository;
    private TaskRepository taskRepository;

    @Override
    public void onCreate() {
        super.onCreate();

        //init datasource
        this.dataSource = InMemoryDataSource.fromDefault();
        this.routineRepository = new RoutineRepository(dataSource);
        this.taskRepository = new TaskRepository(dataSource);
    }
    public RoutineRepository getRoutineRepository(){
        return routineRepository;
    }
    public TaskRepository getTaskRepository(){
        return taskRepository;
    }
}