package com.example.zhaoziliang.mydemo.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.zhaoziliang.mydemo.FileConstant;
import com.example.zhaoziliang.mydemo.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class SplashActivity extends AppCompatActivity {
    /**
     * 最短的等待时间 1.5秒
     */
    private static final int WAIT_SECONDS = 3000;// 最小显示时间
    /**
     * 引导页面的开始时间
     */
    private long mStartTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mStartTime = System.currentTimeMillis();  //获取当前的时间
        mHandler.sendEmptyMessage(GO_TO_LOGIN);

    }

    public void decompression(View view){
        Toast.makeText(this,"点击解压",Toast.LENGTH_SHORT).show();
        try {
            ZipInputStream inZip = null;
            inZip = new ZipInputStream(new FileInputStream(FileConstant.zipPath));
            ZipEntry zipEntry;
            String szName;
            try {
                while((zipEntry = inZip.getNextEntry()) != null) {

                    szName = zipEntry.getName();
                    if(zipEntry.isDirectory()) {
                        szName = szName.substring(0,szName.length()-1);
                        File folder = new File(FileConstant.JS_BUNDLE_REACT_UPDATE_PATH + File.separator + szName);
                        folder.mkdirs();
                    }else{
                        File file1 = new File(FileConstant.JS_BUNDLE_REACT_UPDATE_PATH + File.separator + szName);
                        boolean s = file1.createNewFile();
                        FileOutputStream fos = new FileOutputStream(file1);
                        int len;
                        byte[] buffer = new byte[1024];
                        while((len = inZip.read(buffer)) != -1) {
                            fos.write(buffer, 0 , len);
                            fos.flush();
                        }
                        fos.close();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            inZip.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mHandler.sendEmptyMessage(GO_TO_LOGIN);

    }

    private static final int GO_TO_LOGIN = 0;

    private Handler mHandler = new Handler() {

        public void handleMessage(Message msg) {

            switch (msg.what) {
                case GO_TO_LOGIN:// 这要进行判断 是否登陆过，如果有用户信息，这里就直接显示登陆验证码
                    long loadingTime = System.currentTimeMillis() - mStartTime;
                    if (loadingTime < WAIT_SECONDS) {// 如果比现实时间短，就延时进入主界面；否则直接进入
                        mHandler.postDelayed(openActivityRunnable, WAIT_SECONDS - loadingTime);
                    } else {
                        mHandler.post(openActivityRunnable);
                    }

                    break;


                default:
                    break;
            }

        }

        ;
    };

    // 打开主界面的runnable
    Runnable openActivityRunnable = new Runnable() {
        @Override
        public void run() {
            Intent intent = null;
                // 进入应用
                intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();
        }
    };

}
