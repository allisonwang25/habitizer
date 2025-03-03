package edu.ucsd.cse110.habitizer.app.util.routine;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import edu.ucsd.cse110.habitizer.app.MainViewModel;
import edu.ucsd.cse110.habitizer.app.databinding.FragmentDialogNewRoutineBinding;

public class NewRoutineDialogFragment extends DialogFragment {
    private FragmentDialogNewRoutineBinding view;
    private MainViewModel activityModel;
    public NewRoutineDialogFragment() {
    }
    public static NewRoutineDialogFragment newInstance() {
        return new NewRoutineDialogFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        this.view = FragmentDialogNewRoutineBinding.inflate(getLayoutInflater());
        return new AlertDialog.Builder(getActivity())
                .setTitle("Create your routine")
                .setMessage("Please name your new routine.")
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
        var routineName = view.editRoutineNameInput.getText().toString();

        if (routineName.trim().isEmpty()) {
            view.editRoutineNameInput.setError("Routine name cannot be empty");
            return; // TODO: BUG: dialog should not be dismissed if task name is empty
        }

        activityModel.addRoutine(routineName);
        dialog.dismiss();
    }

    private void onNegativeButtonClick(DialogInterface dialog, int which) {
        dialog.cancel();
    }
}