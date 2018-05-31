package com.skyward.android.sdk.network.dowload;

/**
 * @author: skyward
 * date: 2018/5/14
 * desc:
 */
public class DownloadFileInfo {
    private String fileName;
    private String url;
    private String filePath;

    private int id;

    public DownloadFileInfo(int id) {
        this.id = id;
    }

    public DownloadFileInfo(String url) {
        this.url = url;
    }

    public DownloadFileInfo(String url, String filePath) {
        this.url = url;
        this.filePath = filePath;
    }

    public DownloadFileInfo(String fileName, String url, String filePath) {
        this.fileName = fileName;
        this.url = url;
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "DownloadFileInfo{" +
                "fileName='" + fileName + '\'' +
                ", url='" + url + '\'' +
                ", filePath='" + filePath + '\'' +
                ", id=" + id +
                '}';
    }
}
