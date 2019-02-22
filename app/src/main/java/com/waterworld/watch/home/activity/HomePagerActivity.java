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
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.acker.simplezxing.activity.CaptureActivity;
import com.waterworld.watch.R;
import com.waterworld.watch.common.activity.BaseActivity;
import com.waterworld.watch.common.customview.CustomViewPager;
import com.waterworld.watch.common.net.BaseObserverListener;
import com.waterworld.watch.common.net.BaseResultBean;
import com.waterworld.watch.home.adapter.HomePagerAdapter;
import com.waterworld.watch.home.adapter.BabyAdapter;
import com.waterworld.watch.home.bean.BabyBean;
import com.waterworld.watch.common.customview.CommonPopupWindow;
import com.waterworld.watch.common.util.ScreenAdapterUtil;
import com.waterworld.watch.common.util.ToastUtils;
import com.waterworld.watch.home.fragment.HomeFragment;
import com.waterworld.watch.home.interfaces.IHomeManager;
import com.waterworld.watch.home.manager.HomeManager;
import com.waterworld.watch.mine.fragment.MineFragment;
import com.waterworld.watch.chat.fragment.ChatFragment;
import com.waterworld.watch.location.fragment.LocationFragment;
import com.waterworld.watch.mine.interfaces.IMineManager;
import com.waterworld.watch.mine.manager.MineManager;

import java.util.ArrayList;
import java.util.List;


/**
 * 编写者：Created by SunnyTang
 * 时间：2018/11/27 17:54
 * 主要作用：
 */
public class HomePagerActivity extends BaseActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {

    public HomePagerActivity thisActivity;
    /**
     * 泡泡窗口
     */
    private CommonPopupWindow popupWindow; //自定义通用泡泡窗口
    private CommonPopupWindow.LayoutGravity layoutGravity;// 泡泡窗口锚点对象
    /**
     * 碎片
     */
    private HomeFragment homeFragment;
    private LocationFragment locationFragment;
    private ChatFragment chatFragment;
    private MineFragment mineFragment;
    /**
     * “ViewPager”适配器相关
     */
    private HomePagerAdapter viewPagerAdapter;//适配器
    private FragmentManager fragmentManager;//碎片管理
    private List<Fragment> fragmentDataList;//碎片集合
    /**
     * "选择宝贝"列表适配器相关
     */
    private BabyAdapter babyAdapter;
    private List<BabyBean> babyDataList;
    private RecyclerView babyRecycler;//宝贝列表
    /**
     * header部分View
     */
    private LinearLayout headerParent;//根view
    //左部
    private ImageButton headerBabyAvatar;//宝贝头像
    //中部
    private TextView headerTitle;//标题
    private LinearLayout headerBabySelect;//选择宝贝
    private TextView headerBabyName;//宝贝名称
    //右部
    private ImageButton headerQrCode;//二维码
    private ImageButton headerAdd;
    /**
     * body部分
     */
    private CustomViewPager view_pager;//viewpager，用于放碎片
    /**
     * foot部分View
     */
    private LinearLayout foot_tab;//单选按钮组
    private LinearLayout tab_home;//主页
    private ImageView iv_home;
    private TextView tv_home;
    private LinearLayout tab_position;//定位
    private ImageView iv_position;
    private TextView tv_position;
    private LinearLayout tab_message;//聊天
    private ImageView iv_message;
    private TextView tv_message;
    private LinearLayout tab_me;//我的
    private ImageView iv_me;
    private TextView tv_me;


    //相机权限
    private static final int REQ_CODE_PERMISSION = 0x01;
    //扫描结果
    private String result;

