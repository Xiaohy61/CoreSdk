package com.skyward.android.sdk.network.dowload;

import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * @author: skyward
 * date: 2018/5/11
 * desc:
 */
public class DownloadHttpUtil {
    private OkHttpClient mOkHttpClient;
    private static DownloadHttpUtil instance;
    private static final long CONNECT_TIMEOUT = 60;
    private final static long READ_TIMEOUT = 60;
    private final static long WRITE_TIMEOUT = 60;

    private static class Holder{
        private static final DownloadHttpUtil instance = new DownloadHttpUtil();
    }


    public static DownloadHttpUtil getInstance() {
        return Holder.instance;
    }

    private DownloadHttpUtil() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT,TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT,TimeUnit.SECONDS);
        mOkHttpClient = builder.build();
    }


    /**
     * 续点下载
     */
    public void downloadFileByRange(String url, long startIndex, long endIndex, Callback callback){
        Request request = new Request.Builder().header("RANGE", "bytes=" + startIndex + "-" + endIndex)
                .url(url)
                .build();
        postRequest(request,callback);
    }

    public void getContentLength(String url,Callback callback){
        Request request = new Request.Builder()
                .url(url)
                .build();
        postRequest(request,callback);
    }

    private void postRequest(Request request, Callback callback) {
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(callback);
    }



}
