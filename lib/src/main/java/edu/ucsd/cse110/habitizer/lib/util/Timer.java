package edu.ucsd.cse110.habitizer.lib.util;

public interface Timer {
    int getTaskTimeElapsed();
    int getTotalTimeElapsed();

    int getCurrentlyElapsedTime();
    int getCurrTaskTimeElapsed();

    void pauseTime();
    void resumeTime();

    void stopTimer();
    void startTime();

    void endTime();

    void advanceTime();
}
