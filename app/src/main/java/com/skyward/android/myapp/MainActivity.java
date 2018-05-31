package com.skyward.android.myapp;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.skyward.android.myapp.bean.LoginBean;
import com.skyward.android.myapp.filter.FilterActivity;
import com.skyward.android.myapp.mvp.presenter.LoginPresenter;
import com.skyward.android.myapp.mvp.view.LoginView;
import com.skyward.android.myapp.webviewjs.WebViewJsActivity;
import com.skyward.android.sdk.custom.toast.MyToast;
import com.skyward.android.sdk.network.update.app.UpdateApp;
import com.skyward.android.sdk.permission.OnPermissionListener;
import com.skyward.android.sdk.permission.RequestPermission;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoginView {

    private LoginPresenter mLoginPresenter;
    private String url = "http://tym.ty-2009.com/File/App/app.apk";

    ArrayList<String> updateLogs = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RequestPermission.request(MainActivity.this, new OnPermissionListener() {
            @Override
            public void onPermissionSuccess() {

            }

            @Override
            public void onPermissionFailure(String[] permission) {

            }
        },Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE);

        updateLogs.add("1.修复已知bug");
        updateLogs.add("2.新增会员体系");
        updateLogs.add("3.提升用户体验，优化流畅度");

        mLoginPresenter = new LoginPresenter(this);
        findViewById(R.id.login_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginPresenter.login("admin", "123");
            }
        });

        findViewById(R.id.upload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), uploadActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), DownLoadActivity.class));
            }
        });

        findViewById(R.id.update_app).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new UpdateApp.Builder(MainActivity.this)
                        .setAppVersion("v1.1")
                        .setAppSize("5.05M")
                        .setForcedUpdate(false)
                        .setAppUpdateLogs(updateLogs)
                        .setAppDownloadUrl(url)
                        .build();
            }
        });

        findViewById(R.id.permission).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RequestPermission.request(MainActivity.this, new OnPermissionListener() {
                    @Override
                    public void onPermissionSuccess() {
                        Toast.makeText(MainActivity.this,"取得权限",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionFailure(String[] permission) {
                        Toast.makeText(MainActivity.this,"禁止权限",Toast.LENGTH_SHORT).show();
                    }
                }, Manifest.permission.CALL_PHONE);

            }
        });

        findViewById(R.id.filter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),FilterActivity.class));
            }
        });

        findViewById(R.id.btn_webview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), WebViewJsActivity.class));
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLoginPresenter.unbindComponent();
    }

    @Override
    public void getSuccessLoginData(LoginBean loginBean) {

        MyToast.getInstance().info("登陆成功!");
    }

    @Override
    public void loginStatus(boolean status, String msg) {

    }

    @Override
    public void getPower(LoginBean.DataBean bean) {

    }

    @Override
    public String loginAccount() {
        return null;
    }

    @Override
    public String loginPassword() {
        return null;
    }

    @Override
    public void loginButtonEnable(boolean enable) {

    }

    @Override
    public void pageStatusManager(int status) {

    }
}
