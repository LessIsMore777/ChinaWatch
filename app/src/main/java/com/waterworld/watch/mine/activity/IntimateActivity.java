package com.waterworld.watch.mine.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.waterworld.watch.R;
import com.waterworld.watch.common.activity.BaseActivity;
import com.waterworld.watch.common.util.ScreenAdapterUtil;
import com.waterworld.watch.common.util.ToastUtils;

/**
 * 编写者：Created by SunnyTang
 * 时间：2018/12/18 18:05
 * 主要作用：贴心设置(活动)
 */
public class IntimateActivity extends BaseActivity implements View.OnClickListener {

    private IntimateActivity thisActivity = this;

    private LinearLayout headerParent; //根布局
    private ImageButton headerBack; //返回按钮
    private TextView headerTitle; //标题
    private ImageButton headerMenu;//菜单按钮

    private CheckBox cbShieldStrangePhone;//屏蔽陌生电话
    private CheckBox cbReserveElectricity;//预留电量
    private CheckBox cbPowerSavingMode;//省电模式
    private CheckBox cbCallLocation;//通话位置
    private CheckBox cbAutoAnswer;//自动接听
    private CheckBox cbBanShutDown;//禁止关机
    private CheckBox cbVolCall;//VOL高清通话

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intimate);
        bindView();
        setView();
        bindClick();
    }

    private void bindView() {
        headerParent = findViewById(R.id.header_parent);
        headerBack = findViewById(R.id.header_back);
        headerTitle = findViewById(R.id.header_title);
        headerMenu = findViewById(R.id.header_menu);

        cbShieldStrangePhone = findViewById(R.id.cb_shield_strange_phone);
        cbReserveElectricity = findViewById(R.id.cb_reserve_electricity);
        cbPowerSavingMode = findViewById(R.id.cb_power_saving_mode);
        cbCallLocation = findViewById(R.id.cb_call_location);
        cbAutoAnswer = findViewById(R.id.cb_auto_answer);
        cbBanShutDown = findViewById(R.id.cb_ban_shutDown);
        cbVolCall = findViewById(R.id.cb_vol_call);
    }

    private void setView() {
        setViewSize(headerParent, ViewGroup.LayoutParams.MATCH_PARENT, ScreenAdapterUtil.getHeightPx(this) / 12);
        headerTitle.setText("贴心设置");
        headerBack.setVisibility(View.VISIBLE);
        headerTitle.setVisibility(View.VISIBLE);
        headerMenu.setVisibility(View.VISIBLE);
    }

    private void bindClick() {
        headerBack.setOnClickListener(this);
        headerMenu.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.header_back:
                finish();
                break;
            case R.id.header_menu:
                ToastUtils.showCustomTime(thisActivity,
                        cbShieldStrangePhone.isChecked() + "," +
                                cbReserveElectricity.isChecked() + "," +
                                cbPowerSavingMode.isChecked() + "," +
                                cbCallLocation.isChecked() + "," +
                                cbAutoAnswer.isChecked() + "," +
                                cbBanShutDown.isChecked() + "," +
                                cbVolCall.isChecked() + ",",
                        5000);
                break;
        }
    }
}
