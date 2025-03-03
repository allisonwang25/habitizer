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
import edu.ucsd.cse110.habitizer.app.databinding.FragmentDialogDeleteTaskBinding;

public class DeleteTaskDialogFragment extends DialogFragment {
    private static final String ARG_TASK_ID = "task_id";
    private @NonNull FragmentDialogDeleteTaskBinding view;
    private static int routineId;
    private MainViewModel activityModel;
    private int taskId;
    public DeleteTaskDialogFragment() {
    }
    public static DeleteTaskDialogFragment newInstance(int taskId, int r) {
        routineId = r;
        var fragment = new DeleteTaskDialogFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TASK_ID, taskId);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        this.view = FragmentDialogDeleteTaskBinding.inflate(getLayoutInflater());
        return new AlertDialog.Builder(getActivity())
                .setTitle("Delete Task")
                .setMessage("Are you sure you want to delete this task?")
                .setView(view.getRoot())
                .setPositiveButton("Delete", this::onPositiveButtonClick)
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
        activityModel.removeTask(taskId, routineId);
        dialog.dismiss();
    }

    private void onNegativeButtonClick(DialogInterface dialog, int which) {
        dialog.cancel();
    }
}