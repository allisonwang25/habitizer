package  edu.ucsd.cse110.habitizer.app.util.routine_list;


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
import edu.ucsd.cse110.habitizer.app.databinding.FragmentRoutineListBinding;

public class RoutineListFragment extends Fragment {
    private MainViewModel activityModel;
    private FragmentRoutineListBinding view;

    private RoutineListAdapter adapter;

    public RoutineListFragment() {}

    public static RoutineListFragment newInstance() {
        RoutineListFragment fragment = new RoutineListFragment();
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

        this.adapter = new RoutineListAdapter(requireActivity(), List.of());

        activityModel.getOrderedRoutines().observe(routine -> {
            if (routine == null) return;
            adapter.clear();
            adapter.addAll(new ArrayList<>(routine));
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
        this.view = FragmentRoutineListBinding.inflate(inflater, container, false);

        view.routine.setAdapter(adapter);
        return view.getRoot();
    }
}