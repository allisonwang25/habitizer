package edu.ucsd.cse110.habitizer.lib.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.ucsd.cse110.habitizer.lib.domain.Task;
import edu.ucsd.cse110.habitizer.lib.domain.Routine;
import edu.ucsd.cse110.habitizer.lib.util.ElapsedTime;
import edu.ucsd.cse110.observables.PlainMutableSubject;
import edu.ucsd.cse110.observables.Subject;


public class InMemoryDataSource {
    private final Map<Integer, Task> tasks;
    private final Map<Integer, PlainMutableSubject<Task>> taskSubjects;
    private final PlainMutableSubject<List<Task>> allTasksSubjects;

    private final Map<Integer, Routine> routines; // ID is 0: "Morning" or 1: "Evening"
    private final Map<Integer, PlainMutableSubject<Routine>> routineSubjects;
    private final PlainMutableSubject<List<Routine>> allRoutinesSubjects;

    // Should the timers be initalized here?
    private static ElapsedTime mTimer = new ElapsedTime();
    private static ElapsedTime eTimer = new ElapsedTime();

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

    public void renameTask(int taskId, String taskName){
        Task task = tasks.get(taskId);
        task.setName(taskName);
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

    public final static List<Routine> DEFAULT_ROUTINES = List.of(
            new Routine("Morning",0, mTimer),
            new Routine("Evening",1, eTimer)
    );
    public final static List<Task> DEFAULT_MORNING_TASKS = List.of(
            new Task("Shower", mTimer),
            new Task("Brush Teeth", mTimer),
            new Task("Dress", mTimer),
            new Task("Make Coffee", mTimer),
            new Task("Make Lunch", mTimer),
            new Task("Dinner Prep", mTimer),
            new Task("Pack Bag", mTimer)
    );
    public final static List<Task> DEFAULT_EVENING_TASKS = List.of(
            new Task("Charge Devices", eTimer),
            new Task("Prepare Dinner", eTimer),
            new Task("Eat Dinner", eTimer),
            new Task("Wash Dishes", eTimer),
            new Task("Pack Bag for Morning", eTimer),
            new Task("Homework", eTimer)
    );



    public static InMemoryDataSource fromDefault() {
        var data = new InMemoryDataSource();

        for (int i = 0; i < DEFAULT_ROUTINES.size(); ++i) {
            Routine routine = DEFAULT_ROUTINES.get(i);
            data.putRoutine(routine);
            if(i == 0){
                for(int j = 0; j < DEFAULT_MORNING_TASKS.size(); ++j){
                    Task task = DEFAULT_MORNING_TASKS.get(j);
                    routine.addTask(task);
                }
            } else if (i == 1){
                for(int j = 0; j < DEFAULT_EVENING_TASKS.size(); ++j){
                    Task task = DEFAULT_EVENING_TASKS.get(j);
                    routine.addTask(task);
                }
            }
        }
        for (Task task : DEFAULT_MORNING_TASKS) {
            data.putTask(task);
        }
        for (Task task : DEFAULT_EVENING_TASKS) {
            data.putTask(task);
        }
        return data;
    }
    public String getRoutineGoalTime(int id){
        return getRoutine(id).getGoalTime();
    }
}