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
import com.example.liujiao.sanitationwork.adapter.MyCardAdapter;
import com.example.liujiao.sanitationwork.bean.Card;
import com.example.liujiao.sanitationwork.util.Constant;
import com.example.liujiao.sanitationwork.util.HttpUtil;
import com.example.liujiao.sanitationwork.util.T;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CardManageActivity extends AppCompatActivity {

    private ListView mListviewCard;
    private Card card;
    private MyCardAdapter adapter;
    private List<Card> list = new ArrayList<>();
    private String sql = Constant.host + "getAllCard";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_manage);
        initView();

        getAllCard(sql);
        mListviewCard.setOnItemClickListener(listener);

    }

    AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, final int pos, long l) {
            if ("已发".equals(list.get(pos).getCstate())) {
                T.toast("此员工已发工资！");
                return;
            }
            if ("未发".equals(list.get(pos).getCstate())) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CardManageActivity.this);
                builder.setTitle("提示");
                builder.setMessage("是否给此员工发送工资？");
                builder.setNegativeButton("取消", null);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String sql = Constant.host + "updateCardInfo?cid=" + list.get(pos).getCid() + "";
                        updateCardState(sql);
                    }
                });

                builder.show();
            }
        }
    };

    private void initView() {
        mListviewCard = (ListView) findViewById(R.id.listview_card);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x123) {
                T.log("获取银行卡数据成功！");
                adapter = new MyCardAdapter(list, CardManageActivity.this);
                mListviewCard.setAdapter(adapter);
            } else if (msg.what == 0x124) {
                T.toast("获取银行卡数据失败！");
            } else if (msg.what == 0x125) {
                getAllCard(sql);
                T.toast("派发成功！");
            } else if (msg.what == 0x126) {
                T.toast("派发失败！");
            }
        }
    };

    //更新银行卡工资状态
    private void updateCardState(final String sql) {
        new Thread() {
            @Override
            public void run() {
                String str = HttpUtil.doGet(sql);
                if ("false".equals(str)) {
                    handler.sendEmptyMessage(0x126);
                } else if ("true".equals(str)){
                    handler.sendEmptyMessage(0x125);
                }

            }
        }.start();

    }


    //获取所有银行卡信息
    private void getAllCard(final String sqls) {
        list.clear();
        new Thread() {
            @Override
            public void run() {
                String str = HttpUtil.doGet(sqls);
                if ("false".equals(str)) {
                    handler.sendEmptyMessage(0x124);
                } else {
                    try {
                        JSONArray jsonArray = new JSONArray(str);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            card = new Card();
                            card.setCid(jsonObject.getInt("cid"));
                            card.setCardnum(jsonObject.getString("cardnum"));
                            card.setCeid(jsonObject.getInt("ceid"));
                            card.setCstate(jsonObject.getString("cstate"));
                            list.add(card);
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
