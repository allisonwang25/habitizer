package edu.ucsd.cse110.habitizer.app.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {TaskEntity.class, RoutineEntity.class, TimerEntity.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class HabitizerDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();
    public abstract RoutineDao routineDao();
    public abstract TimerDao timerDao();
}
