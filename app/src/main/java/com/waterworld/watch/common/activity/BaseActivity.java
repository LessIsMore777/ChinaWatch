package com.waterworld.watch.common.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.hjq.permissions.OnPermission;
import com.hjq.permissions.XXPermissions;
import com.waterworld.watch.R;
import com.waterworld.watch.common.customview.dialog.LoadingDialog;
import com.waterworld.watch.common.util.AppManager;

import java.util.List;



/**
 * 编写者：Created by SunnyTang
 * 时间：2018/11/27 17:57
 * 主要作用：基础活动
 */
public class BaseActivity extends AppCompatActivity{

    private final String TAG = BaseActivity.class.getSimpleName();
    private InputMethodManager manager;//输入管理类

    protected LoadingDialog mLoadingDialog;
    private LoadingDialog.Speed speed = LoadingDialog.Speed.SPEED_TWO;
    private int repeatTime = 0;
    private int color = Color.argb(255, 1, 162, 157);
    /**
     * 权限集合
     */
    private String[] permissionGroup = {
            Manifest.permission.CAMERA,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.addActivity(this);
        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //isGetPermission();
        setTranslucentStatus(true);
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0 全透明实现
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);//calculateStatusColor(Color.WHITE, (int) alphaValue)
        }
    }

    /**
     * 全屏显示
     */
    protected void setNoTitle() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    public void setViewSize(View view, int width, int height) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = width;
        params.height = height;
        view.setLayoutParams(params);
    }

    protected void showNormalDialog(String content,
                                    String exitText,
                                    String enterText,
                                    DialogInterface.OnClickListener onEnter,
                                    DialogInterface.OnClickListener onExit) {
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(this);
        //normalDialog.setTitle(getString(R.string.sweet_toast));
        normalDialog.setMessage(content);
        normalDialog.setPositiveButton(enterText, onEnter);
        normalDialog.setNegativeButton(exitText, onExit);
        normalDialog.show();
    }

    /**
     * 点击空白处隐藏软键盘
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.onTouchEvent(event);
    }


    /**
     * 界面跳转
     *
     * @param activity 要跳转到哪个界面
     */
    public void goActivity(Class<?> activity) {
        Intent intent = new Intent();
        intent.setClass(BaseActivity.this, activity);
        startActivity(intent);
    }

    /**
     * 带参数的界面跳转
     *
     * @param activity 要跳转到哪个界面
     * @param bundle   要携带的参数
     */
    public void goActivity(Class<?> activity, Bundle bundle) {
        Intent intent = new Intent(BaseActivity.this, activity);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 覆写声明周期方法
     * 当BaseActivity销毁的时候，销毁全部的activity
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.finishActivity(this);
    }

    /**
     * 是否获取了权限
     */
    public void isGetPermission() {
        if (!XXPermissions.isHasPermission(this, permissionGroup)) {
            getPermission();
        }
    }

    /**
     * 获取权限
     */
    public void getPermission() {
        XXPermissions.with(this)
                .constantRequest()//可设置被拒绝后继续申请，直到用户授权或者永久拒绝
                .permission(permissionGroup)
                .request(new OnPermission() {
                    @Override
                    public void hasPermission(List<String> granted, boolean isAll) {
                        if (isAll){
                            //所需要的权限已经全部授予
                            Log.i("权限请求:", "全部成功");
                        } else {
                            //有部分权限没有被授予
                            Log.i("权限请求:", "部分成功");
                            if (granted.contains(permissionGroup)){
                                Log.i("权限请求:", "继续申请");
                            }
                        }
                    }

                    @Override
                    public void noPermission(List<String> denied, boolean quick) {
                        if (quick) {
                            //权限被永久拒绝，需用户自己去设置里面开启权限
                            Log.i("权限请求：", "永久拒绝");
                        } else {
                            //权限获取失败
                            Log.i("权限请求：", "失败");
                        }
                        //这里在设置弹框，提示用户必须要该权限才能使用app，并设置跳转界面去手动开启
                    }
                });
    }

    public void gotoPermissionSettings() {
        XXPermissions.gotoPermissionSettings(this);
    }


    /**
     ***********************************************************************************************
     *
    */
    /**
     * showCustomTime Dialog
     */

    public void showDialog(String loading,String loadSuccess,String loadFail){
        if(mLoadingDialog == null){
            mLoadingDialog = new LoadingDialog(this);
        }
        if(loading != null && !loading.equals("")){
            mLoadingDialog.setLoadingText(loading);
        }else {
            mLoadingDialog.setLoadingText(getString(R.string.loading));
        }

        if(loadSuccess != null && !loadSuccess.equals("")){
            mLoadingDialog.setSuccessText(loadSuccess);
        }else {
            mLoadingDialog.setSuccessText(getString(R.string.load_success));
        }

        if(loadFail != null && !loadFail.equals("")){
            mLoadingDialog.setFailedText(loadFail);
        }else {
            mLoadingDialog.setFailedText(getString(R.string.load_fail));
        }
        mLoadingDialog.setLoadStyle(LoadingDialog.STYLE_RING); //转圈圈样式
        mLoadingDialog.setLoadSpeed(speed);
        mLoadingDialog.setRepeatCount(repeatTime);
        mLoadingDialog.setDrawColor(color);
        mLoadingDialog.setTextColor(Color.WHITE);
        mLoadingDialog.show();
    }

    protected void showNormalDialog(String content, DialogInterface.OnClickListener pos, DialogInterface.OnClickListener nega) {
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(this);
        normalDialog.setTitle(getString(R.string.sweet_toast));
        normalDialog.setMessage(content);
        normalDialog.setPositiveButton(getString(R.string.confirm), pos);
        normalDialog.setNegativeButton(getString(R.string.cancel), nega);
        normalDialog.show();
    }

    /**
     * 关闭Dialog
     */
    public void closeDialog(){
        if(mLoadingDialog != null){
            mLoadingDialog.close();
            mLoadingDialog = null;
        }
    }

}
