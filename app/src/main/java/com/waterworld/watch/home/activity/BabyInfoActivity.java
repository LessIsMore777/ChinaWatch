package com.waterworld.watch.home.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;

import com.bumptech.glide.Glide;
import com.waterworld.watch.R;
import com.waterworld.watch.common.activity.BaseActivity;
import com.waterworld.watch.common.bean.UserInfoBean;
import com.waterworld.watch.common.bean.WatchUserInfoBean;
import com.waterworld.watch.common.customview.CircleImageView;
import com.waterworld.watch.common.customview.CommonPopupWindow;
import com.waterworld.watch.common.net.BaseObserverListener;
import com.waterworld.watch.common.net.BaseResultBean;
import com.waterworld.watch.common.net.UrlContants;
import com.waterworld.watch.common.util.AppManager;
import com.waterworld.watch.common.util.PhotoUtils;
import com.waterworld.watch.common.util.TimeUtils;
import com.waterworld.watch.common.util.ScreenAdapterUtil;
import com.waterworld.watch.common.util.ToastUtils;
import com.waterworld.watch.home.event.BabyAvatarEvent;
import com.waterworld.watch.home.interfaces.IHomeManager;
import com.waterworld.watch.home.manager.HomeManager;
import com.waterworld.watch.mine.event.AvatarEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.carbswang.android.numberpickerview.library.NumberPickerView;

