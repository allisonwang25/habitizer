package edu.ucsd.cse110.habitizer.lib.domain;

import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.junit.Test;
import org.junit.Before;
import edu.ucsd.cse110.habitizer.lib.data.InMemoryDataSource;
import edu.ucsd.cse110.habitizer.lib.domain.Task;
import edu.ucsd.cse110.habitizer.lib.domain.TaskRepository;
import edu.ucsd.cse110.habitizer.lib.domain.RoutineRepository;
import edu.ucsd.cse110.habitizer.lib.domain.Routine;

import edu.ucsd.cse110.observables.PlainMutableSubject;

public class TaskRepositoryTest {
    private TaskRepository taskRepository;
    private InMemoryDataSource inMemoryDataSource;
    private RoutineRepository routineRepository;

    @Before
    public void setUp() {
        inMemoryDataSource = new InMemoryDataSource();
        taskRepository = new TaskRepository(inMemoryDataSource);
        taskRepository.save(new Task("Task 1", null));
        taskRepository.save(new Task("Task 2", null));
    }

    @Test
    public void count_returnsTwoForTwoTasks() {
        assertEquals(2, (long) taskRepository.count());
    }

    @Test
    public void findAll_returnsAllTasks() {
        assertThat(taskRepository.findAll().getValue().size(), is(2));
    }

    @Test
    public void save_addsTaskToDataSource() {
        Task task = new Task("Task 3", null);
        taskRepository.save(task);
        assertThat(inMemoryDataSource.getTasks().size(), is(3));
    }

    @Test
    public void save_addsTaskToDataSourceWithCorrectId() {
        Task task = new Task("Task 3", null);
        taskRepository.save(task);
        assertThat(inMemoryDataSource.getTasks().get(2), is(task));
    }

    @Test
    public void save_updatesTaskInDataSource() {
        Task task = new Task("Task 3", null);
        taskRepository.save(task);
        assertThat(inMemoryDataSource.getTasks().get(2), is(task));
    }

}

