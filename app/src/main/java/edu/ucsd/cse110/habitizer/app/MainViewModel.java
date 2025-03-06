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
    private final TaskRepository mTaskRepository;
    private final TaskRepository eTaskRepository;

    // UI state
    private final PlainMutableSubject<String> routineGoalTime;
    private final PlainMutableSubject<List<Integer>> mTaskOrdering;
    private final PlainMutableSubject<List<Integer>> eTaskOrdering;

    // LiveData to hold the elapsed time text
    private final MutableLiveData<String> elapsedTimeText = new MutableLiveData<>();

    private final PlainMutableSubject<List<Integer>> RoutineOrdering;

    // LIST OF ORDERED TASKS
    private final PlainMutableSubject<List<Task>> orderedMTasks;

    private final PlainMutableSubject<List<Routine>> orderedRoutines;

    public static final ViewModelInitializer<MainViewModel> initializer =
            new ViewModelInitializer<>(
                    MainViewModel.class,
                    creationExtras -> {
                        var app = (HabitizerApplication) creationExtras.get(APPLICATION_KEY);
                        assert app != null;
                        return new MainViewModel(app.getRoutineRepository(), app.getMTaskRepository(), app.getETaskRepository());
                    });

    public MainViewModel(RoutineRepository r, TaskRepository m, TaskRepository e) {
        this.routineRepository = r;
        this.mTaskRepository = m;
        this.eTaskRepository = e;

        // Create the observable subjects.
        routineGoalTime = new PlainMutableSubject<>();
        mTaskOrdering = new PlainMutableSubject<>();
        eTaskOrdering = new PlainMutableSubject<>();
        RoutineOrdering = new PlainMutableSubject<>();

        orderedMTasks = new PlainMutableSubject<>();
        orderedRoutines = new PlainMutableSubject<>();

        routineRepository.find(0).observe(routine -> {
            if (routine == null) return;
            routineGoalTime.setValue(routine.getGoalTime());
        });

        // When the list of tasks changes (or is first loaded), reset the ordering.
        mTaskRepository.findAll().observe(tasks -> {
            if (tasks == null) return; // not ready yet, ignore

            var newOrderedTasks = tasks.stream()
                    .sorted(Comparator.comparingInt(Task::getId))
                    .toList();

            var ordering = new ArrayList<Integer>();
            for (Task t : newOrderedTasks) {
                ordering.add(t.getId());
            }

            mTaskOrdering.setValue(ordering);
        });

        mTaskOrdering.observe(ordering -> {
            if (ordering == null) return;

            var tasks = new ArrayList<Task>();
            for (var id : ordering) {
                var task = mTaskRepository.find(id).getValue();
                if (task == null) return;
                tasks.add(task);

            }
            this.orderedMTasks.setValue(tasks);
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

    public PlainMutableSubject<List<Task>> getOrderedTasks() {
        return orderedMTasks;
    }

    public PlainMutableSubject<List<Routine>> getOrderedRoutines() {
        return orderedRoutines;
    }

    public Routine getRoutine(int routineId){
        return orderedRoutines.getValue().get(routineId);
    }
    public LiveData<String> getElapsedTimeText() {
        return elapsedTimeText;
    }

    private final Handler handler = new Handler(Looper.getMainLooper());
    private Runnable updateElapsedTimeRunnable;

    // Call this method to start the timer for a specific routine
    public void startUpdatingElapsedTime(int routineId) {
        updateElapsedTimeRunnable = new Runnable() {
            @Override
            public void run() {
                // Get the goal time for this routine
                String goalTime = getRoutineGoalTime(routineId);
                // Get the elapsed minutes for this routine (replace with actual logic)
                int elapsedMinutes = getRoutine(routineId).getTotalTimeElapsed();
                String goalTimeText = elapsedMinutes + " out of " + goalTime + " minutes elapsed";
                // Update the LiveData instead of directly updating a view
                elapsedTimeText.setValue(goalTimeText);

                // Schedule the next update after 1 second (1000ms)
                handler.postDelayed(this, 1000);
            }
        };
        // Start the periodic update
        handler.post(updateElapsedTimeRunnable);
    }

    public void addTask(Task task, int routineId){
        getRoutine(routineId).addTask(task);
        mTaskRepository.save(task);
    }

    public Task getTask(int taskId) {
        return mTaskRepository.find(taskId).getValue();
    }

    public void renameTask(int taskId, String taskName) {
        mTaskRepository.renameTask(taskId, taskName);
    }

    public void removeTask(int taskId, int routineId) {
        getRoutine(routineId).removeTask(taskId);
        mTaskRepository.removeTask(taskId);
    }

    public void addRoutine(String name){
        List<Routine> routines = getOrderedRoutines().getValue();
        routines.add(new Routine(name, 6969, new ElapsedTime()));
        orderedRoutines.setValue(routines);
    }

    public String getRoutineGoalTime(int routineId) {
        return getRoutine(routineId).getGoalTime();
    }

    public void setRoutineGoalTime(int routineId, int minutes) {
        orderedRoutines.getValue().get(routineId).setGoalTime(minutes);
    }
}
