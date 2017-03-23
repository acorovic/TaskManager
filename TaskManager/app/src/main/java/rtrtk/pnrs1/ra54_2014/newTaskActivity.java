package rtrtk.pnrs1.ra54_2014;

import android.content.res.ColorStateList;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class newTaskActivity extends AppCompatActivity {

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
                Button addTask = (Button)findViewById(R.id.addTask);
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
        EditText editTaskName = (EditText)findViewById(R.id.editTaskName);
        EditText editTaskDescription = (EditText)findViewById(R.id.editTaskDescription);

        editTextsFilled = false;
        priorityPressed = false;

        editTaskName.addTextChangedListener(editTextWatcher);
        editTaskDescription.addTextChangedListener(editTextWatcher);

        buttonLowPriority.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonMediumPriority.setEnabled(false);
                buttonMediumPriority.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorMediumPriorityDisabled)));
                buttonHighPriority.setEnabled(false);
                buttonHighPriority.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorHighPriorityDisabled)));
                priorityPressed = true;
                if(editTextsFilled) {
                    Button addTask = (Button)findViewById(R.id.addTask);
                    addTask.setEnabled(true);
                }
            }
        });

        buttonMediumPriority.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonLowPriority.setEnabled(false);
                buttonLowPriority.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorLowPriorityDisabled)));
                buttonHighPriority.setEnabled(false);
                buttonHighPriority.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorHighPriorityDisabled)));
                priorityPressed = true;
                if(editTextsFilled) {
                    Button addTask = (Button)findViewById(R.id.addTask);
                    addTask.setEnabled(true);
                }
            }
        });

        buttonHighPriority.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonMediumPriority.setEnabled(false);
                buttonMediumPriority.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorMediumPriorityDisabled)));
                buttonLowPriority.setEnabled(false);
                buttonLowPriority.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorLowPriorityDisabled)));
                priorityPressed = true;
                if(editTextsFilled) {
                    Button addTask = (Button)findViewById(R.id.addTask);
                    addTask.setEnabled(true);
                }
            }
        });
    }
}
