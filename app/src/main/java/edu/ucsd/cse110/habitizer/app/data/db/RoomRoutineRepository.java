package edu.ucsd.cse110.habitizer.app.data.db;

import androidx.lifecycle.Transformations;

import java.util.List;
import java.util.stream.Collectors;

import edu.ucsd.cse110.habitizer.app.util.LiveDataSubjectAdapter;
import edu.ucsd.cse110.habitizer.lib.domain.Routine;
import edu.ucsd.cse110.habitizer.lib.domain.RoutineRepository;
import edu.ucsd.cse110.observables.PlainMutableSubject;
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
    public void save(Routine routine) {
        routineDao.insert(RoutineEntity.fromRoutine(routine));
    }

    @Override
    public void saveAll(List<Routine> routines) {
        routineDao.insert(routines.stream()
            .map(RoutineEntity::fromRoutine)
            .collect(Collectors.toList()));
    }
}
