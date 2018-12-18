package com.waterworld.watch.common.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.waterworld.watch.R;
import com.waterworld.watch.common.adapter.HomePagerAdapter;
import com.waterworld.watch.common.adapter.BabyAdapter;
import com.waterworld.watch.common.bean.BabyBean;
import com.waterworld.watch.common.customview.CommonPopupWindow;
import com.waterworld.watch.common.util.ScreenAdapterUtil;
import com.waterworld.watch.home.activity.WatchInfoActivity;
import com.waterworld.watch.home.fragment.HomeFragment;
import com.waterworld.watch.me.fragment.MeFragment;
import com.waterworld.watch.message.fragment.MessageFragment;
import com.waterworld.watch.position.fragment.PositionFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * 编写者：Created by SunnyTang
 * 时间：2018/11/27 17:54
 * 主要作用：
 */
public class HomoPagerActivity extends BaseActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {

    public HomoPagerActivity homePager;
    /**
     * 泡泡窗口
     */
    private CommonPopupWindow popupWindow; //自定义通用泡泡窗口
    private CommonPopupWindow.LayoutGravity layoutGravity;// 泡泡窗口锚点对象
    /**
     * 碎片
     */
    private HomeFragment homeFragment;
    private PositionFragment positionFragment;
    private MessageFragment messageFragment;
    private MeFragment meFragment;
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
    private LinearLayout header_parent;
    private ImageView header_baby_avatar;//宝贝头像
    private LinearLayout header_baby_select;//选择宝贝
    private ImageView header_qr_code;//二维码
    /**
     * body部分
     */
    private ViewPager view_pager;//viewpager，用于放碎片
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_pager);
        homePager = this;
        bindView();//绑定控件
        initView();
        initViewPager();//初始化ViewPager + Fragment
        initBabyData();//初始化宝贝数据
        initPopupWindow();//初始化泡泡窗口
        bindClick();//绑定点击事件
    }

    /**
     * 绑定View
     */
    private void bindView() {
        //header
        header_parent = findViewById(R.id.header_parent);
        header_baby_avatar = findViewById(R.id.header_baby_avatar);
        header_baby_select = findViewById(R.id.header_baby_select);
        header_qr_code = findViewById(R.id.header_qr_code);
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
        //设置公共头部栏的高度为手机屏幕的10分之1
        setViewSize(header_parent,ViewGroup.LayoutParams.MATCH_PARENT,ScreenAdapterUtil.getHeightPx(homePager) / 11);
        header_baby_avatar.setVisibility(View.VISIBLE);
        header_baby_select.setVisibility(View.VISIBLE);
        header_qr_code.setVisibility(View.VISIBLE);
    }

    /**
     * 初始化(ViewPager/Fragment/List<Fragment>)
     */
    private void initViewPager() {
        //得到碎片
        homeFragment = new HomeFragment();
        positionFragment = new PositionFragment();
        messageFragment = new MessageFragment();
        meFragment = new MeFragment();
        //得到碎片管理对象
        fragmentManager = getSupportFragmentManager();
        //实例化集合对象并往里面添加数据
        fragmentDataList = new ArrayList<>();
        fragmentDataList.add(homeFragment);
        fragmentDataList.add(positionFragment);
        fragmentDataList.add(messageFragment);
        fragmentDataList.add(meFragment);
        //实例化适配器
        viewPagerAdapter = new HomePagerAdapter(fragmentManager, fragmentDataList);
        view_pager.setAdapter(viewPagerAdapter);
        //限定预加载碎片的个数
        view_pager.setOffscreenPageLimit(4);
        //设置监听
        view_pager.addOnPageChangeListener(this);
        selectFootTab(true, false, false, false);
    }

    /**
     * 初始化Baby适配器
     */
    private void initBabyData() {
        babyDataList = new ArrayList<>();
        babyDataList.add(new BabyBean(getResources().getDrawable(R.drawable.ic_avatar_1), "宝贝儿子1"));
        babyDataList.add(new BabyBean(getResources().getDrawable(R.drawable.ic_avatar_1), "宝贝儿子2"));
        babyDataList.add(new BabyBean(getResources().getDrawable(R.drawable.ic_avatar_1), "宝贝儿子1"));
        babyDataList.add(new BabyBean(getResources().getDrawable(R.drawable.ic_avatar_1), "宝贝儿子2"));
        babyDataList.add(new BabyBean(getResources().getDrawable(R.drawable.ic_avatar_1), "宝贝儿子1"));
        babyDataList.add(new BabyBean(getResources().getDrawable(R.drawable.ic_avatar_1), "宝贝儿子2"));
        babyAdapter = new BabyAdapter(this, babyDataList);
    }

    /**
     * 初始化PopupWindow
     */
    private void initPopupWindow() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int height = dm.heightPixels;
        int width = dm.widthPixels;
        float qwe = dm.density;
        Log.i("绝对宽度(px)：" + dm.widthPixels, "绝对高度(px)：" + dm.heightPixels);
        Log.i("本设备的系统像素密度(dpi)：" + dm.densityDpi, "本设备的系统像素密度基准比例：" + dm.density);
        Log.i("字体缩放密度：", "" + dm.scaledDensity);
        Log.i("水平方向的dpi(X轴)：" + dm.xdpi, "垂直方向的dpi(Y轴)：" + dm.ydpi);

        //Log.i("像素密度基准比例：" + dm.density, "像素密度dpi：" + dm.densityDpi);

        popupWindow = new CommonPopupWindow(homePager, R.layout.common_popup, width / 2, height / 5) {
            @Override
            protected void initView() {
                View view = getContentView();
                babyRecycler = view.findViewById(R.id.babyRecycler);
            }

            @Override
            protected void initEvent() {
                LinearLayoutManager layoutManager = new LinearLayoutManager(homePager);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                babyRecycler.setLayoutManager(layoutManager);
                babyRecycler.setAdapter(babyAdapter);
                babyAdapter.setBabyClick(new BabyAdapter.BabyClick() {
                    @Override
                    public void onItem(View view, int position) {
                        switch (position) {
                            case 0:
                                setCustomTimeToast(homePager, "点击了宝贝" + position, 3000);
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
        header_baby_avatar.setOnClickListener(this);
        header_baby_select.setOnClickListener(this);
        header_qr_code.setOnClickListener(this);
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
                Intent intent = new Intent(homePager,WatchInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.header_baby_select://切换宝贝手表
                if (babyDataList != null && babyDataList.size() > 1) {
                    popupWindow.showBashOfAnchor(header_parent, layoutGravity, 0, 0);
                } else {
                    setCustomTimeToast(this, "亲爱的家长，您目前只绑定了一个孩子哦", 3000);
                }
                break;
            case R.id.header_qr_code://二维码扫描
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

    /**
     * 高亮显示当前选中的按钮
     */
    private void selectFootTab(boolean home, boolean position, boolean message, boolean me) {
        if (home) {
            iv_home.setImageDrawable(getResources().getDrawable(R.drawable.ic_home_true));
            tv_home.setTextColor(getResources().getColor(R.color.yellow_1));
        } else {
            iv_home.setImageDrawable(getResources().getDrawable(R.drawable.ic_home_false));
            tv_home.setTextColor(getResources().getColor(R.color.gray_1));
        }
        if (position) {
            iv_position.setImageDrawable(getResources().getDrawable(R.drawable.ic_position_true));
            tv_position.setTextColor(getResources().getColor(R.color.yellow_1));
        } else {
            iv_position.setImageDrawable(getResources().getDrawable(R.drawable.ic_position_false));
            tv_position.setTextColor(getResources().getColor(R.color.gray_1));
        }
        if (message) {
            iv_message.setImageDrawable(getResources().getDrawable(R.drawable.ic_message_true));
            tv_message.setTextColor(getResources().getColor(R.color.yellow_1));
        } else {
            iv_message.setImageDrawable(getResources().getDrawable(R.drawable.ic_message_false));
            tv_message.setTextColor(getResources().getColor(R.color.gray_1));
        }
        if (me) {
            iv_me.setImageDrawable(getResources().getDrawable(R.drawable.ic_me_true));
            tv_me.setTextColor(getResources().getColor(R.color.yellow_1));
        } else {
            iv_me.setImageDrawable(getResources().getDrawable(R.drawable.ic_me_false));
            tv_me.setTextColor(getResources().getColor(R.color.gray_1));
        }
    }

}
