package de.ur.mi.android.demos.todo;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import de.ur.mi.android.demos.todo.fragments.CreateTaskDialogFragment;
import de.ur.mi.android.demos.todo.fragments.DetailFragment;
import de.ur.mi.android.demos.todo.fragments.TaskListFragment;
import de.ur.mi.android.demos.todo.tasks.Task;
import de.ur.mi.android.demos.todo.tasks.TaskManager;
import de.ur.mi.android.demos.todo.ui.TaskListRecyclerAdapter;


public class MainActivity extends AppCompatActivity implements
        TaskListRecyclerAdapter.AdapterClickListener,
        TaskManager.TaskManagerListener,
        CreateTaskDialogFragment.OnTaskCreationListener {

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


    // Lädt das DetailFragment in den Fragment-Container
    private void showDetailFragment(Task task){
        DetailFragment df = new DetailFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container_task_list, df)
                .addToBackStack(null)
                .commit(); // commit führt die Transaktion nicht sofort durch
        fragmentManager.executePendingTransactions(); // ausstehende Transaktionen sofort durchführen
        df.displayTask(task);
    }

    @Override
    public void onAdapterClick(Task task) {
        if(detailFragment != null){
            detailFragment.displayTask(task);
        }
        else{
            showDetailFragment(task);
        }
    }

    @Override
    public void onAdapterLongClick(Task task) {
        taskManager.toggleTaskStateForId(task.getID());
    }
}