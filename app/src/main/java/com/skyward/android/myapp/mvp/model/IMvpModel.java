package com.skyward.android.myapp.mvp.model;

import com.skyward.android.myapp.bean.LoginBean;
import com.skyward.android.myapp.mvp.ModelDataCallBack;

/**
 * @author: skyward
 * date: 2018/4/21
 * desc:
 */
public interface IMvpModel {

    /**
     * 登陆
     * @param account 用户名
     * @param pwd 密码
     * @param dataCallBack 数据回调
     */
    void login(String account, String pwd, final ModelDataCallBack<LoginBean> dataCallBack);

    /**
     * 注册
     * @param account 账号
     * @param pwd 密码
     * @param phone 手机
     * @param sex 性别
     */
    void register(String account,String pwd,String phone,String sex);
}
