package com.example.zhaoziliang.mydemo;

import android.app.Application;

import com.liulishuo.filedownloader.FileDownloader;

/**
 * Created by zhaoziliang on 2018/3/30.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FileDownloader.setup(this);
    }
}
