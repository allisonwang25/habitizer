package edu.ucsd.cse110.habitizer.lib.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import org.junit.Before;
import org.junit.Test;
import edu.ucsd.cse110.habitizer.lib.domain.Task;
import edu.ucsd.cse110.habitizer.lib.domain.Routine;
import edu.ucsd.cse110.habitizer.lib.data.InMemoryDataSource;
import edu.ucsd.cse110.observables.PlainMutableSubject;
import edu.ucsd.cse110.observables.Subject;
import java.util.List;
import java.util.Objects;

public class InMemoryDataSourceTest {
    private InMemoryDataSource dataSource;

    @Before
    public void setUp() {
        dataSource = new InMemoryDataSource();
    }

    @Test
    public void getTasks_returnsEmptyListWhenNoTasks() {
        assertThat(dataSource.getTasks(), is(empty()));
    }

    @Test
    public void getTask_returnsNullWhenTaskNotFound() {
        assertNull(dataSource.getTask(1));
    }

    @Test
    public void getAllTasksSubject_returnsSubjectWithAllTasks() {
        Task task1 = new Task("Task 1", 0,null, 0,0);
        Task task2 = new Task("Task 2", 1, null, 0,1);
        dataSource.putTask(task1);
        dataSource.putTask(task2);
        Subject<List<Task>> allTasksSubject = dataSource.getAllTasksSubject();
        assertThat(Objects.requireNonNull(allTasksSubject.getValue()).size(), is(2));
    }

    @Test
    public void getRoutines_returnsEmptyListWhenNoRoutines() {
        assertThat(dataSource.getRoutines(), is(empty()));
    }

    @Test
    public void getRoutine_returnsNullWhenRoutineNotFound() {
        assertNull(dataSource.getRoutine(1));
    }

    @Test
    public void fromDefault_initializesWithDefaultTasksAndRoutines() {
        InMemoryDataSource dataSource = InMemoryDataSource.fromDefault();
        assertThat(dataSource.getTasks().size(), is(InMemoryDataSource.DEFAULT_MORNING_TASKS.size() + InMemoryDataSource.DEFAULT_EVENING_TASKS.size()));
        assertThat(dataSource.getRoutines().size(), is(InMemoryDataSource.DEFAULT_ROUTINES.size()));
    }
}