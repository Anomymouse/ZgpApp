package com.zgp.application;

import android.app.Application;
import android.util.Log;

import com.zgp.utils.MyLogger;

/**
 * Created by 61720 on 2016/4/13.
 */
public class MyApplication extends Application {

    private MyApplication application;

    @Override
    public void onCreate() {
        super.onCreate();

        MyLogger.log("程序运行到这里 MyApplication onCreate");
    }

    public MyApplication getApplication() {
        return application;
    }

    public void setApplication(MyApplication application) {
        this.application = application;
    }
}
