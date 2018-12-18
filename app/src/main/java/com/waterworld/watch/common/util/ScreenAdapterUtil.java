package com.waterworld.watch.common.util;

import android.content.Context;

/**
 * 编写者：Created by SunnyTang
 * 时间：2018/11/30 16:40
 * 主要作用：屏幕工具类
 */
public class ScreenAdapterUtil {

    /**
     * 获取设备的宽度有多少个像素
     */
    public static int getWidthPx(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取设备的高度有多少个像素
     */
    public static int getHeightPx(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获取设备所对应的像素密度标准值
     */
    public static int getDeviceDensityDpi(Context context) {
        return context.getResources().getDisplayMetrics().densityDpi;
    }

    /**
     * 获取设备的像素密度基准比例
     */
    public static float getDeviceDensity(Context context) {
        return (int) context.getResources().getDisplayMetrics().density;
    }

    /**
     * 获取设备的缩放密度因子，主要用于字体
     */
    public static float getDeviceScaledDensity(Context context) {
        return (int) context.getResources().getDisplayMetrics().scaledDensity;
    }

    /**
     * 设备X轴的DPI值
     */
    public static float getDeviceXDpi(Context context) {
        return (int) context.getResources().getDisplayMetrics().xdpi;
    }

    /**
     * 获取设备Y轴的DPI值
     */
    public static int getDeviceYDpi(Context context) {
        return (int) context.getResources().getDisplayMetrics().ydpi;
    }

    /**
     * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}
