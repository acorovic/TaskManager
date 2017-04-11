package rtrtk.pnrs1.ra54_2014;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by student on 6.4.2017.
 */

public class TaskAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<TaskClass> mTasks;

    public TaskAdapter(Context context) {
        mContext = context;
        mTasks = new ArrayList<TaskClass>();
    }

    public void addTask(TaskClass task) {
        mTasks.add(task);
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
            view = inflater.inflate(R.layout.tesk_adapter_row, parent, false);
            ViewHolder holder = new ViewHolder();
            holder.name = (TextView) view.findViewById(R.id.taskNameRow);
            holder.date = (TextView) view.findViewById(R.id.taskDateRow);
            holder.urgency = (RelativeLayout) view.findViewById(R.id.taskUrgencyRow);
            holder.checkBox = (CheckBox) view.findViewById(R.id.taskReminderRow);
            view.setTag(holder);
        }

        TaskClass task = (TaskClass) getItem(position);
        ViewHolder holder = (ViewHolder) view.getTag();

        //setting properties
        holder.name.setText(task.getTaskName());
        switch (task.getTaskPrioirty()) {
            case LOW:
                holder.urgency.setBackgroundColor(R.color.colorLowPriority);
                break;
            case MEDIUM:
                holder.urgency.setBackgroundColor(R.color.colorMediumPriority);
                break;
            case HIGH:
                holder.urgency.setBackgroundColor(R.color.colorHighPriority);
                break;
        }
        holder.checkBox.setChecked(task.isTaskReminder());


        return view;
    }

    private class ViewHolder{
        public TextView name = null;
        public TextView date = null;
        public RelativeLayout urgency = null;
        public CheckBox checkBox = null;

    }
}
