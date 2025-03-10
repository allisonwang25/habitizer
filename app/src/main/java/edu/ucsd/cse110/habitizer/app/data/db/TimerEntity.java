package edu.ucsd.cse110.habitizer.app.data.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import edu.ucsd.cse110.habitizer.lib.util.Timer;

@Entity(tableName = "Timer")
public class TimerEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "tid")
    public Integer tid = null;

    @ColumnInfo(name = "rid")
    public Integer rid = null;

    // TODO: Add more timer attributes


    TimerEntity(@NonNull Integer tid, @NonNull Integer rid) {
        this.tid = tid;
        this.rid = rid;

        //TODO: Update constructor to include more timer attributes
    }

    public static TimerEntity fromTimer(@NonNull Timer timer, @NonNull Integer rid, @NonNull Integer tid) {
        var timer = new TimerEntity(tid, rid, );

        return timer;
    }

    public @NonNull Timer toTimer() {
//        return new Timer();
        // TODO: should return a timer based off of the timer attributes
        return null;
    }
}
