package com.waterworld.watch.location.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.amap.api.maps.MapView;
import com.waterworld.watch.R;
import com.waterworld.watch.common.activity.BaseActivity;
import com.waterworld.watch.common.customview.CommonPopupWindow;
import com.waterworld.watch.common.util.DialogUtils;
import com.waterworld.watch.common.util.ScreenAdapterUtil;
import com.waterworld.watch.common.util.TimeUtils;
import com.waterworld.watch.common.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.carbswang.android.numberpickerview.library.NumberPickerView;

public class AddElectronicFenceActivity extends BaseActivity {

    @BindView(R.id.ll_parent)
    LinearLayout ll_parent;

    @BindView(R.id.header_parent)
    LinearLayout header_parent;

    @BindView(R.id.header_back)
    ImageButton header_back;

    @BindView(R.id.header_title)
    TextView header_title;

    @BindView(R.id.header_confirm)
    Button header_confirm;

    @BindView(R.id.et_fence_name)
    EditText et_fence_name;

    @BindView(R.id.rl_remind_way)
    RelativeLayout rl_remind_way;

    @BindView(R.id.iv_remind_way)
    ImageView iv_remind_way;

    @BindView(R.id.rg_remind_way)
    RadioGroup rg_remind_way;

    @BindView(R.id.rb_remind_enter)
    RadioButton rb_remind_enter;

    @BindView(R.id.rb_remind_leave)
    RadioButton rb_remind_leave;

    @BindView(R.id.rb_remind_not_enter_on_time)
    RadioButton rb_remind_not_enter_on_time;

    @BindView(R.id.sb_fence_range)
    SeekBar sb_fence_range;

    @BindView(R.id.tv_fence_range)
    TextView tv_fence_range;

    @BindView(R.id.rl_fence_range)
    RelativeLayout rl_fence_range;

    @BindView(R.id.map)
    MapView mMapView;

    private static final int RANGE_MIN = 300;
    private static final int RANGE = 2000;

    private CommonPopupWindow popupWindow;

    private CommonPopupWindow.LayoutGravity layoutGravity;

    private static boolean isExtend = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_electronic_fence);
        ButterKnife.bind(this);
        initView();
        onClick();
        mMapView.onCreate(savedInstanceState);
    }

    private void initView(){
        setViewSize(header_parent, RelativeLayout.LayoutParams.MATCH_PARENT, ScreenAdapterUtil.getHeightPx(this)/12);
        header_back.setVisibility(View.VISIBLE);
        header_title.setVisibility(View.VISIBLE);
        header_confirm.setVisibility(View.VISIBLE);
        header_title.setText("添加电子围栏");

        //initial PopWindow
        popupWindow = new CommonPopupWindow(this,R.layout.electronic_fence_timing,(int)(ScreenAdapterUtil.getWidthPx(this)*0.9),(int)(ScreenAdapterUtil.getHeightPx(this)*0.7)) {
            @Override
            protected void initView() {
                View view = getContentView();
                NumberPickerView hour = view.findViewById(R.id.hour);
                NumberPickerView minute = view.findViewById(R.id.minute);

                final String[] hourArray = TimeUtils.returnStringArray(0,23);
                final String[] minuteArray = TimeUtils.returnStringArray(0,59);

                hour.setDisplayedValues(hourArray);
                hour.setMinValue(0);
                hour.setMaxValue(hourArray.length-1);
                hour.setValue(hourArray.length-1);

                minute.setDisplayedValues(minuteArray);
                minute.setMinValue(0);
                minute.setMaxValue(minuteArray.length-1);
                minute.setValue(minuteArray.length-1);
            }

            @Override
            protected void initEvent() {

            }
        };
        layoutGravity = new CommonPopupWindow.LayoutGravity(CommonPopupWindow.LayoutGravity.CENTER_HORIZONTAL | CommonPopupWindow.LayoutGravity.CENTER_VERTICAL);
    }

    private void onClick(){
        sb_fence_range.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv_fence_range.setText(String.valueOf(progress+RANGE_MIN)+getString(R.string.meter));
                double step = sb_fence_range.getWidth() / (double)RANGE;
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) rl_fence_range.getLayoutParams();
                lp.setMargins((int)(progress * step),0,0,0);
                rl_fence_range.setLayoutParams(lp);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        rg_remind_way.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                iv_remind_way.setImageResource(isExtend ? R.drawable.ic_arrow_right : R.drawable.ic_arrow_down);
                rg_remind_way.setVisibility(isExtend ? View.GONE : View.VISIBLE);
                isExtend = false;
                if (rb_remind_not_enter_on_time.getId() == checkedId){
                    popupWindow.showBashOfAnchor(ll_parent, layoutGravity, 0, 0);
                }
            }
        });

        rl_remind_way.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_remind_way.setImageResource(isExtend ? R.drawable.ic_arrow_right : R.drawable.ic_arrow_down);
                rg_remind_way.setVisibility(isExtend ? View.GONE : View.VISIBLE);
                isExtend = !isExtend;
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }
}
