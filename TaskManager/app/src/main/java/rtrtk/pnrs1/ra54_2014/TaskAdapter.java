package rtrtk.pnrs1.ra54_2014;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

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
            //view = inflater.inflate(R.la)
        }

        return  view;
    }
}
