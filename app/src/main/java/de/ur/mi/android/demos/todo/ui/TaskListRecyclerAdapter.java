package de.ur.mi.android.demos.todo.ui;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import de.ur.mi.android.demos.todo.R;
import de.ur.mi.android.demos.todo.tasks.Task;
import de.ur.mi.android.demos.todo.ui.viewholder.TaskListViewHolder;

/**
 * Dieser Adapter verbindet eine ArrayList von Task-Objekten mit einem RecyclerView im UserInterface. Der
 * Adapter stellt dem RecyclerView die notwendigen Views zu Darstellung der einzelnen Aufgaben bereit. Dabei
 * unterscheidet der Adpater zwischen offen und bereits erledigten Aufgaben und gibt je nach Zustand der
 * Aufgabe andere Views an das ListView weiter. Dadurch werden die beiden "Typen" von Aufgaben im
 * User Interface auf unterschiedliche Art und Weise dargestellt.
 */

public class TaskListRecyclerAdapter
        extends RecyclerView.Adapter<TaskListViewHolder>
        implements TaskListViewHolder.TaskListViewHolderLongClickListener,
        TaskListViewHolder.TaskListViewHolderClickListener   {

    /* Konstanten, die unterschiedliche Typen von Einträgen des angeschlossenen RecyclerViews identifizieren.
     * Das ist dann notwendig, wenn nicht alle Einträge einer Liste auf die gleiche Art und Weise dargestellt werden sollen,
     * in unserem Fall können wir so zwischen offenen und geschlossenen Aufgaben unterscheiden. In einer überschriebenen Methode
     * des Adapters werden den Task-Objekten jeweils einer dieser Typen zugeordnet. Diese "ID" wird dann in anderen Methoden des Adapters
     * verwendet, um die passenden Views für diese Art von Aufgabe zu erstellen.
     */
    private static final int VIEW_TYPE_FOR_OPEN_ITEMS = 1; // Repräsentiert eine noch offene Aufgabe
    private static final int VIEW_TYPE_FOR_CLOSED_ITEMS = 2; // Repräsentiert eine bereits geschlossene Aufgabe

    // Listener, der über Klicks auf einzelne Einträge im RecyclerView informiert werden soll
    private final TaskLongClickedListener longClickedListener;
    private final TaskSelectedListener selectedListener;

    /**
     * Achtung: Dieser Adapter nutzt eine Kopie der Aufgabenliste, die vom TaskManager verwaltet wird. Ändern sich
     * dort die Inhalte, wird zuerst die Activity informiert, die dem Adapter dann eine aktualisierte Kopie der
     * Aufgabenliste übergibt.
     */

    // Liste der Aufgaben, die aktuell vom Adapter verwaltet werden
    private ArrayList<Task> tasks;

    /**
     * Erzeugt einen neuen Adapter
     *
     * @param longClickedListener Listener, der über die Interaktionen der Nutzer*innen mit den Listeneinträgen informiert werden soll
     */
    public TaskListRecyclerAdapter(TaskLongClickedListener longClickedListener, TaskSelectedListener selectedListener) {
        this.longClickedListener = longClickedListener;
        this.selectedListener = selectedListener;
        this.tasks = new ArrayList<>();
    }

    /**
     * Überschreibt die Aufabenliste des Adapters mit der neuen, als Parameter übergebenen Liste und informiert im Anschluss
     * das angeschlossene RecyclerView
     *
     * @param tasks
     */
    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
        this.notifyDataSetChanged();
    }

    /**
     * Gibt den Typ von View zurück, der für die Darstellung der Aufgabe an der übergebenen Listenposition verwendet werden soll. Diese
     * Typen-Beschreibung wird dann an anderer Stelle verwendet, um das passende View zur Darstellung zu erstellen.
     * Die möglichen Typen werden über int-Werte unterschieden, zur besseren lesbarkeit nutzen wir Konstanten, die zu Beginn der Adaoterklasse
     * definiert wurden.
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        Task task = tasks.get(position);
        if (task == null || task.isClosed()) {
            return VIEW_TYPE_FOR_CLOSED_ITEMS; // Typ für bereits geschlossene Aufgaben
        }
        return VIEW_TYPE_FOR_OPEN_ITEMS; // Typ für noch offene Aufgaben
    }

    /**
     * Wird aufgerufen, wenn ein neuer ViewHolder benötigt wird
     *
     * @param parent
     * @param viewType Anzeige-Typ, der benötigt wird (wird automatisch über Aufruf der getItemViewType-Methode ausgewählt)
     * @return Der erstellte ViewHOlder
     */
    @NonNull
    @Override
    public TaskListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TaskListViewHolder vh = createViewHolderForType(parent, viewType);
        return vh;
    }

    /**
     * Wird aufgerufen, wenn die Inhalte eines ViewHolders aktualisiert werden sollen
     *
     * @param holder   ViewHolder, der aktualisiert werden soll
     * @param position Position des zu aktualisierenden ViewHolders innerhalb des RecyclerViews (indirekter Verweis auf den zugehörigen Datensatz in der ArrayList)
     */
    @Override
    public void onBindViewHolder(@NonNull TaskListViewHolder holder, int position) {
        // Wir identifzieren über den Positionsparameter, für welchen Datensatz der ViewHolder aktualisiert werden soll
        Task task = tasks.get(position);
        // Referenzieren der einzelnen TextViews im übergebenen View
        TextView title = holder.taskView.findViewById(R.id.list_item_title);
        TextView createdAt = holder.taskView.findViewById(R.id.list_item_creationDate);
        // Auslesen der Task-Eigenschaften und übertragen in die jeweiligen TextViews
        title.setText(task.getTitle());
        createdAt.setText(getFormattedDateForUI(task.getCreationDate()));
    }

    /**
     * Wird vom RecyclerView aufgerufen, um zu erfahren, wie viele Datensätze im Adapter verwaltet werden
     *
     * @return Anazhl der Einträge/Datensätze im Adapter (hier: Anzahl der Tasks in der ArrayList)
     */
    @Override
    public int getItemCount() {
        return tasks.size();
    }

    /**
     * Erzeugt einen ViewHolder für eine Aufgabe des übergebenen Typs. Hier wird nur der leere View erzeugt,
     * es werden noch keine Inhalte einer konkreten Aufgaben in dem View eingetragen.
     *
     * @param parent   Das Elternelement im UI, zu dem der neue View hinzugefügt werden kann, z.B. das RecyclerView selber
     * @param viewType Typ des Views, das für die Darstellung dieser Aufgabe verwendet werden soll (siehe: getItemViewType)
     * @return Der erstellte VieHolder
     */
    private TaskListViewHolder createViewHolderForType(ViewGroup parent, int viewType) {
        View v;
        if (viewType == VIEW_TYPE_FOR_OPEN_ITEMS) {
            // Erstellen des View für offene Aufgaben
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list_item, parent, false);
        } else {
            // Alternativ: Erstellen des View für abgeschlossene Aufgaben
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list_item_done, parent, false);
        }
        // Erstellen des ViewHolders auf Basis der oben ausgewählten View
        TaskListViewHolder vh = new TaskListViewHolder(v, this, this);
        return vh;
    }

    /**
     * Wandelt das übergeben Datum in einen sauber formatiertern String zur Darstellung im UI um.
     * Datumsangaben werden in der Regel im Format Tag. Monat zurückgegeben. Liegt das übergebene Datum
     * weniger als 24h zurück, wird statt dessen das Format  Stunden:Minuten verwendet.
     *
     * @param date Datum, das formatiert werden soll
     * @return Formatierte Stringrepräsentation des übergebenen Datums
     */
    private String getFormattedDateForUI(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        Date now = new Date();
        long timeDifferenceInMilliseconds = Math.abs(now.getTime() - date.getTime());
        long timeDifferenceInDays = TimeUnit.DAYS.convert(timeDifferenceInMilliseconds, TimeUnit.MILLISECONDS);
        if (timeDifferenceInDays > 0) {
            sdf = new SimpleDateFormat("dd. MMMM", Locale.getDefault());
        }
        return sdf.format(date);
    }

    /**
     * Fängt lange Klicks auf einzelne ViewHolder ab. Der Adapter implementiert das TaskListViewHolderListener-Interface
     * und registriert sich selbst als Listener auf allen neuen ViewHolder. Diese informieren den Adapter über den Aufruf
     * dieser Methode, wenn sie von den Nutzer*innen angeklickt wurden.
     *
     * @param position Position dieses ViewHolders innerhalb des angeschlossenen RecyclerView
     */
    @Override
    public void onViewHolderLongClicked(int position) {
        Task task = tasks.get(position);
        if (task != null) {
            longClickedListener.onTaskLongClicked(task);
        }
    }

    /**
     * Fängt normale Klicks auf einzelne ViewHolder ab. Der Adapter implementiert das TaskListViewHolderListener-Interface
     * und registriert sich selbst als Listener auf allen neuen ViewHolder. Diese informieren den Adapter über den Aufruf
     * dieser Methode, wenn sie von den Nutzer*innen angeklickt wurden.
     *
     * @param position Position dieses ViewHolders innerhalb des angeschlossenen RecyclerView
     */
    @Override
    public void onViewHolderClicked(int position) {
        Task task = tasks.get(position);
        if (task != null) {
            selectedListener.onTaskSelected(task);
        }
    }

    /**
     * Interface für Observer, die über einen langen Klick auf ein Element der RecyclerView informiert werden sollen.
     * Wird von der MainActiviy implementiert, die sich selbst als Listener beim Erstellen dieses Adapters übergibt.
     */
    public interface TaskLongClickedListener {
        void onTaskLongClicked(Task task);
    }

    /***
     * Interface für Observer, die über einen einfachen Klick auf ein Element der RecyclerView informiert werden sollen.
     * Wir von der MainActivity implementiert, die sich selbst als Listener beim Erstellen dieses Adapters übergibt.
     */
    public interface TaskSelectedListener{
        void onTaskSelected(Task task);
    }

}

