package com.example.liujiao.sanitationwork.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.liujiao.sanitationwork.R;
import com.example.liujiao.sanitationwork.bean.Work;

import java.util.List;

/**
 * Created by liujiao on 2017/3/21.
 */

public class MyWorkAdapter extends BaseAdapter {

    private List<Work> list;
    private Context context;

    public MyWorkAdapter(List<Work> list, Context context) {
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
            v = inflater.inflate(R.layout.item_kaohe, null);
            holder.wname = (TextView) v.findViewById(R.id.tv_wname);
            holder.wstate = (TextView) v.findViewById(R.id.tv_state);
            holder.wnum = (TextView) v.findViewById(R.id.tv_snum);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        holder.wname.setText(list.get(position).getWname());
        holder.wstate.setText(list.get(position).getWstate());
        holder.wnum.setText(list.get(position).getWnum()+"人");

        return v;
    }

    class ViewHolder {
        TextView wname, wstate, wnum;
    }

}
