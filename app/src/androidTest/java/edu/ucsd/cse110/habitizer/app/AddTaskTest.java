package edu.ucsd.cse110.habitizer.app;


import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

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

@RunWith(AndroidJUnit4.class)
public class AddTaskTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

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

    // Tests Adding a Task
    @Test
    public void testAddTask() {
        onView(withId(R.id.routine_list_view)).check(matches(isDisplayed()));

        onData(anything())
                .inAdapterView(withId(R.id.routine))
                .atPosition(0)                  // Morning Routine List?
                .onChildView(withId(R.id.routine_edit_btn))
                .perform(click());

        onView(withId(R.id.fragment_edit_routine)).check(matches(isDisplayed()));

        onView(withId(R.id.add_task_button)).perform(click());

        onView(withId(R.id.new_task_dialog)).check(matches(isDisplayed()));
    }

    // Tests Typing a Title into the Add Task Dialog box
    @Test
    public void testAddTaskTitle() {
        onView(withId(R.id.routine_list_view)).check(matches(isDisplayed()));

        onData(anything())
                .inAdapterView(withId(R.id.routine))
                .atPosition(0)                  // Morning Routine List?
                .onChildView(withId(R.id.routine_edit_btn))
                .perform(click());

        onView(withId(R.id.fragment_edit_routine)).check(matches(isDisplayed()));

        onView(withId(R.id.add_task_button)).perform(click());

        onView(withId(R.id.new_task_dialog)).check(matches(isDisplayed()));

        onView(withId(R.id.taskNameInput)).perform(typeText("Test Task"), closeSoftKeyboard());

        onView(withId(R.id.taskNameInput)).check(matches(withText("Test Task")));
    }

    // Adds task, and check it
    @Test
    public void testAddTaskCheck() {
        onView(withId(R.id.routine_list_view)).check(matches(isDisplayed()));

        onData(anything())
                .inAdapterView(withId(R.id.routine))
                .atPosition(0)
                .onChildView(withId(R.id.routine_edit_btn))
                .perform(click());

        onView(withId(R.id.fragment_edit_routine)).check(matches(isDisplayed()));

        onView(withId(R.id.add_task_button)).check(matches(isDisplayed())).perform(click());

        onView(withId(R.id.new_task_dialog)).check(matches(isDisplayed()));

        onView(withId(R.id.taskNameInput)).perform(typeText("Test Task"), closeSoftKeyboard());

        onView(withId(R.id.taskNameInput)).check(matches(withText("Test Task")));

        onView(withText("Create")).perform(click());

        onView(withId(R.id.fragment_edit_routine)).check(matches(isDisplayed()));

        int lastPosition = getListViewSize(R.id.routine) - 1;

        onData(anything())
                .inAdapterView(withId(R.id.routine))
                .atPosition(lastPosition)
                .onChildView(withId(R.id.task_title))
                .check(matches(withText("Test Task")));
    }

    @Test
    public void testAddTaskPersistence() {
        onView(withId(R.id.routine_list_view)).check(matches(isDisplayed()));

        onData(anything())
                .inAdapterView(withId(R.id.routine))
                .atPosition(0)
                .onChildView(withId(R.id.routine_edit_btn))
                .perform(click());

        onView(withId(R.id.fragment_edit_routine)).check(matches(isDisplayed()));

        onView(withId(R.id.add_task_button)).check(matches(isDisplayed())).perform(click());

        onView(withId(R.id.new_task_dialog)).check(matches(isDisplayed()));

        onView(withId(R.id.taskNameInput)).perform(typeText("Test Task"), closeSoftKeyboard());

        onView(withId(R.id.taskNameInput)).check(matches(withText("Test Task")));

        onView(withText("Create")).perform(click());

        onView(withId(R.id.fragment_edit_routine)).check(matches(isDisplayed()));

        int lastPosition = getListViewSize(R.id.routine) - 1;

        onData(anything())
                .inAdapterView(withId(R.id.routine))
                .atPosition(lastPosition)
                .onChildView(withId(R.id.task_title))
                .check(matches(withText("Test Task")));

        onView(withId(R.id.back_button)).perform(click());

        // duplicate of previous
        onView(withId(R.id.routine_list_view)).check(matches(isDisplayed()));

        onData(anything())
                .inAdapterView(withId(R.id.routine))
                .atPosition(0)
                .onChildView(withId(R.id.routine_edit_btn))
                .perform(click());

        onView(withId(R.id.fragment_edit_routine)).check(matches(isDisplayed()));

        lastPosition = getListViewSize(R.id.routine) - 1;

        onData(anything())
                .inAdapterView(withId(R.id.routine))
                .atPosition(lastPosition)
                .onChildView(withId(R.id.task_title))
                .check(matches(withText("Test Task")));

        // check it is in run as well
        onView(withId(R.id.back_button)).perform(click());

        // duplicate of previous
        onView(withId(R.id.routine_list_view)).check(matches(isDisplayed()));

        onData(anything())
                .inAdapterView(withId(R.id.routine))
                .atPosition(0)
                .onChildView(withId(R.id.routine_start_btn))
                .perform(click());

        onView(withId(R.id.fragment_active_routine)).check(matches(isDisplayed()));

        lastPosition = getListViewSize(R.id.routine) - 1;

        onData(anything())
                .inAdapterView(withId(R.id.routine))
                .atPosition(lastPosition)
                .onChildView(withId(R.id.task_title))
                .check(matches(withText("Test Task")));
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
