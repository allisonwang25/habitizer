package edu.ucsd.cse110.habitizer.app.util.routine;

import static edu.ucsd.cse110.habitizer.app.util.fragments.ROUTINE1_EDIT;
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
import edu.ucsd.cse110.habitizer.app.databinding.FragmentEditRoutineEveningBinding;
import edu.ucsd.cse110.habitizer.lib.domain.Routine;
import edu.ucsd.cse110.habitizer.lib.domain.Task;


public class EveningEditRoutineFragment extends Fragment {

    private MainViewModel activityModel;
    private FragmentEditRoutineEveningBinding view;

    private EditRoutineAdapter adapter;

    public EveningEditRoutineFragment() {
        // Required empty public constructor
    }

    public static EveningEditRoutineFragment newInstance() {
        EveningEditRoutineFragment fragment = new EveningEditRoutineFragment();
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

        this.adapter = new EditRoutineAdapter(requireActivity(), List.of(), id -> {
            var dialogFragment = new RenameTaskDialogFragment().newInstance(id);
            dialogFragment.show(getParentFragmentManager(), "RenameTaskDialogFragment");
        }, id -> {
            var dialogFragment = new DeleteTaskDialogFragment().newInstance(id);
            dialogFragment.show(getParentFragmentManager(), "DeleteTaskDialogFragment");
        });

        activityModel.getOrderedRoutines().observe(routines -> {
            Routine routine = routines.get(1);
            List<Task> tasks = routine.getTasks();
            if (tasks == null) return;
            adapter.clear();
            adapter.addAll(new ArrayList<>(tasks));
            adapter.notifyDataSetChanged();
        });

        activityModel.getOrderedTasks().observe(tasks -> {
            if (tasks == null) return;
            adapter.clear();
            adapter.addAll(activityModel.getOrderedRoutines().getValue().get(1).getTasks());
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
        this.view = FragmentEditRoutineEveningBinding.inflate(inflater, container, false);

        view.addTaskButton.setOnClickListener(v -> {
            var dialogFragment = NewTaskDialogFragment.newInstance(1);
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

        // Handle when user enters a new goal time
        view.goalTimeEditText.setOnEditorActionListener((v, actionId, event) -> {
            String input = view.goalTimeEditText.getText().toString();
            if (!input.isEmpty()) {
                int newGoalTime = Integer.parseInt(input);
                activityModel.setRoutineGoalTime(newGoalTime); // Update ViewModel
            }
            return false;
        });

        view.backButton.setOnClickListener(v -> {
            if (getContext() instanceof MainActivity) {
                MainActivity mainActivity = (MainActivity) getContext();
                mainActivity.setActiveFragment(ROUTINE_LIST);
            }
        });

        view.routine.setAdapter(adapter);
        return view.getRoot();
    }
}