//package edu.ucsd.cse110.habitizer.app;
//
//import static androidx.test.espresso.Espresso.onData;
//import static androidx.test.espresso.Espresso.onView;
//import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
//import static androidx.test.espresso.action.ViewActions.typeText;
//import static androidx.test.espresso.assertion.ViewAssertions.matches;
//import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
//import static androidx.test.espresso.matcher.ViewMatchers.withId;
//import static androidx.test.espresso.matcher.ViewMatchers.withText;
//import static androidx.test.espresso.action.ViewActions.click;
//
//import static org.hamcrest.Matchers.allOf;
//import static org.hamcrest.Matchers.anything;
//import static org.hamcrest.Matchers.hasEntry;
//import static org.hamcrest.Matchers.is;
//
//import android.widget.ListView;
//
//import androidx.test.core.app.ActivityScenario;
//import androidx.test.ext.junit.runners.AndroidJUnit4;
//
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//@RunWith(AndroidJUnit4.class)
//public class BelindaTests {
//
//    @Before
//    public void setUp() {
//        ActivityScenario.launch(MainActivity.class);
//    }
//
//    @Test
//    public void testBelindaCreatesNewRoutine(){
//
//        //Clicks the add button
//        onView(withId(R.id.add_routine_button)).perform(click());
//        onView(withId(R.id.new_routine_dialog)).check(matches(isDisplayed()));
//
//        //Enters “Friday Evening” and presses enter
//        onView(withId(R.id.editRoutineNameInput)).perform(typeText("Friday Evening"), closeSoftKeyboard());
//        onView(withId(R.id.editRoutineNameInput)).check(matches(withText("Friday Evening")));
//
//        //Clicks the "Create" button
//        onView(withText("Create")).perform(click());
//        int lastPos = getListViewSize(R.id.routine_list) - 1;
//        onData(anything())
//                .inAdapterView(withId(R.id.routine_list))
//                .atPosition(lastPos)
//                .onChildView(withId(R.id.routine_title))
//                .check(matches(withText("Friday Evening")));
//
//        //Go to edit view
//        onData(anything())
//                .inAdapterView(withId(R.id.routine_list))
//                .atPosition(lastPos)
//                .onChildView(withId(R.id.routine_edit_btn))
//                .perform(click());
//        onView(withId(R.id.fragment_edit_routine)).check(matches(isDisplayed()));
//
//        //Clicks add task button
//        onView(withId(R.id.add_task_button)).perform(click());
//        onView(withId(R.id.new_task_dialog)).check(matches(isDisplayed()));
//
//        //Enters "Dinner" and presses enter
//        onView(withId(R.id.taskNameInput)).perform(typeText("Dinner"), closeSoftKeyboard());
//        onView(withId(R.id.taskNameInput)).check(matches(withText("Dinner")));
//        onView(withText("Create")).perform(click());
//        lastPos = getListViewSize(R.id.routine) - 1;
//        onData(anything())
//                .inAdapterView(withId(R.id.routine))
//                .atPosition(lastPos)
//                .onChildView(withId(R.id.task_title))
//                .check(matches(withText("Dinner")));
//
//        //does it for a couple more tasks
//        for(int i = 0; i < 5; ++i){
//            //Clicks add task button
//            onView(withId(R.id.add_task_button)).perform(click());
//            onView(withId(R.id.new_task_dialog)).check(matches(isDisplayed()));
//
//            //Enters "New Task" and presses enter
//            onView(withId(R.id.taskNameInput)).perform(typeText("NewTask"), closeSoftKeyboard());
//            onView(withId(R.id.taskNameInput)).check(matches(withText("NewTask")));
//            onView(withText("Create")).perform(click());
//            lastPos = getListViewSize(R.id.routine) - 1;
//            onData(anything())
//                    .inAdapterView(withId(R.id.routine))
//                    .atPosition(lastPos)
//                    .onChildView(withId(R.id.task_title))
//                    .check(matches(withText("NewTask")));
//        }
//
//        //Add task "Charge Devices"
//        onView(withId(R.id.add_task_button)).perform(click());
//        onView(withId(R.id.new_task_dialog)).check(matches(isDisplayed()));
//        onView(withId(R.id.taskNameInput)).perform(typeText("Charge Devices"), closeSoftKeyboard());
//        onView(withId(R.id.taskNameInput)).check(matches(withText("Charge Devices")));
//        onView(withText("Create")).perform(click());
//        lastPos = getListViewSize(R.id.routine) - 1;
//        onData(anything())
//                .inAdapterView(withId(R.id.routine))
//                .atPosition(lastPos)
//                .onChildView(withId(R.id.task_title))
//                .check(matches(withText("Charge Devices")));
//
//        int size = getListViewSize(R.id.routine);
//        for(int i = 0; i < size-1; ++i){
//            //move task "CreateDevices" all the way to the top
//            onData(anything())
//                    .inAdapterView(withId(R.id.routine))
//                    .atPosition(size - i - 1)
//                    .onChildView(withId(R.id.task_title))
//                    .check(matches(withText("Charge Devices")));
//            onData(anything())
//                    .inAdapterView(withId(R.id.routine))
//                    .atPosition(size - i - 1)
//                    .onChildView(withId(R.id.upButton))
//                    .perform(click());
//        }
//        onData(anything())
//                .inAdapterView(withId(R.id.routine))
//                .atPosition(0)
//                .onChildView(withId(R.id.task_title))
//                .check(matches(withText("Charge Devices")));
//
//        //Deletes task "Charge Devices"
//        onData(anything())
//                .inAdapterView(withId(R.id.routine))
//                .atPosition(0)
//                .onChildView(withId(R.id.task_delete_button))
//                .perform(click());
//        onView(withId(R.id.delete_task_dialog)).check(matches(isDisplayed()));
//        onView(withText("Delete")).perform(click());
//
//        //Checks if task "Charge Devices" is deleted
//        for(int i = 0; i < getListViewSize(R.id.routine); ++i){
//            String text = "NewTask";
//            if(i == 0)
//                text = "Dinner";
//
//            onData(anything())
//                    .inAdapterView(withId(R.id.routine))
//                    .atPosition(i)
//                    .onChildView(withId(R.id.task_title))
//                    .check(matches(withText(text)));
//        }
//
//
//        //check persistence
//
//
//
//    }
//
//    //temporarily needed before we have persistence
//    //TODO: remove once persistence exists
//    public void setupRoutine(){
//        //Clicks the add button
//        onView(withId(R.id.add_routine_button)).perform(click());
//        onView(withId(R.id.new_routine_dialog)).check(matches(isDisplayed()));
//
//        //Enters “Friday Evening” and presses enter
//        onView(withId(R.id.editRoutineNameInput)).perform(typeText("Friday Evening"), closeSoftKeyboard());
//        onView(withId(R.id.editRoutineNameInput)).check(matches(withText("Friday Evening"));
//
//        //Clicks the "Create" button
//        onView(withText("Create")).perform(click());
//    }
//    // needs persistence
//    public void testBelindaPauses(){
//        //Click start on "Friday Evening"
//        onData(anything())
//                .inAdapterView(withId(R.id.routine_list))
//                .atPosition(2)
//                .onChildView(withId(R.id.routine_start_btn))
//                .perform(click());
//        onView(withId(R.id.fragment_active_routine)).check(matches(isDisplayed()));
//
//
//    }
//    private int getListViewSize(int listViewId) {
//        final int[] size = new int[1];
//        onView(withId(listViewId)).check((view, noViewFoundException) -> {
//            if (view instanceof ListView listView) {
//                size[0] = listView.getAdapter().getCount();
//            }
//        });
//        return size[0];
//    }
//}
