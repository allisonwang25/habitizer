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
public class DeleteTaskTest {

    @Before
    public void setUp() {
        ActivityScenario.launch(MainActivity.class);
    }

    @Test
    public void testDeleteTaskDisplaysDialog() {
        onData(anything())
                .inAdapterView(withId(R.id.routine_list))
                .atPosition(0)
                .onChildView(withId(R.id.routine_edit_btn))
                .perform(click());
        for(int i = 0; i < 7; i++) {
            onData(anything())
                    .inAdapterView(withId(R.id.routine))
                    .atPosition(i)
                    .onChildView(withId(R.id.task_delete_button))
                    .perform(click());
            onView(withId(R.id.delete_task_dialog)).check(matches(isDisplayed()));
            onView(withText("Cancel")).perform(click());
        }
        onView(withId(R.id.back_button)).perform(click());

        onData(anything())
                .inAdapterView(withId(R.id.routine_list))
                .atPosition(1)
                .onChildView(withId(R.id.routine_edit_btn))
                .perform(click());
        for(int i = 0; i < 6; ++i){
            onData(anything())
                    .inAdapterView(withId(R.id.routine))
                    .atPosition(i)
                    .onChildView(withId(R.id.task_delete_button))
                    .perform(click());
            onView(withId(R.id.delete_task_dialog)).check(matches(isDisplayed()));
            onView(withId(R.id.delete_task_dialog)).check(matches(isDisplayed()));
            onView(withText("Cancel")).perform(click());
        }
    }


    private static class ListViewSizeMatcher {
        public static Matcher<View> withListSize(final int size) {
            return new TypeSafeMatcher<View>() {
                @Override
                public void describeTo(Description description) {
                    description.appendText("ListView with item count: " + size);
                }

                @Override
                protected boolean matchesSafely(View view) {
                    return ((ListView) view).getCount() == size;
                }
            };
        }
    }

    @Test
    public void testDeleteTasks() {
        //onView(withId(R.id.add_task_button)).perform(click());
        List<String> DEFAULT_MORNING_TASKS = List.of(
                "Dress", "Shower", "Brush Teeth", "Make Coffee", "Make Lunch", "Dinner Prep", "Pack Bag"
        );
        int mSize = DEFAULT_MORNING_TASKS.size();
        for(int i = 0; i < mSize; ++i){
            onView(withId(R.id.routine_list_view)).check(matches(isDisplayed()));

            onData(Matchers.anything())
                    .inAdapterView(withId(R.id.routine_list))
                    .atPosition(0)                  // Morning Routine List?
                    .onChildView(withId(R.id.routine_edit_btn))
                    .perform(click());
            onView(withId(R.id.fragment_edit_routine)).check(matches(isDisplayed()));

            onData(Matchers.anything())
                    .inAdapterView(withId(R.id.routine))
                    .atPosition(0)
                    .onChildView(withId(R.id.task_delete_button))
                    .perform(click());
            onView(withId(R.id.delete_task_dialog)).check(matches(isDisplayed()));
            onView(withText("Delete")).perform(click());
            onView(withId(R.id.routine)).check(matches(ListViewSizeMatcher.withListSize(--mSize)));
            onView(withText(DEFAULT_MORNING_TASKS.get(i))).check(doesNotExist());

            onView(withId(R.id.back_button)).perform(click());
            onView(withId(R.id.routine_list_view)).check(matches(isDisplayed()));
            onData(Matchers.anything())
                    .inAdapterView(withId(R.id.routine_list))
                    .atPosition(0)                  // Morning Routine List?
                    .onChildView(withId(R.id.routine_start_btn))
                    .perform(click());
            onView(withId(R.id.fragment_active_routine)).check(matches(isDisplayed()));
            onView(withId(R.id.routine)).check(matches(ListViewSizeMatcher.withListSize(mSize)));
            onView(withText(DEFAULT_MORNING_TASKS.get(i))).check(doesNotExist());
            onView(withId(R.id.back_button)).perform(click());
        }

        List<String> DEFAULT_EVENING_TASKS = List.of(
               //copy tasks from default evening routine inmemorydatasource
                "Charge Devices", "Prepare Dinner", "Eat Dinner", "Wash Dishes", "Pack Bag for Morning", "Homework"
        );
        int eSize = DEFAULT_EVENING_TASKS.size();
        for(int i = 0; i < eSize; ++i){
            onData(Matchers.anything())
                    .inAdapterView(withId(R.id.routine_list))
                    .atPosition(1)                  // Morning Routine List?
                    .onChildView(withId(R.id.routine_edit_btn))
                    .perform(click());

            onData(Matchers.anything())
                    .inAdapterView(withId(R.id.routine))
                    .atPosition(0)
                    .onChildView(withId(R.id.task_delete_button))
                    .perform(click());
            onView(withText("Delete")).perform(click());
            onView(withId(R.id.routine)).check(matches(ListViewSizeMatcher.withListSize(--eSize)));
            onView(withText(DEFAULT_MORNING_TASKS.get(i))).check(doesNotExist());

            onView(withId(R.id.back_button)).perform(click());
            onData(Matchers.anything())
                    .inAdapterView(withId(R.id.routine_list))
                    .atPosition(1)                  // Evening Routine List?
                    .onChildView(withId(R.id.routine_start_btn))
                    .perform(click());
            onView(withId(R.id.routine)).check(matches(ListViewSizeMatcher.withListSize(eSize)));
            onView(withText(DEFAULT_MORNING_TASKS.get(i))).check(doesNotExist());
            onView(withId(R.id.back_button)).perform(click());
        }
    }

    @Test
    public void testDeleteNewTasks(){
        onView(withId(R.id.routine_list_view)).check(matches(isDisplayed()));
        onView(withId(R.id.add_routine_button)).perform(click());
        onView(withId(R.id.editRoutineNameInput)).perform(typeText("Test Routine"), closeSoftKeyboard());
        onView(withText("Create")).perform(click());

        onData(Matchers.anything())
                .inAdapterView(withId(R.id.routine_list))
                .atPosition(2)
                .onChildView(withId(R.id.routine_edit_btn))
                .perform(click());
        onView(withId(R.id.fragment_edit_routine)).check(matches(isDisplayed()));

        onView(withId(R.id.add_task_button)).perform(click());
        onView(withId(R.id.taskNameInput)).perform(typeText("Test Task"), closeSoftKeyboard());
        onView(withText("Create")).perform(click());


        onData(Matchers.anything())
                .inAdapterView(withId(R.id.routine))
                .atPosition(0)
                .onChildView(withId(R.id.task_delete_button))
                .perform(click());
        onView(withText("Delete")).perform(click());
        onView(withId(R.id.routine)).check(matches(ListViewSizeMatcher.withListSize(0)));
        onView(withText("Test Task")).check(doesNotExist());

        onView(withId(R.id.back_button)).perform(click());
        onView(withId(R.id.routine_list_view)).check(matches(isDisplayed()));
        onData(Matchers.anything())
                .inAdapterView(withId(R.id.routine_list))
                .atPosition(2)                  // Morning Routine List?
                .onChildView(withId(R.id.routine_start_btn))
                .perform(click());
        onView(withId(R.id.routine)).check(matches(ListViewSizeMatcher.withListSize(0)));
        onView(withText("Test Task")).check(doesNotExist());
        onView(withId(R.id.back_button)).perform(click());

    }
}
