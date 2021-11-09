package com.example.barberpalace2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ScheduleAdapter extends BaseAdapter {
    private ArrayList<Appointment> list;

    private LayoutInflater mInflater;
    Context con;

    public ScheduleAdapter(Context context, ArrayList<Appointment> list) {
        this.list = list;
        this.con = context;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        final Appointment appointment = list.get(position);

        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(con);
            convertView = layoutInflater.inflate(R.layout.timeslot_layout, null);
        }

        final TextView time = (TextView)convertView.findViewById(R.id.schedule_time); //availability
        final TextView name = (TextView)convertView.findViewById(R.id.customer_name);
       // final TextView confirmed = (TextView)convertView.findViewById(R.id.);

        String nickName = "";
        if (appointment.getNickname() != "")
        {
            nickName = " (" + appointment.getNickname() + ")";
        }

        time.setText(appointment.getTime());
        name.setText(appointment.getName() + nickName);

        //set view properties here

        //convertView = new TextView(con);
//set layout params or inflate xml layout
        //((TextView) convertView).setText(list.get(position).time);
        //simpleText.setEnabled(list.get(position).isAvailable);
        return convertView;
    }


}
