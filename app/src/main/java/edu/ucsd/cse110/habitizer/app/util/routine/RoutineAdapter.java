package edu.ucsd.cse110.habitizer.app.util.routine;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import edu.ucsd.cse110.habitizer.lib.domain.Task;
import edu.ucsd.cse110.habitizer.app.databinding.TaskCardBinding;
public class RoutineAdapter extends ArrayAdapter<Task> {
    Consumer<Integer>  onFinishClick;
    public RoutineAdapter(Context context, List<Task> tasks, Consumer<Integer> onFinishClick) {
        super(context, 0, new ArrayList<>(tasks));
        this.onFinishClick = onFinishClick;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        var task = getItem(position);
        assert task != null;

        TaskCardBinding binding;
        if(convertView != null) {
            binding = TaskCardBinding.bind(convertView);
        } else {
            var layoutInflater = LayoutInflater.from(getContext());
            binding = TaskCardBinding.inflate(layoutInflater, parent, false);
        }

        binding.taskTitle.setText(task.getName());

        binding.taskCardLayout.setOnClickListener(v -> {
            var id = task.getTid();
            onFinishClick.accept(id);

            // "creating a strikethrough text"
            // https://stackoverflow.com/questions/3881553/is-there-an-easy-way-to-strike-through-text-in-an-app-widget/6739637#6739637
            // 2025 02 12
            // Took the method of setting flags to set strikethrough

            int taskTimeElapsed = task.getTimeElapsed();
            if (taskTimeElapsed <= 55) {
                int roundedTime = (taskTimeElapsed / 5) * 5; // Round to nearest 5 seconds
                binding.elapsedTime.setText(String.format("%d Seconds Elapsed", roundedTime));
            }
            else {
                binding.elapsedTime.setText(String.format(((int)Math.ceil(task.getTimeElapsed() / 60.0)) + " Minutes Elapsed"));
            }
            binding.taskTitle.setPaintFlags(binding.taskTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        });


        return binding.getRoot();
    }

    @Override
    public long getItemId(int pos) {
        var task = getItem(pos);
        assert task != null;

        var id = task.getTid();

//        assert id != null;
//        idk why this don't work bruh
        return id;
    }
}
