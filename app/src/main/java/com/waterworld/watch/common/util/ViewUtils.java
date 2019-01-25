package com.waterworld.watch.common.util;

import android.app.Activity;
import android.content.Context;
import android.view.WindowManager;

/**
 * 编写者：Created by SunnyTang
 * 时间：2019/1/9 17:09
 * 主要作用：
 */
public class ViewUtils {

    public static void setBackgroundAlpha(Activity activity, float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        activity.getWindow().setAttributes(lp);
    }

}
