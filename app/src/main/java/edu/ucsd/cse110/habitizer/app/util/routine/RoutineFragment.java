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
import edu.ucsd.cse110.habitizer.app.R;

import edu.ucsd.cse110.habitizer.app.databinding.FragmentRoutineBinding;
import edu.ucsd.cse110.habitizer.lib.domain.Task;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RoutineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
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

        this.adapter = new RoutineAdapter(requireActivity(), List.of());
        activityModel.getTaskOrdering().observe(task -> {
            if (task == null) return;
            adapter.clear();
            adapter.addAll(new ArrayList<>());
            adapter.notifyDataSetChanged();
        });

        // TODO: Initialize Model
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
        return view.getRoot();
    }
}