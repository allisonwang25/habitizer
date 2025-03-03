package edu.ucsd.cse110.habitizer.lib.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import edu.ucsd.cse110.habitizer.lib.util.Timer;

public class Routine {
    private List<Task> tasks;
    // 0 for morning routine, 1 for evening routine
    private int id;
    private static int idCnt = 0;
    private String name;
    private boolean completed;
    // The routine's goal time in minutes (a negative value indicates none is set).
    private String goalTimeMinutes;
    private Timer timer;
    private int totalRoutineTimeElapsed;

    /**
     * Constructs a new Routine with the given name.
     * Initializes an empty task list, starts the timer, and sets default values.
     *
     * @param name the name of the routine
     */
    public Routine(String name, Timer timer) {
        this.name = name;
        this.id = idCnt++;
        this.tasks = new ArrayList<>();
        this.completed = false;
        this.goalTimeMinutes = "-";
        this.totalRoutineTimeElapsed = 0;
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

    public void removeTask(int taskId) {
        for (int i = 0; i < this.getTasks().size(); i++) {
            Task cur = this.getTasks().get(i);
            if (cur.getTid() == taskId) {
                this.removeTask(cur);
                return;
            }
        }
    }

    public List<Task> getTasks() {
        return this.tasks;
    }
    public void swapSortOrder(Task task1, Task task2) {
        int temp = task1.getSortOrder();
        task1.setSortOrder(task2.getSortOrder());
        task2.setSortOrder(temp);
    }



    public String getName() {
        return this.name;
    }

    public Timer getTimer() {
        return this.timer;
    }

    /**
     * Sets the goal time for the routine in minutes.
     *
     * @param minutes the goal time in minutes
     */
    public void setGoalTime(int minutes) {
        this.goalTimeMinutes = Integer.toString(minutes);
    }

    public String getGoalTime() {
        return this.goalTimeMinutes;
    }

    /**
     * Ends the routine. This stops further updates to the timer
     * and marks the routine as ended.
     */
    public void completeRoutine() {
        this.completed = true;
        this.totalRoutineTimeElapsed = this.timer.getTotalTimeElapsed();
    }

    public int getTotalTimeElapsed(){
        return this.timer.getCurrentlyElapsedTime();
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
        if (tasks.contains(task)) {
            task.checkOff();
        }
    }

    public int getId() {
        return this.id;
    }
    public Routine setID(int id) {this.id = id; return this;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Routine routine = (Routine) o;
        return id == routine.id && completed == routine.completed && goalTimeMinutes == routine.goalTimeMinutes && totalRoutineTimeElapsed == routine.totalRoutineTimeElapsed && Objects.equals(tasks, routine.tasks) && Objects.equals(name, routine.name) && Objects.equals(timer, routine.timer);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}