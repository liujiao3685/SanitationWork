package com.example.liujiao.sanitationwork.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.liujiao.sanitationwork.R;
import com.example.liujiao.sanitationwork.bean.User;
import com.example.liujiao.sanitationwork.util.Constant;
import com.example.liujiao.sanitationwork.util.HttpUtil;
import com.example.liujiao.sanitationwork.util.T;

/**
 * Created by liujiao on 2017/5/2.
 */

public class RegisterActivity extends Activity implements View.OnClickListener {

    /**
     * 用户名
     */
    private EditText mResUsername;
    /**
     * 姓名
     */
    private EditText mResName;
    /**
     * 密码
     */
    private EditText mResPassword;
    /**
     * 请再次输入
     */
    private EditText mResPassword2;
    /**
     * 手机号码
     */
    private EditText mTel;
    /**
     * 请输入性别
     */
    private EditText mResSex;
    /**
     * 立即注册
     */
    private Button mResRegister;
    /**
     * 请输入年龄
     */
    private EditText mResAge;

    private String username, name, pwd, pwd2, age, sex, tel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        mResUsername = (EditText) findViewById(R.id.res_username);
        mResName = (EditText) findViewById(R.id.res_name);
        mResPassword = (EditText) findViewById(R.id.res_password);
        mResPassword2 = (EditText) findViewById(R.id.res_password2);
        mTel = (EditText) findViewById(R.id.tel);
        mResSex = (EditText) findViewById(R.id.res_sex);
        mResRegister = (Button) findViewById(R.id.res_register);
        mResRegister.setOnClickListener(this);
        mResAge = (EditText) findViewById(R.id.res_age);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.res_register:
                username = mResUsername.getText().toString();
                pwd2 = mResPassword2.getText().toString();
                pwd = mResPassword.getText().toString();
                sex = mResSex.getText().toString();
                tel = mTel.getText().toString();
                name = mResName.getText().toString();
                age = mResAge.getText().toString();
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pwd) || TextUtils.isEmpty(pwd2)) {
                    T.toast("用户名或密码不能为空！");
                    return;
                }
                if (!(pwd2.equals(pwd))) {
                    T.toast("两次输入的密码不一致，请重新输入！");
                    return;
                } else {
                    User us = new User();
                    us.setUsername(username);
                    us.setPassword(pwd);
                    us.setName(name);
                    us.setSex(sex);
                    us.setTel(tel);
                    register(Constant.host + "registerServlet?username=" + username + "&password=" + pwd + "" +
                            "&name=" + name + "&tel=" + tel + "&sex=" + sex + "&age=" + age + "");

                    Log.e("url", Constant.host + "registerServlet?username=" + username + "&password=" + pwd + "" +
                            "&name=" + name + "&tel=" + tel + "&sex=" + sex + "&age=" + age + "");
                }

                break;
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x123) {
                T.toast("注册失败");
            } else if (msg.what == 0x124) {
                T.toast("注册成功");
                startActivity(new Intent(getBaseContext(), LoginActivity.class));
                finish();
            }
        }
    };

    private void register(final String urlPath) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                String str = HttpUtil.doGet(urlPath);

                if ("false".equals(str)) {
                    handler.sendEmptyMessage(0x123);
                } else if ("true".equals(str)) {
                    handler.sendEmptyMessage(0x124);
                }
            }
        }.start();

    }
}
