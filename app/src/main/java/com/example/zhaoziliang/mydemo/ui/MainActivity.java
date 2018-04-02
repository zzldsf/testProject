package com.example.zhaoziliang.mydemo.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.example.zhaoziliang.mydemo.R;
import com.example.zhaoziliang.mydemo.fragment.SampleFragment;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private FragmentManager mFragmentManager;// Fragment管理类
    private String mCurrentFragmentTag = "";// 当前显示的二级Fragment标签

    /**
     * 首页
     */
    private RadioButton rbHome;
    /**
     * 分类
     */
    private RadioButton rbCategory;
    /**
     * 购物车
     */
    private RadioButton rbShopCar;
    /**
     * 我
     */
    private RadioButton rbAboutMe;

    private SampleFragment mFragmentMain;
    private SampleFragment mFragmentChoice;
    private SampleFragment mFragmentShopCar;
    private SampleFragment mFragmentAboutMe;
    private Drawable mHomeNor;
    private Drawable mHomePress;
    private Drawable mCategoryNor;
    private Drawable mCategoryPress;
    private Drawable mShopCarNor;
    private Drawable mShopCarPress;
    private Drawable mAboutMeNor;
    private Drawable mAboutMePress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mFragmentManager = this.getSupportFragmentManager();


        // find views和setAction
        rbHome = (RadioButton) this.findViewById(R.id.rb_tab_main);
        rbCategory = (RadioButton) this.findViewById(R.id.rb_tab_choice);
        rbShopCar = (RadioButton) this.findViewById(R.id.rb_tab_shopCar);
        rbAboutMe = (RadioButton) this.findViewById(R.id.rb_tab_aboutme);

        rbHome.setOnCheckedChangeListener(this);
        rbCategory.setOnCheckedChangeListener(this);
        rbShopCar.setOnCheckedChangeListener(this);
        rbAboutMe.setOnCheckedChangeListener(this);

        rbHome.setChecked(true);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int midu = metrics.densityDpi;
        String s1 = "";
        String s2 = "";
        if (midu < 280) {//hdpi
            s2 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/hdpi/pay_rb_checked.png";
            s1 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/hdpi/pay_rb_unchecked.png";
        } else if (midu > 400) {//xxhdpi
            s2 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/xxhdpi/pay_rb_checked.png";
            s1 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/xxhdpi/pay_rb_unchecked.png";
        } else {//xhdpi
            s2 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/xhdpi/pay_rb_checked.png";
            s1 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/xhdpi/pay_rb_unchecked.png";
        }
        Log.d("zzl","屏幕密度"+midu);
        if (fileIsExists(s1) && fileIsExists(s2)) {
            Bitmap bitmap1 = BitmapFactory.decodeFile(s1);
            Bitmap bitmap2 = BitmapFactory.decodeFile(s2);
            Log.d("zzl", s1 + "   " + s2);
            Drawable drawable1 = new BitmapDrawable(bitmap1);
            drawable1.setBounds(0, 0, 80, 80);

            Drawable drawable2 = new BitmapDrawable(bitmap2);
            drawable2.setBounds(0, 0, 80, 80);

            mHomeNor = drawable1;
            mHomePress = drawable2;
            mCategoryNor = drawable1;
            mCategoryPress = drawable2;
            mShopCarNor = drawable1;
            mShopCarPress = drawable2;
            mAboutMeNor = drawable1;
            mAboutMePress = drawable2;
        } else {
            mHomeNor = gainDrawable(R.drawable.rb_home_normal);
            mHomePress = gainDrawable(R.drawable.rb_home_pressed);
            mCategoryNor = gainDrawable(R.drawable.rb_fenlei_normal);
            mCategoryPress = gainDrawable(R.drawable.rb_fenlei_pressed);
            mShopCarNor = gainDrawable(R.drawable.rb_shopcar_normal);
            mShopCarPress = gainDrawable(R.drawable.rb_shopcar_pressed);
            mAboutMeNor = gainDrawable(R.drawable.rb_mine_normal);
            mAboutMePress = gainDrawable(R.drawable.rb_mine_pressed);
        }

        //默认选中首页
        switchTab(0);


    }

    //判断文件是否存在
    public boolean fileIsExists(String strFile) {
        try {
            File f = new File(strFile);
            if (!f.exists()) {
                return false;
            }

        } catch (Exception e) {
            return false;
        }

        return true;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (!isChecked)
            return;
        switch (buttonView.getId()) {
            case R.id.rb_tab_main:
                switchTab(0);
                switchFragmen(this.getString(R.string.tab_main));
                break;
            case R.id.rb_tab_choice:
                switchTab(1);
                switchFragmen(this.getString(R.string.tab_choice));
                break;
            case R.id.rb_tab_shopCar:
                switchTab(2);
                switchFragmen(this.getString(R.string.tab_shopcar));
                break;
            case R.id.rb_tab_aboutme:
                switchTab(3);
                switchFragmen(this.getString(R.string.tab_about_me));
                break;
            default:
                break;
        }
    }

    private void switchTab(int id) {
        switch (id) {
            case 0:
                rbHome.setCompoundDrawables(null, mHomePress, null, null);
                rbCategory.setCompoundDrawables(null, mCategoryNor, null, null);
                rbShopCar.setCompoundDrawables(null, mShopCarNor, null, null);
                rbAboutMe.setCompoundDrawables(null, mAboutMeNor, null, null);
                break;
            case 1:
                rbHome.setCompoundDrawables(null, mHomeNor, null, null);
                rbCategory.setCompoundDrawables(null, mCategoryPress, null, null);
                rbShopCar.setCompoundDrawables(null, mShopCarNor, null, null);
                rbAboutMe.setCompoundDrawables(null, mAboutMeNor, null, null);
                break;
            case 2:
                rbHome.setCompoundDrawables(null, mHomeNor, null, null);
                rbCategory.setCompoundDrawables(null, mCategoryNor, null, null);
                rbShopCar.setCompoundDrawables(null, mShopCarPress, null, null);
                rbAboutMe.setCompoundDrawables(null, mAboutMeNor, null, null);
                break;
            case 3:
                rbHome.setCompoundDrawables(null, mHomeNor, null, null);
                rbCategory.setCompoundDrawables(null, mCategoryNor, null, null);
                rbShopCar.setCompoundDrawables(null, mShopCarNor, null, null);
                rbAboutMe.setCompoundDrawables(null, mAboutMePress, null, null);
                break;

        }
    }

    /**
     * 点击不同的Tab切换fragment
     *
     * @param tag
     */
    private void switchFragmen(String tag) {


        mCurrentFragmentTag = tag;// 设置当前标志位

        // 开启一个Fragment事务
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);

        if (getString(R.string.tab_main).equals(tag)) {
            if (mFragmentMain == null) {
                // 如果MessageFragment为空，则创建一个并添加到界面上
                mFragmentMain = SampleFragment.newInstance(tag);
                transaction.add(R.id.main_content, mFragmentMain);
            } else {
                // 如果MessageFragment不为空，则直接将它显示出来
                transaction.show(mFragmentMain);
            }
        } else if (getString(R.string.tab_choice).equals(tag)) {
            if (mFragmentChoice == null) {
                // 如果MessageFragment为空，则创建一个并添加到界面上
                mFragmentChoice = SampleFragment.newInstance(tag);
                transaction.add(R.id.main_content, mFragmentChoice);
            } else {
                // 如果MessageFragment不为空，则直接将它显示出来
                transaction.show(mFragmentChoice);
            }
        } else if (getString(R.string.tab_shopcar).equals(tag)) {
            if (mFragmentShopCar == null) {
                // 如果MessageFragment为空，则创建一个并添加到界面上
                mFragmentShopCar = SampleFragment.newInstance(tag);
                transaction.add(R.id.main_content, mFragmentShopCar);
            } else {
                // 如果MessageFragment不为空，则直接将它显示出来
                transaction.show(mFragmentShopCar);
            }
        } else if (getString(R.string.tab_about_me).equals(tag)) {
            if (mFragmentAboutMe == null) {
                // 如果MessageFragment为空，则创建一个并添加到界面上
                mFragmentAboutMe = SampleFragment.newInstance(tag);
                transaction.add(R.id.main_content, mFragmentAboutMe);
            } else {
                // 如果MessageFragment不为空，则直接将它显示出来
                transaction.show(mFragmentAboutMe);
            }
        }


//        transaction.commit();
        transaction.commitAllowingStateLoss();


    }

    /**
     * 隐藏其他的fragment
     *
     * @param transaction
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (mFragmentMain != null) {
            transaction.hide(mFragmentMain);
        }
        if (mFragmentChoice != null) {
            transaction.hide(mFragmentChoice);
        }
        if (mFragmentShopCar != null) {
            transaction.hide(mFragmentShopCar);
        }
        if (mFragmentAboutMe != null) {
            transaction.hide(mFragmentAboutMe);
        }
    }

    private Drawable gainDrawable(int resID) {
        Drawable drawable = getResources().getDrawable(resID);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        return drawable;
    }
}
