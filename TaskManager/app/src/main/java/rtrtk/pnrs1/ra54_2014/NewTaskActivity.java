package rtrtk.pnrs1.ra54_2014;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class NewTaskActivity extends AppCompatActivity {

    private boolean editTextsFilled;
    private boolean priorityPressed;
    private boolean isPreviewMode;
    private TaskClass.Priority taskPriority;

    private void setPriorityButton() {
        switch (taskPriority) {
            case LOW:
                Button buttonLowPriority = (Button) findViewById(R.id.buttonLowPriority);
                buttonLowPriority.setEnabled(false);
                buttonLowPriority.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(NewTaskActivity.this, R.color.colorLowPriorityDisabled)));
                break;
            case MEDIUM:
                Button buttonMediumPriority = (Button) findViewById(R.id.buttonMediumPriority);
                buttonMediumPriority.setEnabled(false);
                buttonMediumPriority.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(NewTaskActivity.this, R.color.colorMediumPriorityDisabled)));
                break;
            case HIGH:
                Button buttonHighPriority = (Button) findViewById(R.id.buttonHighPriority);
                buttonHighPriority.setEnabled(false);
                buttonHighPriority.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(NewTaskActivity.this, R.color.colorHighPriorityDisabled)));
                break;
            
        }
    }

    private TextWatcher editTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            checkFilledFields();
        }
        @Override
        public void afterTextChanged(Editable editable) {
            checkFilledFields();
        }
    };

    private void checkFilledFields() {
        EditText editTaskName = (EditText)findViewById(R.id.editTaskName);
        EditText editTaskDescription = (EditText)findViewById(R.id.editTaskDescription);

        String s1 = editTaskName.getText().toString();
        String s2 = editTaskDescription.getText().toString();

        Button addTask = (Button)findViewById(R.id.buttonAddTask);

        if(!s1.equals("") && !s2.equals("")) {
            editTextsFilled = true;
            if(priorityPressed) {
                addTask.setEnabled(true);
            }
        } else {
            editTextsFilled = false;
            addTask.setEnabled(false);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        final Button buttonLowPriority = (Button)findViewById(R.id.buttonLowPriority);
        final Button buttonMediumPriority = (Button) findViewById(R.id.buttonMediumPriority);
        final Button buttonHighPriority = (Button)findViewById(R.id.buttonHighPriority);
        final Button buttonAddTask = (Button) findViewById(R.id.buttonAddTask);
        final Button rejectTask = (Button) findViewById(R.id.buttonRejectTask);
        final CheckBox reminderTask = (CheckBox) findViewById(R.id.reminderTask);
        final EditText editTaskName = (EditText)findViewById(R.id.editTaskName);
        final EditText editTaskDescription = (EditText)findViewById(R.id.editTaskDescription);
        final TimePicker timePicker = (TimePicker)findViewById(R.id.timePicker);
        final DatePicker datePicker = (DatePicker)findViewById(R.id.datePicker);
        datePicker.setMinDate(new Date().getTime());

        Intent mIntent = getIntent();
        if(mIntent.hasExtra(getResources().getString(R.string.taskIntent))) {
            Calendar temp;
            isPreviewMode = true;
            // Get context of task back
            TaskClass mTask = (TaskClass) mIntent.getSerializableExtra(getResources().getString(R.string.taskIntent));
            buttonAddTask.setText(R.string.updateTaskButton);
            rejectTask.setText(R.string.deleteTaskButton);
            editTaskName.setText(mTask.getTaskName());
            editTaskDescription.setText(mTask.getTaskDescription());
            temp = mTask.getTaskTimeDate();
            datePicker.updateDate(temp.get(Calendar.YEAR), temp.get(Calendar.MONTH), temp.get(Calendar.DAY_OF_MONTH));
            timePicker.setHour(temp.get(Calendar.HOUR_OF_DAY));
            timePicker.setMinute(temp.get(Calendar.MINUTE));
            reminderTask.setChecked(mTask.isTaskReminder());
            taskPriority = mTask.getTaskPriority();
            setPriorityButton();

            editTextsFilled = true;
            priorityPressed = true;
            buttonAddTask.setEnabled(true);
        } else {
            isPreviewMode = false;
            editTextsFilled = false;
            priorityPressed = false;
        }


        editTaskName.addTextChangedListener(editTextWatcher);
        editTaskDescription.addTextChangedListener(editTextWatcher);

        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar date = Calendar.getInstance();
                if(datePicker.getDayOfMonth() == date.get(Calendar.DAY_OF_MONTH)
                        && datePicker.getMonth() == date.get(Calendar.MONTH)
                        && datePicker.getYear() == date.get(Calendar.YEAR)
                        && (timePicker.getHour() < date.get(Calendar.HOUR_OF_DAY) || (timePicker.getHour() == date.get(Calendar.HOUR_OF_DAY) && timePicker.getMinute() <= date.get(Calendar.MINUTE)))) {

                    Toast toast = Toast.makeText(NewTaskActivity.this, R.string.timeDateSpinnerError, Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    if(!isPreviewMode) {
                        Intent intent = new Intent(NewTaskActivity.this, MainActivity.class);
                        date.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), timePicker.getHour(), timePicker.getMinute());
                        TaskClass task = new TaskClass(editTaskName.getText().toString(), editTaskDescription.getText().toString(), date, reminderTask.isChecked(), taskPriority, 0);
                        intent.putExtra(getResources().getString(R.string.result), task);
                        setResult(Activity.RESULT_OK, intent);
                        finish();
                    } else {
                        // Different than RESULT OK AND RESULT CANCEL
                        //setResult(1);
                        Intent intent = new Intent(NewTaskActivity.this, MainActivity.class);
                        date.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), timePicker.getHour(), timePicker.getMinute());
                        TaskClass task = new TaskClass(editTaskName.getText().toString(), editTaskDescription.getText().toString(), date, reminderTask.isChecked(), taskPriority);
                        intent.putExtra(getResources().getString(R.string.result), task);
                        setResult(3, intent);
                        finish();
                    }
                }
            }
        });

        rejectTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isPreviewMode) {
                    // Different than RESULT OK AND RESULT CANCEL
                    setResult(1);
                    finish();
                } else {
                    // 2 -> REMOVE
                    setResult(2);
                    finish();
                }
            }
        });

        buttonLowPriority.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonLowPriority.setEnabled(false);
                buttonLowPriority.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(NewTaskActivity.this, R.color.colorLowPriorityDisabled)));
                buttonMediumPriority.setEnabled(true);
                buttonMediumPriority.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(NewTaskActivity.this, R.color.colorMediumPriority)));
                buttonHighPriority.setEnabled(true);
                buttonHighPriority.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(NewTaskActivity.this, R.color.colorHighPriority)));

                priorityPressed = true;
                taskPriority = TaskClass.Priority.LOW;

                Toast toast = Toast.makeText(NewTaskActivity.this, R.string.lowPriorityToast, Toast.LENGTH_SHORT);
                toast.show();

                if(editTextsFilled) {
                    buttonAddTask.setEnabled(true);
                } else {
                    buttonAddTask.setEnabled(false);
                }
            }
        });

        buttonMediumPriority.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonMediumPriority.setEnabled(false);
                buttonMediumPriority.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(NewTaskActivity.this, R.color.colorMediumPriorityDisabled)));
                buttonLowPriority.setEnabled(true);
                buttonLowPriority.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(NewTaskActivity.this, R.color.colorLowPriority)));
                buttonHighPriority.setEnabled(true);
                buttonHighPriority.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(NewTaskActivity.this, R.color.colorHighPriority)));

                priorityPressed = true;
                taskPriority = TaskClass.Priority.MEDIUM;

                Toast toast = Toast.makeText(NewTaskActivity.this, R.string.mediumPriorityToast, Toast.LENGTH_SHORT);
                toast.show();

                if(editTextsFilled) {
                    buttonAddTask.setEnabled(true);
                } else {
                    buttonAddTask.setEnabled(false);
                }
            }
        });

        buttonHighPriority.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonHighPriority.setEnabled(false);
                buttonHighPriority.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(NewTaskActivity.this, R.color.colorHighPriorityDisabled)));
                buttonMediumPriority.setEnabled(true);
                buttonMediumPriority.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(NewTaskActivity.this, R.color.colorMediumPriority)));
                buttonLowPriority.setEnabled(true);
                buttonLowPriority.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(NewTaskActivity.this, R.color.colorLowPriority)));

                priorityPressed = true;
                taskPriority = TaskClass.Priority.HIGH;

                Toast toast = Toast.makeText(NewTaskActivity.this, R.string.highPriorityToast, Toast.LENGTH_SHORT);
                toast.show();

                if(editTextsFilled) {
                    buttonAddTask.setEnabled(true);
                } else {
                    buttonAddTask.setEnabled(false);
                }
            }
        });
    }
}
