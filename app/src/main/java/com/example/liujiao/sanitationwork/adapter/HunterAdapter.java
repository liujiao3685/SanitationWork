package com.example.liujiao.sanitationwork.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.liujiao.sanitationwork.R;
import com.example.liujiao.sanitationwork.bean.Hunter;

import java.util.List;

/**
 * Created by liujiao on 2017/3/21.
 */

public class HunterAdapter extends BaseAdapter {

    private List<Hunter> list;
    private Context context;

    public HunterAdapter(List<Hunter> list, Context context) {
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
            v = inflater.inflate(R.layout.item_hunter, null);
            holder.hname = (TextView) v.findViewById(R.id.tv_hname);
            holder.hwname = (TextView) v.findViewById(R.id.tv_hwname);
            holder.hstate = (TextView) v.findViewById(R.id.tv_hstate);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        holder.hname.setText(list.get(position).getHname());
        holder.hwname.setText(list.get(position).getHwname());
        holder.hstate.setText(list.get(position).getHstate());

        return v;
    }

    class ViewHolder {
        TextView hname, hwname, hstate;
    }

}
