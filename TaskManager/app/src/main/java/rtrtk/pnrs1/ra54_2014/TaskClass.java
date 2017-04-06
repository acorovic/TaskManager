package rtrtk.pnrs1.ra54_2014;

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
    private Calendar taskDate;
    private TimePicker taskTime;
    private boolean taskReminde;
    private Priority taskPrioirty;

    public TaskClass(String taskName, String taskDescription, Calendar taskDate, TimePicker taskTime, boolean taskReminde, Priority taskPrioirty) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskDate = taskDate;
        this.taskTime = taskTime;
        this.taskReminde = taskReminde;
        this.taskPrioirty = taskPrioirty;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public void setTaskDate(Calendar taskDate) {
        this.taskDate = taskDate;
    }

    public void setTaskTime(TimePicker taskTime) {
        this.taskTime = taskTime;
    }

    public void setTaskReminde(boolean taskReminde) {
        this.taskReminde = taskReminde;
    }

    public void setTaskPrioirty(Priority taskPrioirty) {
        this.taskPrioirty = taskPrioirty;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public Calendar getTaskDate() {
        return taskDate;
    }

    public TimePicker getTaskTime() {
        return taskTime;
    }

    public boolean isTaskReminde() {
        return taskReminde;
    }

    public Priority getTaskPrioirty() {
        return taskPrioirty;
    }
}
