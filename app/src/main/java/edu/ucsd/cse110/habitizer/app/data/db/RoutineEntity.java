package edu.ucsd.cse110.habitizer.app.data.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import edu.ucsd.cse110.habitizer.lib.domain.Routine;
import edu.ucsd.cse110.habitizer.lib.util.ElapsedTime;

@Entity(tableName = "Routine")
public class RoutineEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "rid")
    public Integer rid = null;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "completed")
    public Boolean completed;

    @ColumnInfo(name = "goalTime")
    public String goalTime;

    @ColumnInfo(name = "totalRoutineTimeElapsed")
    public Integer totalRoutineTimeElapsed;

    RoutineEntity(@NonNull Integer rid, @NonNull String name, @NonNull Boolean completed, @NonNull String goatTime, @NonNull Integer totalRoutineTimeElapsed) {
        this.rid = rid;
        this.name = name;
        this.completed = completed;
        this.goalTime = goatTime;
        this.totalRoutineTimeElapsed = totalRoutineTimeElapsed;
    }

    public static RoutineEntity fromRoutine(Routine rtn) {
        var routine = new RoutineEntity(rtn.getId(), rtn.getName(), rtn.getCompleted(), rtn.getGoalTime(), rtn.getTotalTimeElapsed());
        return routine;
    }

    public @NonNull Routine toRoutine() {
        return new Routine(name, new ElapsedTime()).setID(rid);
    }
}