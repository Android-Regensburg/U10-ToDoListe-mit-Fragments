package de.ur.mi.android.demos.fragment.database;
import java.util.List;
import de.ur.mi.android.demos.fragment.tasks.Task;

/**Listener für das Laden aller tasks aus der task-Datenbank*/
public interface TaskQueryResultListener {
    void onQueryResult(List<Task> taskList);
}
