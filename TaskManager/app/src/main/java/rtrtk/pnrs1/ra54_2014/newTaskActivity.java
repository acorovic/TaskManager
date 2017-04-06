package rtrtk.pnrs1.ra54_2014;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class NewTaskActivity extends AppCompatActivity {

    boolean editTextsFilled;
    boolean priorityPressed;

    private TextWatcher editTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
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

        if(!s1.equals("") && !s2.equals("")) {
            editTextsFilled = true;
            if(priorityPressed) {
                Button addTask = (Button)findViewById(R.id.buttonAddTask);
                addTask.setEnabled(true);
            }
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
        Button rejectTask = (Button) findViewById(R.id.buttonRejectTask);
        EditText editTaskName = (EditText)findViewById(R.id.editTaskName);
        EditText editTaskDescription = (EditText)findViewById(R.id.editTaskDescription);

        final DatePicker datePicker = (DatePicker)findViewById(R.id.datePicker);
        datePicker.setMinDate(new Date().getTime());

        editTextsFilled = false;
        priorityPressed = false;

        editTaskName.addTextChangedListener(editTextWatcher);
        editTaskDescription.addTextChangedListener(editTextWatcher);

        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePicker timePicker = (TimePicker)findViewById(R.id.timePicker);
                Calendar date = Calendar.getInstance();
                if(datePicker.getDayOfMonth() == date.get(Calendar.DAY_OF_MONTH)
                        && datePicker.getMonth() == date.get(Calendar.MONTH)
                        && datePicker.getYear() == date.get(Calendar.YEAR)
                        && (timePicker.getHour() < date.get(Calendar.HOUR_OF_DAY) || (timePicker.getHour() == date.get(Calendar.HOUR_OF_DAY) && timePicker.getMinute() <= date.get(Calendar.MINUTE)))) {

                    Toast toast = Toast.makeText(NewTaskActivity.this, R.string.timeDateSpinnerError, Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    Intent intent = new Intent(NewTaskActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        rejectTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewTaskActivity.this, MainActivity.class);
                startActivity(intent);
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

                Toast toast = Toast.makeText(NewTaskActivity.this, R.string.lowPriorityToast, Toast.LENGTH_SHORT);
                toast.show();

                if(editTextsFilled) {
                    buttonAddTask.setEnabled(true);
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

                Toast toast = Toast.makeText(NewTaskActivity.this, R.string.mediumPriorityToast, Toast.LENGTH_SHORT);
                toast.show();

                if(editTextsFilled) {
                    buttonAddTask.setEnabled(true);
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

                Toast toast = Toast.makeText(NewTaskActivity.this, R.string.hightPriorityToast, Toast.LENGTH_SHORT);
                toast.show();

                if(editTextsFilled) {
                    buttonAddTask.setEnabled(true);
                }
            }
        });
    }
}
