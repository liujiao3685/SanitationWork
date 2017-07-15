package com.example.liujiao.sanitationwork.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.liujiao.sanitationwork.R;
import com.example.liujiao.sanitationwork.adapter.EmployeeAdapter;
import com.example.liujiao.sanitationwork.bean.Employee;
import com.example.liujiao.sanitationwork.util.Constant;
import com.example.liujiao.sanitationwork.util.HttpUtil;
import com.example.liujiao.sanitationwork.util.T;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PayManageActivity extends Activity {

    private ListView mEmpListview;
    private List<Employee> list = new ArrayList<>();
    private Employee employee;
    private EmployeeAdapter adapter;
    private String sql = Constant.host + "getAllEmployee";
    private AlertDialog.Builder builder;
    //点击控件对话框消失
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_manage);
        initView();

        getAllWork(sql);

        mEmpListview.setOnItemClickListener(listener);


    }

    AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
            if ("录入".equals(list.get(i).getEstate())) {
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.view_employee, null);
                EditText name = (EditText) layout.findViewById(R.id.tv_viewname);
                EditText card = (EditText) layout.findViewById(R.id.tv_viewcard);
                EditText wid = (EditText) layout.findViewById(R.id.tv_viewwid);
                final EditText pay = (EditText) layout.findViewById(R.id.tv_viewpay);
                Button update = (Button) layout.findViewById(R.id.btn_update);
                Button ok = (Button) layout.findViewById(R.id.btn_finish);

                name.setText(list.get(i).getEname());
                card.setText(list.get(i).getEcard());
                pay.setText(list.get(i).getEpay());
                wid.setText(list.get(i).getEwid() + "");

                builder = new AlertDialog.Builder(PayManageActivity.this);
                builder.setView(layout);//添加线性布局
                final AlertDialog dialog = builder.show();

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (list.get(i).getEpay().equals(pay.getText().toString())) {
                            T.toast("工资未修改！");
                        } else {
                            String sql = Constant.host + "updatePayServlet?newpay=" + pay.getText().toString()
                                    + "&eid=" + list.get(i).getEid() + "";
                            updatePay(sql);
                            dialog.dismiss();
                        }
                    }
                });

            } else if ("未录入".equals(list.get(i).getEstate())) {

                AlertDialog.Builder ifFire = new AlertDialog.Builder(PayManageActivity.this);
                ifFire.setTitle("提示");
                ifFire.setMessage("是否录入此员工？");
                ifFire.setNegativeButton("取消", null);
                ifFire.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int pos) {
                        LayoutInflater inflater = getLayoutInflater();
                        View layout = inflater.inflate(R.layout.view_unemployee, null);
                        EditText name = (EditText) layout.findViewById(R.id.tv_viewname);
                        final EditText card = (EditText) layout.findViewById(R.id.tv_viewcard);
                        final EditText wid = (EditText) layout.findViewById(R.id.tv_viewwid);
                        final EditText pay = (EditText) layout.findViewById(R.id.tv_viewpay);
                        Button hire = (Button) layout.findViewById(R.id.btn_hire);

                        name.setText(list.get(i).getEname());

                        builder = new AlertDialog.Builder(PayManageActivity.this);
                        builder.setView(layout);//添加线性布局
                        dialog = builder.show();
                        hire.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (TextUtils.isEmpty(card.getText())) {
                                    T.toast("工资卡号不能为空！");
                                    return;
                                }
                                if (TextUtils.isEmpty(pay.getText())) {
                                    T.toast("工资不能为空！");
                                    return;
                                }
                                if (TextUtils.isEmpty(wid.getText())) {
                                    T.toast("岗位不能为空！");
                                    return;
                                } else {
                                    String sql = Constant.host + "fireEmployeeServlet?newwid=" + wid.getText().toString()
                                            + "&newcard=" + card.getText().toString() + "&newpay=" + pay.getText().toString()
                                            + "&eid=" + list.get(i).getEid() + "";
                                    fireEmployee(sql);
                                    dialog.dismiss();
                                }
                            }
                        });
                    }
                });
                ifFire.show();

            }
        }
    };

    private void initView() {
        mEmpListview = (ListView) findViewById(R.id.emp_listview);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x123) {
                T.log("获取员工数据成功！");
                adapter = new EmployeeAdapter(list, PayManageActivity.this);
                mEmpListview.setAdapter(adapter);
            } else if (msg.what == 0x124) {
                T.toast("获取员工数据失败！");
            } else if (msg.what == 0x111) {
                getAllWork(sql);
                T.toast("修改成功！");
            } else if (msg.what == 0x112) {
                T.toast("修改失败！");
            } else if (msg.what == 0x113) {
                getAllWork(sql);
                T.toast("录用成功！");
            } else if (msg.what == 0x114) {
                T.toast("录用失败！");
            }
        }
    };

    //录用员工
    private void fireEmployee(final String sql) {
        new Thread() {
            @Override
            public void run() {
                String str = HttpUtil.doGet(sql);
                if ("true".equals(str)) {
                    handler.sendEmptyMessage(0x113);
                } else {
                    handler.sendEmptyMessage(0x114);
                }

            }
        }.start();
    }

    //修改员工工资
    private void updatePay(final String sql) {

        new Thread() {
            @Override
            public void run() {
                String str = HttpUtil.doGet(sql);
                if ("true".equals(str)) {
                    handler.sendEmptyMessage(0x111);
                } else {
                    handler.sendEmptyMessage(0x112);
                }
            }
        }.start();
    }

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
                            employee = new Employee();
                            employee.setEid(jsonObject.getInt("eid"));
                            employee.setEname(jsonObject.getString("ename"));
                            employee.setEpay(jsonObject.getString("epay"));
                            employee.setEwid(jsonObject.getInt("ewid"));
                            employee.setEcard(jsonObject.getString("ecard"));
                            employee.setEstate(jsonObject.getString("estate"));
                            list.add(employee);
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
