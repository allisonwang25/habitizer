package edu.ucsd.cse110.habitizer.app.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TimerDao {

    @Query("SELECT COUNT(*) FROM Timer")
    int count();
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(TimerEntity timer);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insert(List<TimerEntity> timer);

    @Query("SELECT * FROM Timer WHERE rid = :rid")
    TimerEntity find(int rid);

    @Query("SELECT * FROM Timer ")
    LiveData<List<TimerEntity>> findAll();

    @Query("SELECT * FROM Timer WHERE rid = :rid")
    LiveData<List<TimerEntity>> findAllRoutineTimersWithId(int rid);

    @Query("SELECT * FROM Timer WHERE rid = :rid")
    LiveData<TimerEntity> findAsLiveData(int rid);

    //TODO : Add more queries
}
