package com.waterworld.watch.home.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;

import com.waterworld.watch.R;
import com.waterworld.watch.common.activity.BaseActivity;
import com.waterworld.watch.common.customview.CommonPopupWindow;
import com.waterworld.watch.common.util.TimeUtils;
import com.waterworld.watch.common.util.ScreenAdapterUtil;

import cn.carbswang.android.numberpickerview.library.NumberPickerView;

/**
 * 编写者：Created by SunnyTang
 * 时间：2018/12/7 14:30
 * 主要作用：宝贝信息(活动)
 */
public class BabyInfoActivity extends BaseActivity implements View.OnClickListener {

    private BabyInfoActivity thisActivity;
    /**
     * header
     */
    private LinearLayout parentView;
    private LinearLayout header_parent;
    private ImageButton header_back;
    private TextView header_title;
    private Button header_save;
    /**
     * body
     */
    private RelativeLayout babyAvatar;//头像
    private RelativeLayout babyNick;//昵称
    private EditText nickText;//昵称数据
    private RelativeLayout babyGender;//性别
    private TextView genderText;//性别数据
    private RelativeLayout babyBirthday;//生日
    private TextView birthdayText;//生日数据
    private EditText gradeText;//年级数据
    private EditText classText;//班级数据
    private EditText heightText;//身高数据
    private EditText weightText;//体重数据
    private TextView phoneText;//手机号码
    /**
     * 生日选择
     */
    private CommonPopupWindow datePopupWindow;
    private CommonPopupWindow.LayoutGravity layoutGravity;// 泡泡窗口锚点对象
    private Button date_finish;
    private NumberPickerView year;
    private NumberPickerView month;
    private NumberPickerView day;
    /**
     * 性别选择
     */
    private CommonPopupWindow genderPopupWindow;
    private NumberPickerView gender;
    private Button gender_finish;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby_info);
        thisActivity = this;
        bindView();
        initView();
        bindClick();
        initGenderPopupWindow();
        initDatePopupWindow();
        editListener();
    }

    /**
     * 绑定View
     */
    private void bindView() {
        parentView = findViewById(R.id.parentView);
        header_parent = findViewById(R.id.header_parent);
        header_back = findViewById(R.id.header_back);
        header_title = findViewById(R.id.header_title);
        header_save = findViewById(R.id.header_save);
        babyAvatar = findViewById(R.id.rl_select_avatar);
        babyNick = findViewById(R.id.rl_nick);
        babyGender = findViewById(R.id.rl_gender);
        babyBirthday = findViewById(R.id.rl_birthday);

        nickText = findViewById(R.id.et_nick);
        genderText = findViewById(R.id.tv_gender);
        birthdayText = findViewById(R.id.tv_birthday);
        gradeText = findViewById(R.id.et_grade);
        classText = findViewById(R.id.et_class);
        heightText = findViewById(R.id.et_height);
        weightText = findViewById(R.id.et_weight);
        phoneText = findViewById(R.id.tv_phoneNumber);
    }

    /**
     * 初始化View
     */
    private void initView() {
        setViewSize(header_parent, ViewGroup.LayoutParams.MATCH_PARENT, ScreenAdapterUtil.getHeightPx(this) / 12);
        header_title.setText("宝贝信息");
        header_back.setVisibility(View.VISIBLE);
        header_title.setVisibility(View.VISIBLE);
        header_save.setVisibility(View.VISIBLE);

        nickText.setSelection(nickText.getText().length());
        gradeText.setSelection(gradeText.getText().length());
        classText.setSelection(classText.getText().length());
        heightText.setSelection(heightText.getText().length());
        weightText.setSelection(weightText.getText().length());
    }

    /**
     * 绑定点击事件
     */
    private void bindClick() {
        header_back.setOnClickListener(this);
        header_save.setOnClickListener(this);
        babyAvatar.setOnClickListener(this);
        babyGender.setOnClickListener(this);
        babyBirthday.setOnClickListener(this);
    }

    /**
     * 初始化日期泡泡窗口
     */
    private void initDatePopupWindow() {
        datePopupWindow = new CommonPopupWindow(thisActivity, R.layout.popup_date_picker, ScreenAdapterUtil.getWidthPx(thisActivity), (int) (ScreenAdapterUtil.getHeightPx(thisActivity) * 0.3)) {
            @Override
            protected void initView() {
                View view = getContentView();
                //绑定控件
                date_finish = view.findViewById(R.id.btn_date_finish);
                year = view.findViewById(R.id.year);
                month = view.findViewById(R.id.month);
                day = view.findViewById(R.id.day);
                //设置年份区间
                final String[] yearArray = TimeUtils.returnStringArray(1970, TimeUtils.getYear());
                //设置月份区间
                final String[] monthArray = TimeUtils.returnStringArray(1, 12);
                //初始化年份选择器
                year.setDisplayedValues(yearArray);
                year.setMinValue(0);
                year.setMaxValue(yearArray.length - 1);
                year.setValue(yearArray.length - 1);
                //初始化月份选择器
                month.setDisplayedValues(monthArray);
                month.setMinValue(0);
                month.setMaxValue(monthArray.length - 1);
                month.setValue(monthArray.length - 1);
                //初始化天数选择器
                monthTypeToDay(month.getValue() + 1);
            }

            @Override
            protected void initEvent() {
                year.setOnValueChangedListener(new NumberPickerView.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPickerView picker, int oldVal, int newVal) {
                        String[] content = picker.getDisplayedValues();
                        //获取当前年份
                        String currentYear = content[newVal - picker.getMinValue()];
                        //判断当前月份是否为2月
                        if (month.getValue() + 1 == 2) {
                            //如果是2月，那么判断当前年份是否闰年
                            if (TimeUtils.aaa(Integer.parseInt(currentYear)) == 0x01 || TimeUtils.aaa(Integer.parseInt(currentYear)) == 0x03) {//去判断当前是否闰年
                                //设置为29天
                                day.refreshByNewDisplayedValues(TimeUtils.returnStringArray(1, 29));//设置为29天
                            } else {
                                //设置为28天
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
                        String data = year.getContentByCurrValue() + "年" + month.getContentByCurrValue() + "月" + day.getContentByCurrValue() + "日";
                        birthdayText.setText(data);
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

    /**
     * 初始化性别泡泡窗口
     */
    private void initGenderPopupWindow() {
        final String[] genderArray = {"女", "男"};
        genderPopupWindow = new CommonPopupWindow(thisActivity, R.layout.popup_gender_picker, ScreenAdapterUtil.getWidthPx(thisActivity), (int) (ScreenAdapterUtil.getHeightPx(thisActivity) * 0.3)) {
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
                        genderText.setText(gender.getContentByCurrValue());
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

    /**
     * 根据月份来设置不同的月份天数
     *
     * @param month 月份
     */
    private void monthTypeToDay(int month) {
        switch (month) {
            case 1://31天
                day.refreshByNewDisplayedValues(TimeUtils.returnStringArray(1, 31));
                break;
            case 2://闰年29天，平年28天
                int currentYear = year.getValue();//拿到当前的年份
                if (TimeUtils.aaa(currentYear) == 0x01 || TimeUtils.aaa(currentYear) == 0x03) {//去判断当前是否闰年
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

    /**
     * 设备背景透明度
     *
     * @param bgAlpha 0.0f~1.0f
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = thisActivity.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        thisActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        thisActivity.getWindow().setAttributes(lp);
    }

    /**
     * 点击事件
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.header_back:
                finish();
                break;
            case R.id.header_save:
                break;
            case R.id.rl_select_avatar://头像
                goActivity(BabyAvatarActivity.class);
                break;
            case R.id.rl_gender://性别
                closeKeyBoard(thisActivity);
                //设置背景变暗
                backgroundAlpha(0.5f);
                mHandler.sendEmptyMessageDelayed(0x01,100);
                break;
            case R.id.rl_birthday://生日
                closeKeyBoard(thisActivity);
                //设置背景变暗
                backgroundAlpha(0.5f);
                mHandler.sendEmptyMessageDelayed(0x02,100);
                break;
        }
    }

    /**
     * 监听edit
     */
    private void editListener() {
        heightText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    if (heightText.getText().length() != 0) {
                        String temp = heightText.getText().toString().replace("cm", "");
                        heightText.setText(temp);
                        heightText.setSelection(heightText.getText().length());
                    }
                } else {
                    if (heightText.getText().length() == 0) {
                        heightText.setText("");
                    } else {
                        String temp = heightText.getText() + "cm";
                        heightText.setText(temp);
                    }
                }
            }
        });
        weightText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    if (weightText.getText().length() != 0) {
                        String temp = weightText.getText().toString().replace("kg", "");
                        weightText.setText(temp);
                        weightText.setSelection(weightText.getText().length());
                    }
                } else {
                    if (weightText.getText().length() == 0) {
                        weightText.setText("");
                    } else {
                        String temp = weightText.getText() + "kg";
                        weightText.setText(temp);
                    }
                }
            }
        });
    }

    /**
     * 强制关闭键盘
     */
    private void closeKeyBoard(Activity activity) {
        InputMethodManager immm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (immm.isActive() && activity.getCurrentFocus() != null) {
            if (activity.getCurrentFocus().getWindowToken() != null) {
                immm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //这里每次可见的时候，都要请求一下数据，得到最新的消息
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x01:
                    //显示popupWindow
                    genderPopupWindow.showBashOfAnchor(parentView, layoutGravity, 0, 0);
                    break;
                case 0x02:
                    //显示popupWindow
                    datePopupWindow.showBashOfAnchor(parentView, layoutGravity, 0, 0);
                    break;
            }
        }
    };
}
