package edu.ucsd.cse110.habitizer.lib.domain;

import java.util.List;

import edu.ucsd.cse110.observables.PlainMutableSubject;
import edu.ucsd.cse110.observables.Subject;
import edu.ucsd.cse110.habitizer.lib.util.Timer;

public interface RoutineRepository {
    Integer count();

    Subject<Routine> find(int id);

    Subject<List<Routine>> findAll();

    void save(Routine routine);
    void saveAll(List<Routine> routines);

    String getRoutineName(int rid);

    Timer getTimer(int rid);

    String getRoutineGoalTime(int rid);
    void setRoutineGoalTime(int rid, String goalTime);

    Boolean getRoutineCompleted(int rid);
//    void setRoutineCompleted(int rid, Boolean completed);

    int getRoutineTotalTimeElapsed(int routineId);

}
