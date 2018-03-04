package com.example.dapp;

/**
 * Created by Administrator on 2018/1/8.
 */

import android.app.Application;
import android.content.Context;

import Util.DrawUtil;


/**
 * application
 */
public class BaseApplication extends Application {
    protected static BaseApplication sInstance;

    public BaseApplication() {
        sInstance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        DrawUtil.resetDensity(this);
    }


    public static Context getAppContext() {
        return sInstance;
    }


}