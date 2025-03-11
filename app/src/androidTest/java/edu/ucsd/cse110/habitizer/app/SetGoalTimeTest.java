package edu.ucsd.cse110.habitizer.app;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasToString;

@RunWith(AndroidJUnit4.class)
public class SetGoalTimeTest {

    @Before
    public void setUp() {
        ActivityScenario.launch(MainActivity.class);
    }

    @Test
    public void testSetGoalTime() {
        for(int i = 0; i < 2; ++i){
            onData(anything())
                    .inAdapterView(withId(R.id.routine_list))
                    .atPosition(i)
                    .onChildView(withId(R.id.routine_edit_btn))
                    .perform(click());
            onView(withId(R.id.goalTimeEditText)).perform(typeText("666"), pressImeActionButton());

            onView(withId(R.id.routine_elapsed_time)).check(matches(withText("Goal time: 666 minutes")));

            onView(withId(R.id.back_button)).perform(click());

            onData(anything())
                    .inAdapterView(withId(R.id.routine_list))
                    .atPosition(i)
                    .onChildView(withId(R.id.goal_time))
                    .check(matches(withText("Goal time: 666 minutes")));

            onData(anything())
                    .inAdapterView(withId(R.id.routine_list))
                    .atPosition(i)
                    .onChildView(withId(R.id.routine_start_btn))
                    .perform(click());
            onView(withId(R.id.routine_elapsed_time)).
                    check(matches(withText(containsString("out of 666 minutes elapsed"))));

            onView(withId(R.id.back_button)).perform(click());

        }
    }

}
