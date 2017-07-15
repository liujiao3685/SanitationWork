package com.example.liujiao.sanitationwork.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.liujiao.sanitationwork.R;

public class WorkManageActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 发布岗位
     */
    private Button mBtnPublicwork;
    /**
     * 岗位考核
     */
    private Button mBtnKaohework;
    /**
     * 岗位录取
     */
    private Button mBtnLuquwork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_manage);
        initView();
    }

    private void initView() {
        mBtnPublicwork = (Button) findViewById(R.id.btn_publicwork);
        mBtnPublicwork.setOnClickListener(this);
        mBtnKaohework = (Button) findViewById(R.id.btn_kaohework);
        mBtnKaohework.setOnClickListener(this);
        mBtnLuquwork = (Button) findViewById(R.id.btn_luquwork);
        mBtnLuquwork.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_publicwork:
                startActivity(new Intent(getBaseContext(), PublicWorkActivity.class));
                break;
            case R.id.btn_kaohework:
                startActivity(new Intent(getBaseContext(), KaoHeActivity.class));
                break;
            case R.id.btn_luquwork:
                startActivity(new Intent(getBaseContext(), LuQuWorkActivity.class));
                break;
        }
    }
}
