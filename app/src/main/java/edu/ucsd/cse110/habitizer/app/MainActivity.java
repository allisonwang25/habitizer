package edu.ucsd.cse110.habitizer.app;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import edu.ucsd.cse110.habitizer.app.databinding.ActivityMainBinding;
import edu.ucsd.cse110.habitizer.app.util.fragments;
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

    public void setActiveFragment(fragments fragment) {
        currentFragment = fragment;
        swapFragments();
    }

    private void swapFragments() {
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
                    .replace(R.id.fragment_container, RoutineFragment.newInstance())
                    .commit();
                // TODO: Switch to 'active' routine
                break;
            case ROUTINE_EDIT:
                getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, RoutineFragment.newInstance())
                    .commit();
                break;
        }
    }
}