package edu.ucsd.cse110.habitizer.app;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import edu.ucsd.cse110.habitizer.lib.domain.Routine;
import edu.ucsd.cse110.habitizer.lib.domain.RoutineRepository;
import edu.ucsd.cse110.habitizer.lib.domain.Task;
import edu.ucsd.cse110.habitizer.lib.domain.TaskRepository;
import edu.ucsd.cse110.habitizer.lib.util.ElapsedTime;
import edu.ucsd.cse110.observables.PlainMutableSubject;

public class MainViewModel extends ViewModel {
    // Domain state (true "Model" state)
    private final RoutineRepository routineRepository;
    private final TaskRepository taskRepository;
    // UI state
    private final PlainMutableSubject<String> routineGoalTime;
    private final PlainMutableSubject<List<Integer>> taskOrdering;
    private final PlainMutableSubject<List<Integer>> routineOrdering;

    // LIST OF ORDERED TASKS
    private final PlainMutableSubject<List<Task>> orderedTasks;

    private final PlainMutableSubject<List<Routine>> orderedRoutines;

    public static final ViewModelInitializer<MainViewModel> initializer =
            new ViewModelInitializer<>(
                    MainViewModel.class,
                    creationExtras -> {
                        var app = (HabitizerApplication) creationExtras.get(APPLICATION_KEY);
                        assert app != null;
                        return new MainViewModel(app.getRoutineRepository(), app.getTaskRepository());
                    });

    public MainViewModel(RoutineRepository r, TaskRepository m) {
        this.routineRepository = r;
        this.taskRepository = m;

        // Create the observable subjects.
        routineGoalTime = new PlainMutableSubject<>();
        taskOrdering = new PlainMutableSubject<>();
        routineOrdering = new PlainMutableSubject<>();

        orderedTasks = new PlainMutableSubject<>();
        orderedRoutines = new PlainMutableSubject<>();

        routineRepository.find(0).observe(routine -> {
            if (routine == null) return;
            routineGoalTime.setValue(routine.getGoalTime());
        });

        // When the list of tasks changes (or is first loaded), reset the ordering.
        taskRepository.findAll().observe(tasks -> {
            if (tasks == null) return; // not ready yet, ignore

            var newOrderedTasks = tasks.stream()
                    .sorted(Comparator.comparingInt(Task::getTid))
                    .collect(Collectors.toList());

            var ordering = new ArrayList<Integer>();
            for (Task t : newOrderedTasks) {
                ordering.add(t.getTid());
            }

            orderedTasks.setValue(newOrderedTasks);
            taskOrdering.setValue(ordering);
        });

        taskOrdering.observe(ordering -> {
            if (ordering == null) return;

            var tasks = new ArrayList<Task>();
            for (var id : ordering) {
                var task = taskRepository.find(id).getValue();
                if (task == null) return;
                tasks.add(task);

            }
//            this.orderedTasks.setValue(tasks);
        });

        routineRepository.findAll().observe(routines -> {
            if (routines == null) return;

            var newOrderedRoutines = routines.stream()
                    .sorted(Comparator.comparingInt(Routine::getId))
                    .collect(Collectors.toList());

            var ordering = new ArrayList<Integer>();
            for (Routine routine : newOrderedRoutines) {
                ordering.add(routine.getId());
            }

            routineOrdering.setValue(ordering);
            orderedRoutines.setValue(newOrderedRoutines);
        });

        routineOrdering.observe(ordering -> {
            if (ordering == null) return;

            var routines = new ArrayList<Routine>();
            for (var id : ordering) {
                var routine = routineRepository.find(id).getValue();
                if (routine == null) return;
                routines.add(routine);
            }
//            this.orderedRoutines.setValue(routines);
        });


        // Commented out the evening routine as we don't need 2 routines for this US
//        eTaskRepository.findAll().observe(tasks -> {
//            if (tasks == null) return; // not ready yet, ignore
//
//            var ordering = new ArrayList<Integer>();
//            for (int i = 0; i < tasks.size(); i++) {
//                ordering.add(i);
//            }
//            eTaskOrdering.setValue(ordering);
//        });

    }

    public PlainMutableSubject<List<Task>> getOrderedTasks() {
        return orderedTasks;
    }

    public PlainMutableSubject<List<Routine>> getOrderedRoutines() {
        return orderedRoutines;
    }

    public void addTask(Task task, int routineId){
        orderedRoutines.getValue().get(routineId).addTask(task);
        taskRepository.save(task);
    }

    public Task getTask(int taskId) {
        return taskRepository.find(taskId).getValue();
    }

    public void renameTask(int taskId, String taskName) {
        taskRepository.renameTask(taskId, taskName);
    }

    public void addRoutine(String name){
        List<Routine> routines = getOrderedRoutines().getValue();
        routines.add(new Routine(name, 6969, new ElapsedTime()));
        orderedRoutines.setValue(routines);
    }

    public void removeTask(int taskId, int routineId) {
        orderedRoutines.getValue().get(routineId).removeTask(taskId);
        taskRepository.removeTask(taskId);
    }

    public PlainMutableSubject<String> getRoutineGoalTime() {
        return routineGoalTime;
    }

    public void setRoutineGoalTime(int minutes) {
        routineGoalTime.setValue(Integer.toString(minutes));
    }
}
