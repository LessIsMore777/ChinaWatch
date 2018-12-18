package com.waterworld.watch.common.customview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

/**
 * 编写者：Created by SunnyTang
 * 时间：2018/11/29 15:45
 * 主要作用：封装PopupWindow对象
 */
public abstract class CommonPopupWindow{

    protected Context context;
    private View contentView;
    private PopupWindow mInstance;

    /**
     * 构造方法
     *
     * @param context         上下文对象
     * @param layoutResources 泡泡窗口的布局文件
     * @param width           宽
     * @param height          高
     */
    public CommonPopupWindow(Context context, int layoutResources, int width, int height) {
        this.context = context;
        contentView = LayoutInflater.from(context).inflate(layoutResources, null, false);
        initView();
        initEvent();
        mInstance = new PopupWindow(contentView, width, height, true);
        initWindow();
    }

    /**
     * 获取View
     *
     * @return View
     */
    public View getContentView() {
        return contentView;
    }

    /**
     * 获取PopupWindow的对象
     * 通过此方法可以直接调用系统PopupWindow里面的方法
     * 比如 dismiss()
     * @return 系统的通过此方法可以直接调用系统PopupWindow里面的方法
     */
    public PopupWindow getPopupWindow() {
        return mInstance;
    }

    /**
     * 抽象方法，由子类实现。
     * 初始化泡泡窗口中的View。
     * 比如，绑定组件。
     */
    protected abstract void initView();

    /**
     * 抽象方法，由子类实现。
     * 初始化泡泡窗口中的Event。
     * 比如，设置点击事件。
     */
    protected abstract void initEvent();

    /**
     * 初始化Window
     */
    private void initWindow() {
        //设置背景色
        mInstance.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //设置是否响应外部事件(比如，触摸外部泡泡窗口消失)
        mInstance.setOutsideTouchable(true);
        //设置是否获得焦点
        mInstance.setTouchable(true);
    }


    /**
     * 显示泡泡窗口
     * @param anchor        泡泡窗口所依附的View
     * @param layoutGravity 锚点对象
     * @param xmerge        x轴偏移量
     * @param ymerge        y轴偏移量
     */
    public void showBashOfAnchor(View anchor, LayoutGravity layoutGravity, int xmerge, int ymerge) {
        int[] offset = layoutGravity.getOffset(anchor, mInstance);
        mInstance.showAsDropDown(anchor, offset[0] + xmerge, offset[1] + ymerge);
    }

    public void showAsDropDown(View anchor, int xOffSet, int yOffSet) {
        mInstance.showAsDropDown(anchor, xOffSet, yOffSet);
    }

    public void showAtLocation(View parent, int gravity, int x, int y) {
        mInstance.showAtLocation(parent, gravity, x, y);
    }

    /**
     * 静态内部类
     * 控制PopupWindow相对锚点位置的对象
     */
    public static class LayoutGravity {

        private int layoutGravity;
        /**
         * 以下常量是水平方向的5种显示方式
         */
        public static final int TO_LEFT = 0x10;//在锚点外部的左边
        public static final int TO_RIGHT = 0x40;//在锚点外部的右边
        public static final int ALIGN_LEFT = 0x1;//在锚点内部的左边
        public static final int ALIGN_RIGHT = 0x4;//在锚点内部的右边
        public static final int CENTER_HORIZONTAL = 0x100;//在锚点水平中部

        /**
         * 以下常量是垂直方向的5种显示方式
         */
        public static final int TO_ABOVE = 0x20;//在锚点外部的上方
        public static final int TO_BOTTOM = 0x80;//在锚点外部的下方
        public static final int ALIGN_ABOVE = 0x2;//在锚点内部的上方
        public static final int ALIGN_BOTTOM = 0x8;//在锚点内部的下方
        public static final int CENTER_VERTICAL = 0x200;//在锚点垂直中部

        /**
         * 构造方法，接收一个重心常量
         * @param gravity 比如：ALIGN_LEFT
         */
        public LayoutGravity(int gravity) {
            layoutGravity = gravity;
        }

        /**
         * 获取当前布局的重心
         * @return 重心常量
         */
        public int getLayoutGravity() {
            return layoutGravity;
        }

        /**
         * 设置当前布局的重心
         * @param gravity 重心常量
         */
        public void setLayoutGravity(int gravity) {
            layoutGravity = gravity;
        }

        /**
         * 设置水平重心
         * @param gravity 重心
         */
        public void setHorizontalGravity(int gravity) {
            layoutGravity &= (0x2 + 0x8 + 0x20 + 0x80 + 0x200);
            layoutGravity |= gravity;
        }

        /**
         * 设置垂直重心
         * @param gravity 重心类型
         */
        public void setVerticalGravity(int gravity) {
            layoutGravity &= (0x1 + 0x4 + 0x10 + 0x40 + 0x100);
            layoutGravity |= gravity;
        }

        /**
         * ？？？
         * @param param
         * @return
         */
        public boolean isParamFit(int param) {
            return (layoutGravity & param) > 0;
        }

        public int getHorizontalParam() {
            for (int i = 0x1; i <= 0x100; i = i << 2)
                if (isParamFit(i))
                    return i;
            return ALIGN_LEFT;
        }

        public int getVerticalParam() {
            for (int i = 0x2; i <= 0x200; i = i << 2)
                if (isParamFit(i))
                    return i;
            return TO_BOTTOM;
        }

        /**
         * 获取偏移量
         * @param anchor 锚点
         * @param window 泡泡窗口
         * @return 偏移量
         */
        public int[] getOffset(View anchor, PopupWindow window) {
            int anchorWidth = anchor.getWidth();
            int anchorHeight = anchor.getHeight();

            int winWidth = window.getWidth();
            int winHeight = window.getHeight();
            View view = window.getContentView();
            if (winWidth <= 0)
                winWidth = view.getWidth();
            if (winHeight <= 0)
                winHeight = view.getHeight();

            int xOffSet = 0;
            int yOffSet = 0;

            switch (getHorizontalParam()) {
                case ALIGN_LEFT:
                    xOffSet = 0;
                    break;
                case ALIGN_RIGHT:
                    xOffSet = anchorWidth - winWidth;
                    break;
                case TO_LEFT:
                    xOffSet = -winWidth;
                    break;
                case TO_RIGHT:
                    xOffSet = anchorWidth;
                    break;
                case CENTER_HORIZONTAL:
                    xOffSet = (anchorWidth - winWidth) / 2;
                    break;
                default:
                    break;
            }
            switch (getVerticalParam()) {
                case ALIGN_ABOVE:
                    yOffSet = -anchorHeight;
                    break;
                case ALIGN_BOTTOM:
                    yOffSet = -winHeight;
                    break;
                case TO_ABOVE:
                    yOffSet = -anchorHeight - winHeight;
                    break;
                case TO_BOTTOM:
                    yOffSet = 0;
                    break;
                case CENTER_VERTICAL:
                    yOffSet = (-winHeight - anchorHeight) / 2;
                    break;
                default:
                    break;
            }
            return new int[]{xOffSet, yOffSet};
        }
    }
}
