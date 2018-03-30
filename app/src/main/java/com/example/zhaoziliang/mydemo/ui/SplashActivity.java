package com.example.zhaoziliang.mydemo.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.zhaoziliang.mydemo.FileConstant;
import com.example.zhaoziliang.mydemo.R;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.util.FileDownloadHelper;
import com.liulishuo.filedownloader.util.FileDownloadUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.PublicKey;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class SplashActivity extends AppCompatActivity {
    public static final int REQUEST_PERMISSION_STORAGE = 1;
    /**
     * 最短的等待时间 1.5秒
     */
    private static final int WAIT_SECONDS = 3000;// 最小显示时间
    /**
     * 引导页面的开始时间。
     */
    private long mStartTime;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mStartTime = System.currentTimeMillis();  //获取当前的时间
//        mHandler.sendEmptyMessage(GO_TO_LOGIN);
        btn=findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE))) {//检查存储权限
                    ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_STORAGE);
                } else {
                    downloadzip();
                }

            }
        });
    }
    //下载
    private void downloadzip(){
        //已经有权限可以下载和解压
        FileDownloader.getImpl().create(FileConstant.remoteZipPath)
                .setPath(FileConstant.zipPath)
                .setListener(createListener())
                .setTag(1)
                .start();
    }
    private FileDownloadListener createListener() {
        return new FileDownloadListener() {

            @Override
            protected boolean isInvalid() {
                return isFinishing();
            }

            @Override
            protected void pending(final BaseDownloadTask task, final int soFarBytes, final int totalBytes) {
                Log.d("aaa","pending");

            }

            @Override
            protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
                super.connected(task, etag, isContinue, soFarBytes, totalBytes);
                Log.d("aaa","connected");

            }

            @Override
            protected void progress(final BaseDownloadTask task, final int soFarBytes, final int totalBytes) {
                Log.d("aaa","progress");

            }

            @Override
            protected void blockComplete(final BaseDownloadTask task) {
                Log.d("aaa","blockComplete");

            }

            @Override
            protected void retry(BaseDownloadTask task, Throwable ex, int retryingTimes, int soFarBytes) {
                super.retry(task, ex, retryingTimes, soFarBytes);
                Log.d("aaa","retry");

            }

            @Override
            protected void completed(BaseDownloadTask task) {
                Log.d("aaa",task.getUrl());
                //解压
                jieya();
            }

            @Override
            protected void paused(final BaseDownloadTask task, final int soFarBytes, final int totalBytes) {
                Log.d("aaa","paused");

            }

            @Override
            protected void error(BaseDownloadTask task, Throwable e) {
                Log.d("aaa","error");

            }

            @Override
            protected void warn(BaseDownloadTask task) {
                Log.d("aaa","warn");

            }
        };
    }
    private void jieya() {
        try {
            ZipInputStream inZip = null;
            inZip = new ZipInputStream(new FileInputStream(FileConstant.zipPath));
            ZipEntry zipEntry;
            String szName;
            try {
                while ((zipEntry = inZip.getNextEntry()) != null) {

                    szName = zipEntry.getName();
                    if (zipEntry.isDirectory()) {
                        szName = szName.substring(0, szName.length() - 1);
                        File folder = new File(FileConstant.JS_BUNDLE_REACT_UPDATE_PATH + File.separator + szName);
                        folder.mkdirs();
                    } else {
                        File file1 = new File(FileConstant.JS_BUNDLE_REACT_UPDATE_PATH + File.separator + szName);
                        boolean s = file1.createNewFile();
                        FileOutputStream fos = new FileOutputStream(file1);
                        int len;
                        byte[] buffer = new byte[1024];
                        while ((len = inZip.read(buffer)) != -1) {
                            fos.write(buffer, 0, len);
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

    public boolean checkPermission(@NonNull String permission) {
        return ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_STORAGE) {
            if (grantResults.length > 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    downloadzip();
                } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {//点击拒绝授权

                    if (ActivityCompat.shouldShowRequestPermissionRationale(this
                            , Manifest.permission.WRITE_EXTERNAL_STORAGE)) {//点击拒绝，再次弹出
                        showToast("存储权限被禁止");
                    } else { // 选择不再询问，并点击拒绝，弹出提示框
                        showToast("选择不再询问，并点击了拒绝。请手动打开存储权限");
                    }
                }
            }

        }
    }
    private void showToast(String string){
        Toast.makeText(this,string,Toast.LENGTH_LONG).show();
    }


}
