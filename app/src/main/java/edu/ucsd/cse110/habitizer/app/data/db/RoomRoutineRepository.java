package edu.ucsd.cse110.habitizer.app.data.db;

import android.util.Log;

import androidx.lifecycle.Transformations;

import java.util.List;
import java.util.stream.Collectors;

import edu.ucsd.cse110.habitizer.app.util.LiveDataSubjectAdapter;
import edu.ucsd.cse110.habitizer.lib.domain.Routine;
import edu.ucsd.cse110.habitizer.lib.domain.RoutineRepository;
import edu.ucsd.cse110.habitizer.lib.util.ElapsedTime;
import edu.ucsd.cse110.habitizer.lib.util.Timer;
import edu.ucsd.cse110.observables.Subject;

public class RoomRoutineRepository implements RoutineRepository {
    private RoutineDao routineDao;
    private final TimerDao timerDao;
    public RoomRoutineRepository(RoutineDao dao, TimerDao timerDao) {
        this.routineDao = dao;
        this.timerDao = timerDao;
    }

    @Override
    public Integer count() {
        return routineDao.count();
    }

    @Override
    public Subject<Routine> find(int id) {
        var entityLiveData = routineDao.findAsLiveData(id);
        var routineLiveData = Transformations.map(entityLiveData, RoutineEntity::toRoutine);

        // I'm not sure if this is the correct way to get the timer
        routineLiveData.getValue().setTimer(getTimer(id));

        return new LiveDataSubjectAdapter<>(routineLiveData);
    }

    @Override
    public Subject<List<Routine>> findAll() {
        var entityLiveData = routineDao.findAllAsLiveData();
        var routineLiveData = Transformations.map(entityLiveData, entities -> {
            return entities.stream()
                .map(RoutineEntity::toRoutine)
                .collect(Collectors.toList());
        });

        var list = routineLiveData.getValue();

//        if (list == null) {
//          throw new RuntimeException("Routine list is null");
//        }
//
//        for (Routine r : list) {
//            r.setTimer(getTimer(r.getId()));
//        }

        return new LiveDataSubjectAdapter<>(routineLiveData);
    }

    @Override
    public Subject<List<ElapsedTime>> findAllRoutineTimers() {
        Log.d("DEBUG", "Fetching all routine timers...");
        var entityLiveData = timerDao.findAll();
        var timerLiveData = Transformations.map(entityLiveData, entities -> {
            return entities.stream()
                .map(TimerEntity::toTimer)
                .collect(Collectors.toList());
        });

//        if (timerLiveData.getValue() == null) {
//            throw new RuntimeException("Timer list is null");
//        }

        return new LiveDataSubjectAdapter<>(timerLiveData);
    }

    @Override
    public void save(Routine routine) {
        routineDao.insert(RoutineEntity.fromRoutine(routine));
        timerDao.insert(TimerEntity.fromTimer((ElapsedTime) routine.getTimer(), routine.getId()));
    }

    @Override
    public void saveAll(List<Routine> routines) {
        routineDao.insert(routines.stream()
            .map(RoutineEntity::fromRoutine)
            .collect(Collectors.toList()));

        for (Routine r : routines) {
            Log.d("DEBUG", "Adding Timer ID: " + r.getId());
            timerDao.insert(TimerEntity.fromTimer((ElapsedTime) r.getTimer(), r.getId()));
        }

        Log.d("DEBUG", "Timer 1 count: " + timerDao.count());

//        timerDao.insert(routines.stream()
//            .map(r -> TimerEntity.fromTimer((ElapsedTime) r.getTimer(), r.getId(), -1))
//            .collect(Collectors.toList()));
//
//        Log.d("DEBUG", "Timer 2 count: " + timerDao.count());

    }

    @Override
    public String getRoutineName(int rid) {
        return routineDao.getRoutineName(rid);
    }

    @Override
    public Timer getTimer(int rid) {
//        var entityLiveData = timerDao.findAsLiveData(-1, rid);
//        var timerLiveData = Transformations.map(entityLiveData, TimerEntity::toTimer);
        return timerDao.find(rid).toTimer();
    }

    @Override
    public String getRoutineGoalTime(int rid) {
        return routineDao.getRoutineGoalTime(rid);
    }

    @Override
    public void setRoutineGoalTime(int rid, String goalTime) {
        routineDao.setRoutineGoalTime(rid, goalTime);
    }

    @Override
    public Boolean getRoutineCompleted(int rid) {
        return routineDao.getRoutineCompleted(rid);
    }

    @Override
    public int getRoutineTotalTimeElapsed(int routineId) {
        Log.d("DEBUG", "Getting total time elapsed for routine id " + routineId);
        return routineDao.getRoutineTotalTimeElapsed(routineId);
    }

    @Override
    public int getCurrTaskTimeElapsed(int routineId) {
        TimerEntity t = timerDao.find(routineId);
        return t.taskSecondsElapsed;
    }

    public void updateTaskSecondsElapsed(int taskSecondsElapsed, int rid){
        Log.d("DEBUG", "Updating taskSecondsElapsed for Routine ID: " + rid + " to " + taskSecondsElapsed);
        timerDao.updateTaskSecondsElapsed(taskSecondsElapsed, rid);
    }

    public void updateTotalMinutesElapsed(int totalSecondsElapsed, int rid){
        timerDao.updateTotalMinutesElapsed(totalSecondsElapsed, rid);
    }
}
