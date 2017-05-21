// INotificationAidlInterface.aidl
package rtrtk.pnrs1.ra54_2014;

// Declare any non-default types here with import statements

interface INotificationAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    void notifyTaskAdded(String taskName);
    void notifyTaskDeleted(String taskName);
    void notifyTaskUpdated(String taskName);
    void notifyTaskReminder(String taskName);
}
