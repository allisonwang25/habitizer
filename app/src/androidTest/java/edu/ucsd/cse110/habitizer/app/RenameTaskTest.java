package edu.ucsd.cse110.habitizer.app;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.anything;

import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class RenameTaskTest {

    @Before
    public void setUp() {
        ActivityScenario.launch(MainActivity.class);
    }

    @Test
    public void testRenameClickOpensRenameFragment() {
        onData(anything())
                .inAdapterView(withId(R.id.routine_list))
                .atPosition(0)
                .onChildView(withId(R.id.routine_edit_btn))
                .perform(click());
        for(int i = 0; i < 7; i++) {
            onData(anything())
                    .inAdapterView(withId(R.id.routine))
                    .atPosition(i)
                    .onChildView(withId(R.id.task_edit_button))
                    .perform(click());
            onView(withId(R.id.rename_task_dialog)).check(matches(isDisplayed()));
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
                    .onChildView(withId(R.id.task_edit_button))
                    .perform(click());
            onView(withId(R.id.rename_task_dialog)).check(matches(isDisplayed()));
            onView(withText("Cancel")).perform(click());
        }
    }

    @Test
    public void testRenameDefaultTasks(){
        List<String> DEFAULT_MORNING_TASKS = List.of(
                "Dress", "Shower", "Brush Teeth", "Make Coffee", "Make Lunch", "Dinner Prep", "Pack Bag"
        );
        onData(anything())
                .inAdapterView(withId(R.id.routine_list))
                .atPosition(0)
                .onChildView(withId(R.id.routine_edit_btn))
                .perform(click());
        for(int i = 0; i < DEFAULT_MORNING_TASKS.size(); ++i){
            onData(anything())
                    .inAdapterView(withId(R.id.routine))
                    .atPosition(i)
                    .onChildView(withId(R.id.task_edit_button))
                    .perform(click());
            onView(withId(R.id.editTaskNameInput)).perform(typeText(" changed"), closeSoftKeyboard());
            onView(withText("Rename")).perform(click());
            onData(anything())
                    .inAdapterView(withId(R.id.routine))
                    .atPosition(i)
                    .onChildView(withId(R.id.task_title))
                    .check(matches(withText(DEFAULT_MORNING_TASKS.get(i) + " changed")));
        }
        onView(withId(R.id.back_button)).perform(click());

        List<String> DEFAULT_EVENING_TASKS = List.of(
                //copy tasks from default evening routine inmemorydatasource
                "Charge Devices", "Prepare Dinner", "Eat Dinner", "Wash Dishes", "Pack Bag for Morning", "Homework"
        );
        onData(anything())
                .inAdapterView(withId(R.id.routine_list))
                .atPosition(1)
                .onChildView(withId(R.id.routine_edit_btn))
                .perform(click());
        for(int i = 0; i < DEFAULT_EVENING_TASKS.size(); ++i){
            if(i == 4) continue;
            onData(anything())
                    .inAdapterView(withId(R.id.routine))
                    .atPosition(i)
                    .onChildView(withId(R.id.task_edit_button))
                    .perform(click());
            onView(withId(R.id.editTaskNameInput)).perform(typeText(" changed"), closeSoftKeyboard());
            onView(withText("Rename")).perform(click());
            onData(anything())
                    .inAdapterView(withId(R.id.routine))
                    .atPosition(i)
                    .onChildView(withId(R.id.task_title))
                    .check(matches(withText(DEFAULT_EVENING_TASKS.get(i) + " changed")));
        }
    }

    @Test
    public void testRenameCustomTasks(){
        onView(withId(R.id.routine_list_view)).check(matches(isDisplayed()));
        onView(withId(R.id.add_routine_button)).perform(click());
        onView(withId(R.id.editRoutineNameInput)).perform(typeText("Test Routine"), closeSoftKeyboard());
        onView(withText("Create")).perform(click());

        onData(Matchers.anything())
                .inAdapterView(withId(R.id.routine_list))
                .atPosition(2)
                .onChildView(withId(R.id.routine_edit_btn))
                .perform(click());
        onView(withId(R.id.add_task_button)).perform(click());
        onView(withId(R.id.taskNameInput)).perform(typeText("Test Task"), closeSoftKeyboard());
        onView(withText("Create")).perform(click());

        onData(anything())
                .inAdapterView(withId(R.id.routine))
                .atPosition(0)
                .onChildView(withId(R.id.task_edit_button))
                .perform(click());
        onView(withId(R.id.editTaskNameInput)).perform(typeText(" changed"), closeSoftKeyboard());
        onView(withText("Rename")).perform(click());
        onData(anything())
                .inAdapterView(withId(R.id.routine))
                .atPosition(0)
                .onChildView(withId(R.id.task_title))
                .check(matches(withText("Test Task changed")));
    }
}