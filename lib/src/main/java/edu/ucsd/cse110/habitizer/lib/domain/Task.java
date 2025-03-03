package edu.ucsd.cse110.habitizer.lib.domain;

import edu.ucsd.cse110.habitizer.lib.util.Timer;
import java.util.Objects;

public class Task {
    private boolean checkedOff;
    private String name;
    private int timeElapsed;
    private final int id;
    private static int idCounter = 0;  // Static counter to assign unique IDs
    private final Timer timer;

    /**
     * Constructs a new Task with the specified name and a TimeProvider.
     * This allows injecting a custom TimeProvider (e.g., a fake in tests).
     *
     * @param name         the name of the task
     * @param timer the TimeProvider to use for time-based operations
     */
    public Task(String name, Timer timer) {
        this.checkedOff = false;
        this.timeElapsed = 0;
        this.name = name;
        this.id = idCounter++;
        this.timer = timer;
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

    public int getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ID: " + this.getId() + " | TITLE: " + this.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void setName(String taskName){
        this.name = taskName;
    }
}
