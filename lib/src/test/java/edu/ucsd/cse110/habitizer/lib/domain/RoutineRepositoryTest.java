package edu.ucsd.cse110.habitizer.lib.domain;

import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.junit.Test;
import org.junit.Before;
import edu.ucsd.cse110.observables.PlainMutableSubject;
import edu.ucsd.cse110.habitizer.lib.data.InMemoryDataSource;

public class RoutineRepositoryTest {

    private RoutineRepository routineRepository;
    private InMemoryDataSource inMemoryDataSource;

    @Before
    public void setUp() {
        inMemoryDataSource = new InMemoryDataSource();
        routineRepository = new RoutineRepository(inMemoryDataSource);
        routineRepository.save(new Routine("Morning Routine", null).setID(0));
        routineRepository.save(new Routine("Evening Routine", null).setID(1));
    }

    @Test
    public void count_returnsTwoForTwoRoutines() {
        assertEquals(2, (long) routineRepository.count());
    }

    @Test
    public void findAll_returnsAllRoutines() {
        assertThat(routineRepository.findAll().getValue().size(), is(2));
    }

    @Test
    public void save_addsRoutineToDataSource() {
        Routine routine = new Routine("Afternoon Routine", null).setID(2);
        routineRepository.save(routine);
        assertThat(inMemoryDataSource.getRoutines().size(), is(3));
    }

    @Test
    public void save_addsRoutineToDataSourceWithCorrectId() {
        Routine routine = new Routine("Afternoon Routine", null).setID(1);
        routineRepository.save(routine);
        assertThat(inMemoryDataSource.getRoutines().get(2), is(routine));
    }

    @Test
    public void save_updatesRoutineInDataSource() {
        Routine routine = new Routine("Afternoon Routine", null).setID(1);
        routineRepository.save(routine);
        assertThat(inMemoryDataSource.getRoutines().get(2), is(routine));
    }

    @Test
    public void save_doesNotAddDuplicateRoutine() {
        Routine routine = new Routine("Morning Routine", null).setID(0);
        routineRepository.save(routine);
        assertThat(inMemoryDataSource.getRoutines().size(), is(3));
    }

    @Test
    public void save_doesNotUpdateRoutineWithDifferentId() {
        Routine routine = new Routine("Afternoon Routine", null).setID(1);
        routineRepository.save(routine);
        assertThat(inMemoryDataSource.getRoutines().get(1), not(routine));
    }

}