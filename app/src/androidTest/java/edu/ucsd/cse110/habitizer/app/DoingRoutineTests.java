package edu.ucsd.cse110.habitizer.app;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.actionWithAssertions;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.action.ViewActions.click;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.containsString;

import android.graphics.Paint;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.ext.junit.runners.AndroidJUnit4;


import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class DoingRoutineTests {

    @Before
    public void setUp() {
        ActivityScenario.launch(MainActivity.class);
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

    @Test
    public void testRunningRoutineFinishByTasks(){
        onView(withId(R.id.add_routine_button)).perform(click());
        onView(withId(R.id.new_routine_dialog)).check(matches(isDisplayed()));

        //Enters “Friday Evening” and presses enter
        onView(withId(R.id.editRoutineNameInput)).perform(typeText("Friday Evening"), closeSoftKeyboard());
        onView(withId(R.id.editRoutineNameInput)).check(matches(withText("Friday Evening")));

        //Clicks the "Create" button
        onView(withText("Create")).perform(click());
        int lastPos = getListViewSize(R.id.routine_list) - 1;
        onData(anything())
                .inAdapterView(withId(R.id.routine_list))
                .atPosition(lastPos)
                .onChildView(withId(R.id.routine_title))
                .check(matches(withText("Friday Evening")));

        //Go to edit view
        onData(anything())
                .inAdapterView(withId(R.id.routine_list))
                .atPosition(lastPos)
                .onChildView(withId(R.id.routine_edit_btn))
                .perform(click());
        onView(withId(R.id.fragment_edit_routine)).check(matches(isDisplayed()));

        //Clicks add task button
        onView(withId(R.id.add_task_button)).perform(click());
        onView(withId(R.id.new_task_dialog)).check(matches(isDisplayed()));

        //Enters "Dinner" and presses enter
        onView(withId(R.id.taskNameInput)).perform(typeText("Dinner"), closeSoftKeyboard());
        onView(withId(R.id.taskNameInput)).check(matches(withText("Dinner")));
        onView(withText("Create")).perform(click());
        lastPos = getListViewSize(R.id.routine) - 1;
        onData(anything())
                .inAdapterView(withId(R.id.routine))
                .atPosition(lastPos)
                .onChildView(withId(R.id.task_title))
                .check(matches(withText("Dinner")));

        //does it for a couple more tasks
        for(int i = 0; i < 3; ++i){
            //Clicks add task button
            onView(withId(R.id.add_task_button)).perform(click());
            onView(withId(R.id.new_task_dialog)).check(matches(isDisplayed()));

            //Enters "New Task" and presses enter
            onView(withId(R.id.taskNameInput)).perform(typeText("NewTask"), closeSoftKeyboard());
            onView(withId(R.id.taskNameInput)).check(matches(withText("NewTask")));
            onView(withText("Create")).perform(click());
            lastPos = getListViewSize(R.id.routine) - 1;
            onData(anything())
                    .inAdapterView(withId(R.id.routine))
                    .atPosition(lastPos)
                    .onChildView(withId(R.id.task_title))
                    .check(matches(withText("NewTask")));
        }
        onView(withId(R.id.back_button)).perform(click());
        //Clicks start button on Morning Routine
        onData(anything())
                .inAdapterView(withId(R.id.routine_list))
                .atPosition(2)
                .onChildView(withId(R.id.routine_start_btn))
                .perform(click());
        onView(withId(R.id.fragment_active_routine)).check(matches(isDisplayed()));

        //Waits for 29 seconds
        try {
            Thread.sleep(9000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //check that elapsed time is updated
        onView(withId(R.id.routine_elapsed_time)).check(matches(withText(containsString("0 out of"))));
        onView(withId(R.id.curr_task_elapsed_time)).check(matches(withText(containsString("9 seconds elapsed"))));

        onData(anything())
                .inAdapterView(withId(R.id.routine))
                .atPosition(0)
                .perform(actionWithAssertions(click()));
        //check that the task has strikethrough
        onData(anything())
                .inAdapterView(withId(R.id.routine))
                .atPosition(0)
                .onChildView(withId(R.id.task_title))
                .check(matches(hasStrikeThrough()));
        onData(anything())
                .inAdapterView(withId(R.id.routine))
                .atPosition(0)
                .onChildView(withId(R.id.elapsed_time))
                .check(matches(withText("10 Seconds Elapsed")));
        //wait 1 second just to make time round, easier for later part of test
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.routine_elapsed_time)).check(matches(withText(containsString("0 out of"))));
        onView(withId(R.id.curr_task_elapsed_time)).check(matches(withText(containsString("1 seconds elapsed"))));

        onData(anything())
                .inAdapterView(withId(R.id.routine))
                .atPosition(1)
                .perform(click());
        //check that the task has strikethrough
        onData(anything())
                .inAdapterView(withId(R.id.routine))
                .atPosition(1)
                .onChildView(withId(R.id.task_title))
                .check(matches(hasStrikeThrough()));
        onData(anything())
                .inAdapterView(withId(R.id.routine))
                .atPosition(1)
                .onChildView(withId(R.id.elapsed_time))
                .check(matches(withText("5 Seconds Elapsed")));

        //complete tasks
        int time = 0;
        for(int i = 2; i < getListViewSize(R.id.routine); ++i){
            if(i % 6  == 0){
                time += 1;
            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            onView(withId(R.id.routine_elapsed_time)).check(matches(withText(containsString(time +" out of"))));
            onView(withId(R.id.curr_task_elapsed_time)).check(matches(withText(containsString("10 seconds elapsed"))));

            onData(anything())
                    .inAdapterView(withId(R.id.routine))
                    .atPosition(i)
                    .perform(click());
            //check that the task has strikethrough
            onData(anything())
                    .inAdapterView(withId(R.id.routine))
                    .atPosition(i)
                    .onChildView(withId(R.id.task_title))
                    .check(matches(hasStrikeThrough()));
            onData(anything())
                    .inAdapterView(withId(R.id.routine))
                    .atPosition(i)
                    .onChildView(withId(R.id.elapsed_time))
                    .check(matches(withText("10 Seconds Elapsed")));
        }
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.routine_elapsed_time)).check(matches(withText(containsString(time +" out of"))));
        onView(withId(R.id.curr_task_elapsed_time)).check(matches(withText(containsString("0 seconds elapsed"))));

    }


    // Add this utility method to your test class
    private static Matcher<View> hasStrikeThrough() {
        return new BoundedMatcher<View, TextView>(TextView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("has strikethrough span");
            }

            @Override
            protected boolean matchesSafely(TextView textView) {
                return textView.getPaintFlags() == (textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }
        };
    }

}