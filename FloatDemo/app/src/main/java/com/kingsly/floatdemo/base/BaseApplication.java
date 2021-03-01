package com.kingsly.floatdemo.base;

import android.app.Application;
import android.content.Context;

/**
 * @author : kingsly
 * @date : On 2021/3/1
 */
public class BaseApplication extends Application {
    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }
}