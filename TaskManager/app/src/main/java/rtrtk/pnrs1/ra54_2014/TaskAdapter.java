package rtrtk.pnrs1.ra54_2014;

import android.content.Context;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;



/**
 * Created by student on 6.4.2017.
 */

public class TaskAdapter extends BaseAdapter {

    private Context mContext;
    public static ArrayList<TaskClass> mTasks;

    public String checkTime(Calendar calendar) {
        String ret = "\n";
        int tmp = calendar.get(Calendar.HOUR_OF_DAY);
        ret += tmp < 10 ? "0" + tmp : tmp;
        ret += ":";
        tmp = calendar.get(Calendar.MINUTE);
        ret += tmp < 10 ? "0" + tmp : tmp;
        return ret;
    }

    public String checkDate(Calendar calendar) {
        Calendar dateTomorrow = Calendar.getInstance();
        Calendar dateThisWeek = Calendar.getInstance();
        String ret = "";

        if(calendar.get(Calendar.DAY_OF_YEAR) == dateTomorrow.get(Calendar.DAY_OF_YEAR)) {
            ret = mContext.getResources().getString(R.string.today);
            ret += checkTime(calendar);
            return ret;
        }

        dateTomorrow.add(Calendar.DAY_OF_YEAR, 1);
        dateThisWeek.add(Calendar.DAY_OF_YEAR, 7);
        if(calendar.get(Calendar.DAY_OF_YEAR) == dateTomorrow.get(Calendar.DAY_OF_YEAR)) {
            ret = mContext.getResources().getString(R.string.tomorrow);
        } else if(calendar.get(Calendar.DAY_OF_YEAR) <= dateThisWeek.get(Calendar.DAY_OF_YEAR)) {
            switch (calendar.get(Calendar.DAY_OF_WEEK)) {
                case Calendar.MONDAY:
                    ret = mContext.getResources().getString(R.string.monday);
                    break;
                case Calendar.TUESDAY:
                    ret = mContext.getResources().getString(R.string.tuesday);
                    break;
                case Calendar.WEDNESDAY:
                    ret = mContext.getResources().getString(R.string.wednesday);
                    break;
                case Calendar.THURSDAY:
                    ret = mContext.getResources().getString(R.string.thursday);
                    break;
                case Calendar.FRIDAY:
                    ret = mContext.getResources().getString(R.string.friday);
                    break;
                case Calendar.SATURDAY:
                    ret = mContext.getResources().getString(R.string.saturday);
                    break;
                case Calendar.SUNDAY:
                    ret = mContext.getResources().getString(R.string.sunday);
                    break;
                default:
                    ret = "";
                    break;
            }
        } else {
            int tmp = calendar.get(Calendar.DAY_OF_MONTH);
            ret += tmp < 10 ? "0" + tmp : tmp;
            ret += "/";
            tmp = calendar.get(Calendar.MONTH);
            ret += tmp < 10 ? "0" + tmp : tmp;
            ret += "/";
            tmp = calendar.get(Calendar.YEAR);
            ret += tmp;
        }

        ret += checkTime(calendar);

        return ret;
    }


    public TaskAdapter(Context context) {
        mContext = context;
        mTasks = new ArrayList<TaskClass>();
    }

    public void addTask(TaskClass task) {
        mTasks.add(task);
        notifyDataSetChanged();
    }

    public void update(TaskClass[] tasks) {
        mTasks.clear();
        if(tasks != null) {
            for(TaskClass task : tasks) {
                mTasks.add(task);
            }
        }
        notifyDataSetChanged();
    }

    public void removeTask(int position) {
        mTasks.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {return mTasks.size();}

    @Override
    public Object getItem(int position) {
        Object rv = null;
        try {
            rv = mTasks.get(position);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        return rv;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View contentView, ViewGroup parent) {
        View view = contentView;

        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.task_adapter_row, parent, false);
            ViewHolder holder = new ViewHolder();
            holder.name = (TextView) view.findViewById(R.id.taskNameRow);
            holder.date = (TextView) view.findViewById(R.id.taskDateRow);
            holder.urgency = (RelativeLayout) view.findViewById(R.id.taskUrgencyRow);
            holder.checkBox = (CheckBox) view.findViewById(R.id.taskFinishedCheckBox);
            holder.reminderIcon = (ImageView) view.findViewById(R.id.taskReminderIcon);
            view.setTag(holder);
        }

        final TaskClass task = (TaskClass) getItem(position);
        final ViewHolder holder = (ViewHolder) view.getTag();

        // Setting properties
        holder.name.setText(task.getTaskName());
        TaskClass.Priority taskPriority = task.getTaskPriority();
        if(taskPriority == TaskClass.Priority.LOW)
            holder.urgency.setBackgroundColor(ContextCompat.getColor(mContext,R.color.colorLowPriority));
        if(taskPriority == TaskClass.Priority.MEDIUM)
            holder.urgency.setBackgroundColor(ContextCompat.getColor(mContext,R.color.colorMediumPriority));
        if(taskPriority == TaskClass.Priority.HIGH)
            holder.urgency.setBackgroundColor(ContextCompat.getColor(mContext,R.color.colorHighPriority));
        if(task.isTaskReminder()) {
            holder.reminderIcon.setVisibility(View.VISIBLE);
        } else {
            holder.reminderIcon.setVisibility(View.INVISIBLE);
        }
        // Form date TextView
        holder.date.setText(checkDate(task.getTaskTimeDate()));
        

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked)
            {
                task.setTaskFinished(1);
                holder.name.setPaintFlags(holder.name.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                MainActivity.mTaskDbHelper.setFinished(1, task.getTaskName());

            }
            else
            {
                task.setTaskFinished(0);
                holder.name.setPaintFlags(holder.name.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
                MainActivity.mTaskDbHelper.setFinished(0, task.getTaskName());
            }
            TaskClass[] tasks = MainActivity.mTaskDbHelper.readTasks();
            MainActivity.mTaskAdapter.update(tasks);
        }
    });

        return view;
    }

    private class ViewHolder{
        public TextView name = null;
        public TextView date = null;
        public RelativeLayout urgency = null;
        public CheckBox checkBox = null;
        public ImageView reminderIcon = null;

    }
}
