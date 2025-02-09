package edu.ucsd.cse110.habitizer.lib.util;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ElapsedTime implements Timer{
    LocalDateTime startTime; // the time when we start the routine
    LocalDateTime prevTaskFinishTime; // the time the most recent task was completed
    public ElapsedTime(){
        this.startTime = LocalDateTime.now();
        this.prevTaskFinishTime = this.startTime;
    }

    @Override
    public int getTaskTimeElapsed(){
        int timeElapsed = (int) ChronoUnit.MINUTES.between(this.prevTaskFinishTime, LocalDateTime.now());
        this.prevTaskFinishTime = LocalDateTime.now(); // update time the most recent task was completed
        return timeElapsed;
    }

    @Override
    public int getTotalTimeElapsed(){
        return (int) ChronoUnit.MINUTES.between(this.startTime, LocalDateTime.now());
    }
}