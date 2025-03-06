package edu.ucsd.cse110.habitizer.lib.domain;

import java.util.List;

import edu.ucsd.cse110.observables.PlainMutableSubject;

public interface RoutineRepository {
    Integer count();

    PlainMutableSubject<Routine> find(int id);

    PlainMutableSubject<List<Routine>> findAll();

    void save(Routine routine);
}
