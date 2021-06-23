package de.ur.mi.android.demos.todo.database;
import android.app.Activity;
import androidx.room.Room;
import java.util.List;
import java.util.concurrent.Executors;
import de.ur.mi.android.demos.todo.tasks.Task;

/**Datenbank-Helferklasse, die den Zugriff auf die Datenbank abstrahiert*/
public class DatabaseHelper {

    private static final String DATABASE_NAME = "tasks-db";
    private final Activity context;
    private TaskDatabase db;

    public DatabaseHelper(Activity context){
        this.context = context;
        initDatabase();
    }

    private void initDatabase(){
        db = Room.databaseBuilder(context, TaskDatabase.class, DATABASE_NAME).build();
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


    /*Datenbankoperationen laufen jeweils in einem separaten Thread*/
    private static abstract class DBOperationThread implements Runnable{

        void start() {
            Executors.newSingleThreadExecutor().submit(this);
        }
    }

    /*Runnable für das Einfügen eines einzelnen Tasks in die Datenbank*/
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

    /*Runnable für das Updaten eines einzelnen Tasks*/
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

    /*Runnable für das Laden aller Tasks aus der Datenbank*/
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
