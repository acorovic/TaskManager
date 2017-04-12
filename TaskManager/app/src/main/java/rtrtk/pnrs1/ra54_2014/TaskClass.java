package rtrtk.pnrs1.ra54_2014;

import android.widget.DatePicker;
import android.widget.TimePicker;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

/**
 * Created by student on 6.4.2017.
 */


public class TaskClass implements Serializable {
    public enum Priority {LOW, MEDIUM, HIGH};

    private String taskName;
    private String taskDescription;
    private Calendar taskTimeDate;
    private boolean taskReminder;
    private boolean taskFinished;
    private Priority taskPriority;

    public TaskClass(String taskName, String taskDescription, Calendar taskTimeDate, boolean taskReminder, Priority taskPriority) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskTimeDate = taskTimeDate;
        this.taskReminder = taskReminder;
        this.taskPriority = taskPriority;
        this.taskFinished = false;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public void setTaskTimeDate(Calendar taskTimeDate) {
        this.taskTimeDate = taskTimeDate;
    }

    public void setTaskReminde(boolean taskReminder) {
        this.taskReminder = taskReminder;
    }

    public void setTaskPriority(Priority taskPriority) {
        this.taskPriority = taskPriority;
    }

    public void setTaskFinished(boolean taskFinished) { this.taskFinished = taskFinished;}

    public String getTaskName() {
        return taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public Calendar getTaskTimeDate() { return taskTimeDate;}

    public boolean isTaskReminder() {
        return taskReminder;
    }

    public boolean isTaskFinished() {return taskFinished;}

    public Priority getTaskPriority() {
        return taskPriority;
    }
}
