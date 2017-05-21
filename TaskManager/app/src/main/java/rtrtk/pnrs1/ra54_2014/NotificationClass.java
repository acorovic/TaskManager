package rtrtk.pnrs1.ra54_2014;

import android.app.NotificationManager;
import android.content.Context;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;

/**
 * Created by alexa on 16.5.17..
 */

public class NotificationClass extends INotificationAidlInterface.Stub {

    private NotificationCompat.Builder mBuilder;
    private NotificationManager mNotificationManager;
    private Context mContext;
    private static int ADD_NOTIFICATION = 1;

    public NotificationClass(Context context) {
        mContext = context;
    }

    public void setmNotificationManager(NotificationManager mNotificationManager) {
        this.mNotificationManager = mNotificationManager;
    }

    @Override
    public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

    }

    @Override
    public void notifyTaskReminder(String taskName) throws RemoteException {
        mBuilder = new NotificationCompat.Builder(mContext);
        mBuilder.setContentTitle("Task manager");
        mBuilder.setSmallIcon(android.R.drawable.stat_sys_warning);
        mBuilder.setContentText(taskName + " - istice za 15 minuta");
        mNotificationManager.notify(ADD_NOTIFICATION, mBuilder.build());
    }


    @Override
    public void notifyTaskAdded(String taskName) throws RemoteException {
        mBuilder = new NotificationCompat.Builder(mContext);
        mBuilder.setContentTitle("Task manager");
        mBuilder.setSmallIcon(android.R.drawable.ic_input_add);
        mBuilder.setContentText(taskName + " dodat");
        mNotificationManager.notify(ADD_NOTIFICATION, mBuilder.build());
    }

    @Override
    public void notifyTaskDeleted(String taskName) throws RemoteException {
        mBuilder = new NotificationCompat.Builder(mContext);
        mBuilder.setContentTitle("Task manager");
        mBuilder.setSmallIcon(android.R.drawable.ic_delete);
        mBuilder.setContentText(taskName + " obrisan");
        mNotificationManager.notify(ADD_NOTIFICATION, mBuilder.build());
    }

    @Override
    public void notifyTaskUpdated(String taskName) throws RemoteException {
        mBuilder = new NotificationCompat.Builder(mContext);
        mBuilder.setContentTitle("Task manager");
        mBuilder.setSmallIcon(android.R.drawable.ic_input_add);
        mBuilder.setContentText(taskName + " azuriran");
        mNotificationManager.notify(ADD_NOTIFICATION, mBuilder.build());
    }
}
