package edu.ucsd.cse110.habitizer.app.util.routine;

import static edu.ucsd.cse110.habitizer.app.util.fragments.ROUTINE_LIST;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import edu.ucsd.cse110.habitizer.app.MainActivity;
import edu.ucsd.cse110.habitizer.app.MainViewModel;
import edu.ucsd.cse110.habitizer.app.databinding.FragmentRoutineBinding;
import edu.ucsd.cse110.habitizer.lib.domain.Routine;
import edu.ucsd.cse110.habitizer.lib.domain.Task;


public class RoutineFragment extends Fragment {

    private MainViewModel activityModel;
    private FragmentRoutineBinding view;

    private RoutineAdapter adapter;
    private int routineId;
    private static final String ROUTINE_ARGS = "ROUTINE_ID";

    public RoutineFragment() {
        // Required empty public constructor
    }

    public static RoutineFragment newInstance(int routineId) {
        RoutineFragment fragment = new RoutineFragment();
        Bundle args = new Bundle();
        args.putInt(ROUTINE_ARGS, routineId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            routineId = getArguments().getInt(ROUTINE_ARGS);
        }

        var modelOwner = requireActivity();
        var modelFactory = ViewModelProvider.Factory.from(MainViewModel.initializer);
        var modelProvider = new ViewModelProvider(modelOwner, modelFactory);
        this.activityModel = modelProvider.get(MainViewModel.class);

    // Initialize the Adapter (with an empty list for now)
        this.adapter = new RoutineAdapter(requireActivity(), List.of(), id -> {
           activityModel.checkOffTask(id, routineId);
        });

        activityModel.getOrderedTasks().observe(tasks -> {
            if (tasks == null) return;
            adapter.clear();
            adapter.addAll(tasks
                    .stream()
                    .filter(task -> task.getRid() == routineId)
                    .collect(Collectors.toList()));
            adapter.notifyDataSetChanged();
        });
    }

    @Nullable
    @Override
    public View onCreateView(
        @NonNull LayoutInflater inflater,
        @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState
    ) {
        this.view = FragmentRoutineBinding.inflate(inflater, container, false);

        view.routineTitle.setText(activityModel.getRoutineName(routineId));

        view.backButton.setOnClickListener(v -> {
            if (getContext() instanceof MainActivity) {
                MainActivity mainActivity = (MainActivity) getContext();
                activityModel.stopUpdatingElapsedTime();
                mainActivity.setActiveFragment(ROUTINE_LIST, 69420);
            }
        });

        view.stopTimerButton.setOnClickListener(v -> {
            if (view.stopTimerButton.getText().equals("Stop Time")) {
                activityModel.getTimer(routineId).stopTimer();
                view.stopTimerButton.setText("Advance Time");
            }
            else {
                activityModel.getTimer(routineId).advanceTime();
            }
        });


        view.endRoutineButton.setOnClickListener(v -> {
            activityModel.completeRoutine(routineId);
        });

        view.pauseRoutineButton.setOnClickListener(v -> {
            if (view.pauseRoutineButton.getText().equals("Pause Time")) {
                view.pauseRoutineButton.setText("Resume Time");
                activityModel.getOrderedRoutines().getValue().get(routineId).getTimer().pauseTime();
            } else {
                view.pauseRoutineButton.setText("Pause Time");
                activityModel.getOrderedRoutines().getValue().get(routineId).getTimer().resumeTime();
            }
        });

        activityModel.startUpdatingElapsedTime(routineId);

        activityModel.getRoutineElapsedTimeText().observe(getViewLifecycleOwner(), elapsedText -> {
            view.routineElapsedTime.setText(elapsedText);
        });

        activityModel.getTaskElapsedTimeText().observe(getViewLifecycleOwner(), elapsedText -> {
            view.currTaskElapsedTime.setText(elapsedText);
        });

        view.routine.setAdapter(adapter);
        return view.getRoot();
    }
}