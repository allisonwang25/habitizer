package edu.ucsd.cse110.habitizer.lib.domain;

import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.junit.Test;
import org.junit.Before;
import edu.ucsd.cse110.habitizer.lib.data.InMemoryDataSource;

public class TaskRepositoryTest {
    private TaskRepository taskRepository;
    private InMemoryDataSource inMemoryDataSource;
    private RoutineRepository routineRepository;

    @Before
    public void setUp() {
        inMemoryDataSource = new InMemoryDataSource();
        taskRepository = new SimpleTaskRepository(inMemoryDataSource);
        taskRepository.save(new Task("Task 1", 0, null, 0,0));
        taskRepository.save(new Task("Task 2", 1, null, 0,1));
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
        Task task = new Task("Task 3", 3,null, 0,0);
        taskRepository.save(task);
        assertThat(inMemoryDataSource.getTasks().size(), is(3));
    }

    @Test
    public void save_addsTaskToDataSourceWithCorrectId() {
        Task task = new Task("Task 3", 3,null, 0,0);
        taskRepository.save(task);
        assertThat(inMemoryDataSource.getTasks().get(2), is(task));
    }

    @Test
    public void save_updatesTaskInDataSource() {
        Task task = new Task("Task 3", 3,null, 0,0);
        taskRepository.save(task);
        assertThat(inMemoryDataSource.getTasks().get(2), is(task));
    }

}

