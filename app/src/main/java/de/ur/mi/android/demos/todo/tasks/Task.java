package de.ur.mi.android.demos.todo.tasks;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Repräsentiert eine Aufgabe auf der ToDo-liste.
 * <p>
 * Aufgaben verfügen über eine eindeutige ID, eine textuelle Beschreibung und ein Erstellungs-
 * datum. Diese Eigenschaften sind nach dem Erzeugen einer Aufgabe nicht veränderbar. Zusätzlich
 * wird für jede Aufgabe festgehalten, ob diese offen oder erledigt ist. Der Zustand kann über
 * öffentliche Methoden geändert werden.
 * <p>
 * Kopien
 * Über die copy-Methode können tiefe (deep) Kopien eines Task-Objekts erstellt werden.
 * <p>
 * Sortierung
 * Die Task-Klasse implementiert das Comparable-Interface das die Sortierung von Task-Objekten
 * ermöglicht. Offene Aufgaben werden vor geschlossenen Aufgaben einsportiert. Aufgaben mit
 * gleichem Status werden nach dem Erstellungsdatum sortiert.
 */
@Entity(tableName = "task_table")
public class Task implements Comparable<Task>, Serializable {

    @PrimaryKey
    @NonNull
    public final UUID id;
    public final String description;
    public final String title;
    @ColumnInfo(name = "created_at")
    public final Date createdAt;
    @ColumnInfo(name = "current_state")
    private TaskState currentState;

    @Ignore
    public Task(String title, String description) {
        this.id = UUID.randomUUID();
        this.createdAt = new Date();
        this.currentState = TaskState.OPEN;
        this.description = description;
        this.title = title;
    }

    @Ignore
    public Task(String title) {
        this.id = UUID.randomUUID();
        this.createdAt = new Date();
        this.currentState = TaskState.OPEN;
        this.description = "";
        this.title = title;
    }

    public Task(String title, String description, UUID id, Date createdAt, TaskState currentState) {
        this.id = id;
        this.createdAt = createdAt;
        this.currentState = currentState;
        this.title = title;
        this.description = description;
    }

    public TaskState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(TaskState currentState) {
        this.currentState = currentState;
    }

    public String getID() {
        return id.toString();
    }

    public String getTitle(){
        return this.title;
    }

    public String getDescription() {
        return description;
    }

    public Date getCreationDate() {
        return getCreationDateCopy();
    }

    public boolean isClosed() {
        return currentState == TaskState.CLOSED;
    }

    public void toggleState(){
        if(currentState == TaskState.CLOSED){
            currentState = TaskState.OPEN;
        }
        else{
            currentState = TaskState.CLOSED;
        }
    }

    public Task copy() {
        Date creationDateFromOriginal = getCreationDateCopy();
        return new Task(title, description, id, creationDateFromOriginal, currentState);
    }

    private Date getCreationDateCopy() {
        return new Date(createdAt.getTime());
    }

    @Override
    public int compareTo(Task otherTask) {
        if (this.isClosed() && !otherTask.isClosed()) {
            // Diese Aufgabe wird, weil bereits erledigt, nach der anderen sortiert
            return 1;
        }
        if (!this.isClosed() && otherTask.isClosed()) {
            // Diese Aufgabe wird, weil noch nicht erledigt, vor der anderen sortiert
            return -1;
        }
        // Die beiden Aufgaben werden auf Basis des Erstellungsdatums sortiert (neuere vor älteren)
        return -this.createdAt.compareTo(otherTask.createdAt);
    }

    public enum TaskState {
        OPEN,
        CLOSED
    }

}
