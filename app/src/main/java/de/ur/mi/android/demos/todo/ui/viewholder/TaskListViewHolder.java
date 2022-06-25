package de.ur.mi.android.demos.todo.ui.viewholder;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * ViewHolder für einzelne Einträge des RecyclerViews. Ein ViewHolder umschließt einen einzelnen Eintrag
 * innerhalb eines RecyclerViews. D.h. für jeden Listeneintrag wird ein solcher ViewHolder benötigt, der
 * in sich dann das eigentlichen View beeinhaltet, in dem die Informationen des jeweiligen Task-Objektes
 * dargestellt werden.
 */
public class TaskListViewHolder extends RecyclerView.ViewHolder {

    public final View taskView;
    public ViewHolderClickListener listener;

    /**
     * Wird im TaskListRecyclerAdapter aufgerufen, wenn ein neuer View für die Darstellung eines Eintrags
     * benötigt wird.
     *
     */
    public TaskListViewHolder(@NonNull View itemView, ViewHolderClickListener listener) {
        super(itemView);
        this.listener = listener;
        taskView = itemView;
        taskView.setOnClickListener(new View.OnClickListener() {
            /*
            Bei normalen Klick auf ein Element soll des darunterliegende Task-Objekt im DetailFragment geladen werden
            */
            @Override
            public void onClick(View v) {
                listener.onViewHolderClicked(getAdapterPosition());
            }
        });
        taskView.setOnLongClickListener(new View.OnLongClickListener() {
            /*
            Bei langem Klick auf ein Element soll der Zustand des darunterliegenden Task-Objektes
            getoggelt werden.
            */
            @Override
            public boolean onLongClick(View v) {
                listener.onViewHolderLongClicked(getAdapterPosition());
                return true;
            }
        });
    }

    /**
     * Interface für die Komponenten, die sich für lange Klicks der Nutzer*innen auf diesem ViewHolder bzw. dem
     * umschlossenen View interessieren
     */
    public interface ViewHolderClickListener {
        void onViewHolderClicked(int position);
        void onViewHolderLongClicked(int position);
    }





}
