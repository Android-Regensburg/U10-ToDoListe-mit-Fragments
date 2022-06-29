package de.ur.mi.android.demos.todo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import de.ur.mi.android.demos.todo.tasks.Task;
import de.ur.mi.android.demos.todo.tasks.TaskManager;
import de.ur.mi.android.demos.todo.ui.TaskListRecyclerAdapter;

public class MainActivity extends AppCompatActivity implements
        TaskListRecyclerAdapter.AdapterClickListener,
        TaskManager.TaskManagerListener{

    public TaskManager taskManager;
    private RecyclerView recyclerView;
    private TaskListRecyclerAdapter adapter;
    private EditText taskTitleInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTaskManager();
        initUI();
        taskManager.requestUpdate();
    }

    private void initTaskManager() {
        taskManager = new TaskManager(this, this);
    }

    private void initUI() {
        setContentView(R.layout.activity_main_old);
        initRecyclerView();
        initInputElements();
    }

    private void initRecyclerView(){
        recyclerView = findViewById(R.id.task_list);
        adapter = new TaskListRecyclerAdapter(this);
        recyclerView.setAdapter(adapter);
    }

    private void initInputElements(){
        taskTitleInput = findViewById(R.id.input_text);
        Button addButton = findViewById(R.id.input_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentInput = taskTitleInput.getText().toString().trim();
                onUserInputClicked(currentInput);
            }
        });
    }

    private void onUserInputClicked(String input){
        if (input.length() > 0) {
            Task t = new Task(input);
            taskManager.addTask(t);
            taskTitleInput.setText("");
            taskTitleInput.requestFocus();
        }
    }

    @Override
    public void onTaskListUpdated() {
        adapter.setTasks(taskManager.getCurrentTasks());
    }


    @Override
    public void onAdapterClicked(Task task) {

    }

    @Override
    public void onAdapterLongClicked(Task task) {
        taskManager.toggleTaskStateForId(task.getID());
    }
}