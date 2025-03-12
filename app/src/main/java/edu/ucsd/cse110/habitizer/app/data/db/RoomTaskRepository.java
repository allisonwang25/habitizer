package edu.ucsd.cse110.habitizer.app.data.db;

import androidx.lifecycle.Transformations;
import androidx.room.Room;

import java.util.List;
import java.util.stream.Collectors;

import edu.ucsd.cse110.habitizer.app.util.LiveDataSubjectAdapter;
import edu.ucsd.cse110.habitizer.lib.domain.Task;
import edu.ucsd.cse110.habitizer.lib.domain.TaskRepository;
import edu.ucsd.cse110.habitizer.lib.util.ElapsedTime;
import edu.ucsd.cse110.observables.PlainMutableSubject;
import edu.ucsd.cse110.observables.Subject;

public class RoomTaskRepository implements TaskRepository {
    private final TaskDao taskDao;
    private final TimerDao timerDao;

    public RoomTaskRepository(TaskDao dao, TimerDao timerDao) {
        this.taskDao = dao;
        this.timerDao = timerDao;
    }

    @Override
    public Integer count() {
        return taskDao.count();
    }

    @Override
    public void append(Task task) {
        save(task);
    }

    @Override
    public Subject<Task> find(int id) {
        var entityLiveData = taskDao.findAsLiveData(id);
        var taskLiveData = Transformations.map(entityLiveData, TaskEntity::toTask);

        return new LiveDataSubjectAdapter<>(taskLiveData);
    }

    @Override
    public Subject<List<Task>> findAll() {
        var entitiesLivaData = taskDao.findAllAsLiveData();
        var tasksLiveData = Transformations.map(entitiesLivaData, entities -> {
            return entities.stream()
                .map(TaskEntity::toTask)
                .collect(Collectors.toList());
        });

        return new LiveDataSubjectAdapter<>(tasksLiveData);
    }

    @Override
    public Subject<List<Task>> findAllWithRID(int rid) {
        var entitiesLiveData = taskDao.findAllWithRIDAsLiveData(rid);
        var tasksLiveData = Transformations.map(entitiesLiveData, entities -> {
            return entities.stream()
                .map(TaskEntity::toTask)
                .collect(Collectors.toList());
        });

        return new LiveDataSubjectAdapter<>(tasksLiveData);
    }

    @Override
    public Subject<List<ElapsedTime>> findAllTaskTimers() {
        return null;
    }

    @Override
    public void renameTask(int taskId, String taskName) {
        taskDao.updateName(taskName, taskId);
    }

    @Override
    public void removeTask(int taskId) {
        taskDao.remove(taskDao.find(taskId));
    }

    @Override
    public void checkOffTask(int taskId, int routineId) {

    }

    @Override
    public void save(Task task) {
        taskDao.save(TaskEntity.fromTask(task));
//        timerDao.insert(TimerEntity.fromTimer((ElapsedTime) task.getTimer(), task.getRid(), task.getTid()));
    }

    @Override
    public void saveAll(List<Task> tasks) {
        taskDao.insert(tasks.stream()
            .map(TaskEntity::fromTask)
            .collect(Collectors.toList()));

//        timerDao.insert(tasks.stream()
//            .map(task -> TimerEntity.fromTimer((ElapsedTime) task.getTimer(), task.getRid(), task.getTid()))
//            .collect(Collectors.toList()));
    }

    @Override
    public void moveTaskUp(int routineId, int taskId) {
        int sortOrder = taskDao.getTaskSortOrder(taskId);
        if (sortOrder == 0) { // already at the top
            return;
        }


        taskDao.moveTaskUp(routineId, taskId);
    }

    @Override
    public void moveTaskDown(int routineId, int taskId) {

        int sortOrder = taskDao.getTaskSortOrder(taskId);
        if (sortOrder == taskDao.getMaxSortOrder(routineId)) { // already at the bottom
            return;
        }

        taskDao.moveTaskDown(routineId, taskId);
    }
}
