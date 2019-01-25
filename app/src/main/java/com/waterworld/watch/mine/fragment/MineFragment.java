package com.waterworld.watch.mine.fragment;

import android.support.constraint.ConstraintLayout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.waterworld.watch.R;
import com.waterworld.watch.home.activity.HomePagerActivity;
import com.waterworld.watch.common.customview.CommonPopupWindow;
import com.waterworld.watch.common.fragment.BaseFragment;
import com.waterworld.watch.common.util.ScreenAdapterUtil;
import com.waterworld.watch.mine.activity.IntimateActivity;
import com.waterworld.watch.mine.activity.MessageNotificationActivity;
import com.waterworld.watch.mine.activity.MyInformationActivity;
import com.waterworld.watch.mine.activity.TimingActivity;

/**
 * 编写者：Created by SunnyTang
 * 时间：2018/11/28 13:59
 * 主要作用：app用户个人信息(碎片)
 */
public class MineFragment extends BaseFragment implements View.OnClickListener {



    private HomePagerActivity parentActivity;//宿主Activity
    private ConstraintLayout parentView;

    private ConstraintLayout myInfo;//我的信息
    private ConstraintLayout notification;//消息通知
    private ConstraintLayout intimate;//贴心设置
    private ConstraintLayout timing;//定时开关机
    private ConstraintLayout shutdown;//远程关机
    private ConstraintLayout clean;//清理缓存
    private ConstraintLayout update;//版本更新
    private Button outLogin;//退出登录
    /**
     * 泡泡窗口对象
     */
    private CommonPopupWindow popupWindow;//退出登录泡泡窗口
    private TextView popupContent;
    private Button popupBtnOk;
    private Button popupBtnCancel;




    private final int SHUT_DOWN = 0X01;
    private final int CLEAN = 0X02;
    private final int UPDATE = 0X03;
    private final int OUT_LOGIN = 0X04;

    @Override
    protected int setContentView() {
        return R.layout.fragment_me;
    }

    @Override
    protected void initView() {
        parentActivity = (HomePagerActivity) getActivity();
        bindView();
        bindClick();
    }

    @Override
    protected void lazyLoad() {

    }

    private void bindView() {
        parentView = findViewById(R.id.parentView);
        myInfo = findViewById(R.id.cl_myInfo);
        notification = findViewById(R.id.cl_notification);
        intimate = findViewById(R.id.cl_intimate);
        timing = findViewById(R.id.cl_timing);
        shutdown = findViewById(R.id.cl_shutdown);
        clean = findViewById(R.id.cl_clean);
        update = findViewById(R.id.cl_update);
        outLogin = findViewById(R.id.btn_outLogin);
    }

    private void bindClick() {
        myInfo.setOnClickListener(this);
        notification.setOnClickListener(this);
        intimate.setOnClickListener(this);
        timing.setOnClickListener(this);
        shutdown.setOnClickListener(this);
        clean.setOnClickListener(this);
        update.setOnClickListener(this);
        outLogin.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cl_myInfo://个人信息
                parentActivity.goActivity(MyInformationActivity.class);
                break;
            case R.id.cl_notification://消息通知
                parentActivity.goActivity(MessageNotificationActivity.class);
                break;
            case R.id.cl_intimate://贴心设置
                parentActivity.goActivity(IntimateActivity.class);
                break;
            case R.id.cl_timing://定时开关机
                parentActivity.goActivity(TimingActivity.class);
                break;
            case R.id.cl_shutdown://远程关机
                backgroundAlpha(0.5f);
                showPopupWindow("确认强制关机手表？", "确定", "取消", SHUT_DOWN);
                break;
            case R.id.cl_clean://清除缓存
                backgroundAlpha(0.5f);
                showPopupWindow("确认要删除所有微聊消息以及消息通知的记录吗？", "确定", "取消", CLEAN);
                break;
            case R.id.cl_update://版本更新
                backgroundAlpha(0.5f);
                showPopupWindow("确认进行版本更新？", "确定", "取消", UPDATE);
                break;
            case R.id.btn_outLogin://退出登录
                backgroundAlpha(0.5f);
                showPopupWindow("确认退出登陆？", "确定", "取消", OUT_LOGIN);
                break;
        }
    }

    /**
     * 退出登录
     */
    private void showPopupWindow(final String contentText, final String btnOKText, final String btnCancelText, int type) {
        final int popupType = type;
        popupWindow = new CommonPopupWindow(parentActivity, R.layout.popup_out_login, (int) (ScreenAdapterUtil.getWidthPx(parentActivity) * 0.75), ViewGroup.LayoutParams.WRAP_CONTENT) {
            @Override
            protected void initView() {
                View view = getContentView();
                popupContent = view.findViewById(R.id.tv_content);
                popupBtnOk = view.findViewById(R.id.btn_popup_ok);
                popupBtnCancel = view.findViewById(R.id.btn_popup_cancel);
                popupContent.setText(contentText);
                popupBtnOk.setText(btnOKText);
                popupBtnCancel.setText(btnCancelText);
            }

            @Override
            protected void initEvent() {
                popupBtnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (popupType) {
                            case SHUT_DOWN:
                                break;
                            case CLEAN:
                                break;
                            case UPDATE:
                                break;
                            case OUT_LOGIN:
                                //parentActivity.getiMineManager().
                                break;
                        }
                    }
                });
                popupBtnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.getPopupWindow().dismiss();
                    }
                });
            }
        };
        popupWindow.getPopupWindow().setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1.0f);
            }
        });
        popupWindow.showAtLocation(parentView, Gravity.CENTER, 0, 0);
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = parentActivity.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        parentActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        parentActivity.getWindow().setAttributes(lp);
    }
}
