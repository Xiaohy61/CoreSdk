package com.skyward.android.myapp.mvp.view;

import com.skyward.android.myapp.bean.LoginBean;
import com.skyward.android.sdk.base.BaseView;

/**
 * @author: skyward
 * date: 2018/4/21
 * desc:
 */
public interface LoginView extends BaseView{
    void getSuccessLoginData(LoginBean loginBean);
    void loginStatus(boolean status,String msg);
    void getPower(LoginBean.DataBean bean);
    String loginAccount();
    String loginPassword();
    void loginButtonEnable(boolean enable);
}
