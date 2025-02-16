package edu.ucsd.cse110.habitizer.app;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.ucsd.cse110.habitizer.app.HabitizerApplication;
import edu.ucsd.cse110.habitizer.lib.domain.Routine;
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
                return new MainViewModel(app.getRoutineRepository(), app.getMTaskRepository(),app.getETaskRepository());
            });

    public MainViewModel(RoutineRepository r, TaskRepository m, TaskRepository e) {
        this.routineRepository = r;
        this.mTaskRepository = m;
        this.eTaskRepository = e;

        // Create the observable subjects.
        mTaskOrdering = new PlainMutableSubject<>();
        eTaskOrdering = new PlainMutableSubject<>();
        RoutineOrdering = new PlainMutableSubject<>();

        orderedMTasks = new PlainMutableSubject<>();
        orderedRoutines = new PlainMutableSubject<>();


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

        routineRepository.findAll().observe(routines -> {
           if (routines == null) return;

           var ordering = new ArrayList<Integer>();
            for (int i = 0; i < routines.size(); i++) {
                ordering.add(i);
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

    public PlainMutableSubject<List<Task>> getOrderedTasks() { return orderedMTasks; }
    public PlainMutableSubject<List<Routine>> getOrderedRoutines() { return orderedRoutines; }

}
