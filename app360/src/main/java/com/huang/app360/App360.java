package com.huang.app360;

import android.app.Application;
import android.content.Context;

/**
 * Created by huang on 2018/3/5.
 */

public class App360 extends Application {

    private  static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext(){
        return mContext;
    }
}
