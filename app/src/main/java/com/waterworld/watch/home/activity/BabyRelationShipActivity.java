package com.waterworld.watch.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.waterworld.watch.R;
import com.waterworld.watch.common.activity.BaseActivity;
import com.waterworld.watch.common.net.BaseObserverListener;
import com.waterworld.watch.common.net.BaseResultBean;
import com.waterworld.watch.common.util.ScreenAdapterUtil;
import com.waterworld.watch.common.util.ToastUtils;
import com.waterworld.watch.home.bean.RoleBean;
import com.waterworld.watch.home.interfaces.IHomeManager;
import com.waterworld.watch.home.manager.HomeManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BabyRelationShipActivity extends BaseActivity {

    @BindView(R.id.header_parent)
    LinearLayout header_parent;

    @BindView(R.id.header_back)
    ImageView header_back;

    @BindView(R.id.header_title)
    TextView header_title;

    @BindView(R.id.header_confirm)
    TextView header_confirm;

    @BindView(R.id.et_relationship)
    EditText et_relationship;

    private IHomeManager iHomeManager = HomeManager.getInstance();
    private String imei;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby_relation_ship);
        ButterKnife.bind(this);
        initView();
        if (getIntent().getStringExtra("imei") != null) {
            imei = getIntent().getStringExtra("imei");
        }
    }

    private void initView(){
        setViewSize(header_parent, ViewGroup.LayoutParams.MATCH_PARENT, ScreenAdapterUtil.getHeightPx(this) / 10);
        header_back.setVisibility(View.VISIBLE);
        header_title.setVisibility(View.VISIBLE);
        header_title.setText(getString(R.string.baby_info));
        header_confirm.setVisibility(View.VISIBLE);
        header_confirm.setText(getString(R.string.save));
    }

    @OnClick(R.id.header_back)
    void onBack(){
        finish();
    }

    @OnClick(R.id.header_confirm)
    void onConfirm(){
        if (et_relationship.getText().toString().trim().length() > 0) {
            if (iHomeManager != null) {
                iHomeManager.bindWatch(imei, et_relationship.getText().toString().trim(), new BaseObserverListener<BaseResultBean<RoleBean>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showShort(BabyRelationShipActivity.this, getString(R.string.data_error));
                    }

                    @Override
                    public void onNext(BaseResultBean<RoleBean> baseResultBean) {
                        if (baseResultBean.getCode() == 0) {
                            if (baseResultBean.getData().getUserRole() == 0){
                                startActivity(new Intent(BabyRelationShipActivity.this,BabyInfoActivity.class));
                            }else {
                                startActivity(new Intent(BabyRelationShipActivity.this,HomePagerActivity.class));
                            }
                        }else if(baseResultBean.getCode() == 1){ //已绑定
                           //ToastUtils.showShort(BabyRelationShipActivity.this,baseResultBean.getMsg());
                           startActivity(new Intent(BabyRelationShipActivity.this,BabyInfoActivity.class));
                        }
                    }
                });
            }
        }else {
            ToastUtils.showShort(this,getString(R.string.input_right_relationship));
        }
    }

}
