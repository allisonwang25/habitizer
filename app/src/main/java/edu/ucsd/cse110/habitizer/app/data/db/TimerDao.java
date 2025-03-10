package edu.ucsd.cse110.habitizer.app.data.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import java.util.List;

@Dao
public interface TimerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(TimerEntity timer);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insert(List<TimerEntity> timer);

    //TODO : Add more queries
}
