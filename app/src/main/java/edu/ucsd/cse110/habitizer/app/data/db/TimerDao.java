package edu.ucsd.cse110.habitizer.app.data.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import java.util.List;

@Dao
public interface TimerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(TimerEntitiy timer);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insert(List<TimerEntitiy> timer);

    //TODO : Add more queries
}
