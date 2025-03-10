package edu.ucsd.cse110.habitizer.app.data.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import edu.ucsd.cse110.habitizer.lib.domain.Task;
import edu.ucsd.cse110.habitizer.lib.util.ElapsedTime;

@Entity(tableName = "Task")
public class TaskEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "tid")
    public Integer tid = null;

    @ColumnInfo(name = "rid")
    public Integer rid = null;

    @ColumnInfo(name = "name")
    public String name = null;

    @ColumnInfo(name = "isCheckedOff")
    public Boolean isCheckedOff = null;

    @ColumnInfo(name = "sort_order")
    public Integer sort_order = null;

    TaskEntity(@NonNull Integer tid, @NonNull Integer rid, @NonNull String name, @NonNull Boolean isCheckedOff, @NonNull Integer sort_order) {
        this.tid = tid;
        this.rid = rid;
        this.name = name;
        this.isCheckedOff = isCheckedOff;
        this.sort_order = sort_order;
    }

    //  TODO: Constructor depends on setting rid, which doesn't exist in the Task Class as of now
    public static TaskEntity fromTask(@NonNull Task task) {
        var task_card = new TaskEntity(task.getTid(), task.getRid(), task.getName(), task.isCheckedOff(), task.getSortOrder());
        return task_card;
    }

    // TODO: Same deal, incompatible constructors
    public @NonNull Task toTask() {
        return new Task(name, (new ElapsedTime()), rid, sort_order);
    }

}
