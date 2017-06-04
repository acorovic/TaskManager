package rtrtk.pnrs1.ra54_2014;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Calendar;

import static rtrtk.pnrs1.ra54_2014.TaskClass.Priority.HIGH;
import static rtrtk.pnrs1.ra54_2014.TaskClass.Priority.LOW;
import static rtrtk.pnrs1.ra54_2014.TaskClass.Priority.MEDIUM;


/**
 * Created by alexa on 4.6.17..
 */

public class TaskDbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "tasks.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "task";
    public static final String COLUMN_TASK_NAME = "TaskName";
    public static final String COLUMN_TASK_DESCRIPTION = "TaskDescription";
    public static final String COLUMN_TASK_TIME_DATE = "TaskTimeDate";
    public static final String COLUMN_TASK_PRIORITY = "TaskPriority";
    public static final String COLUMN_TASK_REMINDER = "TaskReminder";

    private SQLiteDatabase mDb = null;

    public TaskDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_TASK_NAME + " TEXT, " +
                COLUMN_TASK_DESCRIPTION + " TEXT, " +
                COLUMN_TASK_TIME_DATE + " LONG," +
                COLUMN_TASK_PRIORITY +  " INTEGER, " +
                COLUMN_TASK_REMINDER + " INTEGER);" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(TaskClass task) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK_NAME, task.getTaskName());
        values.put(COLUMN_TASK_DESCRIPTION, task.getTaskDescription());
        values.put(COLUMN_TASK_TIME_DATE, task.getTaskTimeDate().getTimeInMillis());

        Integer priority = 0;
        switch (task.getTaskPriority()) {
            case HIGH:
                priority = 2;
                break;
            case MEDIUM:
                priority = 1;
                break;
            case LOW:
                priority = 0;
                break;
        }

        values.put(COLUMN_TASK_PRIORITY, priority);
        values.put(COLUMN_TASK_REMINDER, task.isTaskReminder());

        db.insert(TABLE_NAME, null, values);
        close();
    }

    public TaskClass[] readTasks() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null, null);

        if (cursor.getCount() <= 0) {
            return null;
        }

        TaskClass[] tasks = new TaskClass[cursor.getCount()];
        int i = 0;
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            tasks[i++] = createTask(cursor);
        }

        close();
        return tasks;
    }

    public void deleteTask(String name) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_TASK_NAME + "=?", new String[] {name});
        close();
    }

    private TaskClass createTask(Cursor cursor) {
        String taskName = cursor.getString(cursor.getColumnIndex(COLUMN_TASK_NAME));
        String taskDescription = cursor.getString(cursor.getColumnIndex(COLUMN_TASK_DESCRIPTION));
        long taskTimeDate = cursor.getLong(cursor.getColumnIndex(COLUMN_TASK_TIME_DATE));
        Calendar taskCalendar = Calendar.getInstance();
        taskCalendar.setTimeInMillis(taskTimeDate);
        int priority = cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_PRIORITY));
        TaskClass.Priority taskPriority = LOW;
        switch (priority) {
            case 2:
                taskPriority = HIGH;
                break;
            case 1:
                taskPriority = MEDIUM;
                break;
            case 0:
                taskPriority = LOW;
                break;
        }

        int isReminded = cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_REMINDER));

        return new TaskClass(taskName, taskDescription, taskCalendar, isReminded == 1, taskPriority);
    }
}
