package de.ur.mi.android.demos.todo.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import de.ur.mi.android.demos.todo.R;
import de.ur.mi.android.demos.todo.tasks.Task;

public class CreateTaskDialogFragment extends DialogFragment {

    private EditText inputTitle, inputDescription;
    private Button cancelBtn, createBtn;
    private OnTaskCreationListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (OnTaskCreationListener) getActivity();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View root = inflater.inflate(R.layout.fragment_create_task, null);
        initUI(root);
        builder.setView(root);
        return builder.create();
    }

    private void initUI(View view){
        inputTitle = view.findViewById(R.id.edittext_task_name);
        inputDescription = view.findViewById(R.id.edittext_task_description);
        cancelBtn = view.findViewById(R.id.cancel_btn);
        createBtn = view.findViewById(R.id.create_btn);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss(); // close dialog fragment
            }
        });
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(createTask()){
                    dismiss();
                }
            }
        });
    }

    // returns true if creation successfull
    private boolean createTask(){
        String description = inputDescription.getText().toString().trim();
        String title = inputTitle.getText().toString().trim();
        if(!description.isEmpty() && !title.isEmpty()){
            Task task = new Task(title, description);
            listener.onTaskCreated(task);
            return true;
        }
        else{
            Toast.makeText(getContext(), "Du hast nicht alles ausgefüllt!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public interface OnTaskCreationListener{
        void onTaskCreated(Task task);
    }
}
