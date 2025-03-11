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

    @Query("SELECT name FROM Routine WHERE rid = :rid")
    String getRoutineName(int rid);

    @Query("SELECT goalTime FROM Routine WHERE rid = :rid")
    String getRoutineGoalTime(int rid);

    @Query("UPDATE Routine SET goalTime = :goalTime WHERE rid = :rid")
    void setRoutineGoalTime(int rid, String goalTime);

    @Query("SELECT completed FROM Routine WHERE rid = :rid")
    Boolean getRoutineCompleted(int rid);

    @Query("SELECT totalRoutineTimeElapsed FROM Routine WHERE rid = :rid")
    Integer getRoutineTotalTimeElapsed(int rid);

    @Query("UPDATE Routine SET totalRoutineTimeElapsed = :totalRoutineTimeElapsed WHERE rid = :rid")
    void setRoutineTotalTimeElapsed(int rid, Integer totalRoutineTimeElapsed);
}