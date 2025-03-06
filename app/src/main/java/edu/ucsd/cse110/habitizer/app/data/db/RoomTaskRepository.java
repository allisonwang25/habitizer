package edu.ucsd.cse110.habitizer.app.data.db;

import androidx.lifecycle.Transformations;
import androidx.room.Room;

import java.util.List;
import java.util.stream.Collectors;

import edu.ucsd.cse110.habitizer.app.util.LiveDataSubjectAdapter;
import edu.ucsd.cse110.habitizer.lib.domain.Task;
import edu.ucsd.cse110.habitizer.lib.domain.TaskRepository;
import edu.ucsd.cse110.observables.PlainMutableSubject;

public class RoomTaskRepository implements TaskRepository {
    private final TaskDao taskDao;

    public RoomTaskRepository(TaskDao dao) {
        this.taskDao = dao;
    }

    @Override
    public Integer count() {
        return taskDao.count();
    }

    @Override
    public void append(Task task) {

    }

    @Override
    public PlainMutableSubject<Task> find(int id) {
        var entityLiveData = taskDao.findAsLiveData(id);
//        var taskLiveData = Transformations.map(entityLiveData, TaskEntity::toTask());

//        return new LiveDataSubjectAdapter<>(taskLiveData);
        return null;
    }

    @Override
    public PlainMutableSubject<List<Task>> findAll() {
        var entitiesLivaDaa = taskDao.findAllAsLiveData();
        var tasksLiveData = Transformations.map(entitiesLivaDaa, entities -> {
            return entities.stream()
                .map(TaskEntity::toTask)
                .collect(Collectors.toList());
        });

//        return new LiveDataSubjectAdapter<>(tasksLiveData);
        return null;
    }

    @Override
    public void renameTask(int taskId, String taskName) {
        taskDao.updateName(taskName, taskId);
    }

    @Override
    public void save(Task task) {
        taskDao.insert(TaskEntity.fromTask(task));
    }
}
