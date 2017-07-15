package com.example.liujiao.sanitationwork.util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by liuji on 2017/3/13.
 */

public class T {

    private static Context context = null;

    public T(Context context) {
        this.context = context;
    }


    public static void toast(String string) {
        if (context != null) {
            Toast.makeText(context, string, Toast.LENGTH_SHORT).show();

        }
    }

    public static void log(String string) {
        Log.i("TAG ", string);
    }

}
