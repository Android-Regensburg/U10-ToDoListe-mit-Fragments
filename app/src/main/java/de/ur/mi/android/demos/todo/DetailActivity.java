package de.ur.mi.android.demos.todo;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import de.ur.mi.android.demos.todo.fragments.DetailFragment;
import de.ur.mi.android.demos.todo.tasks.Task;

public class DetailActivity extends AppCompatActivity {

    private DetailFragment detailFragment;
    private Task task;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        receiveIntentData();
        initUI();
    }

    @Override
    protected void onStart() {
        super.onStart();
        detailFragment.displayTask(task);
    }

    private void receiveIntentData(){
        task = (Task) getIntent().getSerializableExtra(MainActivity.TASK_KEY);
    }

    private void initUI(){
        setContentView(R.layout.activity_detail);
        detailFragment = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.container_detail);
    }
}
