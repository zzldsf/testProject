package com.example.zhaoziliang.mydemo;

import android.os.Environment;

import java.io.File;

/**
 * Created by zhaoziliang on 2018/2/8.
 */

public class FileConstant {
    public static final String zipPath= Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator +"tupian.zip";
    public static final String JS_BUNDLE_REACT_UPDATE_PATH = Environment.getExternalStorageDirectory().toString();
    public static final String remoteZipPath="http://xiazaizip.oss-cn-beijing.aliyuncs.com/tupian.zip";
}
