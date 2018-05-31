package com.skyward.android.sdk.network.update.app;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;

import java.util.ArrayList;

/**
 * @author: skyward
 * date: 2018/4/26
 * desc:
 */
public class UpdateApp {

    private Context mContext;
    private String appVersion;
    private String appSize;
    private String appDownloadUrl;
    private ArrayList<String> appUpdateLogs;
    private String appUpdateButtonText;
    private boolean isForcedUpdate;
    private @DrawableRes int topStyleImage;
    private @DrawableRes int updateButtonBackground;



    public  UpdateApp(Builder builder) {
        this.mContext = builder.mContext;
        this.appVersion = builder.appVersion;
        this.appSize = builder.appSize;
        this.appDownloadUrl = builder.appDownloadUrl;
        this.appUpdateLogs = builder.appUpdateLogs;
        this.appUpdateButtonText = builder.appUpdateButtonText;
        this.topStyleImage = builder.topStyleImage;
        this.isForcedUpdate = builder.isForcedUpdate;
        this.updateButtonBackground = builder.updateButtonBackground;
        openUpdatePanel();
    }





    public static class Builder {
        private Context mContext;
        private String appVersion;
        private String appSize;
        private String appDownloadUrl;
        private ArrayList<String> appUpdateLogs = new ArrayList<>();
        private String appUpdateButtonText;
        private @DrawableRes int topStyleImage;
        private @DrawableRes int updateButtonBackground;
        private boolean isForcedUpdate = false;

        public Builder(Context context) {
            mContext = context;
        }

        public Builder setAppVersion(String appVersion) {
            this.appVersion = appVersion;
            return this;
        }

        public Builder setAppSize(String appSize) {
            this.appSize = appSize;
            return this;
        }

        public Builder setAppDownloadUrl(String appDownloadUrl) {
            this.appDownloadUrl = appDownloadUrl;
            return this;
        }

        public Builder setAppUpdateLogs(ArrayList<String> appUpdateLogs) {
            this.appUpdateLogs = appUpdateLogs;
            return this;
        }

        public Builder setUpdateButtonText(String appUpdateButtonText) {
            this.appUpdateButtonText = appUpdateButtonText;
            return this;
        }

        public Builder setTopStyleImage(int topStyleImage) {
            this.topStyleImage = topStyleImage;
            return this;
        }

        public Builder setForcedUpdate(boolean forcedUpdate) {
            isForcedUpdate = forcedUpdate;
            return this;
        }

        public Builder setUpdateButtonBackground(int updateButtonBackground) {
            this.updateButtonBackground = updateButtonBackground;
            return this;
        }

        public UpdateApp build() {
            return new UpdateApp(this);
        }
    }


    private void openUpdatePanel() {


        if(TextUtils.isEmpty(appDownloadUrl)){
            throw new NullPointerException("请输入有效的app下载地址");
        }

        UpdateAppDialogFragment dialogFragment = UpdateAppDialogFragment.newInstance(appDownloadUrl,appVersion, appSize, appUpdateButtonText, appUpdateLogs,topStyleImage,isForcedUpdate,updateButtonBackground);
        dialogFragment.setContext(mContext);
        dialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), "dialog");

    }


}
