package com.waterworld.watch.common.util;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;

import com.mylhyl.circledialog.CircleDialog;
import com.mylhyl.circledialog.callback.ConfigButton;
import com.mylhyl.circledialog.callback.ConfigDialog;
import com.mylhyl.circledialog.callback.ConfigText;
import com.mylhyl.circledialog.callback.ConfigTitle;
import com.mylhyl.circledialog.params.ButtonParams;
import com.mylhyl.circledialog.params.DialogParams;
import com.mylhyl.circledialog.params.TextParams;
import com.mylhyl.circledialog.params.TitleParams;
import com.waterworld.watch.R;

/**
 * Created by nhuan
 * Time:2018/12/25.
 */

public class DialogUtils {
    public static void LoginInvalidShowDialog(Context context, String title, String content, View.OnClickListener onClickListener){
        new CircleDialog.Builder()
                .setCanceledOnTouchOutside(false)
                .setCancelable(false)
                .setWidth(0.75f)
                .setRadius(15)
                .setItemsManualClose(true)
                .configDialog(new ConfigDialog() {
                    @Override
                    public void onConfig(DialogParams params) {
                        params.backgroundColorPress = Color.parseColor("#E4E6E6");
                        params.animStyle = R.style.CircleDialogWindowAnim;
                        params.gravity = Gravity.CENTER;
                    }
                })
                .setTitle(title)
                .configTitle(new ConfigTitle() {
                    @Override
                    public void onConfig(TitleParams params) {
                        params.textColor = Color.BLACK;
                    }
                })
                .setText(content)
                .configText(new ConfigText() {
                    @Override
                    public void onConfig(TextParams params) {
                        params.padding = new int[]{100, 0, 100, 50};
                        params.textColor = Color.parseColor("#2F2F2F");
                    }
                }).setPositive(context.getString(R.string.confirm), onClickListener)
                .configPositive(new ConfigButton() {
                    @Override
                    public void onConfig(ButtonParams params) {
                        params.backgroundColorPress = Color.parseColor("#f1f1f1");
                        params.textColor = Color.parseColor("#2F2F2F");
                    }
                }).show(((AppCompatActivity)AppManager.currentActivity()).getSupportFragmentManager());
    }
}
