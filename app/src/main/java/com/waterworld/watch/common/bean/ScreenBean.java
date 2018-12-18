package com.waterworld.watch.common.bean;

/**
 * 编写者：Created by SunnyTang
 * 时间：2018/11/30 16:51
 * 主要作用：屏幕参数实体类
 */
public class ScreenBean {

    private int WidthPx;//宽度的像素
    private int HeightPx;//高度的像素
    private int DeviceDensityDpi;//设备所对应的像素密度标准值
    private float DeviceDensity;//设备的像素密度基准比例
    private float DeviceXDpi;//设备X轴的DPI值
    private float DeviceYDpi;//设备Y轴的DPI值
    private float DeviceScaledDensity;//设备的缩放密度，主要用于字体

    public ScreenBean(){

    }

    public ScreenBean(int widthPx, int heightPx, int deviceDensityDpi, float deviceDensity, float deviceXDpi, float deviceYDpi, float deviceScaledDensity) {
        WidthPx = widthPx;
        HeightPx = heightPx;
        DeviceDensityDpi = deviceDensityDpi;
        DeviceDensity = deviceDensity;
        DeviceXDpi = deviceXDpi;
        DeviceYDpi = deviceYDpi;
        DeviceScaledDensity = deviceScaledDensity;
    }

    public int getWidthPx() {
        return WidthPx;
    }

    public void setWidthPx(int widthPx) {
        WidthPx = widthPx;
    }

    public int getHeightPx() {
        return HeightPx;
    }

    public void setHeightPx(int heightPx) {
        HeightPx = heightPx;
    }

    public int getDeviceDensityDpi() {
        return DeviceDensityDpi;
    }

    public void setDeviceDensityDpi(int deviceDensityDpi) {
        DeviceDensityDpi = deviceDensityDpi;
    }

    public float getDeviceDensity() {
        return DeviceDensity;
    }

    public void setDeviceDensity(float deviceDensity) {
        DeviceDensity = deviceDensity;
    }

    public float getDeviceXDpi() {
        return DeviceXDpi;
    }

    public void setDeviceXDpi(float deviceXDpi) {
        DeviceXDpi = deviceXDpi;
    }

    public float getDeviceYDpi() {
        return DeviceYDpi;
    }

    public void setDeviceYDpi(float deviceYDpi) {
        DeviceYDpi = deviceYDpi;
    }

    public float getDeviceScaledDensity() {
        return DeviceScaledDensity;
    }

    public void setDeviceScaledDensity(float deviceScaledDensity) {
        DeviceScaledDensity = deviceScaledDensity;
    }
}
