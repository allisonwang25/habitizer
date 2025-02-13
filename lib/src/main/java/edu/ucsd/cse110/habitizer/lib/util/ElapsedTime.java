package edu.ucsd.cse110.habitizer.lib.util;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ElapsedTime implements Timer{
    LocalDateTime startTime; // the time when we start the routine
    LocalDateTime prevTaskFinishTime; // the time the most recent task was completed
    public ElapsedTime(){
        this.startTime = this.prevTaskFinishTime = LocalDateTime.now();
    }

    @Override
    public int getTaskTimeElapsed(){
        int timeElapsed = (int) ChronoUnit.SECONDS.between(this.prevTaskFinishTime, LocalDateTime.now());
        int timeElapsedRounded = (int) Math.ceil(timeElapsed / 60.0);

        this.prevTaskFinishTime = LocalDateTime.now(); // update time the most recent task was completed
        return timeElapsedRounded;
    }

    @Override
    public int getTotalTimeElapsed(){
        int timeElapsed = (int) ChronoUnit.SECONDS.between(this.startTime, LocalDateTime.now());
        return (int) Math.ceil(timeElapsed / 60.0);
    }
}