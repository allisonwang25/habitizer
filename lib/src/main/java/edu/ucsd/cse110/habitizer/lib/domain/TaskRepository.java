package edu.ucsd.cse110.habitizer.lib.domain;

import java.util.List;

import edu.ucsd.cse110.observables.PlainMutableSubject;
import edu.ucsd.cse110.observables.Subject;

public interface TaskRepository {
    Integer count();

    void append(Task task);

    Subject<Task> find(int id);

    Subject<List<Task>> findAll();

    Subject<List<Task>> findAllWithRID(int rid);

    void renameTask(int taskId, String taskName);

    void removeTask(int taskId);

    void checkOffTask(int taskId, int routineId);

    void save(Task task);
    void saveAll(List<Task> tasks);

     void moveTaskUp(int routineId, int taskId) ;
     void moveTaskDown(int routineId, int taskId) ;
}
