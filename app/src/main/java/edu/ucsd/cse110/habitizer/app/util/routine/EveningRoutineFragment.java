package edu.ucsd.cse110.habitizer.app.util.routine;

import android.os.Bundle;
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
import edu.ucsd.cse110.habitizer.app.databinding.FragmentRoutineEveningBinding;
import edu.ucsd.cse110.habitizer.app.databinding.FragmentRoutineMorningBinding;
import edu.ucsd.cse110.habitizer.lib.domain.Routine;
import edu.ucsd.cse110.habitizer.lib.domain.Task;


public class EveningRoutineFragment extends Fragment {

    private MainViewModel activityModel;
    private FragmentRoutineEveningBinding view;

    private RoutineAdapter adapter;

    public EveningRoutineFragment() {
        // Required empty public constructor
    }

    public static EveningRoutineFragment newInstance() {
        EveningRoutineFragment fragment = new EveningRoutineFragment();
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

        activityModel.getOrderedRoutines().observe(routines -> {
            Routine routine = routines.get(1);
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
        this.view = FragmentRoutineEveningBinding.inflate(inflater, container, false);

        view.routine.setAdapter(adapter);
        return view.getRoot();
    }
}