package edu.ucsd.cse110.habitizer.lib.util;

public interface Timer {
    int getTaskTimeElapsed();
    int getTotalTimeElapsed();
    int getCurrentlyElapsedTime();

    void stopTimer();

    void advanceTime();
}
