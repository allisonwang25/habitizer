package edu.ucsd.cse110.habitizer.app.data.db;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import edu.ucsd.cse110.habitizer.lib.util.ElapsedTime;
import edu.ucsd.cse110.habitizer.lib.util.Timer;

@Entity(tableName = "Timer")
public class TimerEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "rid")
    public Integer rid = null;

    @ColumnInfo(name = "taskSecondsElapsed")
    public Integer taskSecondsElapsed;

    @ColumnInfo(name = "totalSecondsElapsed")
    public Integer prevSecondsElapsed;

    @ColumnInfo(name = "stopped")
    public boolean stopped;

    @ColumnInfo(name = "started")
    public boolean started;

    @ColumnInfo(name = "ended")
    public boolean ended;

    // TODO: Add more timer attributes


    TimerEntity(@NonNull Integer rid, @NonNull Integer taskSecondsElapsed, @NonNull Integer prevSecondsElapsed, @NonNull boolean stopped, @NonNull boolean started, @NonNull boolean ended) {
        this.rid = rid;
        this.taskSecondsElapsed = taskSecondsElapsed;
        this.prevSecondsElapsed = prevSecondsElapsed;
        this.stopped = stopped;
        this.started = started;
        this.ended = ended;

        //TODO: Update constructor to include more timer attributes
    }

    public static TimerEntity fromTimer(@NonNull ElapsedTime t, @NonNull Integer rid) {
        var timer = new TimerEntity(rid, t.getTaskTimeElapsed(), t.getPrevSecondsElapsed(), t.isStopped(), t.isPaused(), t.isEnded());
        Log.d("DEBUG", "fromTimer: " + rid);
        return timer;
    }

    public @NonNull ElapsedTime toTimer() {
        return new ElapsedTime(this.taskSecondsElapsed, this.prevSecondsElapsed, this.started, this.stopped, this.ended).setRID(this.rid);
    }
}
