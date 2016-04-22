package com.zgp.application;

import android.app.Application;
import android.util.Log;

/**
 * Created by 61720 on 2016/4/13.
 */
public class MyApplication extends Application {

    private MyApplication application;

    @Override
    public void onCreate() {
        super.onCreate();

        Log.e("MyApplication", "程序运行到这里");
    }

    public MyApplication getApplication() {
        return application;
    }

    public void setApplication(MyApplication application) {
        this.application = application;
    }
}
