package edu.ucsd.cse110.habitizer.app.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(TaskEntity task);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insert(List<TaskEntity> tasks);

    @Query("DELETE FROM Task WHERE tid = :tid")
    void remove(int tid);

    @Query("SELECT * FROM Task WHERE tid = :tid")
    TaskEntity find(int tid);

    @Query("SELECT * FROM Task WHERE rid = :rid AND sort_order = :order")
    LiveData<TaskEntity> findTaskWithOrderAsLiveData(int rid, int order);

    @Query("SELECT * FROM Task WHERE tid = :tid")
    LiveData<TaskEntity> findAsLiveData(int tid);

    @Query("SELECT * FROM Task")
    LiveData<List<TaskEntity>> findAllAsLiveData();

    @Query("SELECT * FROM Task WHERE rid = :rid")
    LiveData<List<TaskEntity>> findAllWithRIDAsLiveData(int rid);

    @Query("UPDATE Task SET name = :name WHERE tid = :tid")
    void updateName(String name, int tid);

    @Query("SELECT count(*) FROM Task")
    int count();

    @Query("SELECT sort_order FROM Task WHERE tid = :tid")
    int getTaskSortOrder(int tid);

    @Query("UPDATE Task " +
        "SET sort_order = CASE " +
        "    WHEN tid = :tid THEN sort_order - 1 " +
        "    WHEN sort_order = (SELECT sort_order FROM Task WHERE tid = :tid) - 1 THEN sort_order + 1 " +
        "    ELSE sort_order END " +
        "WHERE rid = :rid AND (tid = :tid OR sort_order = (SELECT sort_order FROM Task WHERE tid = :tid) - 1)")
    void moveTaskUp(int rid, int tid);

    @Query("UPDATE Task " +
        "SET sort_order = CASE " +
        "    WHEN tid = :tid THEN sort_order - 1 " +
        "    WHEN sort_order = (SELECT sort_order FROM Task WHERE tid = :tid) - 1 THEN sort_order + 1 " +
        "    ELSE sort_order END " +
        "WHERE rid = :rid AND (tid = :tid OR sort_order = (SELECT sort_order FROM Task WHERE tid = :tid) - 1)")
    void moveTaskDown(int rid, int tid);
}
