package de.ur.mi.android.demos.todo.database;
import androidx.room.TypeConverter;
import java.util.Date;
import java.util.UUID;
import de.ur.mi.android.demos.todo.tasks.Task;

public class TaskAttributeTypeConverter {


    // TypeConverter for UUID <-> String
    @TypeConverter
    public static UUID stringToUUID(String taskID_string) {
        return taskID_string == null ? null : UUID.fromString(taskID_string);
    }

    @TypeConverter
    public static String UUID_toString(UUID taskID) {
        return taskID == null ? null : taskID.toString();
    }


    // TypeConverter für TaskState Enum <-> Integer
    @TypeConverter
    public static Task.TaskState intToTaskState(Integer taskStateInt) {
        return Task.TaskState.values()[taskStateInt];
    }

    @TypeConverter
    public static Integer taskStateToInt(Task.TaskState taskState) {
        return taskState.ordinal();
    }
}
