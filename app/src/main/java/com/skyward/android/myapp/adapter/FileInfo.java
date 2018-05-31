package com.skyward.android.myapp.adapter;

/**
 * @author: skyward
 * date: 2018/5/15
 * desc:
 */
public class FileInfo {

    private String url;
    private int progress;

    public FileInfo(String url) {
        this.url = url;
    }

    public FileInfo(int progress) {
        this.progress = progress;
    }

    public FileInfo(String url, int progress) {
        this.url = url;
        this.progress = progress;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
