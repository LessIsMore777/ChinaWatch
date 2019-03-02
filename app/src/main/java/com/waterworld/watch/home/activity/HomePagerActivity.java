package com.waterworld.watch.home.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.waterworld.watch.R;
import com.waterworld.watch.common.activity.BaseActivity;
import com.waterworld.watch.common.bean.BindWatchBean;
import com.waterworld.watch.common.bean.UserInfoBean;
import com.waterworld.watch.common.customview.CustomViewPager;
import com.waterworld.watch.common.net.BaseObserverListener;
import com.waterworld.watch.common.net.BaseResultBean;
import com.waterworld.watch.common.net.UrlContants;
import com.waterworld.watch.common.util.PhotoUtils;
import com.waterworld.watch.home.adapter.HomePagerAdapter;
import com.waterworld.watch.home.adapter.BabyAdapter;
import com.waterworld.watch.home.bean.BabyBean;
import com.waterworld.watch.common.customview.CommonPopupWindow;
import com.waterworld.watch.common.util.ScreenAdapterUtil;
import com.waterworld.watch.common.util.ToastUtils;
import com.waterworld.watch.home.fragment.HomeFragment;
import com.waterworld.watch.home.interfaces.IHomeManager;
import com.waterworld.watch.home.manager.HomeManager;
import com.waterworld.watch.login.event.AutoLoginEvent;
import com.waterworld.watch.mine.fragment.MineFragment;
import com.waterworld.watch.chat.fragment.ChatFragment;
import com.waterworld.watch.location.fragment.LocationFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomePagerActivity extends BaseActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {

    @BindView(R.id.header_parent)
    LinearLayout headerParent;

    @BindView(R.id.header_baby_avatar)
    ImageView headerBabyAvatar;

    @BindView(R.id.header_title)
    TextView headerTitle;

    @BindView(R.id.header_baby_select)
    LinearLayout headerBabySelect;

    @BindView(R.id.header_baby_name)
    TextView headerBabyName;

    @BindView(R.id.header_qr_code)
    ImageView headerQrCode;

    @BindView(R.id.header_add)
    ImageView headerAdd;

    @BindView(R.id.view_pager)
    CustomViewPager view_pager;

    @BindView(R.id.tab_home)
    LinearLayout tab_home;

    @BindView(R.id.iv_home)
    ImageView iv_home;

    @BindView(R.id.tv_home)
    TextView tv_home;

    @BindView(R.id.tab_location)
    LinearLayout tab_location;

    @BindView(R.id.iv_location)
    ImageView iv_location;

    @BindView(R.id.tv_location)
    TextView tv_location;

    @BindView(R.id.tab_chat)
    LinearLayout tab_chat;

    @BindView(R.id.iv_chat)
    ImageView iv_chat;

    @BindView(R.id.tv_chat)
    TextView tv_chat;

    @BindView(R.id.tab_mine)
    LinearLayout tab_mine;

    @BindView(R.id.iv_mine)
    ImageView iv_mine;

    @BindView(R.id.tv_mine)
    TextView tv_mine;

    private CommonPopupWindow popupWindow;
    private CommonPopupWindow.LayoutGravity layoutGravity;

    private BabyAdapter babyAdapter;
    private List<BabyBean> babyDataList;
    private RecyclerView babyRecycler;

    //相机权限
    private static final int REQ_CODE_PERMISSION = 0x01;

    private IHomeManager iHomeManager = HomeManager.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_pager);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initView() {
        view_pager.setScanScroll(false);
        setViewSize(headerParent, ViewGroup.LayoutParams.MATCH_PARENT, ScreenAdapterUtil.getHeightPx(this) / 10);
        headerBabyAvatar.setVisibility(View.VISIBLE);
        headerBabySelect.setVisibility(View.VISIBLE);
        headerQrCode.setVisibility(View.VISIBLE);
        HomeFragment homeFragment = new HomeFragment();
        LocationFragment locationFragment = new LocationFragment();
        ChatFragment chatFragment = new ChatFragment();
        MineFragment mineFragment = new MineFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        List<Fragment> fragmentDataList = new ArrayList<>();
        fragmentDataList.add(homeFragment);
        fragmentDataList.add(locationFragment);
        fragmentDataList.add(chatFragment);
        fragmentDataList.add(mineFragment);
        HomePagerAdapter viewPagerAdapter = new HomePagerAdapter(fragmentManager, fragmentDataList);
        view_pager.setAdapter(viewPagerAdapter);
        view_pager.setOffscreenPageLimit(4);
        view_pager.addOnPageChangeListener(this);
        selectFootTab(true, false, false, false);
        ctrlHeader(0);

        headerBabyAvatar.setOnClickListener(this);
        headerBabySelect.setOnClickListener(this);
        headerQrCode.setOnClickListener(this);
        headerAdd.setOnClickListener(this);
        tab_home.setOnClickListener(this);
        tab_location.setOnClickListener(this);
        tab_chat.setOnClickListener(this);
        tab_mine.setOnClickListener(this);
    }

    //从服务器拉取数据
    private void initData() {
        if (iHomeManager != null){
            iHomeManager.listBindWatch(new BaseObserverListener<BaseResultBean<BindWatchBean[]>>() {
                @Override
                public void onCompleted() {
                }

                @Override
                public void onError(Throwable e) {
                    ToastUtils.showShort(HomePagerActivity.this, getString(R.string.data_error));
                }

                @Override
                public void onNext(BaseResultBean<BindWatchBean[]> baseResultBean) {
                    BindWatchBean[] bindWatchs = baseResultBean.getData();
                    babyDataList = new ArrayList<>();
                    if (bindWatchs != null) {
                        for (BindWatchBean bindWatch : bindWatchs) {
                            if (bindWatch.getHead().startsWith("sys")){
                                babyDataList.add(new BabyBean(PhotoUtils.getResource(bindWatch.getHead().substring(0,13)), bindWatch.getName()));
                            }else {
                                babyDataList.add(new BabyBean(UrlContants.BASE_URL + "resources/watch/" + bindWatch.getHead(), bindWatch.getName()));
                            }

                        }
                    }
                    babyAdapter = new BabyAdapter(HomePagerActivity.this, babyDataList);
                    initPopupWindow();
                }
            });
            iHomeManager.getCurrentUser(new BaseObserverListener<BaseResultBean<UserInfoBean>>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    ToastUtils.showShort(HomePagerActivity.this, getString(R.string.data_error));
                }

                @Override
                public void onNext(BaseResultBean<UserInfoBean> baseResultBean) {
                    UserInfoBean.getInstance().setWatchUser(baseResultBean.getData().getWatchUser());
                    UserInfoBean.getInstance().setUser(baseResultBean.getData().getUser());
                    if(UserInfoBean.getInstance().getWatchUser().getHead().startsWith("sys")){
                        Glide.with(HomePagerActivity.this).load(PhotoUtils.getResource(UserInfoBean.getInstance().getWatchUser().getHead().substring(0,13))).into(headerBabyAvatar);
                    }else {
                        Glide.with(HomePagerActivity.this).load(UrlContants.BASE_URL + "resources/watch/" + UserInfoBean.getInstance().getWatchUser().getHead()).into(headerBabyAvatar);
                    }
                }
            });
        }

    }

    /**
     * 初始化PopupWindow
     */
    private void initPopupWindow() {
        popupWindow = new CommonPopupWindow(this, R.layout.common_popup, ScreenAdapterUtil.getWidthPx(this) / 2, ScreenAdapterUtil.getHeightPx(this) / 5) {
            @Override
            protected void initView() {
                View view = getContentView();
                babyRecycler = view.findViewById(R.id.babyRecycler);
            }

            @Override
            protected void initEvent() {
                LinearLayoutManager layoutManager = new LinearLayoutManager(HomePagerActivity.this);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                babyRecycler.setLayoutManager(layoutManager);
                babyRecycler.setAdapter(babyAdapter);
                babyAdapter.setBabyClick(new BabyAdapter.BabyClick() {
                    @Override
                    public void onItem(View view, int position) {

                    }
                });
            }
        };
        //实例化锚点对象
        layoutGravity = new CommonPopupWindow.LayoutGravity(CommonPopupWindow.LayoutGravity.CENTER_HORIZONTAL | CommonPopupWindow.LayoutGravity.TO_BOTTOM);
    }


    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                selectFootTab(true, false, false, false);
                break;
            case 1:
                selectFootTab(false, true, false, false);
                break;
            case 2:
                selectFootTab(false, false, true, false);
                break;
            case 3:
                selectFootTab(false, false, false, true);
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.header_baby_avatar://宝贝头像
                startActivity(new Intent(this, WatchInfoActivity.class));
                break;
            case R.id.header_baby_select://切换宝贝手表
                if (babyDataList != null) {
                    popupWindow.showBashOfAnchor(headerParent, layoutGravity, 0, 0);
                }
                break;
            case R.id.header_qr_code://二维码扫描
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQ_CODE_PERMISSION);
                } else {
                    startCaptureActivityForResult();
                }
                break;
            case R.id.header_add:
                break;
            case R.id.tab_home:
                selectFootTab(true, false, false, false);
                view_pager.setCurrentItem(0);
                break;
            case R.id.tab_location:
                selectFootTab(false, true, false, false);
                view_pager.setCurrentItem(1);
                break;
            case R.id.tab_chat:
                selectFootTab(false, false, true, false);
                view_pager.setCurrentItem(2);
                break;
            case R.id.tab_mine:
                selectFootTab(false, false, false, true);
                view_pager.setCurrentItem(3);
                break;
            default:
                break;
        }
    }

    private void ctrlHeader(int viewPagerIndex) {
        switch (viewPagerIndex) {
            case 0:
                headerBabyAvatar.setVisibility(View.VISIBLE);
                headerTitle.setVisibility(View.GONE);
                headerBabySelect.setVisibility(View.VISIBLE);
                headerQrCode.setVisibility(View.VISIBLE);
                headerAdd.setVisibility(View.GONE);
                headerBabyName.setText("宝贝的手表");
                break;
            case 1:
                headerBabyAvatar.setVisibility(View.VISIBLE);
                headerTitle.setVisibility(View.VISIBLE);
                headerBabySelect.setVisibility(View.GONE);
                headerQrCode.setVisibility(View.GONE);
                headerAdd.setVisibility(View.GONE);
                headerTitle.setText("定位");
                break;
            case 2:
                headerBabyAvatar.setVisibility(View.VISIBLE);
                headerTitle.setVisibility(View.VISIBLE);
                headerBabySelect.setVisibility(View.GONE);
                headerQrCode.setVisibility(View.GONE);
                headerAdd.setVisibility(View.VISIBLE);
                headerTitle.setText("聊天");
                break;
            case 3:
                headerBabyAvatar.setVisibility(View.VISIBLE);
                headerTitle.setVisibility(View.VISIBLE);
                headerBabySelect.setVisibility(View.GONE);
                headerQrCode.setVisibility(View.GONE);
                headerAdd.setVisibility(View.GONE);
                headerTitle.setText("我的");
                break;
        }
    }

    /**
     * 高亮显示当前选中的按钮
     */
    private void selectFootTab(boolean home, boolean position, boolean message, boolean me) {
        if (home) {
            iv_home.setImageDrawable(getResources().getDrawable(R.drawable.ic_home_true));
            tv_home.setTextColor(getResources().getColor(R.color.yellow_1));
            ctrlHeader(0);
        } else {
            iv_home.setImageDrawable(getResources().getDrawable(R.drawable.ic_home_false));
            tv_home.setTextColor(getResources().getColor(R.color.gray_1));
        }
        if (position) {
            iv_location.setImageDrawable(getResources().getDrawable(R.drawable.ic_position_true));
            tv_location.setTextColor(getResources().getColor(R.color.yellow_1));
            ctrlHeader(1);
        } else {
            iv_location.setImageDrawable(getResources().getDrawable(R.drawable.ic_position_false));
            tv_location.setTextColor(getResources().getColor(R.color.gray_1));
        }
        if (message) {
            iv_chat.setImageDrawable(getResources().getDrawable(R.drawable.ic_message_true));
            tv_chat.setTextColor(getResources().getColor(R.color.yellow_1));
            ctrlHeader(2);
        } else {
            iv_chat.setImageDrawable(getResources().getDrawable(R.drawable.ic_message_false));
            tv_chat.setTextColor(getResources().getColor(R.color.gray_1));
        }
        if (me) {
            iv_mine.setImageDrawable(getResources().getDrawable(R.drawable.ic_me_true));
            tv_mine.setTextColor(getResources().getColor(R.color.yellow_1));
            ctrlHeader(3);
        } else {
            iv_mine.setImageDrawable(getResources().getDrawable(R.drawable.ic_me_false));
            tv_mine.setTextColor(getResources().getColor(R.color.gray_1));
        }
    }

    private void startCaptureActivityForResult() {
        IntentIntegrator intentIntegrator = new IntentIntegrator(HomePagerActivity.this);
        intentIntegrator.setCaptureActivity(ScanQRCodeActivity.class);
        intentIntegrator.initiateScan();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQ_CODE_PERMISSION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startCaptureActivityForResult();
                } else {
                    ToastUtils.showLong(this, getString(R.string.have_to_agree_premissions));
                }
            }
            break;
        }
    }

    //拿到扫描返回数据，去绑定
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // 获取解析结果
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                String content = result.getContents();
                startActivity(new Intent(this,BabyInfoActivity.class).putExtra("imei",content));
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Subscribe(sticky = true ,threadMode = ThreadMode.MAIN)
    public void loginStatus(AutoLoginEvent autoLoginEvent){
        if(autoLoginEvent.getAuto() == 1) {
            initData();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}