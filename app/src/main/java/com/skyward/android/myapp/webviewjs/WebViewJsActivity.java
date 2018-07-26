package com.skyward.android.myapp.webviewjs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.skyward.android.myapp.R;
import com.skyward.android.sdk.jsBridge.JsInterface;

/**
 * @author skyward
 */
public class WebViewJsActivity extends AppCompatActivity {

    private WebView mWebView;
    private TextView mtvResult;
    private EditText mEditText;
    private Button btn_input;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_js);

        mWebView = findViewById(R.id.webview);

        mtvResult = findViewById(R.id.tv_result);

        mEditText = findViewById(R.id.edittext);

        btn_input = findViewById(R.id.btn_input);


        //允许webview加载js代码
        mWebView.getSettings().setJavaScriptEnabled(true);
        //给webview添加js接口
        mWebView.addJavascriptInterface(new JsInterface(new com.skyward.android.sdk.jsBridge.JsBridge() {
            @Override
            public void webCallBackValue(String value) {

                mtvResult.setText(value);

            }
        }),"skyward");


        mWebView.loadUrl("file:///android_asset/index.html");

        btn_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = mEditText.getText().toString();
                mWebView.loadUrl("javaScript:if(window.remote){window.remote('"+text+"')}");
            }
        });

    }


}
