package com.waterworld.watch.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.waterworld.watch.R;
import com.waterworld.watch.common.activity.BaseActivity;
import com.waterworld.watch.common.bean.UserInfoBean;
import com.waterworld.watch.common.bean.WatchUserInfoBean;
import com.waterworld.watch.common.net.BaseObserverListener;
import com.waterworld.watch.common.net.BaseResultBean;
import com.waterworld.watch.common.net.UrlContants;
import com.waterworld.watch.common.util.PhotoUtils;
import com.waterworld.watch.common.util.ScreenAdapterUtil;
import com.waterworld.watch.home.interfaces.IHomeManager;
import com.waterworld.watch.home.manager.HomeManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WatchInfoActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.header_parent)
    LinearLayout header_parent;

    @BindView(R.id.header_back)
    ImageView header_back;

    @BindView(R.id.header_title)
    TextView header_title;

    @BindView(R.id.iv_baby_avatar)
    ImageView iv_baby_avatar;

    @BindView(R.id.cl_baby_info)
    ConstraintLayout cl_baby_info;

    @BindView(R.id.tv_baby_name)
    TextView tv_baby_name;

    @BindView(R.id.tv_baby_phoneNumber)
    TextView tv_baby_phoneNumber;

    @BindView(R.id.cl_watch_bind_code)
    ConstraintLayout cl_watch_bind_code;

    @BindView(R.id.tv_watch_type_info)
    TextView tv_watch_type_info;

    @BindView(R.id.tv_watch_version_info)
    TextView tv_watch_version_info;

    @BindView(R.id.cl_watch_bind)
    ConstraintLayout cl_watch_bind;

    @BindView(R.id.cl_watch_unbind)
    ConstraintLayout cl_watch_unbind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_info);
        ButterKnife.bind(this);
        initView();
    }

    private void initView(){
        setViewSize(header_parent,ViewGroup.LayoutParams.MATCH_PARENT,ScreenAdapterUtil.getHeightPx(this) / 12);
        header_back.setVisibility(View.VISIBLE);
        header_title.setVisibility(View.VISIBLE);
        header_title.setText(getString(R.string.watch_info));

        if(WatchUserInfoBean.getInstance().getWatchUserInfo().getHead().startsWith("sys")){
            Glide.with(this).load(PhotoUtils.getResource(WatchUserInfoBean.getInstance().getWatchUserInfo().getHead().substring(0,13))).into(iv_baby_avatar);
        }else {
            Glide.with(this).load(UrlContants.BASE_URL + "resources/watch/" + WatchUserInfoBean.getInstance().getWatchUserInfo().getHead()).into(iv_baby_avatar);
        }
        tv_baby_name.setText(WatchUserInfoBean.getInstance().getWatchUserInfo().getName());
        tv_baby_phoneNumber.setText(WatchUserInfoBean.getInstance().getWatchUserInfo().getPhone());
        tv_watch_type_info.setText(WatchUserInfoBean.getInstance().getWatchInfo().getModel());
        tv_watch_version_info.setText(WatchUserInfoBean.getInstance().getWatchInfo().getDv());

        header_back.setOnClickListener(this);
        cl_baby_info.setOnClickListener(this);
        cl_watch_bind_code.setOnClickListener(this);
        cl_watch_bind.setOnClickListener(this);
        cl_watch_unbind.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.header_back:
                finish();
                break;
            case R.id.cl_baby_info:
                startActivity(new Intent(this,BabyInfoActivity.class));
                break;
            case R.id.cl_watch_bind_code:
                startActivity(new Intent(this,WatchBindCodeActivity.class));
                break;
            case R.id.cl_watch_bind:
                startActivity(new Intent(this,WatchBindActivity.class));
                break;
            case R.id.cl_watch_unbind:
                startActivity(new Intent(this,WatchUnbindActivity.class));
                break;
        }
    }

}
