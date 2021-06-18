package de.ur.mi.android.demos.todo;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import de.ur.mi.android.demos.todo.tasks.Task;
import de.ur.mi.android.demos.todo.tasks.TaskManager;
import de.ur.mi.android.demos.todo.ui.TaskListRecyclerAdapterLongClick;

public class MainActivity extends AppCompatActivity implements
        TaskListRecyclerAdapterLongClick.TaskLongClickedListener,
        TaskListRecyclerAdapterLongClick.TaskSelectedListener,
        TaskManager.TaskManagerListener{

    public TaskManager taskManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTaskManager();
        initUI();
    }

    private void initTaskManager() {
        taskManager = new TaskManager(this, this);
    }

    private void initUI() {
        setContentView(R.layout.activity_main);
        initFragments();
    }

    private void initFragments(){
        // TODO: Fragmente initialisieren
    }

    @Override
    public void onTaskListUpdated() {
        // TODO: dem TaskListFragment die neuen Daten übergeben, sodass diese korrekt in der RecyclerView angezeigt werden können
    }

    @Override
    public void onTaskLongClicked(Task task) {
        taskManager.toggleTaskStateForId(task.getID());
    }

    @Override
    public void onTaskSelected(Task task) {
        // TODO: ausgewählten Task in die Detailansicht laden
    }
}