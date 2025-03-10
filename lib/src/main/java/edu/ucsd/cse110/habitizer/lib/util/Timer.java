package edu.ucsd.cse110.habitizer.lib.util;

public interface Timer {
    int getTaskTimeElapsed();
    int getTotalTimeElapsed();
    int getCurrentlyElapsedTime();
    int getCurrTaskTimeElapsed();
    void stopTimer();
    void endTime();

    void advanceTime();
    void startTime();
}
