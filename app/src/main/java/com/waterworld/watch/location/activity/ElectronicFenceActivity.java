package com.waterworld.watch.location.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopeer.itemtouchhelperextension.ItemTouchHelperExtension;
import com.waterworld.watch.R;
import com.waterworld.watch.common.activity.BaseActivity;
import com.waterworld.watch.common.util.ScreenAdapterUtil;
import com.waterworld.watch.location.adapter.ElectronicFenceAdapter;
import com.waterworld.watch.location.adapter.ItemTouchHelperCallBack;
import com.waterworld.watch.location.bean.ElectronicFenceBean;
import com.waterworld.watch.location.interfaces.onElectronicFenceClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ElectronicFenceActivity extends BaseActivity {

    @BindView(R.id.header_parent)
    LinearLayout header_parent;

    @BindView(R.id.header_back)
    ImageButton header_back;

    @BindView(R.id.header_title)
    TextView header_title;

    @BindView(R.id.header_add)
    ImageButton header_add;

    @BindView(R.id.rv_electronic_fence)
    RecyclerView rv_electronic_fence;

    private List<ElectronicFenceBean> mList;
    private ElectronicFenceAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electronic_fence);
        ButterKnife.bind(this);
        initView();

        mAdapter.setOnElectronicFenceClickListener(new onElectronicFenceClickListener() {
            @Override
            public void onClick(View view, int position) {
            }
        });
    }

    private void initView(){
        setViewSize(header_parent, ViewGroup.LayoutParams.MATCH_PARENT, ScreenAdapterUtil.getHeightPx(this)/12);
        header_back.setVisibility(View.VISIBLE);
        header_title.setVisibility(View.VISIBLE);
        header_add.setVisibility(View.VISIBLE);
        header_title.setText(getString(R.string.electronic_fence));

        //进行网络请求，获取围栏数据,设置adapter的list
        mList = new ArrayList<>();
        //模拟数据
        mList.add(new ElectronicFenceBean("学校","未进入提醒","500米",true));
        mList.add(new ElectronicFenceBean("家","进入提醒","800米",false));


        mAdapter = new ElectronicFenceAdapter(this,mList);
        rv_electronic_fence.setLayoutManager(new LinearLayoutManager(this));
        rv_electronic_fence.setAdapter(mAdapter);

        ItemTouchHelperExtension.Callback callback = new ItemTouchHelperCallBack();
        ItemTouchHelperExtension itemTouchHelperExtension = new ItemTouchHelperExtension(callback);
        itemTouchHelperExtension.attachToRecyclerView(rv_electronic_fence);
    }

    @OnClick(R.id.header_back)
    void onBack(){
        finish();
    }

    @OnClick(R.id.header_add)
    void onAdd(){
        startActivity(new Intent(this,AddElectronicFenceActivity.class));
    }
}
