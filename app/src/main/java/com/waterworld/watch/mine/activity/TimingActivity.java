package com.waterworld.watch.mine.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.waterworld.watch.R;
import com.waterworld.watch.common.activity.BaseActivity;
import com.waterworld.watch.common.customview.CommonPopupWindow;
import com.waterworld.watch.common.util.TimeUtils;
import com.waterworld.watch.common.util.ScreenAdapterUtil;
import com.waterworld.watch.common.util.ViewUtils;

import cn.carbswang.android.numberpickerview.library.NumberPickerView;

/**
 * 编写者：Created by SunnyTang
 * 时间：2018/12/18 14:07
 * 主要作用：定时开关机(碎片)
 */
public class TimingActivity extends BaseActivity implements View.OnClickListener {

    private TimingActivity thisActivity;

    private ConstraintLayout parentView;
    private LinearLayout headerParent; //根布局
    private ImageButton headerBack; //返回按钮
    private TextView headerTitle; //标题

    private ConstraintLayout clAutoBoot;//自动开机
    private TextView tvAutoBootTime;//自动开机显示的时间
    private CheckBox cbAutoBoot;//自动开机开关
    private ConstraintLayout clAutoShutdown;//自动关机
    private TextView tvAutoShutdownTime;//自动关机显示的时间
    private CheckBox cbAutoShutdown;//自动关机开关

    private CommonPopupWindow popupWindow;
    private CommonPopupWindow.LayoutGravity layoutGravity;// 泡泡窗口锚点对象
    private TextView tvTitle;
    private NumberPickerView npHour;
    private NumberPickerView npMinute;
    private ConstraintLayout clCancel;
    private ConstraintLayout clConfirm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_timing);
        thisActivity = this;
        bindView();
        initView();
        bindClick();
    }

    private void bindView() {
        parentView = findViewById(R.id.parentView);
        headerParent = findViewById(R.id.header_parent);
        headerBack = findViewById(R.id.header_back);
        headerTitle = findViewById(R.id.header_title);

        clAutoBoot = findViewById(R.id.cl_auto_boot);
        tvAutoBootTime = findViewById(R.id.tv_auto_boot_time);
        cbAutoBoot = findViewById(R.id.cb_auto_boot);
        clAutoShutdown = findViewById(R.id.cl_auto_shutdown);
        tvAutoShutdownTime = findViewById(R.id.tv_auto_shutdown_time);
        cbAutoShutdown = findViewById(R.id.cb_auto_shutdown);
    }

    private void initView() {
        setViewSize(headerParent, ViewGroup.LayoutParams.MATCH_PARENT, ScreenAdapterUtil.getHeightPx(this) / 12);
        headerTitle.setText("定时开关机");
        headerBack.setVisibility(View.VISIBLE);
        headerTitle.setVisibility(View.VISIBLE);
    }

    private void bindClick() {
        headerBack.setOnClickListener(thisActivity);
        clAutoBoot.setOnClickListener(thisActivity);
        cbAutoBoot.setOnClickListener(thisActivity);
        clAutoShutdown.setOnClickListener(thisActivity);
        cbAutoShutdown.setOnClickListener(thisActivity);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.header_back:
                finish();
                break;
            case R.id.cl_auto_boot:
                ViewUtils.setBackgroundAlpha(thisActivity,0.5f);
                showPopupWindow("开机时间");
                break;
            case R.id.cb_auto_boot:
                if (cbAutoBoot.isChecked()) {
                    clAutoBoot.setEnabled(true);
                    //tvAutoBootTime.setText("06:30");
                } else {
                    clAutoBoot.setEnabled(false);
                    tvAutoBootTime.setText("00:00");
                }
                break;
            case R.id.cl_auto_shutdown:
                ViewUtils.setBackgroundAlpha(thisActivity,0.5f);
                showPopupWindow("关机时间");
                break;
            case R.id.cb_auto_shutdown:
                if (cbAutoShutdown.isChecked()) {
                    clAutoShutdown.setEnabled(true);
                    //tvAutoShutdownTime.setText("22:00");
                } else {
                    clAutoShutdown.setEnabled(false);
                    tvAutoShutdownTime.setText("00:00");
                }
                break;
        }
    }

    private void showPopupWindow(final String title) {
        popupWindow = new CommonPopupWindow(thisActivity, R.layout.popup_timing, (int) (ScreenAdapterUtil.getWidthPx(thisActivity) * 0.9),ViewGroup.LayoutParams.WRAP_CONTENT) {
            @Override
            protected void initView() {
                View view = getContentView();
                tvTitle = view.findViewById(R.id.tv_popup_title);
                npHour = view.findViewById(R.id.np_hour);
                npMinute = view.findViewById(R.id.np_minute);
                clCancel = view.findViewById(R.id.cl_cancel);
                clConfirm = view.findViewById(R.id.cl_confirm);
                tvTitle.setText(title);
                //设置“小时”数据，且初始化“小时”选择器
                String[] hour = TimeUtils.returnStringArray(0,24);
                npHour.setDisplayedValues(hour);
                npHour.setMinValue(0);
                npHour.setMaxValue(hour.length - 1);
                npHour.setValue(TimeUtils.getCurrentHour24());
                //设置“分钟”数据，且初始化“分钟”选择器
                String[] minute = TimeUtils.returnStringArray(0,59);
                npMinute.setDisplayedValues(minute);
                npMinute.setMinValue(0);
                npMinute.setMaxValue(minute.length - 1);
                npMinute.setValue(TimeUtils.getCurrentMinute());
            }

            @Override
            protected void initEvent() {
                clCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.getPopupWindow().dismiss();
                    }
                });
                clConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String data = npHour.getContentByCurrValue() + ":" +npMinute.getContentByCurrValue();
                        if (title.equals("开机时间")){
                            tvAutoBootTime.setText(data);
                        } else if (title.equals("关机时间")){
                            tvAutoShutdownTime.setText(data);
                        }
                        popupWindow.getPopupWindow().dismiss();
                    }
                });
            }
        };
        popupWindow.getPopupWindow().setOnDismissListener(new android.widget.PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                ViewUtils.setBackgroundAlpha(thisActivity,1.0f);
                popupWindow = null;
            }
        });
        popupWindow.showAtLocation(parentView, Gravity.CENTER, 0, 0);
    }
}
