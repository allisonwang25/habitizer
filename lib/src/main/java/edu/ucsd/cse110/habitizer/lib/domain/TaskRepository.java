package edu.ucsd.cse110.habitizer.lib.domain;

import java.util.List;

import edu.ucsd.cse110.habitizer.lib.data.InMemoryDataSource;
import edu.ucsd.cse110.observables.PlainMutableSubject;

public class TaskRepository {
    private final InMemoryDataSource dataSource;

    public TaskRepository(InMemoryDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Integer count() {
        return dataSource.getTasks().size();
    }

//    public void append(Task task) {
//        dataSource.putTask(task);
//    }

    public PlainMutableSubject<Task> find(int id) {
        return (PlainMutableSubject<Task>) dataSource.getTaskSubject(id);
    }

    public PlainMutableSubject<List<Task>> findAll() {
        return (PlainMutableSubject<List<Task>>) dataSource.getAllTasksSubject();
    }

    public void renameTask(int taskId, String taskName) {
        dataSource.renameTask(taskId, taskName);
    }


    public void checkOffTask(int taskId, int routineId) {
        dataSource.checkOffTask(taskId, routineId);
    }

    public void save(Task task) {
        task.setSortOrder(dataSource.getMaxSortOrder(task.getRid()) + 1);
        dataSource.putTask(task);
    }
}