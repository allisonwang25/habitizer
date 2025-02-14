package edu.ucsd.cse110.habitizer.app.util.routine_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.ucsd.cse110.habitizer.app.databinding.RoutineCardBinding;
import edu.ucsd.cse110.habitizer.lib.domain.Routine;

public class RoutineListAdapter extends ArrayAdapter<Routine> {

    public RoutineListAdapter(Context context, List<Routine> routineList){
        super(context, 0, routineList);
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
        return binding.getRoot();
    }

                              @Override
    public int getCount() {
        return 0;
    }


    @Override
    public long getItemId(int i) {
        return 0;
    }


}
