package com.waterworld.watch.mine.interfaces;

import com.waterworld.watch.common.net.BaseObserverListener;
import com.waterworld.watch.common.net.BaseResultBean;

import java.io.File;

/**
 * Created by nhuan
 * Time:2018/12/19.
 */

public interface IMineManager {
    //修改密码
    void modifyPwd(String newPwd, String oldPwd ,BaseObserverListener baseObserverListener);

    //上传文件
    void uploadImg(File file,BaseObserverListener baseObserverListener);

    //修改手机号
    void modifyPhone(String newPhone,String authCode,String pwd,ModifyAccountResultListener<BaseResultBean> modifyAccountResultListener);

    //修改用户信息
    void modifyUserInfo(String head,String name,String relationWithBaby,BaseObserverListener baseObserverListener);
}