    private IHomeManager iHomeManager = HomeManager.getInstance();
    private IMineManager iMineManager = MineManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_pager);
        thisActivity = this;
        bindView();//绑定控件
        initView();
        initViewPager();//初始化ViewPager + Fragment
        initBabyData();//初始化宝贝数据
        initPopupWindow();//初始化泡泡窗口
        bindClick();//绑定点击事件


    }

    public IHomeManager getiHomeManager() {
        return iHomeManager;
    }

    public IMineManager getiMineManager() {
        return iMineManager;
    }

    /**
     * 绑定View
     */
    private void bindView() {
        //header
        headerParent = findViewById(R.id.header_parent);
        headerBabyAvatar = findViewById(R.id.header_baby_avatar);
        headerBabySelect = findViewById(R.id.header_baby_select);
        headerQrCode = findViewById(R.id.header_qr_code);
        headerBabyName = findViewById(R.id.header_baby_name);
        headerTitle = findViewById(R.id.header_title);
        headerAdd = findViewById(R.id.header_add);
        //body
        view_pager = findViewById(R.id.view_pager);
        //foot
        foot_tab = findViewById(R.id.foot_tab);
        tab_home = findViewById(R.id.tab_home);
        iv_home = findViewById(R.id.iv_home);
        tv_home = findViewById(R.id.tv_home);
        tab_position = findViewById(R.id.tab_position);
        iv_position = findViewById(R.id.iv_position);
        tv_position = findViewById(R.id.tv_position);
        tab_message = findViewById(R.id.tab_message);
        iv_message = findViewById(R.id.iv_message);
        tv_message = findViewById(R.id.tv_message);
        tab_me = findViewById(R.id.tab_me);
        iv_me = findViewById(R.id.iv_me);
        tv_me = findViewById(R.id.tv_me);
    }

    /**
     * 初始化View
     */
    private void initView() {
        //设置自定义ViewPager禁止滑动
        view_pager.setScanScroll(false);
        //设置公共头部栏的高度为手机屏幕的10分之1
        setViewSize(headerParent, ViewGroup.LayoutParams.MATCH_PARENT, ScreenAdapterUtil.getHeightPx(thisActivity) / 10);
        headerBabyAvatar.setVisibility(View.VISIBLE);
        headerBabySelect.setVisibility(View.VISIBLE);
        headerQrCode.setVisibility(View.VISIBLE);
    }

    /**
     * 初始化(ViewPager/Fragment/List<Fragment>)
     */
    private void initViewPager() {
        //得到碎片
        homeFragment = new HomeFragment();
        locationFragment = new LocationFragment();
        chatFragment = new ChatFragment();
        mineFragment = new MineFragment();
        //得到碎片管理对象
        fragmentManager = getSupportFragmentManager();
        //实例化集合对象并往里面添加数据
        fragmentDataList = new ArrayList<>();
        fragmentDataList.add(homeFragment);
        fragmentDataList.add(locationFragment);
        fragmentDataList.add(chatFragment);
        fragmentDataList.add(mineFragment);
        //实例化适配器
        viewPagerAdapter = new HomePagerAdapter(fragmentManager, fragmentDataList);
        view_pager.setAdapter(viewPagerAdapter);
        //限定预加载碎片的个数
        view_pager.setOffscreenPageLimit(4);
        //设置监听
        view_pager.addOnPageChangeListener(this);
        selectFootTab(true, false, false, false);
        ctrlHeader(0);
    }

    /**
     * 初始化Baby适配器
     */
    private void initBabyData() {
        babyDataList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            babyDataList.add(new BabyBean(getResources().getDrawable(R.drawable.ic_avatar_1_smile), "宝贝儿子" + i));
        }
        babyAdapter = new BabyAdapter(this, babyDataList);
    }

    /**
     * 初始化PopupWindow
     */
    private void initPopupWindow() {
        popupWindow = new CommonPopupWindow(thisActivity, R.layout.common_popup, ScreenAdapterUtil.getWidthPx(thisActivity) / 2, ScreenAdapterUtil.getHeightPx(thisActivity) / 5) {
            @Override
            protected void initView() {
                View view = getContentView();
                babyRecycler = view.findViewById(R.id.babyRecycler);
            }

            @Override
            protected void initEvent() {
                LinearLayoutManager layoutManager = new LinearLayoutManager(thisActivity);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                babyRecycler.setLayoutManager(layoutManager);
                babyRecycler.setAdapter(babyAdapter);
                babyAdapter.setBabyClick(new BabyAdapter.BabyClick() {
                    @Override
                    public void onItem(View view, int position) {
                        switch (position) {
                            case 0:
                                ToastUtils.showCustomTime(thisActivity, "点击了宝贝" + position, 3000);
                                popupWindow.getPopupWindow().dismiss();
                                break;
                        }
                    }
                });
            }
        };
        //实例化锚点对象
        layoutGravity = new CommonPopupWindow.LayoutGravity(CommonPopupWindow.LayoutGravity.CENTER_HORIZONTAL | CommonPopupWindow.LayoutGravity.TO_BOTTOM);
    }

    /**
     * 绑定点击事件
     */
    private void bindClick() {
        headerBabyAvatar.setOnClickListener(this);
        headerBabySelect.setOnClickListener(this);
        headerQrCode.setOnClickListener(this);
        headerAdd.setOnClickListener(this);
        tab_home.setOnClickListener(this);
        tab_position.setOnClickListener(this);
        tab_message.setOnClickListener(this);
        tab_me.setOnClickListener(this);
    }

    /**
     * @param i
     * @param v
     * @param i1
     */
    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    /**
     * @param position
     */
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

    /**
     * @param i
     */
    @Override
    public void onPageScrollStateChanged(int i) {

    }

    /**
     * 点击事件
     *
     * @param view 可被点击的组件
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.header_baby_avatar://宝贝头像
                Intent intent = new Intent(thisActivity, WatchInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.header_baby_select://切换宝贝手表
                if (babyDataList != null && babyDataList.size() > 1) {
                    popupWindow.showBashOfAnchor(headerParent, layoutGravity, 0, 0);
                } else {
                    ToastUtils.showCustomTime(this, "亲爱的家长，您目前只绑定了一个孩子哦", 3000);
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
            case R.id.tab_position:
                selectFootTab(false, true, false, false);
                view_pager.setCurrentItem(1);
                break;
            case R.id.tab_message:
                selectFootTab(false, false, true, false);
                view_pager.setCurrentItem(2);
                break;
            case R.id.tab_me:
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
            iv_position.setImageDrawable(getResources().getDrawable(R.drawable.ic_position_true));
            tv_position.setTextColor(getResources().getColor(R.color.yellow_1));
            ctrlHeader(1);
        } else {
            iv_position.setImageDrawable(getResources().getDrawable(R.drawable.ic_position_false));
            tv_position.setTextColor(getResources().getColor(R.color.gray_1));
        }
        if (message) {
            iv_message.setImageDrawable(getResources().getDrawable(R.drawable.ic_message_true));
            tv_message.setTextColor(getResources().getColor(R.color.yellow_1));
            ctrlHeader(2);
        } else {
            iv_message.setImageDrawable(getResources().getDrawable(R.drawable.ic_message_false));
            tv_message.setTextColor(getResources().getColor(R.color.gray_1));
        }
        if (me) {
            iv_me.setImageDrawable(getResources().getDrawable(R.drawable.ic_me_true));
            tv_me.setTextColor(getResources().getColor(R.color.yellow_1));
            ctrlHeader(3);
        } else {
            iv_me.setImageDrawable(getResources().getDrawable(R.drawable.ic_me_false));
            tv_me.setTextColor(getResources().getColor(R.color.gray_1));
        }
    }

    private void startCaptureActivityForResult() {
        Intent intent = new Intent(this, CaptureActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean(CaptureActivity.KEY_NEED_BEEP, CaptureActivity.VALUE_BEEP);
        bundle.putBoolean(CaptureActivity.KEY_NEED_VIBRATION, CaptureActivity.VALUE_VIBRATION);
        bundle.putBoolean(CaptureActivity.KEY_NEED_EXPOSURE, CaptureActivity.VALUE_NO_EXPOSURE);
        bundle.putByte(CaptureActivity.KEY_FLASHLIGHT_MODE, CaptureActivity.VALUE_FLASHLIGHT_OFF);
        bundle.putByte(CaptureActivity.KEY_ORIENTATION_MODE, CaptureActivity.VALUE_ORIENTATION_AUTO);
        bundle.putBoolean(CaptureActivity.KEY_SCAN_AREA_FULL_SCREEN, CaptureActivity.VALUE_SCAN_AREA_FULL_SCREEN);
        bundle.putBoolean(CaptureActivity.KEY_NEED_SCAN_HINT_TEXT, CaptureActivity.VALUE_SCAN_HINT_TEXT);
        intent.putExtra(CaptureActivity.EXTRA_SETTING_BUNDLE, bundle);
        startActivityForResult(intent, CaptureActivity.REQ_CODE);
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
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CaptureActivity.REQ_CODE:
                switch (resultCode) {
                    case RESULT_OK:
                        result = data.getStringExtra(CaptureActivity.EXTRA_SCAN_RESULT);
                        if (iHomeManager != null) {
                            iHomeManager.bindWatch(result, new BaseObserverListener<BaseResultBean>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {
                                    ToastUtils.showShort(HomePagerActivity.this, getString(R.string.data_error));
                                }

                                @Override
                                public void onNext(BaseResultBean baseResultBean) {
                                    if (baseResultBean.getCode() == 0) {
                                        ToastUtils.showShort(HomePagerActivity.this, getString(R.string.bind_success));
                                    } else {
                                        ToastUtils.showShort(HomePagerActivity.this, getString(R.string.bind_error) + "," + baseResultBean.getMsg());
                                    }
                                }
                            });
                        }
                        break;
                    case RESULT_CANCELED:
                        //因某种原因导致相机没有正确工作
                        if (data != null) {
                            result = data.getStringExtra(CaptureActivity.EXTRA_SCAN_RESULT);
                        }
                        break;
                }
                break;
            default:
                break;
        }
    }
}
