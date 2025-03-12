package edu.ucsd.cse110.habitizer.lib.util;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


public class ElapsedTimeTester implements Timer {
    LocalDateTime prevTaskFinishTime; // the time the most recent task was completed
    LocalDateTime currTime; // our "running timer" that will keep on being imcremented by 30s
    LocalDateTime startTime; // initialized time
    public ElapsedTimeTester(){
        this.prevTaskFinishTime = this.currTime = this.startTime = LocalDateTime.MIN;
    }

    /**
     * Advance the timer by 30 seconds.
     */
    public void advanceTime(){
        this.currTime = this.currTime.plusSeconds(30);
    }

    @Override
    public int getTaskTimeElapsed(){
        int timeElapsed = (int) ChronoUnit.SECONDS.between(this.prevTaskFinishTime, this.currTime);
        int timeElapsedRounded = (int) Math.ceil(timeElapsed / 60.0);

        // update time the most recent task was completed
        this.prevTaskFinishTime = this.currTime;
        return timeElapsedRounded;
    }
    ;

    @Override
    public int getTotalTimeElapsed(){
        int timeElapsed = (int) ChronoUnit.SECONDS.between(this.startTime, this.currTime);
        return (int) Math.ceil(timeElapsed / 60.0);
    };

    @Override
    public void stopTimer(){
        return;
    }


    @Override
    public int getCurrentlyElapsedTime(){
        int timeElapsed = (int) ChronoUnit.SECONDS.between(this.startTime, this.currTime);
        return (int) Math.ceil(timeElapsed / 60.0);
    }

    @Override
    public void pauseTime(){
        return;
    }

    @Override
    public void resumeTime(){
        return;
    }

    @Override
    public int getCurrTaskTimeElapsed() {
        return -1;
    }

    @Override
    public void startTime(){}
    public void endTime(){}
}
