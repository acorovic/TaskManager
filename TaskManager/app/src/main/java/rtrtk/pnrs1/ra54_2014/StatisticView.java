package rtrtk.pnrs1.ra54_2014;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

/**
 * Created by alexa on 2.5.17..
 */

public class StatisticView extends View {
    private Paint mPaint;

    private RectF highPriorityCircle = new RectF();
    private RectF mediumPriorityCircle = new RectF();
    private RectF lowPriorityCircle = new RectF();

    private DrawAnimation drawAnimation = new DrawAnimation();

    private int highPriorityTasksFinished = 0;
    private int mediumPriorityTasksFinished = 0;
    private int lowPriorityTasksFinished = 0;

    private int highPriorityDrawn = 0;
    private int mediumPriorityDrawn = 0;
    private int lowPriorityDrawn = 0;

    private String highPercentage = "0%";
    private String mediumPercentage = "0%";
    private String lowPercentage = "0%";


    public StatisticView(Context context) {
        super(context);

        mPaint = new Paint();
        drawAnimation.execute();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        highPriorityCircle.set(canvas.getWidth()/12 * 4, canvas.getWidth()/12 *1 , canvas.getWidth()/12 * 8, canvas.getWidth() / 12 * 5);
        mediumPriorityCircle.set(canvas.getWidth()/12 * 1, canvas.getWidth()/12 *6 , canvas.getWidth()/12 * 5, canvas.getWidth() / 12 * 10);
        lowPriorityCircle.set(canvas.getWidth()/12 * 7, canvas.getWidth()/12 *6 , canvas.getWidth()/12 * 11, canvas.getWidth() / 12 * 10);

        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mPaint.setColor(getResources().getColor(R.color.colorBackground));
        canvas.drawOval(highPriorityCircle, mPaint);
        mPaint.setColor(getResources().getColor(R.color.colorHighPriority));
        canvas.drawArc(highPriorityCircle, -90, (float)(highPriorityDrawn * 3.6), true, mPaint);

        mPaint.setColor(getResources().getColor(R.color.colorBackground));
        canvas.drawOval(mediumPriorityCircle, mPaint);
        mPaint.setColor(getResources().getColor(R.color.colorMediumPriority));
        canvas.drawArc(mediumPriorityCircle, -90, (float)(mediumPriorityDrawn * 3.6), true, mPaint);

        mPaint.setColor(getResources().getColor(R.color.colorBackground));
        canvas.drawOval(lowPriorityCircle, mPaint);
        mPaint.setColor(getResources().getColor(R.color.colorLowPriority));
        canvas.drawArc(lowPriorityCircle, -90, (float)(lowPriorityDrawn * 3.6), true, mPaint);

        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(40);

        canvas.drawText(getResources().getString(R.string.highPriorityTask), 0, getResources().getString(R.string.highPriorityTask).length(), canvas.getWidth()/12 * 5, canvas.getWidth()/12 * 6, mPaint);
        canvas.drawText(getResources().getString(R.string.mediumPriorityTask), 0, getResources().getString(R.string.mediumPriorityTask).length(), canvas.getWidth()/12 * 2, canvas.getWidth()/12 * 11, mPaint);
        canvas.drawText(getResources().getString(R.string.lowPriorityTask), 0, getResources().getString(R.string.lowPriorityTask).length(), canvas.getWidth()/12 * 8, canvas.getWidth()/12 * 11, mPaint);

        mPaint.setTextSize(80);
        if(highPercentage.length() == 2)
            canvas.drawText(highPercentage, 0, 2, highPriorityCircle.centerX() - 45, highPriorityCircle.centerY() + 30, mPaint);
        else
            canvas.drawText(highPercentage, 0, 3, highPriorityCircle.centerX() - 67, highPriorityCircle.centerY() + 30, mPaint);

        if(mediumPercentage.length() == 2)
            canvas.drawText(mediumPercentage, 0, 2, mediumPriorityCircle.centerX() - 45, mediumPriorityCircle.centerY() + 30, mPaint);
        else
            canvas.drawText(mediumPercentage, 0, 3, mediumPriorityCircle.centerX() - 67, mediumPriorityCircle.centerY() + 30, mPaint);

        if(lowPercentage.length() == 2)
            canvas.drawText(lowPercentage, 0, 2, lowPriorityCircle.centerX() - 45, lowPriorityCircle.centerY() + 30, mPaint);
        else
            canvas.drawText(lowPercentage, 0, 3, lowPriorityCircle.centerX() - 67, lowPriorityCircle.centerY() + 30, mPaint);
    }

    private class DrawAnimation extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override protected void onPostExecute(Void ret) {
            super.onPostExecute(ret);
        }

        @Override protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override protected Void doInBackground(Void... params) {
            TaskClass[] tasks = MainActivity.mTaskDbHelper.readTasks();
            int totalCntHigh = 0;
            int totalCntMedium = 0;
            int totalCntLow = 0;
            if(tasks != null) {
                for (TaskClass task : tasks) {
                    Log.d("task u bazi", task.getTaskName() + " " + Integer.toString(task.isTaskFinished()));
                    switch (task.getTaskPriority()) {
                        case HIGH:
                            totalCntHigh++;
                            if(task.isTaskFinished() == 1) {
                                highPriorityTasksFinished++;
                            }
                            break;
                        case MEDIUM:
                            totalCntMedium++;
                            if(task.isTaskFinished() == 1) {
                                mediumPriorityTasksFinished++;
                            }
                            break;
                        case LOW:
                            totalCntLow++;
                            if(task.isTaskFinished() == 1) {
                                lowPriorityTasksFinished++;
                            }
                            break;
                    }

                }
            }

            if(totalCntHigh != 0) {
                highPriorityTasksFinished = (int) (highPriorityTasksFinished*100/totalCntHigh);}
            else {
                highPriorityTasksFinished = 0;
            }
            if(totalCntMedium != 0) {
                mediumPriorityTasksFinished = (int) (mediumPriorityTasksFinished * 100 / totalCntMedium);
            }
            else {
                mediumPriorityTasksFinished = 0;
            }
            if(totalCntLow != 0) {
                lowPriorityTasksFinished = (int) (lowPriorityTasksFinished * 100 / totalCntLow);
            }
            else {
                lowPriorityTasksFinished = 0;
            }
            while(highPriorityDrawn < highPriorityTasksFinished || mediumPriorityDrawn < mediumPriorityTasksFinished || lowPriorityDrawn < lowPriorityTasksFinished) {
                if(highPriorityDrawn < highPriorityTasksFinished) {
                    highPriorityDrawn++;
                    highPercentage = Integer.toString(highPriorityDrawn) + "%";
                }
                if(mediumPriorityDrawn < mediumPriorityTasksFinished) {
                    mediumPriorityDrawn++;
                    mediumPercentage = Integer.toString(mediumPriorityDrawn) + "%";
                }

                if(lowPriorityDrawn < lowPriorityTasksFinished) {
                    lowPriorityDrawn++;
                    lowPercentage = Integer.toString(lowPriorityDrawn) + "%";
                }

                postInvalidate();
                SystemClock.sleep(25);
            }
            return null;
        }


    }
}
