package com.skyward.android.sdk.custom.toast;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.skyward.android.sdk.R;

/**
 *
 * @author skyward
 * @date 2018/4/22
 * email:
 * desc:自定义toast
 */
public class MyToast {

    private Context mContext;
    private Toast mToast;


    private static class Holder{
        private static final MyToast INSTANCE = new MyToast();
    }


    public static MyToast getInstance() {

        return Holder.INSTANCE;
    }

    /**
     * 初始化
     * @param context 上下文
     */
    public void initToast(Context context) {
        mContext = context.getApplicationContext();
        mToast = new Toast(mContext);
        mToast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 200);
        mToast.setDuration(Toast.LENGTH_SHORT);


    }

    /**
     * 默认模式
     * @param text toast内容
     */
    public void normal(String text) {

        try {
            View mView = LayoutInflater.from(mContext).inflate(R.layout.toast_normal, null);
            TextView mNormalToastText = mView.findViewById(R.id.toast_normal);
            mNormalToastText.setText(text);
            mToast.setView(mView);
            mToast.show();
        } catch (Exception e) {
            throw new ExceptionInInitializerError("请初始化MyToast");
        }

    }

    /**
     * 成功模式
     * @param text toast内容
     */
    public void success(String text){
        try {
            View mView = LayoutInflater.from(mContext).inflate(R.layout.toast_success, null);
            TextView mNormalToastText = mView.findViewById(R.id.toast_success);
            mNormalToastText.setText(text);
            mToast.setView(mView);
            mToast.show();
        } catch (Exception e) {
            throw new ExceptionInInitializerError("请初始化MyToast");
        }
    }

    /**
     * 错误模式
     * @param text toast内容
     */
    public void error(String text){
        try {
            View mView = LayoutInflater.from(mContext).inflate(R.layout.toast_error, null);
            TextView mNormalToastText = mView.findViewById(R.id.toast_error);
            mNormalToastText.setText(text);
            mToast.setView(mView);
            mToast.show();
        } catch (Exception e) {
            throw new ExceptionInInitializerError("请初始化MyToast");
        }
    }

    /**
     * 警告模式
     * @param text toast内容
     */
    public void warn(String text){
        try {
            View mView = LayoutInflater.from(mContext).inflate(R.layout.toast_warn, null);
            TextView mNormalToastText = mView.findViewById(R.id.toast_warn);
            mNormalToastText.setText(text);
            mToast.setView(mView);
            mToast.show();
        } catch (Exception e) {
            throw new ExceptionInInitializerError("请初始化MyToast");
        }
    }

    /**
     * 信息模式
     * @param text toast内容
     */
    public void info(String text){
        try {
            View mView = LayoutInflater.from(mContext).inflate(R.layout.toast_info, null);
            TextView mNormalToastText = mView.findViewById(R.id.toast_info);
            mNormalToastText.setText(text);
            mToast.setView(mView);
            mToast.show();
        } catch (Exception e) {
            throw new ExceptionInInitializerError("请初始化MyToast");
        }
    }

}
