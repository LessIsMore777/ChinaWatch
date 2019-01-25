package com.waterworld.watch.common.customview.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.ColorInt;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.waterworld.watch.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nhuan
 * Time:2018/12/21.
 */

public class LoadingDialog implements FinishDrawListener{

    public static final int STYLE_RING = 0;
    public static final int STYLE_LINE = 1;
    private Context mContext;

    private LVCircularRing mLoadingView;
    private Dialog mLoadingDialog;
    private LinearLayout layout;
    private TextView loadingText;
    private RightDiaView mSuccessView;
    private WrongDiaView mFailedView;

    private String loadSuccessStr;
    private String loadFailedStr;
    private List<View> viewList;

    private boolean interceptBack = true;
    private boolean openSuccessAnim = true;
    private boolean openFailedAnim = true;
    private int speed = 1;
    private long time = 500;
    private int loadStyle = STYLE_RING;

    private static StyleManager s = new StyleManager(true, 0, Speed.SPEED_TWO, -1, -1, 1000L,
            true, "加载中...", "加载成功", "加载失败");
    private LoadCircleView mCircleLoadView;

    public enum Speed {
        SPEED_ONE,
        SPEED_TWO
    }

    @SuppressLint("HandlerLeak")
    private Handler h = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            LoadingDialog.this.close();
            if (o != null) o.onFinish();
        }
    };

    private OnFinshListener o;

    public LoadingDialog(Context context) {
        mContext = context;
        // 首先得到整个View
        @SuppressWarnings("all")
        View view = LayoutInflater.from(context).inflate(R.layout.loading_dialog_view, null);
        initView(view);
        // 创建自定义样式的Dialog
        mLoadingDialog = new Dialog(context, R.style.loading_dialog) {
            @Override
            public void onBackPressed() {
                if (interceptBack) {
                    return;
                }
                LoadingDialog.this.close();
            }
        };
        // 设置返回键无效
        mLoadingDialog.setCancelable(!interceptBack);

        mLoadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        mLoadingDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mContext = null;
            }
        });
        initStyle();
    }

    /**
     * 初始化风格
     */
    private void initStyle() {
        if (s != null) {
            setInterceptBack(s.isInterceptBack());
            setRepeatCount(s.getRepeatTime());
            setParams(s.getContentSize());
            setTextSize(s.getTextSize());
            setShowTime(s.getShowTime());
            if (!s.isOpenAnim()) {
                closeFailedAnim();
                closeSuccessAnim();
            }
            setLoadingText(s.getLoadText());
            setSuccessText(s.getSuccessText());
            setFailedText(s.getFailedText());
            setLoadStyle(s.getLoadStyle());
        }
    }

    private void initView(View view) {
        layout = view.findViewById(R.id.dialog_view);
        mLoadingView = view.findViewById(R.id.lv_circularring);
        loadingText = view.findViewById(R.id.loading_text);
        mSuccessView = view.findViewById(R.id.rdv_right);
        mFailedView = view.findViewById(R.id.wv_wrong);
        mCircleLoadView = view.findViewById(R.id.lcv_circleload);
        initData();
    }

    private void initData() {
        viewList = new ArrayList<>();
        viewList.add(mLoadingView);
        viewList.add(mSuccessView);
        viewList.add(mFailedView);
        viewList.add(mCircleLoadView);

        mSuccessView.setOnDrawFinishListener(this);//绘制完成监听器
        mFailedView.setOnDrawFinishListener(this);//绘制完成监听器
    }

    /**
     * 分发完成事件
     * 发送延迟空消息
     * @param v
     */
    @Override
    public void dispatchFinishEvent(View v) {
        if (v instanceof WrongDiaView) {
            h.sendEmptyMessageDelayed(2, time);
        } else {
            h.sendEmptyMessageDelayed(1, time);
        }
    }

    /**
     * 隐藏全部控件
     */
    private void hideAll() {
        for (View v : viewList) {
            if (v.getVisibility() != View.GONE) {
                v.setVisibility(View.GONE);
            }
        }
    }

    private void setParams(int size) {
        if (size < 0) {
            return;
        }
        ViewGroup.LayoutParams successParams = mSuccessView.getLayoutParams();
        successParams.height = size;
        successParams.width = size;
        mSuccessView.setLayoutParams(successParams);

        ViewGroup.LayoutParams failedParams = mFailedView.getLayoutParams();
        failedParams.height = size;
        failedParams.width = size;
        mFailedView.setLayoutParams(failedParams);

        ViewGroup.LayoutParams loadingParams = mLoadingView.getLayoutParams();
        loadingParams.height = size;
        loadingParams.width = size;
    }






    /**
     * 请在最后调用show，因此show返回值为void会使链式api断开
     */
    public void show() {
        hideAll();
        if (loadStyle == STYLE_RING) {
            mLoadingView.setVisibility(View.VISIBLE);
            mCircleLoadView.setVisibility(View.GONE);
            mLoadingDialog.show();
            mLoadingView.startAnim(mLoadingView);
            Log.d("showCustomTime", "style_ring");
        } else if (loadStyle == STYLE_LINE) {
            mCircleLoadView.setVisibility(View.VISIBLE);
            mLoadingView.setVisibility(View.GONE);
            mLoadingDialog.show();
            Log.d("showCustomTime", "style_line");
        }
    }

    /**
     * 设置load的样式，目前支持转圈圈和菊花转圈圈
     */
    public LoadingDialog setLoadStyle(int style) {
        if (style >= 3) {
            throw new IllegalArgumentException("Your style is wrong!");
        }
        this.loadStyle = style;
        return this;
    }

    /**
     * 让这个dialog消失，在拦截back事件的情况下一定要调用这个方法！
     * 在调用了该方法之后如需再次使用loadingDialog，请新创建一个
     * LoadingDialog对象。
     */
    public void close() {
        viewList.clear();
        h.removeCallbacksAndMessages(null);
        if (mLoadingDialog != null) {
            mLoadingView.stopAnim();
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
    }

    /**
     * 设置加载时的文字提示
     */
    public LoadingDialog setLoadingText(String msg) {
        if (msg != null && msg.length() > 0)
            loadingText.setText(msg);
        return this;
    }

    /**
     * 设置加载成功的文字提示
     */
    public LoadingDialog setSuccessText(String msg) {
        if (msg != null && msg.length() > 0)
            loadSuccessStr = msg;
        return this;
    }

    /**
     * 设置加载失败的文字提示
     */
    public LoadingDialog setFailedText(String msg) {
        if (msg != null && msg.length() > 0) loadFailedStr = msg;
        return this;
    }

    /**
     * 当你需要一个成功的反馈的时候，在加载成功的回调中调用此方法
     */
    public void loadSuccess() {
        mLoadingView.stopAnim();
        hideAll();
        mSuccessView.setDrawDynamic(openSuccessAnim);
        mSuccessView.setVisibility(View.VISIBLE);
        loadingText.setText(loadSuccessStr);
    }

    /**
     * 当你需要一个失败的反馈的时候，在加载失败的回调中调用此方法
     */
    public void loadFailed() {
        mLoadingView.stopAnim();
        hideAll();
        mFailedView.setDrawDynamic(openFailedAnim);
        mFailedView.setVisibility(View.VISIBLE);
        loadingText.setText(loadFailedStr);
    }

    /**
     * 关闭动态绘制
     */
    public LoadingDialog closeSuccessAnim() {
        this.openSuccessAnim = false;
        return this;
    }

    /**
     * 关闭动态绘制
     */
    public LoadingDialog closeFailedAnim() {
        this.openFailedAnim = false;
        return this;
    }

    /**
     * 设置是否拦截back，默认会拦截
     */
    public LoadingDialog setInterceptBack(boolean interceptBack) {
        this.interceptBack = interceptBack;
        mLoadingDialog.setCancelable(!interceptBack);
        return this;
    }

    /**
     * 当前dialog是否拦截back事件
     */
    public boolean getInterceptBack() {
        return interceptBack;
    }

    /**
     * 使用该方法改变成功和失败绘制的速度
     */
    public LoadingDialog setLoadSpeed(Speed speed) {
        if (speed == Speed.SPEED_ONE) {
            this.speed = 1;
            mSuccessView.setSpeed(1);
            mFailedView.setSpeed(1);
        } else {
            this.speed = 2;
            mSuccessView.setSpeed(2);
            mFailedView.setSpeed(2);
        }
        return this;
    }

    /**
     * 返回当前绘制的速度
     */
    public int getSpeed() {
        return this.speed;
    }

    public LoadingDialog setDrawColor(@ColorInt int color) {
        mFailedView.setDrawColor(color);
        mSuccessView.setDrawColor(color);
        mLoadingView.setColor(color);
        return this;
    }

    /**
     * 设置字体颜色
     */
    public LoadingDialog setTextColor(@ColorInt int color){
        loadingText.setTextColor(color);
        return this;
    }

    /**
     * 设置中间弹框的尺寸
     */
    public LoadingDialog setSize(int size) {
//        int dip = SizeUtils.px2dip(mContext, size);
        if (size <= 50) return this;
        setParams(size);
        return this;
    }

    /**
     * 设置重新绘制的次数，默认只绘制一次，如果你设置这个
     * 数值为1，那么在绘制一次过后，还会再次绘制一次。
     */
    public LoadingDialog setRepeatCount(int count) {
        mFailedView.setRepeatTime(count);
        mSuccessView.setRepeatTime(count);
        return this;
    }

    /**
     * 设置反馈展示时间
     */
    public LoadingDialog setShowTime(long time) {
        if (time < 0) return this;
        this.time = time;
        return this;
    }

    /**
     * 设置加载字体大小
     */
    public LoadingDialog setTextSize(float size) {
        if (size < 0) return this;
        loadingText.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        return this;
    }

    public static void initStyle(StyleManager style) {
        if (style != null)
            s = style;
    }

    /**
     * 传递绘制完成的事件
     */
    public void setOnFinishListener(OnFinshListener o) {
        this.o = o;
    }

    /**
     * 监听器
     */
    public interface OnFinshListener {
        void onFinish();
    }
}
