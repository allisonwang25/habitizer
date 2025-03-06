package edu.ucsd.cse110.habitizer.app.util.routine;

import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.util.Log;

import edu.ucsd.cse110.habitizer.app.databinding.FragmentDialogNewTaskBinding;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import edu.ucsd.cse110.habitizer.app.MainViewModel;
import edu.ucsd.cse110.habitizer.lib.domain.Task;
import edu.ucsd.cse110.habitizer.lib.util.ElapsedTime;

public class NewTaskDialogFragment extends DialogFragment {
    private FragmentDialogNewTaskBinding view;
    private static int routineId;
    private MainViewModel activityModel;
    public NewTaskDialogFragment() {}
    public static NewTaskDialogFragment newInstance(int r) {
        var fragment = new NewTaskDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        routineId = r;
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        this.view = FragmentDialogNewTaskBinding.inflate(getLayoutInflater());
        return new AlertDialog.Builder(getActivity())
                .setTitle("New Task")
                .setMessage("Please write your new task.")
                .setView(view.getRoot())
                .setPositiveButton("Create", this::onPositiveButtonClick)
                .setNegativeButton("Cancel", this::onNegativeButtonClick)
                .create();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        var modelOwner = requireActivity();
        var modelFactory = ViewModelProvider.Factory.from(MainViewModel.initializer);
        var modelProvider = new ViewModelProvider(modelOwner, modelFactory);
        this.activityModel = modelProvider.get(MainViewModel.class);
    }
    private void onPositiveButtonClick(DialogInterface dialog, int which) {
        var taskName = view.taskNameInput.getText().toString();

        if (taskName.trim().isEmpty()) {
            view.taskNameInput.setError("Task name cannot be empty");
            return; // TODO: BUG: dialog should not be dismissed if task name is empty
        }
        Task task = new Task(taskName, new ElapsedTime(), routineId);
//        activityModel.getOrderedRoutines().getValue().get(0).addTask(task); // TODO: refactor to pass in current routine ID
        activityModel.addTask(task, routineId);
        dialog.dismiss();
    }

    private void onNegativeButtonClick(DialogInterface dialog, int which) {
        dialog.cancel();
    }
}