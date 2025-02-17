package edu.ucsd.cse110.habitizer.app.util.routine;

import static edu.ucsd.cse110.habitizer.app.util.fragments.ROUTINE_LIST;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import edu.ucsd.cse110.habitizer.app.MainActivity;
import edu.ucsd.cse110.habitizer.app.MainViewModel;
import edu.ucsd.cse110.habitizer.app.databinding.FragmentRoutineBinding;
import edu.ucsd.cse110.habitizer.app.databinding.FragmentRoutineMorningBinding;
import edu.ucsd.cse110.habitizer.lib.domain.Routine;
import edu.ucsd.cse110.habitizer.lib.domain.Task;


public class MorningRoutineFragment extends Fragment {

    private MainViewModel activityModel;
    private FragmentRoutineMorningBinding view;

    private RoutineAdapter adapter;

    public MorningRoutineFragment() {
        // Required empty public constructor
    }

    public static MorningRoutineFragment newInstance() {
        MorningRoutineFragment fragment = new MorningRoutineFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        var modelOwner = requireActivity();
        var modelFactory = ViewModelProvider.Factory.from(MainViewModel.initializer);
        var modelProvider = new ViewModelProvider(modelOwner, modelFactory);
        this.activityModel = modelProvider.get(MainViewModel.class);

        this.adapter = new RoutineAdapter(requireActivity(), List.of(), id -> {
            var dialogFragment = new RenameTaskDialogFragment().newInstance(id);
            dialogFragment.show(getParentFragmentManager(), "RenameTaskDialogFragment");
        });

        activityModel.getOrderedRoutines().observe(routines -> {
            Routine routine = routines.get(0);
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
        this.view = FragmentRoutineMorningBinding.inflate(inflater, container, false);

        view.backButton.setOnClickListener(v -> {
            if (getContext() instanceof MainActivity) {
                MainActivity mainActivity = (MainActivity) getContext();
                mainActivity.setActiveFragment(ROUTINE_LIST);
            }
        });

        view.stopTimerButton.setOnClickListener(v -> {
            activityModel.getOrderedRoutines().getValue().get(0).getTimer().stopTimer();
        });

        view.advanceTimeButton.setOnClickListener(v -> {
            activityModel.getOrderedRoutines().getValue().get(0).getTimer().advanceTime();
        });

        view.routine.setAdapter(adapter);
        return view.getRoot();
    }
}