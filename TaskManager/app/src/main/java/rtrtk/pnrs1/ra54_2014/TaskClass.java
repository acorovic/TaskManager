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
    private DatePicker taskDate;
    private TimePicker taskTime;
    private boolean taskReminder;
    private Priority taskPriority;

    public TaskClass(String taskName, String taskDescription, /*DatePicker taskDate, TimePicker taskTime,*/ boolean taskReminder, Priority taskPriority) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        //this.taskDate = taskDate;
        //this.taskTime = taskTime;
        this.taskReminder = taskReminder;
        this.taskPriority = taskPriority;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public void setTaskDate(DatePicker taskDate) {
        this.taskDate = taskDate;
    }

    public void setTaskTime(TimePicker taskTime) {
        this.taskTime = taskTime;
    }

    public void setTaskReminde(boolean taskReminder) {
        this.taskReminder = taskReminder;
    }

    public void setTaskPriority(Priority taskPriority) {
        this.taskPriority = taskPriority;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public DatePicker getTaskDate() {
        return taskDate;
    }

    public TimePicker getTaskTime() {
        return taskTime;
    }

    public boolean isTaskReminder() {
        return taskReminder;
    }

    public Priority getTaskPriority() {
        return taskPriority;
    }
}
