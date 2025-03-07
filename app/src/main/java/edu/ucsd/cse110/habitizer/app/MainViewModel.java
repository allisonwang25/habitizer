package edu.ucsd.cse110.habitizer.app;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;
import 	androidx.lifecycle.MutableLiveData;
import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;


import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
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

    // LiveData to hold the elapsed time text
    private final MutableLiveData<String> routineElapsedTimeText = new MutableLiveData<>();
    private final MutableLiveData<String> taskElapsedTimeText = new MutableLiveData<>();

    private final PlainMutableSubject<List<Integer>> RoutineOrdering;

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

    public MainViewModel(RoutineRepository r, TaskRepository t) {
        this.routineRepository = r;
        this.taskRepository = t;

        // Create the observable subjects.
        routineGoalTime = new PlainMutableSubject<>();
        taskOrdering = new PlainMutableSubject<>();
        RoutineOrdering = new PlainMutableSubject<>();

        orderedTasks = new PlainMutableSubject<>();
        orderedRoutines = new PlainMutableSubject<>();

        routineRepository.find(0).observe(routine -> {
            if (routine == null) return;
            routineGoalTime.setValue(routine.getGoalTime());
        });

        // When the list of tasks changes (or is first loaded), reset the ordering.
        taskRepository.findAll().observe(tasks -> {
            if (tasks == null) return; // not ready yet, ignore

//            var ordering = new ArrayList<Integer>();
//            for (int i = 0; i < tasks.size(); i++) {
//                ordering.add(i);
//            }
            var newOrdering = tasks.stream()
                            .sorted(Comparator.comparingInt(Task::getSortOrder))
                            .map(Task::getTid)
                            .collect(Collectors.toList());
//            Collections.sort(newOrdering);
            Log.d("Debug ordering", "taskRepo changed " + newOrdering.toString());

            taskOrdering.setValue(newOrdering);
        });

        taskOrdering.observe(ordering -> {
            if (ordering == null) return;

            var tasks = new ArrayList<Task>();
            for (var id : ordering) {
                var task = taskRepository.find(id).getValue();
                if (task == null) return;
                tasks.add(task);

            }
            Log.d("Debug ordering", "taskOrdering changed " + tasks.toString());
            this.orderedTasks.setValue(tasks);
        });

        routineRepository.findAll().observe(routines -> {
           if (routines == null) return;

            var newOrderedRoutines = routines.stream()
                    .sorted(Comparator.comparingInt(Routine::getId))
                    .toList();

            var ordering = new ArrayList<Integer>();
            for (Routine routine : newOrderedRoutines) {
                ordering.add(routine.getId());
            }

            RoutineOrdering.setValue(ordering);
        });

        RoutineOrdering.observe(ordering -> {
            if (ordering == null) return;

            var routines = new ArrayList<Routine>();
            for (var id : ordering) {
                var routine = routineRepository.find(id).getValue();
                if (routine == null) return;
                routines.add(routine);
            }
            this.orderedRoutines.setValue(routines);
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

    public PlainMutableSubject<List<Task>> getOrderedTasks() { return orderedTasks; }
    public PlainMutableSubject<List<Routine>> getOrderedRoutines() { return orderedRoutines; }


    public Routine getRoutine(int routineId){
        return orderedRoutines.getValue().get(routineId);
    }
    public LiveData<String> getRoutineElapsedTimeText() {
        return routineElapsedTimeText;
    }

    public LiveData<String> getTaskElapsedTimeText() {
        return taskElapsedTimeText;
    }

    private final Handler handler = new Handler(Looper.getMainLooper());
    private Runnable updateElapsedTimeRunnable;

    // Call this method to start the timer for a specific routine
    public void startUpdatingElapsedTime(int routineId) {
        updateElapsedTimeRunnable = new Runnable() {
            @Override
            public void run() {
                String goalTime = getRoutineGoalTime(routineId);
                int routineElapsedMinutes = getRoutine(routineId).getTotalTimeElapsed();
                String goalTimeText = routineElapsedMinutes + " out of " + goalTime + " minutes elapsed";
                routineElapsedTimeText.setValue(goalTimeText);

                int currTaskElapsedTime = getRoutine(routineId).getTimer().getCurrTaskTimeElapsed();
                if (currTaskElapsedTime < 60){
                    String currTaskTimeText = currTaskElapsedTime + " seconds elapsed";
                    taskElapsedTimeText.setValue(currTaskTimeText);
                }
                else {
                    String currTaskTimeText = currTaskElapsedTime / 60 + " minutes elapsed";
                    taskElapsedTimeText.setValue(currTaskTimeText);
                }
                // Schedule the next update after 1 second (1000ms)
                handler.postDelayed(this, 1000);
            }
        };
        // Start the periodic update
        handler.post(updateElapsedTimeRunnable);
    }

    public void stopUpdatingElapsedTime() {
        if (updateElapsedTimeRunnable != null) {
            handler.removeCallbacks(updateElapsedTimeRunnable);
            updateElapsedTimeRunnable = null;
        }
    }


    public void addTask(Task task, int routineId){
        getRoutine(routineId).addTask(task);
        taskRepository.save(task);
    }

    public void moveTaskUp(int routineId, int taskId) {
        routineRepository.moveTaskUp(routineId,taskId);
        Log.d("Debug ordering", "movingTaskUp" + taskOrdering.getValue().toString());

    }

    public void moveTaskDown(int routineId, int taskId) {
        routineRepository.moveTaskDown(routineId, taskId);
    }
    public Task getTask(int taskId) {
        return taskRepository.find(taskId).getValue();
    }

    public void renameTask(int taskId, String taskName){
        taskRepository.renameTask(taskId, taskName);
    }

    public void removeTask(int taskId, int routineId) {
        getRoutine(routineId).removeTask(taskId);
        taskRepository.removeTask(taskId);
    }

    public void addRoutine(String name){
        routineRepository.save(new Routine(name, new ElapsedTime()));
    }

    public String getRoutineGoalTime(int routineId) {
        return getRoutine(routineId).getGoalTime();
    }

    public void setRoutineGoalTime(int routineId, int minutes) {
        getRoutine(routineId).setGoalTime(minutes);
    }
}
