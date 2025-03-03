package edu.ucsd.cse110.habitizer.app.util.routine;

import static edu.ucsd.cse110.habitizer.app.util.fragments.ROUTINE_LIST;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

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
            var dialogFragment = new RenameTaskDialogFragment().newInstance(id);
            dialogFragment.show(getParentFragmentManager(), "RenameTaskDialogFragment");
        });

        activityModel.getOrderedRoutines().observe(routines -> {
            Routine routine = routines.get(routineId);
            List<Task> tasks = routine.getTasks();
            if (tasks == null) return;
            adapter.clear();
            adapter.addAll(new ArrayList<>(tasks));
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

        view.routineTitle.setText(activityModel.getOrderedRoutines().getValue().get(routineId).getName());

        view.backButton.setOnClickListener(v -> {
            if (getContext() instanceof MainActivity) {
                MainActivity mainActivity = (MainActivity) getContext();
                mainActivity.setActiveFragment(ROUTINE_LIST, 69420);
            }
        });

        view.stopTimerButton.setOnClickListener(v -> {
            activityModel.getOrderedRoutines().getValue().get(routineId).getTimer().stopTimer();
        });

        view.advanceTimeButton.setOnClickListener(v -> {
            activityModel.getOrderedRoutines().getValue().get(routineId).getTimer().advanceTime();
        });


        view.routine.setAdapter(adapter);

        return view.getRoot();
    }
}