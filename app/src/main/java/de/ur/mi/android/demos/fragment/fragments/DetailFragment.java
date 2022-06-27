package de.ur.mi.android.demos.fragment.fragments;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import de.ur.mi.android.demos.fragment.R;
import de.ur.mi.android.demos.fragment.tasks.Task;

/**Fragment für die Detailansicht eines einzelnen Tasks*/
public class DetailFragment extends Fragment {

    private TextView titleText, descriptionText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_view, container, false);
        initUI(view);
        return view;
    }

    private void initUI(View view){
        titleText = view.findViewById(R.id.detail_title);
        descriptionText = view.findViewById(R.id.detail_description);
    }

    // Updatet das UI für einen ausgewählten Task
    public void displayTask(Task task){
        titleText.setText(task.getTitle());
        descriptionText.setText(task.getDescription());
    }
}
