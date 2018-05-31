package com.skyward.android.myapp;

import android.app.Application;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.skyward.android.sdk.custom.toast.MyToast;
import com.skyward.android.sdk.network.request.SubNetworkRequest;

import okhttp3.OkHttpClient;

/**
 * @author: skyward
 * date: 2018/4/21
 * desc:
 */
public class App extends Application {

    private String url="http://tyys.ty-2009.com/api/";
    private String newSaleUrl="https://ta.tymapi.com/MallApi/";

    public static App myContext;
    public static RequestOptions optionsFitCenter;
    public static RequestOptions optionsCenterCrop;
    public static RequestOptions optionsWithoutScaleType;

    public static Application getContext(){
        return myContext;
    }

    private  OkHttpClient client = new OkHttpClient.Builder()

            .build();
    @Override
    public void onCreate() {
        super.onCreate();
        myContext = this;
        SubNetworkRequest.getInstance().init(newSaleUrl,client);
        MyToast.getInstance().initToast(myContext);
        initGlideOption();
    }

    private void initGlideOption() {
        optionsFitCenter = new RequestOptions()
                .fitCenter()
                .placeholder(R.drawable.loading_default_icon)
                .error(R.drawable.image_default_loading)
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        optionsCenterCrop = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.loading_default_icon)
                .error(R.drawable.image_default_loading)
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        optionsWithoutScaleType = new RequestOptions()
                .placeholder(R.drawable.loading_default_icon)
                .error(R.drawable.image_default_loading)
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);

    }
}
