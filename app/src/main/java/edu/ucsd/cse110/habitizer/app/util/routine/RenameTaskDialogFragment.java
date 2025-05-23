package edu.ucsd.cse110.habitizer.app.util.routine;

import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import edu.ucsd.cse110.habitizer.app.MainViewModel;
import edu.ucsd.cse110.habitizer.app.databinding.FragmentDialogRenameTaskBinding;
import edu.ucsd.cse110.habitizer.lib.domain.Task;

public class RenameTaskDialogFragment extends DialogFragment {
    private static final String ARG_TASK_ID = "task_id";
    private FragmentDialogRenameTaskBinding view;
    private MainViewModel activityModel;
    private int taskId;
    public RenameTaskDialogFragment() {
    }
    public static RenameTaskDialogFragment newInstance(int taskId) {
        var fragment = new RenameTaskDialogFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TASK_ID, taskId);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        this.view = FragmentDialogRenameTaskBinding.inflate(getLayoutInflater());
        this.view.editTaskNameInput.setText(activityModel.getTask(taskId).getName());
        return new AlertDialog.Builder(getActivity())
                .setTitle("Edit Your Task")
                .setMessage("Please edit your task name.")
                .setView(view.getRoot())
                .setPositiveButton("Rename", this::onPositiveButtonClick)
                .setNegativeButton("Cancel", this::onNegativeButtonClick)
                .create();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.taskId = requireArguments().getInt(ARG_TASK_ID);

        var modelOwner = requireActivity();
        var modelFactory = ViewModelProvider.Factory.from(MainViewModel.initializer);
        var modelProvider = new ViewModelProvider(modelOwner, modelFactory);
        this.activityModel = modelProvider.get(MainViewModel.class);
    }
    private void onPositiveButtonClick(DialogInterface dialog, int which) {
        var taskName = view.editTaskNameInput.getText().toString();

        if (taskName.trim().isEmpty()) {
            view.editTaskNameInput.setError("Task name cannot be empty");
            return; // TODO: BUG: dialog should not be dismissed if task name is empty
        }

        activityModel.renameTask(taskId, taskName);
        // task is a pointer so should update automatically, but need to push notify the observers?
        dialog.dismiss();
    }

    private void onNegativeButtonClick(DialogInterface dialog, int which) {
        dialog.cancel();
    }
}