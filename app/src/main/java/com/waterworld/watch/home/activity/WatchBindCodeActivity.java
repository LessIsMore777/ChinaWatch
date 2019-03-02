package com.waterworld.watch.home.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.waterworld.watch.R;
import com.waterworld.watch.common.activity.BaseActivity;
import com.waterworld.watch.common.bean.WatchUserInfoBean;
import com.waterworld.watch.common.util.QRCodeUtil;
import com.waterworld.watch.common.util.ScreenAdapterUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WatchBindCodeActivity extends BaseActivity{


    @BindView(R.id.header_parent)
    LinearLayout header_parent;

    @BindView(R.id.header_back)
    ImageView header_back;

    @BindView(R.id.header_title)
    TextView header_title;

    @BindView(R.id.iv_bind_code)
    ImageView iv_bind_code;

    @BindView(R.id.tv_code)
    TextView tv_code;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_bind_code);
        ButterKnife.bind(this);
        initView();
    }


    private void initView() {
        setViewSize(header_parent, ViewGroup.LayoutParams.MATCH_PARENT, ScreenAdapterUtil.getHeightPx(this) / 10);
        header_title.setText(getString(R.string.watch_bind_code));
        header_back.setVisibility(View.VISIBLE);
        header_title.setVisibility(View.VISIBLE);

        Bitmap mBitmap = QRCodeUtil.createQRCodeBitmap(WatchUserInfoBean.getInstance().getWatchInfo().getImei(), 480, 480);
        iv_bind_code.setImageBitmap(mBitmap);
        tv_code.setText(WatchUserInfoBean.getInstance().getWatchInfo().getImei());
    }

    @OnClick(R.id.header_back)
    void onBack(){
        finish();
    }
}
