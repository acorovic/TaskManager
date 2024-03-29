package rtrtk.pnrs1.ra54_2014;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by alexa on 21.5.17..
 */

public class NotifyService extends Service {
    private NotificationClass mNotificationClass;
    private TaskTimeChecker mTaskTimeChecker;

    @Override
    public void onCreate() {
        Log.d("binderLog", "upao u onCreate");
        mTaskTimeChecker = new TaskTimeChecker(this);
        mTaskTimeChecker.start();

        super.onCreate();
    }

    @Override
    public void onDestroy() {
        mTaskTimeChecker.exit();
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("binderLog", "upao u onBind");
        if(mNotificationClass == null)
            mNotificationClass = new NotificationClass(this);
        return mNotificationClass;
    }
}
