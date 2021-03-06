package com.waterworld.watch.common.util;

import android.content.ContentResolver;
import android.content.Context;
import android.util.Log;

import java.util.Calendar;

/**
 * 编写者：Created by SunnyTang
 * 时间：2018/5/17 16:07
 * 主要作用：时间工具类
 */
public class TimeUtils {

    private Context context;
    private static Calendar calendar;

    /**
     * 获取系统日期
     */
    //年
    public static int getYear() {
        calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    //月
    public static int getMonth() {
        calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) + 1;
    }

    //日
    public static int getDay() {
        calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 判断是AM制还是PM制
     */
    public void AMorPM() {
        ContentResolver cv = context.getContentResolver();
        String strTimeFormat = android.provider.Settings.System.getString(cv, android.provider.Settings.System.TIME_12_24);
        if (strTimeFormat != null && strTimeFormat.equals("24")) {
            Log.i("Debug", "24小时制");
        } else if (strTimeFormat != null && strTimeFormat.equals("12")) {
            Log.i("Debug", "12小时制");
        }
    }

    /**
     * 获取当前系统时间
     */
    //时(24小时制)
    public static int getCurrentHour24() {
        calendar = Calendar.getInstance();
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    //时(12小时制)
    public static int getCurrentHour12() {
        calendar = Calendar.getInstance();
        return calendar.get(Calendar.HOUR);
    }

    //分
    public static int getCurrentMinute() {
        calendar = Calendar.getInstance();
        return calendar.get(Calendar.MINUTE);
    }

    //秒
    public static int getCurrentSecond() {
        calendar = Calendar.getInstance();
        return calendar.get(Calendar.SECOND);
    }

    /**
     * 判断是否闰年
     *
     * @param year 年份
     * @return 关于年份四种不同的区别
     */
    public static int isLeapYear(int year) {
        if (year % 100 == 0) {//能被100整除的就是世纪年
            if (year % 400 == 0) {
                return 0x01;//世纪年 闰年
            } else {
                return 0x02;//世纪年 平年
            }
        } else {
            if (year % 4 == 0) {
                return 0x03;//普通年 闰年
            } else {
                return 0x04;//普通年 平年
            }
        }
    }


    /**
     * 返回字符串区间
     *
     * @param start 从...开始
     * @param end   从...结束
     * @return 字符串数组
     */
    public static String[] returnStringArray(int start, int end) {
        int temp = end - start + 1;
        String[] data = new String[temp];
        for (int i = start; i <= end; i++) {
            if (i >= 0 && i < 10) {
                data[i - start] = "0" + String.valueOf(i);
            } else {
                data[i - start] = String.valueOf(i);
            }
        }
        return data;
    }

    public static String arrayToString(String[] array) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i=0 ; i<array.length ; i++){
            if(i != array.length-1) {
                stringBuilder.append(array[i]);
                stringBuilder.append(",");
            }else {
                stringBuilder.append(array[i]);
            }
        }
        return stringBuilder.toString();
    }
}
