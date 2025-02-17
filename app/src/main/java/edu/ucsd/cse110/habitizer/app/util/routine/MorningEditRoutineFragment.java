package edu.ucsd.cse110.habitizer.app.util.routine;

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

import edu.ucsd.cse110.habitizer.app.MainViewModel;
import edu.ucsd.cse110.habitizer.app.databinding.FragmentEditRoutineMorningBinding;
import edu.ucsd.cse110.habitizer.lib.domain.Routine;
import edu.ucsd.cse110.habitizer.lib.domain.Task;


public class MorningEditRoutineFragment extends Fragment {

    private MainViewModel activityModel;
    private FragmentEditRoutineMorningBinding view;

    private EditRoutineAdapter adapter;

    public MorningEditRoutineFragment() {
        // Required empty public constructor
    }

    public static MorningEditRoutineFragment newInstance() {
        MorningEditRoutineFragment fragment = new MorningEditRoutineFragment();
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
            var dialogFrament = new RenameTaskDialogFragment().newInstance(id);
            dialogFrament.show(getParentFragmentManager(), "RenameTaskDialogFragment");
        });

        activityModel.getOrderedRoutines().observe(routines -> {
            Routine routine = routines.get(0);
            List<Task> tasks = routine.getTasks();
            if (tasks == null) return;
            adapter.clear();
            adapter.addAll(new ArrayList<>(tasks));
            adapter.notifyDataSetChanged();
        });

        activityModel.getOrderedTasks().observe(tasks -> {
            Log.d("test", "hi");
            if (tasks == null) return;
            adapter.clear();
            adapter.addAll(activityModel.getOrderedRoutines().getValue().get(0).getTasks());
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
        this.view = FragmentEditRoutineMorningBinding.inflate(inflater, container, false);
        view.routine.setAdapter(adapter);

        view.addTaskButton.setOnClickListener(v -> {
            var dialogFragment = NewTaskDialogFragment.newInstance(0);
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