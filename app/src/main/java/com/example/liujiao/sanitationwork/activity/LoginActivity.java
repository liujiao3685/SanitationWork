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
import com.google.gson.Gson;

/**
 * Created by liujiao on 2017/5/2.
 */

public class LoginActivity extends Activity implements View.OnClickListener {

    /**
     * 用户名
     */
    private EditText mUsername;
    /**
     * 密码
     */
    private EditText mPassword;
    /**
     * 登录
     */
    private Button mLogin;
    /**
     * 注册
     */
    private Button mRegister;
    private String name, pwd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        mUsername = (EditText) findViewById(R.id.username);
        mPassword = (EditText) findViewById(R.id.password);
        mLogin = (Button) findViewById(R.id.login);
        mLogin.setOnClickListener(this);
        mRegister = (Button) findViewById(R.id.register);
        mRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String name = mUsername.getText().toString();
        String pwd = mPassword.getText().toString();
        switch (v.getId()) {
            case R.id.login:
                String url = Constant.host + "loginServlet?username=" + name + "&password=" + pwd;
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pwd)) {
                    T.toast("用户名或密码不能为空！");
                } else {
                    Log.e("url:",url);
                    doLogin(url);
                }

                break;
            case R.id.register:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;

        }
    }

    private void doLogin(final String urlPath) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                String str = HttpUtil.doGet(urlPath);
                Log.e("user", str + "");
                try {
                    if ("false".equals(str)) {
                        handler.sendEmptyMessage(0x123);
                    } else {
                        //gson解析
                        Gson gson = new Gson();
                        App.user = gson.fromJson(str, User.class);
                        handler.sendEmptyMessage(0x124);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x123) {
                T.toast("用户名密码错误");
            } else if (msg.what == 0x124) {
                T.toast("登录成功");
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        }
    };
}