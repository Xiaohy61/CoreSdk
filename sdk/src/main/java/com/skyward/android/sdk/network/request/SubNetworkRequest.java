package com.skyward.android.sdk.network.request;

import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * date: 2018/4/21
 * desc: 网络请求
 * @author skyward
 */
public class SubNetworkRequest {


    private String mBaseUrl;
    private OkHttpClient mClient;

    private Retrofit mRetrofit;

    private static class Holder {
        private static final SubNetworkRequest INSTANCE = new SubNetworkRequest();
    }

    public static SubNetworkRequest getInstance() {
        return Holder.INSTANCE;
    }

    private SubNetworkRequest() {

    }

    /**
     * 初始化
     *
     * @param baseUlr 公共部分网络请求网址
     * @param client  自定义okhttp 配置
     */
    public void init(String baseUlr, OkHttpClient client) {
        this.mBaseUrl = baseUlr;
        this.mClient = client;
    }

    public Retrofit request() {
        if (mRetrofit == null) {
            synchronized (SubNetworkRequest.class) {
                if (mRetrofit == null) {
                    mRetrofit = new Retrofit.Builder()
                            .baseUrl(mBaseUrl)
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                                    .setLenient()
                                    .create()))
                            .client(mClient)
                            .build();
                }
            }
        }
        return mRetrofit;
    }
}
