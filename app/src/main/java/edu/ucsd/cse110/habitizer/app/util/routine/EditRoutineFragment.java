package edu.ucsd.cse110.habitizer.app.util.routine;

import static edu.ucsd.cse110.habitizer.app.util.fragments.ROUTINE_LIST;

import android.os.Bundle;
import android.os.Handler;
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
import java.util.stream.Collectors;

import edu.ucsd.cse110.habitizer.app.MainActivity;
import edu.ucsd.cse110.habitizer.app.MainViewModel;
import edu.ucsd.cse110.habitizer.app.databinding.FragmentEditRoutineBinding;
import edu.ucsd.cse110.habitizer.lib.domain.Routine;
import edu.ucsd.cse110.habitizer.lib.domain.Task;


public class EditRoutineFragment extends Fragment {

    private MainViewModel activityModel;
    private @NonNull FragmentEditRoutineBinding view;

    private EditRoutineAdapter adapter;
    private int routineId;
    private static final String ROUTINE_ARGS = "ROUTINE_ID";

    public EditRoutineFragment() {
        // Required empty public constructor
    }

    public static EditRoutineFragment newInstance(int routineId) {
        EditRoutineFragment fragment = new EditRoutineFragment();
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

        this.adapter = new EditRoutineAdapter(requireActivity(), List.of(), id -> {
            var dialogFragment = new RenameTaskDialogFragment().newInstance(id);
            dialogFragment.show(getParentFragmentManager(), "RenameTaskDialogFragment");
        }, id -> {
            var dialogFragment = new DeleteTaskDialogFragment().newInstance(id, routineId);
            dialogFragment.show(getParentFragmentManager(), "DeleteTaskDialogFragment");
        }, id -> {
            activityModel.moveTaskUp(routineId, id);
        }, id -> {
            activityModel.moveTaskDown(routineId, id);
        });


        activityModel.getOrderedTasks().observe(tasks -> {
            if (tasks == null) return;
            adapter.clear();
//            adapter.addAll(activityModel.getOrderedRoutines().getValue().get(routineId).getTasks(
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
        this.view = FragmentEditRoutineBinding.inflate(inflater, container, false);
        view.routine.setAdapter(adapter);

        view.routineTitle.setText(activityModel.getRoutineName(routineId));
        String goalTime = activityModel.getRoutineGoalTime(routineId);
        if(!goalTime.equals("-")) {
            view.routineElapsedTime.setText(String.format("Goal time: " + goalTime + " minutes"));
        }
        view.addTaskButton.setOnClickListener(v -> {
            var dialogFragment = NewTaskDialogFragment.newInstance(routineId);
            dialogFragment.show(getParentFragmentManager(), "newTaskDialog");
        });

        // Handle when user enters a new goal time
        view.goalTimeEditText.setOnEditorActionListener((v, actionId, event) -> {
            String input = view.goalTimeEditText.getText().toString();
            if (!input.isEmpty()) {
                int newGoalTime = Integer.parseInt(input);
                activityModel.setRoutineGoalTime(routineId, newGoalTime); // Update ViewModel
                view.goalTimeEditText.setText(""); // Clear the text field
                view.routineElapsedTime.setText("Goal time: " + newGoalTime + " minutes");
            }
            return false;
        });

        view.backButton.setOnClickListener(v -> {
            if (getContext() instanceof MainActivity) {
                MainActivity mainActivity = (MainActivity) getContext();
                mainActivity.setActiveFragment(ROUTINE_LIST, 6969);
            }
        });

        return view.getRoot();
    }
}