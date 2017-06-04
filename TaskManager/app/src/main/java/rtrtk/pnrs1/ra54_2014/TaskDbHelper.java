package rtrtk.pnrs1.ra54_2014;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by alexa on 4.6.17..
 */

public class TaskDbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "tasks.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "task";
    public static final String COLUMN_TASK_NAME = "TaskName";
    public static final String COLUMN_TASK_DESCRIPTION = "TaskDescription";
    public static final String COLUMN_TASK_DATE = "TaskDate";
    public static final String COLUMN_TASK_TIME = "TaskTime";
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
                COLUMN_TASK_DATE  + " TEXT, " +
                COLUMN_TASK_TIME  + " TEXT, " +
                COLUMN_TASK_PRIORITY +  " INTEGER, " +
                COLUMN_TASK_REMINDER + " BIT);" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(TaskClass task) {
        SQLiteDatabase db = getWritableDatabase();
        TaskAdapter helper = new TaskAdapter();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK_NAME, task.getTaskName());
        values.put(COLUMN_TASK_DESCRIPTION, task.getTaskDescription());
        values.put(COLUMN_TASK_DATE, helper.checkDate(task.getTaskTimeDate()));
        values.put(COLUMN_TASK_TIME, helper.checkTime(task.getTaskTimeDate()));

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

    private TaskClass createTask(Cursor cursor) {
        String taskName = cursor.getString(cursor.getColumnIndex(COLUMN_TASK_NAME));
        String taskDescription = cursor.getString(cursor.getColumnIndex(COLUMN_TASK_DESCRIPTION));
        String index = cursor.getString(cursor.getColumnIndex(COLUMN_INDEX));

        return new Student(firstName, lastName, index);
    }
}
