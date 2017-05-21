package rtrtk.pnrs1.ra54_2014;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TaskAdapter mTaskAdapter;
    private ListView mListView;
    private int itemPositionPreview;

    public static ArrayList<TaskClass> mArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button newTaskButton = (Button) findViewById(R.id.newTaskButton);
        Button statisticButton = (Button) findViewById(R.id.statisticButton);
        mTaskAdapter = new TaskAdapter(this);
        mListView = (ListView)findViewById(R.id.taskListView);

        mListView.setAdapter(mTaskAdapter);

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
                itemPositionPreview = position;
                Intent i = new Intent(MainActivity.this, NewTaskActivity.class);
                TaskClass task = (TaskClass) mTaskAdapter.getItem(position);
                i.putExtra(getResources().getString(R.string.taskIntent), task);
                startActivityForResult(i, 1);
                return true;
            }
        });

        newTaskButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewTaskActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        statisticButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StatisticActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if(requestCode == 1) {
                if(resultCode == Activity.RESULT_OK) {
                    TaskClass task = (TaskClass) data.getExtras().getSerializable(getResources().getString(R.string.result));
                    mTaskAdapter.addTask(task);
                } else if (resultCode == 2) {   // 2 -> REMOVE
                    mTaskAdapter.removeTask(itemPositionPreview);
                }
            }
    }
}
