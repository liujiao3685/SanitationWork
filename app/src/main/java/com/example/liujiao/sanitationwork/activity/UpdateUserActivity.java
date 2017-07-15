package com.example.liujiao.sanitationwork.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
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
 * Created by liujiao on 2017/4/17.
 */

public class UpdateUserActivity extends Activity implements View.OnClickListener {
    private EditText name, pwd, username, tel, sex, age;
    private Button ok;
    private Boolean flag = true;
    private User user;
    private String uid = App.user.getId() + "";

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateuser);

        initView();

        initEdit(false);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.update_ok:

                String sname = name.getText().toString();
                String spwd = pwd.getText().toString();
                String susername = username.getText().toString();
                String ssex = sex.getText().toString();
                String sage = age.getText().toString();
                String stel = tel.getText().toString();

                if (flag) {
                    initEdit(true);
                    ok.setText("确认修改");
                    flag = false;

                } else {
                    initEdit(false);
                    user = new User();
                    user.setId(Integer.parseInt(uid));
                    user.setPassword(spwd);
                    user.setUsername(susername);
                    user.setName(sname);
                    user.setTel(stel);
                    user.setAge(sage);
                    user.setSex(ssex);

                    String url = Constant.host + "updateUserServlet?uid=" + uid
                            + "&username=" + susername + "&password=" + spwd + "&age=" + sage + "&sex=" + ssex
                            + "&tel=" + stel + "&name=" + sname;
                    T.log(url);
                    flag = true;
                    doUpdateUser(url);
                    ok.setText("立即修改");

                }
                break;
        }
    }

    private void initView() {
        username = (EditText) findViewById(R.id.update_username);
        name = (EditText) findViewById(R.id.update_name);
        pwd = (EditText) findViewById(R.id.update_password);
        tel = (EditText) findViewById(R.id.update_pho);
        sex = (EditText) findViewById(R.id.update_sex);
        age = (EditText) findViewById(R.id.update_ages);
        ok = (Button) findViewById(R.id.update_ok);

        username.setText(App.user.getUsername());
        name.setText(App.user.getName());
        pwd.setText(App.user.getPassword());
        tel.setText(App.user.getTel());
        sex.setText(App.user.getSex());
        age.setText(App.user.getAge());

        ok.setOnClickListener(this);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x123) {
                App.user = user;
                Log.e("newidr", App.user.getTel());
                T.toast("修改成功！");
                finish();
            } else if (msg.what == 0x122) {
                T.toast("修改失败！");
            }
        }
    };


    private void doUpdateUser(final String urlPath) {
        new Thread() {
            @Override
            public void run() {

                String str = HttpUtil.doGet(urlPath);
                Log.e("update:", urlPath);
                if ("true".equals(str)) {
                    handler.sendEmptyMessage(0x123);
                } else if ("false".equals(str)) {
                    handler.sendEmptyMessage(0x122);
                }


            }
        }.start();

    }

    private void initEdit(boolean boo) {
        username.setEnabled(boo);
        name.setEnabled(boo);
        pwd.setEnabled(boo);
        tel.setEnabled(boo);
        age.setEnabled(boo);
        sex.setEnabled(boo);
    }

}
