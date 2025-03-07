package edu.ucsd.cse110.habitizer.lib.util;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ElapsedTime implements Timer{
    LocalDateTime startTime; // the time when we start the routine
    LocalDateTime prevTaskFinishTime; // the time the most recent task was completed
    LocalDateTime endTime;
    int taskSecondsElapsed; // used for TASK paused time
    int prevSecondsElapsed; // used for ROUTINE paused time
    boolean stopped;
    boolean paused;
    public ElapsedTime(){
        this.startTime = LocalDateTime.now();
        this.prevTaskFinishTime = LocalDateTime.now();
        this.stopped = false;
        this.paused = false;
        this.prevSecondsElapsed = 0;
        this.taskSecondsElapsed = 0;
    }

    public void pauseTime() {
        this.paused = true;
        this.prevSecondsElapsed += (int) ChronoUnit.SECONDS.between(this.startTime, LocalDateTime.now());
        this.taskSecondsElapsed += (int) ChronoUnit.SECONDS.between(this.prevTaskFinishTime, LocalDateTime.now());
    }

    public void resumeTime() {
        this.paused = false;
        this.startTime = LocalDateTime.now();
        this.prevTaskFinishTime = LocalDateTime.now();
    }

    // called when a task is completed
    @Override
    public int getTaskTimeElapsed(){
        if (stopped){
            return calcStoppedTaskTime();
        }

        int timeElapsed = (int) ChronoUnit.SECONDS.between(this.prevTaskFinishTime, LocalDateTime.now()) + this.taskSecondsElapsed;

        if (timeElapsed < 60) {
            return timeElapsed; // TODO: refactor to differentiate seconds from minutes
        }

        int timeElapsedRounded = (int) Math.ceil(timeElapsed / 60.0);

        this.prevTaskFinishTime = LocalDateTime.now(); // update time the most recent task was completed
        this.taskSecondsElapsed = 0; // reset task time
        return timeElapsedRounded;
    }

    // called frequently to get routine time
    @Override
    public int getTotalTimeElapsed(){
        if (stopped){
            return calcStoppedRoutineTime();
        }
        int timeElapsed = (int) ChronoUnit.SECONDS.between(this.startTime, LocalDateTime.now()) + this.prevSecondsElapsed;
        return (int) Math.ceil(timeElapsed / 60.0);
    }

    @Override
    public void stopTimer(){
        this.stopped = true;
        this.endTime = LocalDateTime.now();
    }

    // calculates the time that the last task took
    public int calcStoppedTaskTime(){
        if (stopped){
            int timeElapsed = (int) ChronoUnit.SECONDS.between(this.prevTaskFinishTime, endTime) + this.taskSecondsElapsed;
            this.prevTaskFinishTime = endTime;

            return (int) Math.ceil(timeElapsed / 60.0);
        }
        return -1;
    }

    // calculates the time that the routine took
    public int calcStoppedRoutineTime(){
        if (stopped){
            int timeElapsed = (int) ChronoUnit.SECONDS.between(this.startTime, endTime) + this.prevSecondsElapsed;
            return (int) Math.ceil(timeElapsed / 60.0);
        }
        return -1;
    }

    @Override
    public void advanceTime(){
        if (!stopped) return;

        this.endTime = this.endTime.plusSeconds(30);
    }

    // for routine
    @Override
    public int getCurrentlyElapsedTime(){
        if (stopped){
            return calcStoppedRoutineTime();
        }
        return (int) ChronoUnit.MINUTES.between(this.startTime, LocalDateTime.now()) + this.prevSecondsElapsed;
    }
}