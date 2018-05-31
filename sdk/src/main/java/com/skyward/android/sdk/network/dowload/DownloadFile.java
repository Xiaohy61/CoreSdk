package com.skyward.android.sdk.network.dowload;

import android.os.Environment;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: skyward
 * date: 2018/5/15
 * desc:
 */
public class DownloadFile {

    private static DownloadFile instance = null;

    public static final String DOWNLOAD_PATCH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/11AAAA/";
    //文件下载任务索引，String为url,用来唯一区别并操作下载的文件
    private Map<String, DownloadTask> mDownloadTasks;

    private static class Holder {
        private static final DownloadFile INSTANCE = new DownloadFile();
    }

    public static DownloadFile getInstance() {

        return Holder.INSTANCE;
    }

    private DownloadFile() {
        mDownloadTasks = new HashMap<>();
    }


    public void download(String url) {

        if (mDownloadTasks.containsKey(url)) {
            if (!mDownloadTasks.get(url).isDownloading()) {
                mDownloadTasks.get(url).startDownload();
            }
        }
    }

    public void pause(String url) {

        if (mDownloadTasks.containsKey(url)) {
            if (mDownloadTasks.get(url).isDownloading()) {
                mDownloadTasks.get(url).pause();
            }
        }
    }

    public void cancel(String url) {
        if (mDownloadTasks.containsKey(url)) {
            mDownloadTasks.get(url).cancel();
        }
    }

    /**
     * 添加下载任务
     */
    public void initDownload(String url, OnDownloadListener l) {
        initDownload(url, null, null, l);
    }

    /**
     * 添加下载任务
     */
    public void initDownload(String url, String filePath, OnDownloadListener l) {
        initDownload(url, filePath, null, l);
    }

    /**
     * 添加下载任务
     */
    public void initDownload(String url, String filePath, String fileName, OnDownloadListener l) {
        //没有指定下载目录,使用默认目录

        if (TextUtils.isEmpty(filePath)) {
            filePath = getDefaultDirectory();
        }
        if (TextUtils.isEmpty(fileName)) {
            fileName = getFileName(url);
        }
        mDownloadTasks.put(url, new DownloadTask(new DownloadFileInfo(fileName, url, filePath), l));
    }

    /**
     * 默认下载目录
     *
     * @return
     */
    private String getDefaultDirectory() {

        return DOWNLOAD_PATCH;
    }

    // 获取下载文件的名称
    private String getFileName(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }


}
