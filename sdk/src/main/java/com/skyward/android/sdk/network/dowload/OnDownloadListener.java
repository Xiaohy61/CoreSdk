package com.skyward.android.sdk.network.dowload;

import java.io.File;

/**
 * @author: skyward
 * date: 2018/4/25
 * desc:
 */
public interface OnDownloadListener {
    /**
     * 下载成功
     * @param file 下载路径
     */
    void onSuccess(File file);

    /**
     * 下载进度
     * @param progress 进度
     */
    void progress(float progress);

    /**
     * 下载失败
     * @param e Throwable
     */
    void failure(Throwable e);

    /**
     * 下载停止
     */
    void onPause();

    /**
     * 下载取消
     */
    void onCancel();

}
