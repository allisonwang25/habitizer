package edu.ucsd.cse110.habitizer.app;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.action.ViewActions.click;

import android.view.View;
import android.widget.ListView;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;


import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class FragmentNavigationTest {

    @Before
    public void setUp() {
        // Launches the main activity before each test
        ActivityScenario.launch(MainActivity.class);
    }


    @Test
    public void testEditClickOpensEditFragment() {
        onView(withId(R.id.routine_list_view)).check(matches(isDisplayed()));
        List<String> routines = List.of("Morning", "Evening");
        for(int i = 0; i < routines.size(); ++i){
            onData(Matchers.anything())
                    .inAdapterView(withId(R.id.routine_list))
                    .atPosition(i)                  // Morning Routine List?
                    .onChildView(withId(R.id.routine_edit_btn))
                    .perform(click());

            onView(withId(R.id.fragment_edit_routine)).check(matches(isDisplayed()));
            onView(withId(R.id.routine_title)).check(matches(withText(routines.get(i))));
            onView(withId(R.id.back_button)).perform(click());
        }
    }

    @Test
    public void testStartClickOpensActiveFragment(){
        onView(withId(R.id.routine_list_view)).check(matches(isDisplayed()));
        List<String> routines = List.of("Morning", "Evening");
        for(int i = 0; i < routines.size(); ++i){
            onData(Matchers.anything())
                    .inAdapterView(withId(R.id.routine_list))
                    .atPosition(i)                  // Morning Routine List?
                    .onChildView(withId(R.id.routine_start_btn))
                    .perform(click());

            onView(withId(R.id.fragment_active_routine)).check(matches(isDisplayed()));
            onView(withId(R.id.routine_title)).check(matches(withText(routines.get(i))));
            onView(withId(R.id.back_button)).perform(click());
        }
    }

    @Test
    public void testClickBackButtonGoesBack(){
        for(int i = 0; i < 2; ++i){
            onView(withId(R.id.routine_list_view)).check(matches(isDisplayed()));
            onData(Matchers.anything())
                    .inAdapterView(withId(R.id.routine_list))
                    .atPosition(i)                  // Morning Routine List?
                    .onChildView(withId(R.id.routine_edit_btn))
                    .perform(click());

            onView(withId(R.id.fragment_edit_routine)).check(matches(isDisplayed()));
            onView(withId(R.id.back_button)).perform(click());

            onView(withId(R.id.routine_list_view)).check(matches(isDisplayed()));
            onData(Matchers.anything())
                    .inAdapterView(withId(R.id.routine_list))
                    .atPosition(i)                  // Morning Routine List?
                    .onChildView(withId(R.id.routine_start_btn))
                    .perform(click());

            onView(withId(R.id.fragment_active_routine)).check(matches(isDisplayed()));
            onView(withId(R.id.back_button)).perform(click());
        }
    }

}
