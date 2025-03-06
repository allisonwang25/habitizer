package edu.ucsd.cse110.habitizer.app.data.db;

import androidx.lifecycle.LiveData;
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

    @Query("SELECT * FROM Routine WHERE rid = :rid")
    LiveData<RoutineEntity> findAsLiveData(int rid);

    // TODO: How should Routines be ordered? or should this grab a specific Routine
    @Query("SELECT * FROM Routine")
    LiveData<List<RoutineEntity>> findAllAsLiveData();

    @Query("SELECT count(*) FROM Routine")
    int count();

}