package edu.ucsd.cse110.habitizer.lib.domain;

import java.util.List;

import edu.ucsd.cse110.habitizer.lib.data.InMemoryDataSource;
import edu.ucsd.cse110.observables.PlainMutableSubject;

public class RoutineRepository {
    private final InMemoryDataSource dataSource;

    public RoutineRepository(InMemoryDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Integer count() {
        return dataSource.getRoutines().size();
    }

    public PlainMutableSubject<Routine> find(int id) {
        return (PlainMutableSubject<Routine>) dataSource.getRoutineSubject(id);
    }

    public  PlainMutableSubject<List<Routine>> findAll() {
        return (PlainMutableSubject<List<Routine>>) dataSource.getAllRoutinesSubject();
    }

    public void save(Routine routine) {
        dataSource.putRoutine(routine);
    }

    public void moveTaskUp(int routineId, int taskId) {
        dataSource.moveTaskUp(routineId, taskId);
    }
    public void moveTaskDown(int routineId, int taskId) {
        dataSource.moveTaskDown(routineId, taskId);
    }
}
