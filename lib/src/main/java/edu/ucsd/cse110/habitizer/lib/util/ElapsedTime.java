package edu.ucsd.cse110.habitizer.lib.util;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ElapsedTime implements Timer{
    LocalDateTime startTime; // the time when we start the routine
    LocalDateTime prevTaskFinishTime; // the time the most recent task was completed
    LocalDateTime endTime;
    int rid;
    int taskSecondsElapsed; // used for TASK paused time
    int prevSecondsElapsed; // used for ROUTINE paused time
    private boolean started = false;
    boolean stopped;
    boolean paused;
    boolean ended;
    public ElapsedTime(){
        this.rid = -1;
        this.startTime = LocalDateTime.now();
        this.prevTaskFinishTime = LocalDateTime.now();
        this.stopped = false;
        this.paused = false;
        this.prevSecondsElapsed = 0;
        this.taskSecondsElapsed = 0;
    }

    // Constructor for TimerEntity
    public ElapsedTime(LocalDateTime startTime, int taskSecondsElapsed, int prevSecondsElapsed, boolean stopped, boolean paused){
        // TODO: What should these be initialized to?
        this.startTime = startTime;
        this.prevTaskFinishTime = startTime;
        this.taskSecondsElapsed = taskSecondsElapsed;
        this.prevSecondsElapsed = prevSecondsElapsed;
        this.stopped = stopped;
        this.paused = paused;
    }

    public LocalDateTime getStartTime(){
        return this.startTime;
    }

    private LocalDateTime pauseStartTime; // Stores the time when pause starts

    public void pauseTime() {
        if (!paused) {
            this.paused = true;
            this.pauseStartTime = LocalDateTime.now(); // Capture when we paused
        }
    }

    public void resumeTime() {
        if (paused) {
            LocalDateTime resumeTime = LocalDateTime.now();
            int pauseDuration = (int) ChronoUnit.SECONDS.between(this.pauseStartTime, resumeTime);

            // Adjust times to compensate for the pause
            this.startTime = this.startTime.plusSeconds(pauseDuration);
            this.prevTaskFinishTime = this.prevTaskFinishTime.plusSeconds(pauseDuration);

            this.paused = false;
            this.pauseStartTime = null; // Reset pause tracking
        }
    }



    public ElapsedTime setRID(int rid){
        this.rid = rid;
        return this;
    }

    public int getRid(){
        return this.rid;
    }

    // called when a task is completed returns IN SECONDS
    @Override
    public int getTaskTimeElapsed(){
        if (stopped){
            return calcStoppedTaskTime();
        }

        int timeElapsed = (int) ChronoUnit.SECONDS.between(this.prevTaskFinishTime, LocalDateTime.now()) + this.taskSecondsElapsed;

        this.prevTaskFinishTime = LocalDateTime.now(); // update time the most recent task was completed
        this.taskSecondsElapsed = 0; // reset task time
        return timeElapsed;
    }

    public int getPrevSecondsElapsed(){
        return this.prevSecondsElapsed;
    }

    public int getCurrTaskTimeElapsed() {
        if (stopped) {
            return calcStoppedTaskTime();
        }

        return (int) ChronoUnit.SECONDS.between(this.prevTaskFinishTime, LocalDateTime.now());
    }

    // called frequently to get routine time
    @Override
    public int getTotalTimeElapsed(){
        if (stopped){
            return calcStoppedRoutineTime() * 60;
        }
        int timeElapsed = (int) ChronoUnit.SECONDS.between(this.startTime, LocalDateTime.now()) + this.prevSecondsElapsed;
        return (int) Math.ceil(timeElapsed / 60.0);
    }

    @Override
    public void stopTimer(){
        this.stopped = true;
        this.endTime = LocalDateTime.now();
    }

    public void endTime(){
        System.out.println("made it to endtime in elapsed time");
        this.ended = true;
        if(!stopped)
            this.endTime = LocalDateTime.now();
        stopped = true;
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
            System.out.println("aloha " + timeElapsed);
            if(ended)
                return (int) Math.ceil(timeElapsed / 60.0);
            return (int) Math.floor(timeElapsed / 60.0);
        }
        return -1;
    }

    @Override
    public void advanceTime(){
        if (!stopped || ended) return;

        this.endTime = this.endTime.plusSeconds(30);
    }

    // for routine
    @Override
    public int getCurrentlyElapsedTime(){
        if (stopped){
            System.out.println("stopped");;
            return calcStoppedRoutineTime();
        }
        System.out.println("YURRR" + this.prevSecondsElapsed);
        return (int) ChronoUnit.MINUTES.between(this.startTime, LocalDateTime.now());
    }


    @Override
    public void startTime() {
        if(started && ended) {
            this.startTime = LocalDateTime.now();
            this.prevTaskFinishTime = startTime;
            this.stopped = false;
            this.paused = false;
            this.ended = false;
            this.prevSecondsElapsed = 0;
            this.taskSecondsElapsed = 0;
        }
        else if(started) return;
        this.startTime = LocalDateTime.now();
        this.prevTaskFinishTime = startTime;
        started = true;
    }

    public boolean isStopped(){
        return stopped;
    }

    public boolean isEnded() {
        return ended;
    }

    public boolean isPaused() {
        return paused;
    }

    public boolean getPaused() {
        return this.paused;
    }
}