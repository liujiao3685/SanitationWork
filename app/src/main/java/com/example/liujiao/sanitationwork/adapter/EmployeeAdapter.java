package com.example.liujiao.sanitationwork.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.liujiao.sanitationwork.R;
import com.example.liujiao.sanitationwork.bean.Employee;

import java.util.List;

/**
 * Created by liujiao on 2017/3/21.
 */

public class EmployeeAdapter extends BaseAdapter {

    private List<Employee> list;
    private Context context;

    public EmployeeAdapter(List<Employee> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        ViewHolder holder;
        if (v == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            v = inflater.inflate(R.layout.item_employee, null);
            holder.ename = (TextView) v.findViewById(R.id.tv_ename);
            holder.epay = (TextView) v.findViewById(R.id.tv_epay);
            holder.estate = (TextView) v.findViewById(R.id.tv_estate);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        holder.ename.setText(list.get(position).getEname());
        holder.epay.setText(list.get(position).getEpay());
        holder.estate.setText(list.get(position).getEstate());

        return v;
    }

    class ViewHolder {
        TextView ename, epay, estate;
    }

}
