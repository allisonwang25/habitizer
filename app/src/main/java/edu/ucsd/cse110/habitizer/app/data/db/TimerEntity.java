package edu.ucsd.cse110.habitizer.app.data.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import edu.ucsd.cse110.habitizer.lib.util.ElapsedTime;
import edu.ucsd.cse110.habitizer.lib.util.Timer;

@Entity(tableName = "Timer")
public class TimerEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "tid")
    public Integer tid = null;

    @ColumnInfo(name = "rid")
    public Integer rid = null;

    @ColumnInfo(name = "taskSecondsElapsed")
    public Integer taskSecondsElapsed;

    @ColumnInfo(name = "totalSecondsElapsed")
    public Integer prevSecondsElapsed;

    @ColumnInfo(name = "stopped")
    public boolean stopped;

    @ColumnInfo(name = "paused")
    public boolean paused;

    @ColumnInfo(name = "ended")
    public boolean ended;

    // TODO: Add more timer attributes


    TimerEntity(@NonNull Integer tid, @NonNull Integer rid, @NonNull Integer taskSecondsElapsed, @NonNull Integer prevSecondsElapsed, @NonNull boolean stopped, @NonNull boolean paused, @NonNull boolean ended) {
        this.tid = tid;
        this.rid = rid;
        this.taskSecondsElapsed = taskSecondsElapsed;
        this.prevSecondsElapsed = prevSecondsElapsed;
        this.stopped = stopped;
        this.paused = paused;
        this.ended = ended;

        //TODO: Update constructor to include more timer attributes
    }

    public static TimerEntity fromTimer(@NonNull ElapsedTime t, @NonNull Integer rid, @NonNull Integer tid) {
        var timer = new TimerEntity(tid, rid, t.getTaskTimeElapsed(), t.getPrevSecondsElapsed(), t.isStopped(), t.isPaused(), t.isEnded());
        return timer;
    }

    public @NonNull ElapsedTime toTimer() {
        return new ElapsedTime(this.taskSecondsElapsed, this.prevSecondsElapsed, this.stopped, this.paused);
        // TODO: should return a timer based off of the timer attributes
    }
}
