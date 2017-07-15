package com.example.liujiao.sanitationwork.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.liujiao.sanitationwork.R;
import com.example.liujiao.sanitationwork.adapter.HunterAdapter;
import com.example.liujiao.sanitationwork.bean.Hunter;
import com.example.liujiao.sanitationwork.util.Constant;
import com.example.liujiao.sanitationwork.util.HttpUtil;
import com.example.liujiao.sanitationwork.util.T;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class LuQuWorkActivity extends AppCompatActivity {

    private ListView mListviewHunter;
    private HunterAdapter adapter;
    private Hunter hunter;
    private List<Hunter> list = new ArrayList<>();
    private String sql = Constant.host + "getAllHunterServlet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lu_qu_work);
        initView();

        getAllHunter(sql);
        mListviewHunter.setOnItemClickListener(listener);

    }

    AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, final int pos, long l) {
            if ("已录取".equals(list.get(pos).getHstate())) {
                T.toast("此人已录取！");
                return;
            }
            if ("未录取".equals(list.get(pos).getHstate())) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LuQuWorkActivity.this);
                builder.setTitle("提示");
                builder.setMessage("是否录取此人？");
                builder.setNegativeButton("取消", null);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String sql = Constant.host + "updateHunterState?hid=" + list.get(pos).getHid() + "";
                        updateHunterState(sql);
                    }
                });

                builder.show();
            }
        }

        private void updateHunterState(final String sql) {
            new Thread() {
                @Override
                public void run() {
                    String str = HttpUtil.doGet(sql);
                    if ("false".equals(str)) {
                        handler.sendEmptyMessage(0x126);
                    } else if ("true".equals(str)) {
                        handler.sendEmptyMessage(0x125);
                    }
                }
            }.start();

        }
    };

    private void initView() {
        mListviewHunter = (ListView) findViewById(R.id.listview_hunter);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x123) {
                T.log("获取投职者数据成功！");
                adapter = new HunterAdapter(list, LuQuWorkActivity.this);
                mListviewHunter.setAdapter(adapter);
            } else if (msg.what == 0x124) {
                T.toast("获取投职者数据失败！");
            } else if (msg.what == 0x125) {
                getAllHunter(sql);
                T.toast("成功录取！");
            } else if (msg.what == 0x126) {
                T.toast("录取失败！");
            }
        }
    };


    private void getAllHunter(final String sql) {
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
                            hunter = new Hunter();
                            hunter.setHid(jsonObject.getInt("hid"));
                            hunter.setHname(jsonObject.getString("hname"));
                            hunter.setHwid(jsonObject.getInt("hwid"));
                            hunter.setHstate(jsonObject.getString("hstate"));
                            hunter.setHwname(jsonObject.getString("hwname"));
                            list.add(hunter);
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
