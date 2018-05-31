package com.skyward.android.sdk.network.upload;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.skyward.android.sdk.utils.CompressImage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author: skyward
 * date: 2018/4/24
 * desc: 上传图片处理
 */
public class UploadImage {


    public static List<MultipartBody.Part> parts = new ArrayList<>();

    /**
     * 上传单张图片处理
     * @param strFile 文件路径
     * @return MultipartBody.Part
     */
    public static MultipartBody.Part uploadSingleImage(String strFile){
        //转为bitmap
        Bitmap bitmap = BitmapFactory.decodeFile(strFile);
        //对图片进行压缩
        File file = CompressImage.compress(bitmap);

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        return MultipartBody.Part.createFormData("file", file.getName(), requestBody);
    }

    /**
     * 上传多张图片处理
     * @param strFiles 文件路径数组
     * @return List<MultipartBody.Part>
     */
    public static List<MultipartBody.Part> uploadMultiImages(List<String> strFiles){

        for (int i = 0; i <strFiles.size() ; i++) {
            //转为bitmap
            Bitmap bitmap = BitmapFactory.decodeFile(strFiles.get(i));
            //对图片进行压缩
            File file = CompressImage.compress(bitmap);

            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
            parts.add(body);
        }

        return parts;
    }

}
