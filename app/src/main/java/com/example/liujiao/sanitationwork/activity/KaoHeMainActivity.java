package com.example.liujiao.sanitationwork.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.liujiao.sanitationwork.R;
import com.example.liujiao.sanitationwork.bean.Work;
import com.example.liujiao.sanitationwork.util.Constant;
import com.example.liujiao.sanitationwork.util.HttpUtil;
import com.example.liujiao.sanitationwork.util.T;

public class KaoHeMainActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 游戏特效师（2D，3D）
     */
    private TextView tv_wmname;
    /**
     * 15k-25k
     */
    private TextView tv_wmpay;
    /**
     * 100人
     */
    private TextView tv_wmnum;
    /**
     * 1、根据游戏风格完成游戏特效规划及制作游戏中所需特效；
     * <p>
     * 2、负责特效部分的整套流程，跟踪保证项目相关资源进度。
     */
    private TextView tv_wmduty;
    /**
     * 1、4年以上游戏特效从业经验，有一定团队管理经验和管理技巧；对物体运动规律、特效节奏、视觉感受有良好的理解；
     * <p>
     * 2 、熟悉Flash、Unity引擎，精通AE、3DMAX等特效制作软件，了解格斗游戏/ARPG游戏特效制作规范；
     */
    private TextView tv_wmrequire;
    /**
     * 通过
     */
    private Button btn_wstate;
    /**
     * 未通过
     */
    private Button btn_wunstate;
    private Work work;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kao_he_main);
        initView();

        setText();

    }

    private void setText() {
        /*work = getIntent().getParcelableExtra("work");
        tv_wmname.setText(work.getWname());
        tv_wmpay.setText(work.getWpay());
        tv_wmnum.setText(work.getWnum());
        tv_wmduty.setText(work.getWduty());
        tv_wmrequire.setText(work.getWrequire());*/

        intent = getIntent();
        tv_wmname.setText(intent.getStringExtra("name"));
        tv_wmpay.setText(intent.getStringExtra("pay"));
        tv_wmnum.setText(intent.getStringExtra("num"));
        tv_wmduty.setText(intent.getStringExtra("duty"));
        tv_wmrequire.setText(intent.getStringExtra("require"));
    }

    private void initView() {
        tv_wmname = (TextView) findViewById(R.id.tv_wmname);
        tv_wmpay = (TextView) findViewById(R.id.tv_wmpay);
        tv_wmnum = (TextView) findViewById(R.id.tv_wmnum);
        tv_wmduty = (TextView) findViewById(R.id.tv_wmduty);
        tv_wmrequire = (TextView) findViewById(R.id.tv_wmrequire);
        btn_wstate = (Button) findViewById(R.id.btn_wstate);
        btn_wstate.setOnClickListener(this);
        btn_wunstate = (Button) findViewById(R.id.btn_wunstate);
        btn_wunstate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_wstate:
                //通过审核
                String sql = Constant.host + "updateWorkState?wid=" + intent.getStringExtra("id") + "";
                updateState(sql);
                startActivity(new Intent(getBaseContext(), KaoHeActivity.class));
                finish();
                break;
            case R.id.btn_wunstate:
                //未通过审核
                T.toast("感谢您的审核！");
                startActivity(new Intent(getBaseContext(), KaoHeActivity.class));
                finish();
                break;
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x125) {
                T.toast("审核通过！");
            } else if (msg.what == 0x126) {
                T.toast("审核失败！");
            }
        }
    };

    //更新岗位审核状态
    private void updateState(final String sql) {
        new Thread() {
            @Override
            public void run() {
                String str = HttpUtil.doGet(sql);
                if ("true".equals(str)) {
                    handler.sendEmptyMessage(0x125);
                } else {
                    handler.sendEmptyMessage(0x126);
                }

            }
        }.start();
    }
}
