package de.ur.mi.android.demos.todo.database;
import java.util.List;
import de.ur.mi.android.demos.todo.tasks.Task;

public interface TaskQueryResultListener {
    void onQueryResult(List<Task> taskList);
}
