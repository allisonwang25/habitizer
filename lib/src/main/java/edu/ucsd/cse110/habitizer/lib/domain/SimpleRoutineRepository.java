package edu.ucsd.cse110.habitizer.lib.domain;

import java.util.List;

import edu.ucsd.cse110.habitizer.lib.data.InMemoryDataSource;
import edu.ucsd.cse110.habitizer.lib.util.ElapsedTime;
import edu.ucsd.cse110.habitizer.lib.util.Timer;
import edu.ucsd.cse110.observables.PlainMutableSubject;
import edu.ucsd.cse110.observables.Subject;

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
    public Subject<List<ElapsedTime>> findAllRoutineTimers() {
        return null;
    }

    @Override
    public void save(Routine routine) {
        dataSource.putRoutine(routine);
    }

    @Override
    public void saveAll(List<Routine> routines) {
        routines.forEach(routine -> dataSource.putRoutine(routine));
    }


    public void addTask(int routineId, Task task) {
        dataSource.getRoutine(routineId).addTask(task);
        dataSource.putTask(task);
    }

    public void removeTask(int routineId, int taskId) {
        dataSource.removeTask(routineId, taskId);
    }

    public void moveTaskUp(int routineId, int taskId) {
        dataSource.moveTaskUp(routineId, taskId);
    }
    public void moveTaskDown(int routineId, int taskId) {
        dataSource.moveTaskDown(routineId, taskId);
    }

    public String getRoutineName(int routineId) {
        return dataSource.getRoutineName(routineId);
    }
    public Timer getTimer(int routineId) {
        return dataSource.getTimer(routineId);
    }
    public int getRoutineTotalTimeElapsed(int routineId) {
        return dataSource.getRoutineTotalTimeElapsed(routineId);
    }

    @Override
    public int getCurrTaskTimeElapsed(int routineId) {
        return dataSource.getRoutineCurrTaskTimeElapsed(routineId);
    }

    public String getRoutineGoalTime(int routineId) {
        return dataSource.getRoutineGoalTime(routineId);
    }

    @Override
    public void setRoutineGoalTime(int rid, String goalTime) {

    }

    public void setRoutineGoalTime(int routineId, int minutes) {
        dataSource.setRoutineGoalTime(routineId, minutes);
    }

    public boolean getRoutineCompleted(int routineId){
        return dataSource.getRoutineCompleted(routineId);
    }

    public void completeRoutine(int routineId){
        dataSource.completeRoutine(routineId);
    }
}
