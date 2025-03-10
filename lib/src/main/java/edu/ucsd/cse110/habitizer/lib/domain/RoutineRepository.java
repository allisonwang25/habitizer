package edu.ucsd.cse110.habitizer.lib.domain;

import java.util.List;

import edu.ucsd.cse110.observables.PlainMutableSubject;
import edu.ucsd.cse110.observables.Subject;

public interface RoutineRepository {
    Integer count();

    Subject<Routine> find(int id);

    Subject<List<Routine>> findAll();

    void save(Routine routine);
    void saveAll(List<Routine> routines);

}
