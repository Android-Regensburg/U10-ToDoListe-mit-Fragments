package de.ur.mi.android.demos.todo.database;

import android.app.Activity;

import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import de.ur.mi.android.demos.todo.tasks.Task;

public class DatabaseHelper {

    private static final String DATABASE_NAME = "tasks-db";
    private final Activity context;
    private TaskDatabase db;

    public DatabaseHelper(Activity context){
        this.context = context;
        initDatabase();
    }

    private void initDatabase(){
        db = Room.databaseBuilder(context, TaskDatabase.class, DATABASE_NAME).allowMainThreadQueries().build();
    }

    public void addTask(Task task) {
        new AddTaskTask(task).start();
    }

    public void updateTask(Task task) {
        new UpdateTaskTask(task).start();
    }

    public void retrieveAllTasks(TaskQueryResultListener listener) {
        new RetrieveAllTasksTask(listener).start();
    }


    private static abstract class DBOperationThread implements Runnable{

        void start() {
            Executors.newSingleThreadExecutor().submit(this);
        }
    }

    private class AddTaskTask extends DBOperationThread {

        private Task task;

        public AddTaskTask(Task task) {
            this.task = task;
        }

        @Override
        public void run() {
            db.taskDAO().insertTask(task);
        }
    }

    private class UpdateTaskTask extends DBOperationThread {

        private Task task;

        public UpdateTaskTask(Task task) {
            this.task = task;
        }

        @Override
        public void run() {
            db.taskDAO().updateTask(task);
        }
    }

    private class RetrieveAllTasksTask extends DBOperationThread {

        private TaskQueryResultListener listener;

        public RetrieveAllTasksTask(TaskQueryResultListener listener) {
            this.listener = listener;
        }

        @Override
        public void run() {
            final List<Task> list = db.taskDAO().getAllTasks();
            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    listener.onQueryResult(list);
                }
            });
        }
    }
}
