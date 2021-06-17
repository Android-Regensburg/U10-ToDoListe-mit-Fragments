package de.ur.mi.android.demos.todo;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import de.ur.mi.android.demos.todo.fragments.CreateTaskDialogFragment;
import de.ur.mi.android.demos.todo.fragments.DetailFragment;
import de.ur.mi.android.demos.todo.fragments.TaskListFragment;
import de.ur.mi.android.demos.todo.tasks.Task;
import de.ur.mi.android.demos.todo.tasks.TaskManager;
import de.ur.mi.android.demos.todo.ui.TaskListRecyclerAdapterLongClick;


public class MainActivity extends AppCompatActivity implements
        TaskListRecyclerAdapterLongClick.TaskLongClickedListener,
        TaskListRecyclerAdapterLongClick.TaskSelectedListener,
        TaskManager.TaskManagerListener,
        CreateTaskDialogFragment.OnTaskCreationListener {

    public static final String TASK_KEY = "task";
    public TaskManager taskManager;
    private DetailFragment detailFragment;
    private TaskListFragment taskListFragment;

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
        // detailFragment ist null, falls container_detail nicht im XML Layout gefunden wird
        detailFragment = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.container_detail);
        taskListFragment = (TaskListFragment) getSupportFragmentManager().findFragmentById(R.id.container_task_list);
    }

    @Override
    public void onTaskListUpdated() {
        taskListFragment.updateAdapterTaskList(taskManager.getCurrentTasks());
    }

    @Override
    public void onTaskCreated(Task task) {
        taskManager.addTask(task);
    }

    @Override
    public void onTaskLongClicked(Task task) {
        taskManager.toggleTaskStateForId(task.getID());
    }

    @Override
    public void onTaskSelected(Task task) {
        if(detailFragment != null){
            detailFragment.displayTask(task);
        }
        else{
            showDetailFragment(task);
        }
    }

    // Lädt das DetailFragment in den Fragment-Container
    private void showDetailFragment(Task task){
        Bundle data = new Bundle();
        // Die Task Klasse implementiert das Serializable Interface und kann daher dem Bundle übergeben werden
        data.putSerializable(TASK_KEY, task);
        DetailFragment detailFragment = new DetailFragment();
        // ausgewählten Task an das Fragment übergeben, sodass das UI diesen Task anzeigen kann
        detailFragment.setArguments(data);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // ersetzen des aktuellen Fragments im Container durch das DetailFragment
        transaction.replace(R.id.container_task_list, detailFragment);
        transaction.addToBackStack(null);
        transaction.commit(); // Führt die Transaktion durch
    }
}