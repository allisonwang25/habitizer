package edu.ucsd.cse110.habitizer.app;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.ucsd.cse110.habitizer.app.HabitizerApplication;
import edu.ucsd.cse110.habitizer.lib.domain.RoutineRepository;
import edu.ucsd.cse110.habitizer.lib.domain.Task;
import edu.ucsd.cse110.habitizer.lib.domain.TaskRepository;
import edu.ucsd.cse110.observables.PlainMutableSubject;

public class MainViewModel extends ViewModel {
    // Domain state (true "Model" state)
    private final RoutineRepository routineRepository;
    private final TaskRepository mTaskRepository;
    private final TaskRepository eTaskRepository;

    // UI state
    private final PlainMutableSubject<List<Integer>> mTaskOrdering;
    private final PlainMutableSubject<List<Integer>> eTaskOrdering;

    // LIST OF ORDERED TASKS
    private final PlainMutableSubject<List<Task>> orderedMTasks;

    public static final ViewModelInitializer<MainViewModel> initializer =
        new ViewModelInitializer<>(
            MainViewModel.class,
            creationExtras -> {
                var app = (HabitizerApplication) creationExtras.get(APPLICATION_KEY);
                assert app != null;
                return new MainViewModel(app.getRoutineRepository(), app.getMTaskRepository(),app.getETaskRepository());
            });

    public MainViewModel(RoutineRepository r, TaskRepository m, TaskRepository e) {
        this.routineRepository = r;
        this.mTaskRepository = m;
        this.eTaskRepository = e;

        // Create the observable subjects.
        mTaskOrdering = new PlainMutableSubject<>();
        eTaskOrdering = new PlainMutableSubject<>();
        orderedMTasks = new PlainMutableSubject<>();


        // When the list of tasks changes (or is first loaded), reset the ordering.
        mTaskRepository.findAll().observe(tasks -> {
            if (tasks == null) return; // not ready yet, ignore

            var ordering = new ArrayList<Integer>();
            for (int i = 0; i < tasks.size(); i++) {
                ordering.add(i);
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

        // This observe might be irrelavant because we dont need to get the top 'task' like the reference
        orderedMTasks.observe(tasks -> {
            if (tasks == null) return;

            var ordering = mTaskOrdering.getValue();

            var task = mTaskRepository.find(ordering.get(0)).getValue();
            if(task == null) return;
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

    public PlainMutableSubject<List<Task>> getOrderedTasks() { return orderedMTasks; }

    public Task getTask(int taskId) {
        return mTaskRepository.find(taskId).getValue();
    }

    public void renameTask(int taskId, String taskName) {
        mTaskRepository.renameTask(taskId, taskName);
    }

}
