package edu.ucsd.cse110.habitizer.lib.data;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import edu.ucsd.cse110.habitizer.lib.domain.Task;
import edu.ucsd.cse110.habitizer.lib.domain.Routine;
import edu.ucsd.cse110.observables.PlainMutableSubject;
import edu.ucsd.cse110.observables.Subject;
/**
 * Class used as a sort of "database" of decks and tasks that exist. This
 * will be replaced with a real database in the future, but can also be used
 * for testing.
 */
public class InMemoryDataSource {
    private final Map<Integer, Task> tasks;
    private final Map<Integer, PlainMutableSubject<Task>> taskSubjects;
    private final PlainMutableSubject<List<Task>> allTasksSubjects;

    private final Map<Integer, Routine> routines; // ID is 0: "Morning" or 1: "Evening"
    private final Map<Integer, PlainMutableSubject<Routine>> routineSubjects;
    private final PlainMutableSubject<List<Routine>> allRoutinesSubjects;

    public InMemoryDataSource() {
        tasks = new HashMap<>();
        taskSubjects = new HashMap<>();
        allTasksSubjects = new PlainMutableSubject<>();
        routines = new HashMap<>();
        routineSubjects = new HashMap<>();
        allRoutinesSubjects = new PlainMutableSubject<>();
    }

    public List<Task> getTasks() {
        return List.copyOf(tasks.values());
    }

    public Task getTask(int id) {
        return tasks.get(id);
    }

    public Subject<Task> getTaskSubject(int id) {
        if (!taskSubjects.containsKey(id)) {
            var subject = new PlainMutableSubject<Task>();
            subject.setValue(getTask(id));
            taskSubjects.put(id, subject);
        }
        return taskSubjects.get(id);
    }

    public Subject<List<Task>> getAllTasksSubject() {
        return allTasksSubjects;
    }

    public void putTask(Task task) {
        tasks.put(task.getId(), task);
        if (taskSubjects.containsKey(task.getId())) {
            taskSubjects.get(task.getId()).setValue(task);
        }
        allTasksSubjects.setValue(getTasks());
    }

    public List<Routine> getRoutines() {
        return List.copyOf(routines.values());
    }

    public Routine getRoutine(int id) {
        return routines.get(id);
    }

    public Subject<Routine> getRoutineSubject(int id) {
        if (!routineSubjects.containsKey(id)) {
            var subject = new PlainMutableSubject<Routine>();
            subject.setValue(getRoutine(id));
            routineSubjects.put(id, subject);
        }
        return routineSubjects.get(id);
    }

    public Subject<List<Routine>> getAllRoutinesSubject() {
        return allRoutinesSubjects;
    }

    public void putRoutine(Routine routine) {
        routines.put(routine.getId(), routine);
        if (routineSubjects.containsKey(routine.getId())) {
            routineSubjects.get(routine.getId()).setValue(routine);
        }
        allRoutinesSubjects.setValue(getRoutines());
    }

    public final static List<Task> DEFAULT_TASKS = List.of();
    public final static List<Routine> DEFAULT_ROUTINES = List.of();

    public static InMemoryDataSource fromDefault() {
        var data = new InMemoryDataSource();
        for (Task task : DEFAULT_TASKS) {
            data.putTask(task);
        }
        for (Routine routine : DEFAULT_ROUTINES) {
            data.putRoutine(routine);
        }
        return data;
    }
}
