package edu.ucsd.cse110.habitizer.app;

import static android.view.KeyEvent.KEYCODE_ENTER;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.IdlingPolicies;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import androidx.test.espresso.IdlingRegistry;

import static org.hamcrest.Matchers.anything;
import static org.hamcrest.CoreMatchers.containsString;

import android.widget.ListView;

import java.util.concurrent.TimeUnit;

import edu.ucsd.cse110.habitizer.app.util.routine.NewTaskDialogFragment;

@RunWith(AndroidJUnit4.class)
public class ScenarioBasedTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void BDDScenario1() {
        // Given that I am on the Routines screen
        onView(withId(R.id.routine_list_view)).check(matches(isDisplayed()));

        // When I tap a routine to start it
        onData(anything())
                .inAdapterView(withId(R.id.routine_list))
                .atPosition(0)                  // The morning routine
                .onChildView(withId(R.id.routine_start_btn))
                .perform(click());

        // Then I'm shown the routine's Routine screen and the the routine's elapsed time displays "0m"
        onView(withId(R.id.routine)).check(matches(isDisplayed()));
        onView(withId(R.id.routine_elapsed_time)).check(matches(withText(containsString("0 out of"))));

        // When I wait 35 seconds (since I started the routine)
        IdlingPolicies.setMasterPolicyTimeout(70, TimeUnit.SECONDS);
        IdlingPolicies.setIdlingResourceTimeout(70, TimeUnit.SECONDS);
        ElapsedTimeIdlingResource idlingResource = new ElapsedTimeIdlingResource(35000);
        IdlingRegistry.getInstance().register(idlingResource);

        // Then the routine's elapsed time (still) displays "0m"
        onView(withId(R.id.routine_elapsed_time)).check(matches(withText(containsString("0 out of"))));

        // When I wait 25 more seconds (60 total seconds have elapsed)
        ElapsedTimeIdlingResource idlingResource2 = new ElapsedTimeIdlingResource(25000);
        IdlingRegistry.getInstance().register(idlingResource2);

        // Then the routine's elapsed time displays "1m"
        onView(withId(R.id.routine_elapsed_time)).check(matches(withText(containsString("1 out of"))));

        IdlingRegistry.getInstance().unregister(idlingResource);
        IdlingRegistry.getInstance().unregister(idlingResource2);
    }

    @Test
    public void BDDScenario2(){
        // Given that I am on the Routines screen
        onView(withId(R.id.routine_list_view)).check(matches(isDisplayed()));

        // When I tap a routine to start it
        onData(anything())
                .inAdapterView(withId(R.id.routine_list))
                .atPosition(0)                  // The morning routine
                .onChildView(withId(R.id.routine_start_btn))
                .perform(click());

        // Then I'm shown the routine's Routine screen and the the routine's elapsed time displays "0m"
        onView(withId(R.id.routine)).check(matches(isDisplayed()));
        onView(withId(R.id.routine_elapsed_time)).check(matches(withText(containsString("0 out of"))));

        // When I tap the Stop button (Story 3b)
        onView(withId(R.id.stop_timer_button)).perform(click());

        // Then the Stop button turns into an Advance button (and internally the routine timer has been stopped)
        onView(withId(R.id.advance_time_button)).check(matches(ViewMatchers.isDisplayed()));

        // When I tap the Advance button 2 times (i.e., advance time 30 seconds)
        onView(withId(R.id.advance_time_button)).perform(click());
        onView(withId(R.id.advance_time_button)).perform(click());

        // Then the routine's elapsed time (still) displays "0m"
        onView(withId(R.id.routine_elapsed_time)).check(matches(withText(containsString("0 out of"))));

        // When I tap the Advance button 2 more times (i.e., 60 seconds total)
        onView(withId(R.id.advance_time_button)).perform(click());
        onView(withId(R.id.advance_time_button)).perform(click());

        // Then the routine's elapsed time displays "1m"
        onView(withId(R.id.routine_elapsed_time)).check(matches(withText(containsString("1m"))));
    }
}