package de.ur.mi.android.demos.todo.fragments;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import de.ur.mi.android.demos.todo.MainActivity;
import de.ur.mi.android.demos.todo.R;
import de.ur.mi.android.demos.todo.tasks.Task;
import de.ur.mi.android.demos.todo.ui.TaskListRecyclerAdapterLongClick;

public class TaskListFragment extends Fragment {

    private RecyclerView tasksRecyclerView;
    private FloatingActionButton addTaskBtn;
    private TaskListRecyclerAdapterLongClick adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);
        initUI(view);
        return view;
    }

    private void initUI(View view){
        addTaskBtn = view.findViewById(R.id.add_task_fab);
        tasksRecyclerView = view.findViewById(R.id.tasks_recycler_view);
        adapter = new TaskListRecyclerAdapterLongClick((MainActivity)getActivity(), (MainActivity)getActivity());
        tasksRecyclerView.setAdapter(adapter);
        addTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CreateTaskDialogFragment().show(getChildFragmentManager(), null);
            }
        });
    }

    public void updateAdapterTaskList(ArrayList<Task> taskList){
        if(adapter != null){
            adapter.setTasks(taskList);
        }
    }
}
