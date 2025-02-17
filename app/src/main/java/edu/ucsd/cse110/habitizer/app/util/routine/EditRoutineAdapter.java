package edu.ucsd.cse110.habitizer.app.util.routine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import edu.ucsd.cse110.habitizer.app.databinding.TaskCardBinding;
import edu.ucsd.cse110.habitizer.app.databinding.TaskCardEditBinding;
import edu.ucsd.cse110.habitizer.lib.data.InMemoryDataSource;
import edu.ucsd.cse110.habitizer.lib.domain.Task;

public class EditRoutineAdapter extends ArrayAdapter<Task> {

    Consumer<Integer> onEditClick;
    public EditRoutineAdapter(Context context, List<Task> routine, Consumer<Integer> onEditClick) {
        super(context, 0, new ArrayList<>(routine));
        this.onEditClick = onEditClick;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        var task = getItem(position);
        assert task != null;

        TaskCardEditBinding binding;
        if(convertView != null) {
            binding = TaskCardEditBinding.bind(convertView);
        } else {
            var layoutInflater = LayoutInflater.from(getContext());
            binding = TaskCardEditBinding.inflate(layoutInflater, parent, false);
        }

        binding.taskTitle.setText(task.getName());

        binding.taskEditButton.setOnClickListener(v -> {
            var id = task.getId();
            onEditClick.accept(id);
        });

        return binding.getRoot();
    }

    @Override
    public long getItemId(int pos) {
        var task = getItem(pos);
        assert task != null;

        var id = task.getId();

//        assert id != null;
//        idk why this don't work bruh
        return id;
    }
}
