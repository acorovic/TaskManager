package rtrtk.pnrs1.ra54_2014;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TaskAdapter mTaskAdapter;
    private TaskDbHelper mTaskDbHelper;
    private ListView mListView;
    private int itemPositionPreview;
    private INotificationAidlInterface notificationAidlInterface = null;

    public static NotificationManager manager;
    public static NotificationClass notificationClass;


    public static ArrayList<TaskClass> mArrayList;

    @Override
    protected void onResume() {
        super.onResume();

        TaskClass[] tasks = mTaskDbHelper.readTasks();
        mTaskAdapter.update(tasks);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button newTaskButton = (Button) findViewById(R.id.newTaskButton);
        Button statisticButton = (Button) findViewById(R.id.statisticButton);
        mTaskAdapter = new TaskAdapter(this);
        mListView = (ListView)findViewById(R.id.taskListView);

        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(MainActivity.this, NotifyService.class);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
        notificationClass = new NotificationClass(MainActivity.this);
        mArrayList = TaskAdapter.mTasks;

        mListView.setAdapter(mTaskAdapter);
        mTaskDbHelper = new TaskDbHelper(this);

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
                    mTaskDbHelper.insert(task);
                    //mTaskAdapter.addTask(task);
                    TaskClass[] tasks = mTaskDbHelper.readTasks();
                    mTaskAdapter.update(tasks);
                    try {
                        Log.d("text", "pravljenje notifikacije");
                        notificationAidlInterface.notifyTaskAdded(task.getTaskName());
                        } catch (RemoteException ex) {
                        ex.printStackTrace();
                        }
                } else if (resultCode == 2) {   // 2 -> REMOVE
                    TaskClass task = (TaskClass) mTaskAdapter.getItem(itemPositionPreview);
                    //mTaskAdapter.removeTask(itemPositionPreview);
                    mTaskDbHelper.deleteTask(task.getTaskName());
                    TaskClass[] tasks = mTaskDbHelper.readTasks();
                    mTaskAdapter.update(tasks);
                    try{
                        Log.d("text", "pravljenje notifikacije");
                        notificationAidlInterface.notifyTaskDeleted(task.getTaskName());
                    } catch (RemoteException ex) {
                        ex.printStackTrace();
                }
                }
            }
    }
    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("text", "ispis servis");
            notificationAidlInterface = INotificationAidlInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

}
