package com.skyward.android.sdk.jsBridge;

import android.os.Handler;
import android.os.Message;
import android.webkit.JavascriptInterface;

/**
 * @author: skyward
 * date: 2018/5/18
 * desc:
 */
public class JsInterface {

    private JsBridge mJsBridge;
    private final int WEB_CALLBACK_VALUE = 1;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (mJsBridge == null) {
                return false;
            }
            String value = (String) msg.obj;

            switch (msg.what) {
                case WEB_CALLBACK_VALUE:
                    mJsBridge.webCallBackValue(value);
                    break;
                default:
                    break;
            }

            return true;
        }
    });

    public JsInterface(JsBridge jsBridge) {
        mJsBridge = jsBridge;
    }

    /**
     * 这个方法不在主线程执行,而且只能是public
     *
     * @param value 网页输入的值
     */
    @JavascriptInterface
    public void webCallBackValue(String value) {
        mHandler.obtainMessage(WEB_CALLBACK_VALUE, value).sendToTarget();
    }




}
