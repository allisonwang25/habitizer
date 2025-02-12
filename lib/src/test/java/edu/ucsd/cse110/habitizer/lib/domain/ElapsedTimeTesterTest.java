package edu.ucsd.cse110.habitizer.lib.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.Before;
import org.junit.Test;

import edu.ucsd.cse110.habitizer.lib.util.ElapsedTimeTester;

public class ElapsedTimeTesterTest {
    ElapsedTimeTester elapsedTimeTester;

    @Before
    public void setUp() {
        elapsedTimeTester = new ElapsedTimeTester();
    }

    @Test
    public void testInitialTimeElapsed() {
        assertThat("Initial task time elapsed should be 0", elapsedTimeTester.getTaskTimeElapsed(), is(0));
        assertThat("Initial total time elapsed should be 0", elapsedTimeTester.getTotalTimeElapsed(), is(0));
    }

    @Test
    public void testAdvanceTimeOnce() {
        elapsedTimeTester.advanceTime();
        assertThat("Task time elapsed after one advance should be 1", elapsedTimeTester.getTaskTimeElapsed(), is(1));
        assertThat("Total time elapsed after one advance should be 1", elapsedTimeTester.getTotalTimeElapsed(), is(1));
    }

    @Test
    public void testAdvanceTimeMultipleTimes() {
        elapsedTimeTester.advanceTime();
        elapsedTimeTester.advanceTime();
        elapsedTimeTester.advanceTime();

        assertThat("Task time elapsed after three advances should be 2", elapsedTimeTester.getTaskTimeElapsed(), is(2));
        assertThat("Total time elapsed after three advances should be 2", elapsedTimeTester.getTotalTimeElapsed(), is(2));
    }
}
