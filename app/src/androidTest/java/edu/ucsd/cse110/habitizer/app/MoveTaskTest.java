package edu.ucsd.cse110.habitizer.app;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.action.ViewActions.click;

import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.not;

import android.view.View;
import android.widget.ListView;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;


import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import edu.ucsd.cse110.habitizer.lib.domain.Task;

@RunWith(AndroidJUnit4.class)
public class MoveTaskTest {

    @Before
    public void setUp() {
        ActivityScenario.launch(MainActivity.class);
    }

    @Test
    public void testMoveTaskUp() {
        List<String> DEFAULT_MORNING_TASKS = List.of(
                "Dress", "Shower", "Brush Teeth", "Make Coffee", "Make Lunch", "Dinner Prep", "Pack Bag"
        );
        List<String> DEFAULT_EVENING_TASKS = List.of(
                //copy tasks from default evening routine inmemorydatasource
                "Charge Devices", "Prepare Dinner", "Eat Dinner", "Wash Dishes", "Pack Bag for Morning", "Homework"
        );
        for (int i = 0; i < 2; ++i) {
            List<String> tasks;
            if (i == 0) {
                tasks = DEFAULT_MORNING_TASKS;
            } else {
                tasks = DEFAULT_EVENING_TASKS;
            }

            for (int k = 0; k < tasks.size(); ++k) {
                for (int j = 0; j < 2; ++j) {
                    int button;
                    if (j == 0) {
                        button = R.id.routine_edit_btn;
                    } else {
                        button = R.id.routine_start_btn;
                    }
                    onData(anything())
                            .inAdapterView(withId(R.id.routine_list))
                            .atPosition(i)
                            .onChildView(withId(button))
                            .perform(click());
                    if(j== 0) {
                        onData(anything())
                                .inAdapterView(withId(R.id.routine))
                                .atPosition(k)
                                .onChildView(withId(R.id.upButton))
                                .perform(click());
                    }
                    if (k == 0) {
                        onData(anything())
                                .inAdapterView(withId(R.id.routine))
                                .atPosition(k)
                                .onChildView(withId(R.id.task_title))
                                .check(matches(withText(tasks.get(0))));
                    } else {
                        onData(anything())
                                .inAdapterView(withId(R.id.routine))
                                .atPosition(k)
                                .onChildView(withId(R.id.task_title))
                                .check(matches(not(withText(tasks.get(k)))));
                        onData(anything())
                                .inAdapterView(withId(R.id.routine))
                                .atPosition(k - 1)
                                .onChildView(withId(R.id.task_title))
                                .check(matches(withText(tasks.get(k))));
                    }
                    onView(withId(R.id.back_button)).perform(click());
                }
            }
        }
    }

    @Test
    public void testMoveTaskDown() {
        List<String> DEFAULT_MORNING_TASKS = List.of(
                "Dress", "Shower", "Brush Teeth", "Make Coffee", "Make Lunch", "Dinner Prep", "Pack Bag"
        );
        List<String> DEFAULT_EVENING_TASKS = List.of(
                //copy tasks from default evening routine inmemorydatasource
                "Charge Devices", "Prepare Dinner", "Eat Dinner", "Wash Dishes", "Pack Bag for Morning", "Homework"
        );
        for (int i = 0; i < 2; ++i) {
            List<String> tasks;
            if (i == 0) {
                tasks = DEFAULT_MORNING_TASKS;
            } else {
                tasks = DEFAULT_EVENING_TASKS;
            }

            for (int k = tasks.size() - 1; k >= 0; --k) {
                for (int j = 0; j < 2; ++j) {
                    int button;
                    if (j == 0) {
                        button = R.id.routine_edit_btn;
                    } else {
                        button = R.id.routine_start_btn;
                    }
                    onData(anything())
                            .inAdapterView(withId(R.id.routine_list))
                            .atPosition(i)
                            .onChildView(withId(button))
                            .perform(click());
                    if(j == 0) {
                        onData(anything())
                                .inAdapterView(withId(R.id.routine))
                                .atPosition(k)
                                .onChildView(withId(R.id.downButton))
                                .perform(click());
                    }
                    if (k == tasks.size() - 1) {
                        onData(anything())
                                .inAdapterView(withId(R.id.routine))
                                .atPosition(k)
                                .onChildView(withId(R.id.task_title))
                                .check(matches(withText(tasks.get(k))));
                    } else {
                        onData(anything())
                                .inAdapterView(withId(R.id.routine))
                                .atPosition(k)
                                .onChildView(withId(R.id.task_title))
                                .check(matches(not(withText(tasks.get(k)))));
                        onData(anything())
                                .inAdapterView(withId(R.id.routine))
                                .atPosition(k + 1)
                                .onChildView(withId(R.id.task_title))
                                .check(matches(withText(tasks.get(k))));
                    }
                    onView(withId(R.id.back_button)).perform(click());
                }
            }
        }
    }
}
