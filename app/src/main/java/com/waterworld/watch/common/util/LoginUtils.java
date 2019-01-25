package com.waterworld.watch.common.util;

import android.content.Context;
import android.text.TextUtils;

import com.waterworld.watch.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by nhuan
 * Time:2018/12/20.
 * 登录注册验证
 */

public class LoginUtils {

    //手机号码是否匹配
    public static boolean isPhone(Context context, String phone) {
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        if (phone.length() != 11) {
            ToastUtils.showShort(context,context.getString(R.string.phone_number_format));
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            boolean isMatch = m.matches();
            if (!isMatch) {
                ToastUtils.showShort(context,context.getString(R.string.phone_number_sure));
            }
            return isMatch;
        }
    }

    //密码是否匹配
    public static boolean isPassword(Context context,String password){
        if(TextUtils.isEmpty(password)){
            ToastUtils.showShort(context,context.getString(R.string.password_limit));
            return false;
        }
        if(password.length() < 6){
            ToastUtils.showShort(context,context.getString(R.string.password_limit));
            return false;
        }
        return true;
    }

    //重复密码是否匹配
    public static boolean isSamePassword(Context context,String password1,String password2){
        if(TextUtils.isEmpty(password1)){
            ToastUtils.showShort(context,context.getString(R.string.password_limit));
            return false;
        }
        if(!password1.equals(password2)){
            ToastUtils.showShort(context,context.getString(R.string.two_password_no_same));
            return false;
        }
        if(password1.length() < 6){
            ToastUtils.showShort(context,context.getString(R.string.password_limit));
            return false;
        }
        return true;
    }

    //验证码是否匹配
    public static boolean isCode(Context context,String code){
        if(TextUtils.isEmpty(code)){
            ToastUtils.showShort(context,context.getString(R.string.verification_no_empty));
            return false;
        }
        return true;
    }
}
