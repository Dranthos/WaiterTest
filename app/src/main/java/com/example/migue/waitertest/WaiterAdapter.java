package com.example.migue.waitertest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.migue.waitertest.R.id.Nombre;

public class WaiterAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Waiter> mDataSource;

    public WaiterAdapter(Context context, ArrayList<Waiter> items) {
        mContext = context;
        mDataSource = items;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //1
    @Override
    public int getCount() {
        return mDataSource.size();
    }

    //2
    @Override
    public Object getItem(int position) {
        return mDataSource.get(position);
    }

    //3
    @Override
    public long getItemId(int position) {
        return position;
    }

    //4
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get view for row item
        //View rowView = mInflater.inflate(R.layout.list_item, parent, false);

        View rowView = convertView;
        if (rowView == null) {
            // Not recycled, inflate a new view
            rowView = mInflater.inflate(R.layout.list_item, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.name = (TextView) rowView.findViewById(Nombre);
            viewHolder.hours = (TextView) rowView.findViewById(R.id.Horas);
            viewHolder.substract = (TextView) rowView.findViewById(R.id.Resta);
            rowView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) rowView.getTag();

        final Waiter listItem = (Waiter) getItem(position);

        holder.name.setText(listItem.name);
        holder.hours.setText(listItem.hours);
        holder.substract.setText(listItem.substract);


        return rowView;
    }
}
