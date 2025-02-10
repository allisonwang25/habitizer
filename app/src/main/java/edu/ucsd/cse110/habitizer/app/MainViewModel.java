package edu.ucsd.cse110.secards.app;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

//    public static final ViewModelInitializer<MainViewModel> initializer =
//            new ViewModelInitializer<>(
//                    MainViewModel.class,
//                    creationExtras -> {
//                        var app = (HabitizerApplication) creationExtras.get(APPLICATION_KEY);
//                        assert app != null;
//                        return new MainViewModel(app.getFlashcardRepository());
//                    });

    public MainViewModel(RoutineRepository r, TaskRepository m, TaskRepository e) {
        this.routineRepository = r;
        this.mTaskRepository = m;
        this.eTaskRepository = e;

        // Create the observable subjects.
        mTaskOrdering = new PlainMutableSubject<>();
        eTaskOrdering = new PlainMutableSubject<>();

        // Initialize...

        // When the list of tasks changes (or is first loaded), reset the ordering.
        mTaskRepository.findAll().observe(tasks -> {
            if (tasks == null) return; // not ready yet, ignore

            var ordering = new ArrayList<Integer>();
            for (int i = 0; i < tasks.size(); i++) {
                ordering.add(i);
            }
            mTaskOrdering.setValue(ordering);
        });

        eTaskRepository.findAll().observe(tasks -> {
            if (tasks == null) return; // not ready yet, ignore

            var ordering = new ArrayList<Integer>();
            for (int i = 0; i < tasks.size(); i++) {
                ordering.add(i);
            }
            eTaskOrdering.setValue(ordering);
        });
    }
}
