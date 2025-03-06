package edu.ucsd.cse110.habitizer.lib.domain;

import java.util.List;

import edu.ucsd.cse110.observables.PlainMutableSubject;

public interface TaskRepository {
    Integer count();

    void append(Task task);

    PlainMutableSubject<Task> find(int id);

    PlainMutableSubject<List<Task>> findAll();

    void renameTask(int taskId, String taskName);

    void save(Task task);
}
