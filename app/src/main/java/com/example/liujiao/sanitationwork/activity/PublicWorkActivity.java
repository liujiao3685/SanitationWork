package com.example.liujiao.sanitationwork.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.liujiao.sanitationwork.R;
import com.example.liujiao.sanitationwork.util.Constant;
import com.example.liujiao.sanitationwork.util.HttpUtil;
import com.example.liujiao.sanitationwork.util.T;

public class PublicWorkActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 请输入岗位名称
     */
    private EditText mWname;
    /**
     * 请输入岗位工资
     */
    private EditText mWpay;
    /**
     * 请输入招工人数
     */
    private EditText mWnum;
    /**
     * 确认发布
     */
    private Button mBtnPublic;
    /**
     * 请描述岗位职责
     */
    private EditText mWduty;
    /**
     * 请描述任职要求
     */
    private EditText mWrequire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_work);
        initView();
    }

    private void initView() {
        mWname = (EditText) findViewById(R.id.wname);
        mWpay = (EditText) findViewById(R.id.wpay);
        mWnum = (EditText) findViewById(R.id.wnum);
        mBtnPublic = (Button) findViewById(R.id.btn_public);
        mBtnPublic.setOnClickListener(this);
        mWduty = (EditText) findViewById(R.id.wduty);
        mWrequire = (EditText) findViewById(R.id.wrequire);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_public:

                if (TextUtils.isEmpty(mWname.getText())) {
                    T.toast("岗位名称不能为空！");
                    return;
                }
                if (TextUtils.isEmpty(mWpay.getText())) {
                    T.toast("岗位工资不能为空！");
                    return;
                } else {
                    String sql = Constant.host + "publicWorkServlet?wname=" + mWname.getText().toString()
                            + "&wpay=" + mWpay.getText().toString() + "&wnum=" + mWnum.getText().toString()
                            + "&wduty=" + mWduty.getText().toString() + "&wrequire=" + mWrequire.getText().toString() + "";

                    publicWork(sql);

                }

                break;
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x123) {
                T.toast("发布成功！");
                finish();
            }
            if (msg.what == 0x124) {
                T.toast("发布失败，请重新发布！");
            }
        }
    };

    private void publicWork(final String sql) {
        new Thread() {
            @Override
            public void run() {
                String str = HttpUtil.doGet(sql);
                if ("false".equals(str)) {
                    handler.sendEmptyMessage(0x124);
                } else {
                    handler.sendEmptyMessage(0x123);
                }

            }
        }.start();

    }
}
