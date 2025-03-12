package edu.ucsd.cse110.habitizer.app;

import static android.view.KeyEvent.KEYCODE_ENTER;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
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

import static org.hamcrest.Matchers.anything;

import android.widget.ListView;

import edu.ucsd.cse110.habitizer.app.util.routine.NewTaskDialogFragment;

@RunWith(AndroidJUnit4.class)
public class AddRoutineTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testAddRoutineShowsDialog() {
        onView(withId(R.id.routine_list_view)).check(matches(isDisplayed()));

        onView(withId(R.id.add_routine_button)).perform(click());

        onView(withId(R.id.fragment_dialog_new_routine)).check(matches(isDisplayed()));
    }

    @Test
    public void testAddRoutineTitle() {
        onView(withId(R.id.routine_list_view)).check(matches(isDisplayed()));

        onView(withId(R.id.add_routine_button)).perform(click());

        onView(withId(R.id.fragment_dialog_new_routine)).check(matches(isDisplayed()));

        onView(withId(R.id.editRoutineNameInput)).perform(typeText("New Routine"), closeSoftKeyboard());
        onView(withId(R.id.editRoutineNameInput)).check(matches(withText("New Routine")));
    }

    @Test
    public void testAddRoutineTotal() {
        onView(withId(R.id.routine_list_view)).check(matches(isDisplayed()));

        onView(withId(R.id.add_routine_button)).perform(click());

        onView(withId(R.id.fragment_dialog_new_routine)).check(matches(isDisplayed()));

        onView(withId(R.id.editRoutineNameInput)).perform(typeText("New Routine"), closeSoftKeyboard());
        onView(withId(R.id.editRoutineNameInput)).check(matches(withText("New Routine")));

        onView(withText("Create")).perform(click());
        onView(withId(R.id.routine_list_view)).check(matches(isDisplayed()));

        int lastPosition = getListViewSize(R.id.routine) - 1;

        onData(anything())
                .inAdapterView(withId(R.id.routine))
                .atPosition(lastPosition)                  // Morning Routine List?
                .onChildView(withId(R.id.routine_title))
                .check(matches(withText("New Routine")));
    }

    private int getListViewSize(int listViewId) {
        final int[] size = new int[1];
        onView(withId(listViewId)).check((view, noViewFoundException) -> {
            if (view instanceof ListView listView) {
                size[0] = listView.getAdapter().getCount();
            }
        });
        return size[0];
    }
}