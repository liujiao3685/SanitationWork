package com.example.liujiao.sanitationwork.activity;

import android.app.Application;

import com.example.liujiao.sanitationwork.bean.User;
import com.example.liujiao.sanitationwork.bean.Work;
import com.example.liujiao.sanitationwork.util.T;


/**
 * Created by liuji on 2017/3/13.
 * 用于初始化操作
 */

public class App extends Application {

    public static User user;
    public static Work work;

    public void onCreate() {
        super.onCreate();
        //对T进行实例化操作
        new T(this);
    }
}
