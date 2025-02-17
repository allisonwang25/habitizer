package edu.ucsd.cse110.habitizer.app.util.routine_list;

import static edu.ucsd.cse110.habitizer.app.util.fragments.ROUTINE_ACTIVE;
import static edu.ucsd.cse110.habitizer.app.util.fragments.ROUTINE_EDIT;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import edu.ucsd.cse110.habitizer.app.MainActivity;
import edu.ucsd.cse110.habitizer.app.R;
import edu.ucsd.cse110.habitizer.app.databinding.RoutineCardBinding;
import edu.ucsd.cse110.habitizer.app.util.routine.RoutineFragment;
import edu.ucsd.cse110.habitizer.lib.domain.Routine;

public class RoutineListAdapter extends ArrayAdapter<Routine> {

    public RoutineListAdapter(Context context, List<Routine> routineList){
        super(context, 0, new ArrayList<>(routineList));
    }

    @NonNull
    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        var routine = getItem(i);
        assert routine != null;

        RoutineCardBinding binding;
        if (convertView != null) {
            binding = RoutineCardBinding.bind(convertView);
        } else {
            var layoutInflater = LayoutInflater.from(getContext());
            binding = RoutineCardBinding.inflate(layoutInflater, viewGroup, false);
        }

        binding.routineTitle.setText(routine.getName());
        binding.routineTime.setText(routine.getGoalTime());

        binding.routineStartBtn.setOnClickListener(v -> {
            if (getContext() instanceof MainActivity) {
                MainActivity mainActivity = (MainActivity) getContext();
                mainActivity.setActiveFragment(ROUTINE_ACTIVE);
            }
        });

        // TODO: Change Edit Button to initialize correct RoutineEditFragment
        binding.routineEditBtn.setOnClickListener(v -> {
            if (getContext() instanceof MainActivity) {
                MainActivity mainActivity = (MainActivity) getContext();
                mainActivity.setActiveFragment(ROUTINE_EDIT);
            }
        });

        return binding.getRoot();
    }

    @Override
    public long getItemId(int i) {
        var routine = getItem(i);

        var id = routine.getId();
        return id;
    }

}
