package edu.ucsd.cse110.habitizer.lib.util;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ElapsedTime implements Timer{
    LocalDateTime startTime; // the time when we start the routine
    LocalDateTime prevTaskFinishTime; // the time the most recent task was completed
    LocalDateTime endTime;
    boolean stopped;
    public ElapsedTime(){
        this.startTime = this.prevTaskFinishTime = LocalDateTime.now();
        this.stopped = false;
    }

    @Override
    public int getTaskTimeElapsed(){
        if (stopped){
            return calcStoppedTime();
        }

        int timeElapsed = (int) ChronoUnit.SECONDS.between(this.prevTaskFinishTime, LocalDateTime.now());
        int timeElapsedRounded = (int) Math.ceil(timeElapsed / 60.0);

        this.prevTaskFinishTime = LocalDateTime.now(); // update time the most recent task was completed
        return timeElapsedRounded;
    }

    @Override
    public int getTotalTimeElapsed(){
        if (stopped){
            return calcStoppedTime();
        }
        int timeElapsed = (int) ChronoUnit.SECONDS.between(this.startTime, LocalDateTime.now());
        return (int) Math.ceil(timeElapsed / 60.0);
    }

    @Override
    public void stopTimer(){
        this.stopped = true;
        this.endTime = LocalDateTime.now();
    }

    public int calcStoppedTime(){
        if (stopped){
            int timeElapsed = (int) ChronoUnit.SECONDS.between(this.prevTaskFinishTime, endTime);
            this.prevTaskFinishTime = endTime;

            return (int) Math.ceil(timeElapsed / 60.0);
        }
        return -1;
    }

    @Override
    public void advanceTime(){
        if (!stopped) return;

        this.endTime = this.endTime.plusSeconds(30);
    }
    @Override
    public int getCurrentlyElapsedTime(){
        if (stopped){
            return (int) ChronoUnit.MINUTES.between(this.startTime, this.endTime);
    }
        return (int) ChronoUnit.MINUTES.between(this.startTime, LocalDateTime.now());
    }
}