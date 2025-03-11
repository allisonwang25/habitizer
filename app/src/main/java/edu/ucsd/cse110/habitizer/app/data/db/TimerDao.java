package edu.ucsd.cse110.habitizer.app.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TimerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(TimerEntity timer);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insert(List<TimerEntity> timer);

    @Query("SELECT * FROM Timer WHERE tid = :tid AND rid = :rid")
    TimerEntity find(int tid, int rid);

    @Query("SELECT * FROM Timer WHERE tid = :tid AND rid = :rid")
    LiveData<TimerEntity> findAsLiveData(int tid, int rid);

    //TODO : Add more queries
}
