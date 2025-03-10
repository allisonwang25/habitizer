package edu.ucsd.cse110.habitizer.app;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

import static org.hamcrest.Matchers.anything;

@RunWith(AndroidJUnit4.class)
public class AddTaskTest {

    @Before
    public void setUp() {
        ActivityScenario.launch(MainActivity.class);
    }

    @Test
    public void testEditClickOpensEditFragment() {
        onView(withId(R.id.routine_list_view)).check(matches(isDisplayed()));

        onData(anything())
                .inAdapterView(withId(R.id.routine))
                .atPosition(0)                  // Morning Routine List?
                .onChildView(withId(R.id.routine_edit_btn))
                .perform(click());

        onView(withId(R.id.fragment_edit_routine)).check(matches(isDisplayed()));
    }
}
