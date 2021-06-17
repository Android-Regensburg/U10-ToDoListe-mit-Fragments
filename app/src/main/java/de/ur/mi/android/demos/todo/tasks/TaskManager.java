package de.ur.mi.android.demos.todo.tasks;
import android.app.Activity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import de.ur.mi.android.demos.todo.database.DatabaseHelper;
import de.ur.mi.android.demos.todo.database.TaskQueryResultListener;

public class TaskManager implements TaskQueryResultListener {

    private final TaskManagerListener listener;
    private final ArrayList<Task> tasks;
    private final DatabaseHelper dbHelper;

    public TaskManager(Activity context, TaskManagerListener listener) {
        this.listener = listener;
        this.dbHelper = new DatabaseHelper(context);
        this.tasks = new ArrayList<>();
        dbHelper.retrieveAllTasks(this);
    }

    public void addTask(Task taskToAdd) {
        tasks.add(taskToAdd);
        dbHelper.addTask(taskToAdd);
        listener.onTaskListUpdated();
    }

    public void toggleTaskStateForId(String id) {
        for (Task task : tasks) {
            if (task.getID().equals(id)) {
                task.toggleState();
                dbHelper.updateTask(task);
                listener.onTaskListUpdated();
                return;
            }
        }
    }

    /*Gibt eine Kopie der Taskliste nach außen, sodass die eigentlichen Daten nicht verändert werden können*/
    public ArrayList<Task> getCurrentTasks() {
        ArrayList<Task> currentTasks = new ArrayList<>();
        for (Task task : tasks) {
            currentTasks.add(task.copy());
        }
        Collections.sort(currentTasks);
        return currentTasks;
    }

    /*Wird aufgerufen, wenn alle Tasks aus der Datenbank erfolgreich geladen wurden*/
    @Override
    public void onQueryResult(List<Task> taskList) {
        for(Task task : taskList){
            this.tasks.add(task);
        }
        listener.onTaskListUpdated();
    }

    public interface TaskManagerListener {
        void onTaskListUpdated();
    }
}
