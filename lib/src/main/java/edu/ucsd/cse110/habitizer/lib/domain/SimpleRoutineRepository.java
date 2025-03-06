package edu.ucsd.cse110.habitizer.lib.domain;

import java.util.List;

import edu.ucsd.cse110.habitizer.lib.data.InMemoryDataSource;
import edu.ucsd.cse110.observables.PlainMutableSubject;

public class SimpleRoutineRepository implements RoutineRepository {
    private final InMemoryDataSource dataSource;

    public SimpleRoutineRepository(InMemoryDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Integer count() {
        return dataSource.getRoutines().size();
    }

    @Override
    public PlainMutableSubject<Routine> find(int id) {
        return (PlainMutableSubject<Routine>) dataSource.getRoutineSubject(id);
    }

    @Override
    public PlainMutableSubject<List<Routine>> findAll() {
        return (PlainMutableSubject<List<Routine>>) dataSource.getAllRoutinesSubject();
    }

    @Override
    public void save(Routine routine) {
        dataSource.putRoutine(routine);
    }
}
