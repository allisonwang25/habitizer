package edu.ucsd.cse110.habitizer.lib.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.Before;
import org.junit.Test;

import edu.ucsd.cse110.habitizer.lib.util.ElapsedTimeTester;


public class TaskTest {
    private Task task;
    private ElapsedTimeTester mockTimer;

    @Before
    public void setUp() {
        mockTimer = new ElapsedTimeTester();
        task = new Task("New Test Task", mockTimer, 0);
    }

    @Test
    public void testTaskInitialization() {
        assertThat("Task should start as unchecked", task.isCheckedOff(), is(false));
        assertThat("Task name should be initialized correctly", task.getName(), is("New Test Task"));
        assertThat("Time elapsed should be 0", task.getTimeElapsed(), is(0));
    }

    @Test
    public void testCheckOff() {
        mockTimer.advanceTime();
        task.checkOff();

        assertThat("Task should be checked off", task.isCheckedOff(), is(true));
        assertThat("Task time elapsed should be 1 minute (because it was rounded up)", task.getTimeElapsed(), is(1));
    }

    @Test
    public void testCheckOffMultipleTimes() {
        mockTimer.advanceTime();
        task.checkOff();
        int firstTimeElapsed = task.getTimeElapsed();

        assertThat("Task should be checked off", task.isCheckedOff(), is(true));

        // attempt to advance timer by 2 minutes.
        mockTimer.advanceTime();
        mockTimer.advanceTime();
        mockTimer.advanceTime();
        mockTimer.advanceTime();

        task.checkOff();

        assertThat("Task should still be checked off", task.isCheckedOff(), is(true));
        assertThat("Time elapsed should not change after first check off", task.getTimeElapsed(), is(firstTimeElapsed));
    }

    @Test
    public void testEditTask() {
        task.editTask("Updated Task");
        assertThat("Task name should be updated", task.getName(), is("Updated Task"));
    }

    @Test
    public void testUniqueIds() {
        Task anotherTask = new Task("Another Task", mockTimer, 0);
        assertThat("Tasks should have unique IDs", task.getTid(), is(not(anotherTask.getTid())));
    }
}
