package edu.ucsd.cse110.habitizer.app.data.db;

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

        return new LiveDataSubjectAdapter<>(routineLiveData);
    }

    @Override
    public Subject<List<ElapsedTime>> findAllRoutineTimers() {
        return null;
    }

    @Override
    public void save(Routine routine) {
        routineDao.insert(RoutineEntity.fromRoutine(routine));
    }

    @Override
    public void saveAll(List<Routine> routines) {
        routineDao.insert(routines.stream()
            .map(RoutineEntity::fromRoutine)
            .collect(Collectors.toList()));
    }

    @Override
    public String getRoutineName(int rid) {
        return routineDao.getRoutineName(rid);
    }

    @Override
    public Timer getTimer(int rid) {
//        var entityLiveData = timerDao.findAsLiveData(-1, rid);
//        var timerLiveData = Transformations.map(entityLiveData, TimerEntity::toTimer);
        return timerDao.find(-1, rid).toTimer();
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
        return routineDao.getRoutineTotalTimeElapsed(routineId);
    }

    @Override
    public int getCurrTaskTimeElapsed(int routineId) {

        return 0;
    }
}
