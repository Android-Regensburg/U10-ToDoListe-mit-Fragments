package de.ur.mi.android.demos.fragment.database;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import de.ur.mi.android.demos.fragment.tasks.Task;

@Database(entities = {Task.class}, version = 1)
@TypeConverters({TaskAttributeTypeConverter.class})
public abstract class TaskDatabase extends RoomDatabase {
    public abstract TaskDAO taskDAO();
}
