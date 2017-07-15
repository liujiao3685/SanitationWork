package com.example.liujiao.sanitationwork.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.liujiao.sanitationwork.R;
import com.example.liujiao.sanitationwork.bean.Card;

import java.util.List;

/**
 * Created by liujiao on 2017/3/21.
 */

public class MyCardAdapter extends BaseAdapter {

    private List<Card> list;
    private Context context;

    public MyCardAdapter(List<Card> list, Context context) {
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
            v = inflater.inflate(R.layout.item_card, null);
            holder.card = (TextView) v.findViewById(R.id.tv_icard);
            holder.eid = (TextView) v.findViewById(R.id.tv_ieid);
            holder.state = (TextView) v.findViewById(R.id.tv_istate);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        holder.card.setText(list.get(position).getCardnum());
        holder.eid.setText(list.get(position).getCeid()+"");
        holder.state.setText(list.get(position).getCstate());

        return v;
    }

    class ViewHolder {
        TextView card, eid, state;
    }

}
