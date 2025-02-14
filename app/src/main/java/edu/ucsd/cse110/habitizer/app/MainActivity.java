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
import edu.ucsd.cse110.habitizer.app.util.routine.RoutineFragment;
import edu.ucsd.cse110.habitizer.app.util.routine_list.RoutineListFragment;


    public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding view;

    enum fragments {
        ROUTINE_LIST,
        ROUTINE_ACTIVE,
        ROUTINE_EDIT
    }

    fragments currentFragment = fragments.ROUTINE_LIST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.view = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(view.getRoot());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        var itemId = item.getItemId();


//        if () {
//            swapFragments();
//        }

        return super.onOptionsItemSelected(item);
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