public class BabyInfoActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.parentView)
    LinearLayout parentView;

    @BindView(R.id.header_parent)
    LinearLayout header_parent;

    @BindView(R.id.header_back)
    ImageView header_back;

    @BindView(R.id.header_title)
    TextView header_title;

    @BindView(R.id.header_confirm)
    TextView header_confirm;

    @BindView(R.id.rl_select_avatar)
    RelativeLayout rl_select_avatar;

    @BindView(R.id.iv_avatar)
    CircleImageView iv_avatar;

    @BindView(R.id.et_nick)
    EditText et_nick;

    @BindView(R.id.rl_gender)
    RelativeLayout rl_gender;

    @BindView(R.id.tv_gender)
    TextView tv_gender;

    @BindView(R.id.rl_birthday)
    RelativeLayout rl_birthday;

    @BindView(R.id.tv_birthday)
    TextView tv_birthday;//生日数据

    @BindView(R.id.et_grade)
    EditText et_grade;//年级数据

    @BindView(R.id.et_class)
    EditText et_class;//班级数据

    @BindView(R.id.et_height)
    EditText et_height;//身高数据

    @BindView(R.id.et_weight)
    EditText et_weight;//体重数据

    @BindView(R.id.et_phoneNumber)
    TextView et_phoneNumber;//手机号码


    private CommonPopupWindow datePopupWindow;
    private CommonPopupWindow.LayoutGravity layoutGravity;
    private Button date_finish;
    private NumberPickerView year;
    private NumberPickerView month;
    private NumberPickerView day;

    private CommonPopupWindow genderPopupWindow;
    private NumberPickerView gender;
    private Button gender_finish;

    private String imgName = null;
    private Integer sex = null;

    private IHomeManager iHomeManager = HomeManager.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby_info);
        ButterKnife.bind(this);
        initView();
        EventBus.getDefault().register(this);
    }

    private void initView() {
        setViewSize(header_parent, ViewGroup.LayoutParams.MATCH_PARENT, ScreenAdapterUtil.getHeightPx(this) / 10);
        header_title.setText(getString(R.string.baby_info));
        header_back.setVisibility(View.VISIBLE);
        header_title.setVisibility(View.VISIBLE);
        header_confirm.setVisibility(View.VISIBLE);
        header_confirm.setText(getString(R.string.save));

        if(WatchUserInfoBean.getInstance().getWatchUserInfo().getHead().startsWith("sys")){
            Glide.with(this).load(PhotoUtils.getResource(WatchUserInfoBean.getInstance().getWatchUserInfo().getHead().substring(0,13))).into(iv_avatar);
        }else {
            Glide.with(this).load(UrlContants.BASE_URL + "resources/watch/" + WatchUserInfoBean.getInstance().getWatchUserInfo().getHead()).into(iv_avatar);
        }
        et_nick.setText(WatchUserInfoBean.getInstance().getWatchUserInfo().getName());
        if (WatchUserInfoBean.getInstance().getWatchUserInfo().getSex() == 1){
            tv_gender.setText("男");
        }else if ("女".equals(tv_gender.getText().toString())){
            tv_gender.setText("女");
        }
        tv_birthday.setText(WatchUserInfoBean.getInstance().getWatchUserInfo().getBirthday());
        et_grade.setText(WatchUserInfoBean.getInstance().getWatchUserInfo().getGrade());
        et_class.setText(WatchUserInfoBean.getInstance().getWatchUserInfo().getClasses());
        et_height.setText(WatchUserInfoBean.getInstance().getWatchUserInfo().getHeight());
        et_weight.setText(WatchUserInfoBean.getInstance().getWatchUserInfo().getWeight());
        et_phoneNumber.setText(WatchUserInfoBean.getInstance().getWatchUserInfo().getPhone());

        header_back.setOnClickListener(this);
        header_confirm.setOnClickListener(this);
        rl_select_avatar.setOnClickListener(this);
        rl_gender.setOnClickListener(this);
        rl_birthday.setOnClickListener(this);

        initGenderPopupWindow();
        initDatePopupWindow();


    }

    private void initDatePopupWindow() {
        datePopupWindow = new CommonPopupWindow(this, R.layout.popup_date_picker, ScreenAdapterUtil.getWidthPx(this), (int) (ScreenAdapterUtil.getHeightPx(this) * 0.3)) {
            @Override
            protected void initView() {
                View view = getContentView();
                date_finish = view.findViewById(R.id.btn_date_finish);
                year = view.findViewById(R.id.year);
                month = view.findViewById(R.id.month);
                day = view.findViewById(R.id.day);
                final String[] yearArray = TimeUtils.returnStringArray(1970, TimeUtils.getYear());
                final String[] monthArray = TimeUtils.returnStringArray(1, 12);
                year.setDisplayedValues(yearArray);
                year.setMinValue(0);
                year.setMaxValue(yearArray.length - 1);
                year.setValue(yearArray.length - 1);
                month.setDisplayedValues(monthArray);
                month.setMinValue(0);
                month.setMaxValue(monthArray.length - 1);
                month.setValue(monthArray.length - 1);
                monthTypeToDay(month.getValue() + 1);
            }

            @Override
            protected void initEvent() {
                year.setOnValueChangedListener(new NumberPickerView.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPickerView picker, int oldVal, int newVal) {
                        String[] content = picker.getDisplayedValues();
                        String currentYear = content[newVal - picker.getMinValue()];
                        if (month.getValue() + 1 == 2) {
                            if (TimeUtils.isLeapYear(Integer.parseInt(currentYear)) == 0x01 || TimeUtils.isLeapYear(Integer.parseInt(currentYear)) == 0x03) {//去判断当前是否闰年
                                day.refreshByNewDisplayedValues(TimeUtils.returnStringArray(1, 29));//设置为29天
                            } else {
                                day.refreshByNewDisplayedValues(TimeUtils.returnStringArray(1, 28));//设置为28天
                            }
                        }
                    }
                });
                month.setOnValueChangedListener(new NumberPickerView.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPickerView picker, int oldVal, int newVal) {
                        String[] content = picker.getDisplayedValues();
                        String currentMonth = content[newVal - picker.getMinValue()];
                        monthTypeToDay(Integer.parseInt(currentMonth));
                    }
                });
                date_finish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String data = year.getContentByCurrValue() + "-" + month.getContentByCurrValue() + "-" + day.getContentByCurrValue();
                        tv_birthday.setText(data);
                        datePopupWindow.getPopupWindow().dismiss();
                    }
                });
            }
        };
        datePopupWindow.getPopupWindow().setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1.0f);
            }
        });
        //实例化锚点对象
        layoutGravity = new CommonPopupWindow.LayoutGravity(CommonPopupWindow.LayoutGravity.CENTER_HORIZONTAL | CommonPopupWindow.LayoutGravity.ALIGN_BOTTOM);
    }


    private void initGenderPopupWindow() {
        final String[] genderArray = {"女", "男"};
        genderPopupWindow = new CommonPopupWindow(this, R.layout.popup_gender_picker, ScreenAdapterUtil.getWidthPx(this), (int) (ScreenAdapterUtil.getHeightPx(this) * 0.3)) {
            @Override
            protected void initView() {
                View view = getContentView();
                gender_finish = view.findViewById(R.id.btn_gender_finish);
                gender = view.findViewById(R.id.gender);
                gender.setDisplayedValues(genderArray);
                gender.setMinValue(0);
                gender.setMaxValue(genderArray.length - 1);
                gender.setValue(genderArray.length - 1);
            }

            @Override
            protected void initEvent() {
                gender_finish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tv_gender.setText(gender.getContentByCurrValue());
                        genderPopupWindow.getPopupWindow().dismiss();
                    }
                });
            }
        };
        genderPopupWindow.getPopupWindow().setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1.0f);
            }
        });
        //实例化锚点对象
        layoutGravity = new CommonPopupWindow.LayoutGravity(CommonPopupWindow.LayoutGravity.CENTER_HORIZONTAL | CommonPopupWindow.LayoutGravity.ALIGN_BOTTOM);
    }


    private void monthTypeToDay(int month) {
        switch (month) {
            case 1://31天
                day.refreshByNewDisplayedValues(TimeUtils.returnStringArray(1, 31));
                break;
            case 2://闰年29天，平年28天
                int currentYear = year.getValue();//拿到当前的年份
                if (TimeUtils.isLeapYear(currentYear) == 0x01 || TimeUtils.isLeapYear(currentYear) == 0x03) {//去判断当前是否闰年
                    day.refreshByNewDisplayedValues(TimeUtils.returnStringArray(1, 29));//设置为29天
                } else {
                    day.refreshByNewDisplayedValues(TimeUtils.returnStringArray(1, 28));//设置为28天
                }
                break;
            case 3://31天
                day.refreshByNewDisplayedValues(TimeUtils.returnStringArray(1, 31));
                break;
            case 4://30天
                day.refreshByNewDisplayedValues(TimeUtils.returnStringArray(1, 30));
                break;
            case 5://31天
                day.refreshByNewDisplayedValues(TimeUtils.returnStringArray(1, 31));
                break;
            case 6://30天
                day.refreshByNewDisplayedValues(TimeUtils.returnStringArray(1, 30));
                break;
            case 7://31天
                day.refreshByNewDisplayedValues(TimeUtils.returnStringArray(1, 31));
                break;
            case 8://31天
                day.refreshByNewDisplayedValues(TimeUtils.returnStringArray(1, 31));
                break;
            case 9://30天
                day.refreshByNewDisplayedValues(TimeUtils.returnStringArray(1, 30));
                break;
            case 10://31天
                day.refreshByNewDisplayedValues(TimeUtils.returnStringArray(1, 31));
                break;
            case 11://30天
                day.refreshByNewDisplayedValues(TimeUtils.returnStringArray(1, 30));
                break;
            case 12://31天
                day.refreshByNewDisplayedValues(TimeUtils.returnStringArray(1, 31));
                break;
        }
    }


    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        this.getWindow().setAttributes(lp);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.header_back:
                finish();
                break;
            case R.id.header_confirm:
                if (iHomeManager != null){
                    if ("男".equals(tv_gender.getText().toString())){
                        sex = 1;
                    }else if ("女".equals(tv_gender.getText().toString())){
                        sex = 0;
                    }
                    iHomeManager.saveWatchUserInfo(imgName, et_nick.getText().toString(), sex, tv_birthday.getText().toString(), et_grade.getText().toString(), et_class.getText().toString(),
                            et_height.getText().toString(), et_weight.getText().toString(), et_phoneNumber.getText().toString(), new BaseObserverListener<BaseResultBean>() {
                                @Override
                                public void onCompleted() {
                                }

                                @Override
                                public void onError(Throwable e) {
                                    ToastUtils.showShort(BabyInfoActivity.this, getString(R.string.data_error));
                                }

                                @Override
                                public void onNext(BaseResultBean baseResultBean) {
                                    if (baseResultBean.getCode() == 0){
                                        startActivity(new Intent(BabyInfoActivity.this,HomePagerActivity.class));
                                        AppManager.finishAllActivity();
                                    }
                                }
                            });
                }
                break;
            case R.id.rl_select_avatar://头像
                startActivity(new Intent(this,BabyAvatarActivity.class));
                break;
            case R.id.rl_gender://性别
                closeKeyBoard(this);
                //设置背景变暗
                backgroundAlpha(0.5f);
                genderPopupWindow.showBashOfAnchor(parentView, layoutGravity, 0, 0);
                break;
            case R.id.rl_birthday://生日
                closeKeyBoard(this);
                //设置背景变暗
                backgroundAlpha(0.5f);
                datePopupWindow.showBashOfAnchor(parentView, layoutGravity, 0, 0);
                break;
        }
    }

    private void closeKeyBoard(Activity activity) {
        InputMethodManager immm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (immm.isActive() && activity.getCurrentFocus() != null) {
            if (activity.getCurrentFocus().getWindowToken() != null) {
                immm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    //EventBus设置头像数据
    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void setAvatar(BabyAvatarEvent avatar){
        if(avatar.getType() == 0){
            //自定义头像,在拍照完后上传至服务器，再从服务器获取图片uri,便于统一操作
            //图片路径baseUrl/resources/watch/+head
            imgName = avatar.getName();
            Glide.with(this).load(UrlContants.BASE_URL+"resources/watch/"+imgName).into(iv_avatar);

        }else if (avatar.getType() == 1){
            //系统头像
            imgName = avatar.getName();
            Glide.with(this).load(PhotoUtils.getResource(imgName.substring(0,13))).into(iv_avatar);
        }
    }
}
