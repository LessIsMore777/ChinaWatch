package com.waterworld.watch.common.util;

import android.content.ContentResolver;
import android.content.Context;
import android.util.Log;

import java.util.Calendar;

/**
 * 编写者：Created by SunnyTang
 * 时间：2018/5/17 16:07
 * 主要作用：系统时间工具类
 */
public class GetCurrentTime {

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
     * 获取系统时间
     */
    //时(24小时制)
    public static int getHourOfDay() {
        calendar = Calendar.getInstance();
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    //时(12小时制)
    public static int getHour() {
        calendar = Calendar.getInstance();
        return calendar.get(Calendar.HOUR);
    }

    //分
    public static int getMinute() {
        calendar = Calendar.getInstance();
        return calendar.get(Calendar.MINUTE);
    }

    //秒
    public static int getSecond() {
        calendar = Calendar.getInstance();
        return calendar.get(Calendar.SECOND);
    }

    /**
     * 判断是否闰年
     * @param year 年份
     * @return 关于年份四种不同的区别
     */
    public static int aaa(int year) {
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
     * @param start    从...开始
     * @param end      从...结束
     * @return 字符串数组
     */
    public static String[] setYearInterval(int start, int end) {
        int qwe = end - start + 1;
        String[] data = new String[qwe];
        for (int i = start; i <= end; i++) {
            data[i - start] = String.valueOf(i);
        }
        return data;
    }
}
