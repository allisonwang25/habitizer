package edu.ucsd.cse110.habitizer.lib.domain;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import edu.ucsd.cse110.habitizer.lib.util.RoutineType;
import edu.ucsd.cse110.habitizer.lib.util.Timer;

public class Routine {
    private List<Task> tasks;
    private final RoutineType routineType;
    private String name;
    private boolean completed;
    // The routine's goal time in minutes (a negative value indicates none is set).
    private int goalTimeMinutes;
    private Timer timer;
    private int totalTimeElapsed;

    /**
     * Constructs a new Routine with the given name.
     * Initializes an empty task list, starts the timer, and sets default values.
     *
     * @param name the name of the routine
     * @param routineType type of routine (morning/evening)
     */
    public Routine(String name, RoutineType routineType, Timer timer) {
        this.name = name;
        this.routineType = routineType;
        this.tasks = new ArrayList<>();
        this.completed = false;
        this.goalTimeMinutes = 0;
        this.totalTimeElapsed = 0;
        this.timer = timer;
    }

    /**
     * Adds a task to the routine.
     *
     * @param task the task to add
     */
    public void addTask(Task task) {
        this.tasks.add(task);
    }

    /**
     * Removes a task from the routine.
     *
     * @param task the task to remove
     */
    public void removeTask(Task task) {
        this.tasks.remove(task);
    }

    public List<Task> getTasks() {
        return this.tasks;
    }

    public String getName() {
        return this.name;
    }

    /**
     * Sets the goal time for the routine in minutes.
     *
     * @param minutes the goal time in minutes
     */
    public void setGoalTime(int minutes) {
        this.goalTimeMinutes = minutes;
    }

    public int getGoalTime() {
        return this.goalTimeMinutes;
    }

    /**
     * Ends the routine. This stops further updates to the timer
     * and marks the routine as ended.
     */
    public void completeRoutine() {
        this.completed = true;
        this.totalTimeElapsed = this.timer.getTotalTimeElapsed();
    }

    public boolean isEnded() {
        return this.completed;
    }

    /**
     * Checks off the given task.
     *
     * @param task the task to check off
     */
    public void checkOffTask(Task task) {
        if (tasks.contains(task) && !task.isCheckedOff()) {
            task.checkOff();
        }
    }
}
