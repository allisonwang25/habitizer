package edu.ucsd.cse110.habitizer.app.data.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import edu.ucsd.cse110.habitizer.lib.domain.Task;

@Entity(
    tableName = "Task",
    foreignKeys = @ForeignKey(
        entity = RoutineEntity.class,
        parentColumns = "rid",
        childColumns = "rid",
        onDelete = ForeignKey.CASCADE
    )
)
public class TaskEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "tid")
    public Integer tid = null;

    @ColumnInfo(name = "rid")
    public Integer rid = null;

    @ColumnInfo(name = "isCheckedOff")
    public Boolean isCheckedOff = null;

    TaskEntity(@NonNull Integer tid, @NonNull Integer rid, @NonNull Boolean isCheckedOff) {
        this.tid = tid;
        this.rid = rid;
        this.isCheckedOff = isCheckedOff;
    }

    //  TODO: Constructor depends on setting rid, which doesn't exist in the Task Class as of now
//    public static TaskEntity fromTask(@NonNull Task task) {
//        var task_card = new TaskEntity(task.getId())
//    }

    // TODO: Same deal, incompatible constructors
//    public @NonNull Task toTask() {
////        return new Task(tid, )
//    }

}
