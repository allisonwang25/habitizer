package edu.ucsd.cse110.habitizer.lib.domain;

import java.util.List;

import edu.ucsd.cse110.habitizer.lib.data.InMemoryDataSource;
import edu.ucsd.cse110.observables.PlainMutableSubject;

public class SimpleTaskRepository implements TaskRepository {
    private final InMemoryDataSource dataSource;

    public SimpleTaskRepository(InMemoryDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Integer count() {
        return dataSource.getTasks().size();
    }

    @Override
    public void append(Task task) {
        dataSource.putTask(task);
    }

    @Override
    public PlainMutableSubject<Task> find(int id) {
        return (PlainMutableSubject<Task>) dataSource.getTaskSubject(id);
    }

    @Override
    public PlainMutableSubject<List<Task>> findAll() {
        return (PlainMutableSubject<List<Task>>) dataSource.getAllTasksSubject();
    }

    @Override
    public void renameTask(int taskId, String taskName) {
        dataSource.renameTask(taskId, taskName);
    }

    @Override
    public void removeTask(int taskId) {
        dataSource.removeTask(taskId);
    }

    @Override
    public void save(Task task) {
        dataSource.putTask(task);
    }
}