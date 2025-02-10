package edu.ucsd.cse110.habitizer.app;

import android.app.Application;

import edu.ucsd.cse110.habitizer.lib.data.InMemoryDataSource;
import edu.ucsd.cse110.habitizer.lib.domain.RoutineRepository;
import edu.ucsd.cse110.habitizer.lib.domain.TaskRepository;

public class HabitizerApplication extends Application {
    //TODO: Declare memory source here
    InMemoryDataSource dataSource;
    private RoutineRepository routineRepository;
    private TaskRepository mTaskRepository;
    private TaskRepository eTaskRepository;

    @Override
    public void onCreate() {
        super.onCreate();

        //init datasource
        this.dataSource = InMemoryDataSource.fromDefault();
        this.routineRepository = new RoutineRepository(dataSource);
        this.mTaskRepository = new TaskRepository(dataSource);
        this.eTaskRepository = new TaskRepository(dataSource);
    }
    //TODO: Write getter for tasks
    public RoutineRepository getRoutineRepository(){
        return routineRepository;
    }
    public TaskRepository getETaskRepository(){
        return eTaskRepository;
    }
    public TaskRepository getMTaskRepository(){
        return mTaskRepository;
    }
}