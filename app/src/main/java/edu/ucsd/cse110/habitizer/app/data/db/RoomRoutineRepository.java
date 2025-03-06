package edu.ucsd.cse110.habitizer.app.data.db;

import androidx.lifecycle.Transformations;

import java.util.List;
import java.util.stream.Collectors;

import edu.ucsd.cse110.habitizer.app.util.LiveDataSubjectAdapter;
import edu.ucsd.cse110.habitizer.lib.domain.Routine;
import edu.ucsd.cse110.habitizer.lib.domain.RoutineRepository;
import edu.ucsd.cse110.observables.PlainMutableSubject;

public class RoomRoutineRepository implements RoutineRepository {
    private RoutineDao routineDao;

    @Override
    public Integer count() {
        return routineDao.count();
    }

    @Override
    public PlainMutableSubject<Routine> find(int id) {
        var entityLivaData = routineDao.findAsLiveData(id);
        var RoutineLiveData = Transformations.map(entityLivaData, RoutineEntity::toRoutine);

//        return new LiveDataSubjectAdapter<>(RoutineLiveData);
        return null;
    }

    @Override
    public PlainMutableSubject<List<Routine>> findAll() {
        var entitiesLivaDaa = routineDao.findAllAsLiveData();
        var tasksLiveData = Transformations.map(entitiesLivaDaa, entities -> {
            return entities.stream()
                .map(RoutineEntity::toRoutine)
                .collect(Collectors.toList());
        });
        return null;
    }

    @Override
    public void save(Routine routine) {
        routineDao.insert(RoutineEntity.fromRoutine(routine));
    }
}
