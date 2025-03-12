package edu.ucsd.cse110.habitizer.lib.domain;

import edu.ucsd.cse110.habitizer.lib.util.Timer;
import java.util.Objects;

public class Task implements Comparable<Task>{
    private boolean checkedOff;
    private String name;
    private int timeElapsed;
    private final Integer tid;
    private final int rid;
    private static int idCounter = 0;  // Static counter to assign unique IDs
    private Timer timer;
    private int sortOrder;

    /**
     * Constructs a new Task with the specified name and a TimeProvider.
     * This allows injecting a custom TimeProvider (e.g., a fake in tests).
     *
     * @param name         the name of the task
     * @param timer the TimeProvider to use for time-based operations
     */
    public Task(String name, Integer tid, Timer timer, int rid, int sortOrder) {
        this.checkedOff = false;
        this.timeElapsed = 0;
        this.name = name;
//        this.tid = idCounter++;
        this.tid = tid;
        this.rid = rid;
        this.timer = timer;
        this.sortOrder = sortOrder;
    }

    /**
     * Marks this task as done.
     * Calculate the time taken for the task to be completed.
     */
    public void checkOff() {
        if (this.checkedOff) return;

        this.checkedOff = true;
        this.timeElapsed = this.timer.getTaskTimeElapsed();
    }

    public boolean isCheckedOff() {
        return this.checkedOff;
    }

    public String getName() {
        return this.name;
    }

    public int getTimeElapsed() {
        return this.timeElapsed;
    }

    /**
     * Rename the task
     *
     * @param name the new name for the task.
     */
    public void editTask(String name) {
        this.name = name;
    }

    public Integer getTid() {
        return this.tid;
    }

    public int getRid() {return this.rid;}

    @Override
    public String toString() {
        return "ID: " + this.getTid() + " | TITLE: " + this.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;
        return tid == task.tid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tid);
    }

    public void setName(String taskName){
        this.name = taskName;
    }

    public void setSortOrder(int sortOrder){ this.sortOrder = sortOrder; }

    public void setTimer(Timer timer){
        this.timer = timer;
    }

    public Timer getTimer(){
        return timer;
    }

    public int getSortOrder(){
        return sortOrder;
    }
    public int getRoutineId(){
        return rid;
    }

    public int compareTo(Task task){
        return this.sortOrder - task.getSortOrder();
    }

    public static void setIdCounter(int tid){
        idCounter = tid;
    }
}
