package de.ur.mi.android.demos.todo.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import de.ur.mi.android.demos.todo.MainActivity;
import de.ur.mi.android.demos.todo.R;
import de.ur.mi.android.demos.todo.tasks.Task;

public class DetailFragment extends Fragment {

    private TextView titleText, descriptionText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_view, container, false);
        initUI(view);
        receiveExtras();
        return view;
    }

    private void receiveExtras(){
        Bundle extras = getArguments();
        if(extras != null){
            Task task = (Task) extras.getSerializable(MainActivity.TASK_KEY);
            if(task != null){
                displayTask(task);
            }
        }
    }

    private void initUI(View view){
        titleText = view.findViewById(R.id.detail_title);
        descriptionText = view.findViewById(R.id.detail_description);
    }

    public void displayTask(Task task){
        titleText.setText(task.getTitle());
        descriptionText.setText(task.getDescription());
    }
}
