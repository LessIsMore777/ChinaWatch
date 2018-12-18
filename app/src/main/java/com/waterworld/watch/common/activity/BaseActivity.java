package com.waterworld.watch.common.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.hjq.permissions.OnPermission;
import com.hjq.permissions.XXPermissions;
import com.waterworld.watch.common.util.AppManager;

import java.security.Permission;
import java.util.List;

/**
 * 编写者：Created by SunnyTang
 * 时间：2018/11/27 17:57
 * 主要作用：基础活动
 */
public class BaseActivity extends AppCompatActivity {

    private final String TAG = "BaseActivity";
    private InputMethodManager manager;//输入管理类
    public static boolean isShow = true;//是否显示吐司
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
        isGetPermission();
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
     * 自定义显示Toast时间
     */
    public static void setCustomTimeToast(Context context, CharSequence message, int duration) {
        if (isShow) {
            Toast toast = Toast.makeText(context, message, duration);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
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
}
