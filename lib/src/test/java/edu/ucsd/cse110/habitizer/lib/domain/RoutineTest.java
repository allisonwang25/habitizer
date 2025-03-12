package edu.ucsd.cse110.habitizer.lib.domain;

import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.Before;
import org.junit.Test;

import edu.ucsd.cse110.habitizer.lib.util.ElapsedTimeTester;

/**
 * Comprehensive tests for the Routine class using Mockito for dependency injection.
 */
public class RoutineTest {

    private ElapsedTimeTester mockTimer;

    @Before
    public void setup() {
        // Create a mock Timer instance so each test gets its time reset to default.
        mockTimer = new ElapsedTimeTester();
    }

    @Test
    public void testAddAndRemoveTasks() {
        Routine routine = new Routine("Billy's Morning Routine", mockTimer).setID(0);
        Task task1 = new Task("Brush Teeth", 0,mockTimer, 0,0);
        Task task2 = new Task("Meditate", 1,mockTimer, 0,1);

        // GIVEN an empty routine.
        assertThat("Routine should start with no tasks", routine.getTasks(), is(empty()));

        // WHEN adding two tasks.
        routine.addTask(task1);
        routine.addTask(task2);
        // THEN the routine should contain both tasks in order of addition.
        assertThat("Routine should contain task1 and task2",
                routine.getTasks(), contains(task1, task2));

        // WHEN removing task1.
        routine.removeTask(task1);
        // THEN the routine should only contain task2.
        assertThat("Routine should contain only task2 after removal of task1",
                routine.getTasks(), contains(task2));
    }

    @Test
    public void testSetAndGetGoalTime() {
        Routine routine = new Routine("Billy's Evening Routine", mockTimer).setID(0);

        // WHEN setting a goal time.
        routine.setGoalTime(30);
        // THEN the goal time should be retrievable.
        assertThat("Goal time should be 30", routine.getGoalTime(), is("30"));
    }

    @Test
    public void testCompleteRoutine() {
        Routine routine = new Routine("Test Routine",  mockTimer).setID(0);

        // GIVEN a routine that is not yet completed.
        assertThat("Routine should not be ended initially", routine.isEnded(), is(false));

        // WHEN time has advanced
        mockTimer.advanceTime();

        // AND we complete our routine.
        routine.completeRoutine();

        // THEN the routine should be marked as ended.
        assertThat("Routine should be ended after completeRoutine is called", routine.isEnded(), is(true));

        // AND getTotalTimeElapsed() was called on the timer and display 1 MINUTE (rounded up from 30 sec).
        assertThat("getTotalTimeElapsed should have been called on the timer", routine.getTotalTimeElapsed(), is(1));
    }

    @Test
    public void testCheckOffTaskWhenTaskExists() {
        Routine routine = new Routine("Routine",  mockTimer).setID(0);
        Task task = new Task("Read", 0, mockTimer, routine.getId(),0);

        // GIVEN a task that has not been checked off.
        assertThat("Task should not be checked off initially", task.isCheckedOff(), is(false));

        // AND the task is part of the routine.
        routine.addTask(task);

        // AND time has elapsed while performing the task
        mockTimer.advanceTime();

        // WHEN checking off the task via the routine.
        routine.checkOffTask(task);

        // THEN the task should be marked as checked off.
        assertThat("Task should be checked off", task.isCheckedOff(), is(true));

        // AND the time elapsed should match what the mocked Timer returns.
        assertThat("Task time elapsed should be as returned by the timer",
                task.getTimeElapsed(), is(1));
    }

    @Test
    public void testCheckOffTaskWhenTaskNotExists() {
        Routine routine = new Routine("Test Routine", mockTimer).setID(0);
        Task task = new Task("Exercise", 0, mockTimer, routine.getId(),0);

        // GIVEN a task that is not part of the routine.
        assertThat("Task should not be checked off initially", task.isCheckedOff(), is(false));

        // WHEN attempting to check off the task via the routine.
        routine.checkOffTask(task);

        // THEN the task should remain unchecked.
        assertThat("Task should remain unchecked if it is not part of the routine",
                task.isCheckedOff(), is(false));
    }

    @Test
    public void testRemoveNonExistentTaskDoesNothing() {
        Routine routine = new Routine("Routine", mockTimer).setID(0);
        Task task1 = new Task("Task 1", 0, mockTimer, routine.getId(),0);
        Task task2 = new Task("Task 2", 1, mockTimer, routine.getId(),0);

        // GIVEN a routine containing only task1.
        routine.addTask(task1);

        // WHEN attempting to remove task2 (which was never added).
        routine.removeTask(task2);

        // THEN the routine should still contain task1.
        assertThat("Routine should still contain task1 after trying to remove a non-existent task",
                routine.getTasks(), contains(task1));
    }

    @Test
    public void testRoutinesNotEqualWithDifferentIds() {
        Routine routine1 = new Routine("Routine", mockTimer).setID(0);
        Routine routine2 = new Routine("Routine", mockTimer).setID(1);

        // THEN routines with different IDs should not be equal.
        assertThat("Routines with different ids should not be equal", routine1.equals(routine2), is(false));
    }
}
