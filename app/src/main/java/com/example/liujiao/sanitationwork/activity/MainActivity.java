package com.example.liujiao.sanitationwork.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.liujiao.sanitationwork.R;

public class MainActivity extends Activity implements View.OnClickListener {

    private TextView mMyName;
    private RelativeLayout mRlMe;
    private RelativeLayout mRlWork;
    private RelativeLayout mRlPay;
    private RelativeLayout mRlCard;
    private RelativeLayout mRlUnlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        mMyName.setText(App.user.getName());

    }

    private void initView() {
        mMyName = (TextView) findViewById(R.id.my_name);
        mRlMe = (RelativeLayout) findViewById(R.id.rl_me);
        mRlMe.setOnClickListener(this);
        mRlWork = (RelativeLayout) findViewById(R.id.rl_work);
        mRlWork.setOnClickListener(this);
        mRlPay = (RelativeLayout) findViewById(R.id.rl_pay);
        mRlPay.setOnClickListener(this);
        mRlCard = (RelativeLayout) findViewById(R.id.rl_card);
        mRlCard.setOnClickListener(this);
        mRlUnlogin = (RelativeLayout) findViewById(R.id.rl_unlogin);
        mRlUnlogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_me:
                startActivity(new Intent(getBaseContext(), UpdateUserActivity.class));
                break;
            case R.id.rl_work:
                startActivity(new Intent(getBaseContext(), WorkManageActivity.class));
                break;
            case R.id.rl_pay:
                startActivity(new Intent(getBaseContext(), PayManageActivity.class));
                break;
            case R.id.rl_card:
                startActivity(new Intent(getBaseContext(), CardManageActivity.class));
                break;
            case R.id.rl_unlogin:
                startActivity(new Intent(getBaseContext(), LoginActivity.class));
                finish();
                break;
        }
    }
}
