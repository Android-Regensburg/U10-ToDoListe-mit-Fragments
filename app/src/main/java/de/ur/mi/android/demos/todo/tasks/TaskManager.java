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

    public void toggleTaskStateAtPosition(int position) {
        Task task = tasks.get(position);
        toggleTaskState(task);
    }

    public void toggleTaskStateForId(String id) {
        for (Task task : tasks) {
            if (task.getID().equals(id)) {
                toggleTaskState(task);
            }
        }
    }

    private void toggleTaskState(Task taskToToggle) {
        if (taskToToggle != null) {
            if (taskToToggle.isClosed()) {
                taskToToggle.markAsOpen();
            } else {
                taskToToggle.markAsClosed();
            }
            dbHelper.updateTask(taskToToggle);
            listener.onTaskListUpdated();
        }
    }

    public ArrayList<Task> getCurrentTasks() {
        ArrayList<Task> currentTasks = new ArrayList<>();
        for (Task task : tasks) {
            currentTasks.add(task.copy());
        }
        Collections.sort(currentTasks);
        return currentTasks;
    }

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
