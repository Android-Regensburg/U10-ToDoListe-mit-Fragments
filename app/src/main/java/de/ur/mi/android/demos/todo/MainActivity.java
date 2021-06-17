package de.ur.mi.android.demos.todo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

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
        taskManager = new TaskManager(this);
    }

    private void initUI() {
        setContentView(R.layout.activity_main);
        detailFragment = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.container_detail);
        taskListFragment = (TaskListFragment) getSupportFragmentManager().findFragmentById(R.id.container_task_list);
        System.out.println("a: " + detailFragment);
        System.out.println("b: " + taskListFragment);


    }

    @Override
    public void onTaskListUpdated() {
        System.out.println("TaskListUpdated");
        taskListFragment.updateAdapterTaskList(taskManager.getCurrentTasks());
    }

    @Override
    public void onTaskCreated(Task task) {
        System.out.println("Task created");
        taskManager.addTask(task);
    }

    @Override
    public void onTaskLongClicked(Task task) {
        System.out.println("Lang geklickt: " + task.getDescription());
    }

    @Override
    public void onTaskSelected(Task task) {
        System.out.println("cklick: " + task.getDescription());
    }
}