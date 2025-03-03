package edu.ucsd.cse110.habitizer.app;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import edu.ucsd.cse110.habitizer.app.databinding.ActivityMainBinding;
import edu.ucsd.cse110.habitizer.app.util.fragments;
import edu.ucsd.cse110.habitizer.app.util.routine.EditRoutineFragment;
import edu.ucsd.cse110.habitizer.app.util.routine.RoutineFragment;
import edu.ucsd.cse110.habitizer.app.util.routine_list.RoutineListFragment;



public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding view;
    fragments currentFragment = fragments.ROUTINE_LIST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.view = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(view.getRoot());
    }

    public void setActiveFragment(fragments fragment, int routineId) {
        currentFragment = fragment;
        swapFragments(routineId);
    }

    private void swapFragments(int routineId) {
        switch(currentFragment) {
            case ROUTINE_LIST:
                getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, RoutineListFragment.newInstance())
                    .commit();
                break;
            case ROUTINE_ACTIVE:
                getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, RoutineFragment.newInstance(routineId))
                    .commit();
                break;
            case ROUTINE_EDIT:
                getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, EditRoutineFragment.newInstance(routineId))
                    .commit();
                break;
        }
    }
}