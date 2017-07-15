package com.example.liujiao.sanitationwork.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.liujiao.sanitationwork.R;
import com.example.liujiao.sanitationwork.adapter.MyWorkAdapter;
import com.example.liujiao.sanitationwork.bean.Work;
import com.example.liujiao.sanitationwork.util.Constant;
import com.example.liujiao.sanitationwork.util.HttpUtil;
import com.example.liujiao.sanitationwork.util.T;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class KaoHeActivity extends AppCompatActivity {

    private ListView mWlistView;
    private List<Work> list = new ArrayList<>();
    private Work work;
    private MyWorkAdapter adapter;
    private String sql = Constant.host + "getAllWork";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kao_he);
        initView();

        getAllWork(sql);

        mWlistView.setOnItemClickListener(listener);

    }

    AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, final int pos, long l) {
            if ("待审核".equals(list.get(pos).getWstate())) {
                Intent intent = new Intent();
                intent.setClass(KaoHeActivity.this, KaoHeMainActivity.class);

//                intent.putExtra("work", list.get(pos));

                intent.putExtra("name",list.get(pos).getWname());
                intent.putExtra("id",list.get(pos).getWid()+"");
                intent.putExtra("pay",list.get(pos).getWpay());
                intent.putExtra("duty",list.get(pos).getWduty());
                intent.putExtra("require",list.get(pos).getWrequire());
                intent.putExtra("state",list.get(pos).getWstate());
                intent.putExtra("num",list.get(pos).getWnum()+"人");
//                T.toast(list.get(pos).getWduty());
                startActivity(intent);
                finish();

            } else {
                T.toast("此岗位已经通过审核！");
            }
        }
    };

//    private void showDialog(final int pos) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(KaoHeActivity.this);
//        builder.setTitle("提示");
//        builder.setMessage("是否通过审核？");
//        builder.setNegativeButton("取消", null);
//        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                String sql = Constant.host + "updateWorkState?wid=" + list.get(pos).getWid() + "";
//                updateState(sql);
//            }
//        });
//        builder.show();
//    }

    private void initView() {
        mWlistView = (ListView) findViewById(R.id.wlistView);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x123) {
                T.log("获取岗位数据成功！");
                adapter = new MyWorkAdapter(list, KaoHeActivity.this);
                mWlistView.setAdapter(adapter);
            } else if (msg.what == 0x124) {
                T.toast("获取岗位数据失败！");
            } else if (msg.what == 0x125) {
                getAllWork(sql);
                T.toast("审核通过！");
                finish();
            } else if (msg.what == 0x126) {
                T.toast("审核失败！");
            }
        }
    };


    //获取所有岗位
    private void getAllWork(final String sql) {
        list.clear();
        new Thread() {
            @Override
            public void run() {
                super.run();
                String str = HttpUtil.doGet(sql);
                if ("false".equals(str)) {
                    handler.sendEmptyMessage(0x124);
                } else {
                    try {
                        JSONArray jsonArray = new JSONArray(str);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            work = new Work();
                            work.setWid(jsonObject.getInt("wid"));
                            work.setWname(jsonObject.getString("wname"));
                            work.setWnum(jsonObject.getInt("wnum"));
                            work.setWpay(jsonObject.getString("wpay"));
                            work.setWstate(jsonObject.getString("wstate"));
                            work.setWduty(jsonObject.getString("wduty"));
                            work.setWrequire(jsonObject.getString("wrequire"));
                            list.add(work);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    T.log("size:" + list.size() + "");
                    handler.sendEmptyMessage(0x123);
                }

            }
        }.start();
    }

}
