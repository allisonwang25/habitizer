package edu.ucsd.cse110.habitizer.app.data.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RoutineDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(RoutineEntity routine);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insert(List<RoutineEntity> routines);

    @Query("DELETE FROM Routine WHERE rid = :rid")
    void delete(int rid);

    @Query("SELECT * FROM Routine WHERE rid = :rid")
    RoutineEntity find(int rid);

    @Query("SELECT count(*) FROM Routine")
    int count();

}