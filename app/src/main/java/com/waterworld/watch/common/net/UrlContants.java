package com.waterworld.watch.common.net;

/**
 * Created by nhuan
 * Time:2018/12/12.
 * HTTP request params
 */

public class UrlContants {
    //服务器地址
    public final static String BASE_URL = "http://47.107.145.228:8071/";
    //测试服务器地址
    //public final static String BASE_URL = "http://192.168.20.213:8089/";
    //获取验证码
    public final static String GET_AUTH_CODE = "getAuthCode";
    //注册
    public final static String REGISTER = "register";
    //登录
    public final static String LOGIN = "login";
    //忘记密码
    public final static String FORGET_PASSWORD = "forgetPwd";
    //修改密码
    public final static String MODIFY_PASSWORD = "user/modifyPwd";
    //修改手机号
    public final static String MODIFY_PHONE = "user/modifyPhone";
    //头像上传
    public final static String UPLOAD_IMG = "user/uploadImg";
    //修改用户信息
    public final static String MODIFY_USERINFO = "user/modifyUserInfo";
    //获取用户信息
    public final static String GET_CURRENT_USER = "user/getCurrentUser";
    //退出系统
    public final static String LOGOUT = "logout";
    //绑定手表
    public final static String BIND_WATCH = "user/bindWatch";
    //保存或更新手表用户
    public final static String SAVE_WATCH_USERINFO = "user/saveWatchUserInfo";
    //切换手表
    public final static String SWITCH_WATCH = "user/switchWatch";
    //获取当前手表用户信息
    public final static String GET_CURRENT_WATCH_USERIFO = "user/getCurrentWatchUserInfo";
    //获取用户绑定手表列表
    public final static String LIST_BIND_WATCH = "user/listBindWatch";
    //获取绑定该手表的用户列表
    public final static String LIST_BIND_WATCHUSER= "user/listBindWatchUser";
    //移交管理权限
    public final static String TRANSFER_AUTHORITY= "user/transferAuthority";
    //解除绑定（自身或其他用户）
    public final static String UNBIND_WATCH= "user/unbundlingbindWatch";
    //恢复出厂设置
    public final static String RESUME_FACTORY= "user/resumeFactory";
    //编辑主页功能
    public final static String EDIT_HOME_PAGER_FUNCTION = "watch/editHomePageFunction";
    //获取所有功能
    public final static String GET_ALL_FUNCATION = "watch/getAllFunction";
    //获取未应用的功能
    public final static String GET_NOT_USE_FUNCATION = "watch/getNotUseFunction";
}
