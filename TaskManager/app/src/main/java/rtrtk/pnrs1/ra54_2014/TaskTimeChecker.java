package rtrtk.pnrs1.ra54_2014;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static java.lang.Thread.sleep;

/**
 * Created by alexa on 21.5.17..
 */

public class TaskTimeChecker extends Thread {
    private boolean mRun;
    private Context mContext;
    private SimpleDateFormat mSimpleDateFormat;
    private static int SLEEP_PERIOD = 5000;
    private NotificationManager mNotificationManager;
    private Notification.Builder mNotificationBuilder;
    private boolean mNotificationReady;
    public TaskTimeChecker(Context context)
    {
        mRun = true;
        mContext = context;
        mSimpleDateFormat = new SimpleDateFormat("hh:mm");
        mNotificationManager = (NotificationManager) mContext.getSystemService(Context
                .NOTIFICATION_SERVICE);
        mNotificationBuilder = new Notification.Builder(mContext)
                .setContentTitle("Task alert")
                .setSmallIcon(android.R.drawable.ic_popup_reminder);
    }

    @Override
    public synchronized void start() {
        mRun = true;
        super.start();
    }

    public synchronized void exit()
    {
        mRun = false;
    }

    @Override
    public void run() {
        super.run();
        while(mRun) {

            for (TaskClass task : MainActivity.mArrayList) {
                if (task.isTaskReminder()) {
                    Calendar c = task.getTaskTimeDate();
                    Calendar now = Calendar.getInstance();

                    if (c.getTimeInMillis() - now.getTimeInMillis() < 15 * 60 * 1000) {

                        mNotificationBuilder.setContentText(task.getTaskName() + " - 15 minute reminder");

                        mNotificationManager.notify(0, mNotificationBuilder.build());
                        task.setTaskFinished(true);
                    }
                }

        }
    }
}
}