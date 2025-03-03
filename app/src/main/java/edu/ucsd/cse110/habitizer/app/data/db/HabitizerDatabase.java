package edu.ucsd.cse110.habitizer.app.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {TaskEntity.class, RoutineEntity.class}, version = 1)
public abstract class HabitizerDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();
    public abstract RoutineDao routineDao();
}
