package com.example.barberpalace2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SlotListAdapter extends BaseAdapter {
    private ArrayList<TimeSlot> list;

    private LayoutInflater mInflater;
    Context con;

    public SlotListAdapter(Context context, ArrayList<TimeSlot> list) {
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
        final TimeSlot timeSlot = list.get(position);

        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(con);
            convertView = layoutInflater.inflate(R.layout.timeslot_layout, null);
        }

        final TextView time = (TextView)convertView.findViewById(R.id.time); //availability
        final TextView availability = (TextView)convertView.findViewById(R.id.availability);

        time.setText(timeSlot.time);

        if(timeSlot.isAvailable){
            availability.setText("Available");
        }
        else{
            availability.setText("Closed");
            convertView.setEnabled(false);
        }

        //convertView = new TextView(con);
//set layout params or inflate xml layout
        //((TextView) convertView).setText(list.get(position).time);
        //simpleText.setEnabled(list.get(position).isAvailable);
        return convertView;
    }


}
