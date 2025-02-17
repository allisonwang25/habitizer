package edu.ucsd.cse110.habitizer.app.util.routine;

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

import edu.ucsd.cse110.habitizer.app.MainViewModel;
import edu.ucsd.cse110.habitizer.app.databinding.FragmentRoutineBinding;


public class RoutineFragment extends Fragment {

    private MainViewModel activityModel;
    private FragmentRoutineBinding view;

    private RoutineAdapter adapter;

    public RoutineFragment() {
        // Required empty public constructor
    }

    public static RoutineFragment newInstance() {
        RoutineFragment fragment = new RoutineFragment();
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

    // Initialize the Adapter (with an empty list for now)
        this.adapter = new RoutineAdapter(requireActivity(), List.of(), id -> {
            var dialogFragment = new RenameTaskDialogFragment().newInstance(id);
            dialogFragment.show(getParentFragmentManager(), "RenameTaskDialogFragment");
        });

        activityModel.getOrderedTasks().observe(task -> {
            if (task == null) return;
            adapter.clear();
            adapter.addAll(new ArrayList<>(task));
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
        view.routine.setAdapter(adapter);
        view.addTaskButton.setOnClickListener(v -> {
            var dialogFragment = NewTaskDialogFragment.newInstance();
            dialogFragment.show(getParentFragmentManager(), "newTaskDialog");
        });

        activityModel.getRoutineGoalTime().observe(goalTime -> {
            if (goalTime == null) return;
            String elapsedText = "0 out of " + goalTime + " minutes elapsed";
            // Using view binding to update the TextView:
            this.view.routineElapsedTime.setText(elapsedText);
            if (goalTime.equals("-")) view.goalTimeEditText.setText("");
            else view.goalTimeEditText.setText(goalTime);
        });

        // Handle when user enters a new goal time
        view.goalTimeEditText.setOnEditorActionListener((v, actionId, event) -> {
            String input = view.goalTimeEditText.getText().toString();
            if (!input.isEmpty()) {
                int newGoalTime = Integer.parseInt(input);
                activityModel.setRoutineGoalTime(newGoalTime); // Update ViewModel
                view.goalTimeEditText.setText(""); // Clear the text field
            }
            return false;
        });


        return view.getRoot();
    }
}