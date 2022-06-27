package de.ur.mi.android.demos.fragment.database;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;
import de.ur.mi.android.demos.fragment.tasks.Task;

@Dao
public interface TaskDAO {
    @Insert
    void insertTask(Task task);

    @Update
    void updateTask(Task task);

    @Query("SELECT * FROM task_table")
    List<Task> getAllTasks();
}
