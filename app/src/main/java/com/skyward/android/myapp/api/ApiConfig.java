package com.skyward.android.myapp.api;

import com.skyward.android.myapp.bean.LoginBean;
import com.skyward.android.myapp.bean.UploadImageResultBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * @author: skyward
 * date: 2018/4/21
 * desc:
 */
public interface ApiConfig {

    /**
     * 登录
     *
     * @param account 账号
     * @param pwd     密码
     * @param t       t
     * @param brandID 品牌id
     * @return 登陆对象
     */
    @GET("sys/Login")
    Observable<LoginBean> getLogin(
            @Query("account") String account,
            @Query("pwd") String pwd,
            @Query("t") int t,
            @Query("pid") int brandID);


    @Multipart
    @POST("User/UpLoadFile")
    Observable<UploadImageResultBean> uploadMulti(
            @Query("sid") String sid,
            @Part List<MultipartBody.Part> parts
    );

    @Multipart
    @POST("User/UpLoadFile")
    Observable<UploadImageResultBean> uploadSigle(
            @Query("sid") String sid,
            @Part MultipartBody.Part imgs);

}
