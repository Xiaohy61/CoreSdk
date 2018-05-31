package com.skyward.android.sdk.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: skyward
 * date: 2018/4/24
 * desc:图片压缩
 */
public class CompressImage {

    private static final int QUALITY_100=100;
    private static final int QUALITY_50 =50;
    private static final int IMAGE_1024_SIZE = 1024;
    private static final int IMAGE_200_SIZE =200;

    public static File compress(String path) {
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        return compress(bitmap);
    }

    public static File compress(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,QUALITY_100,outputStream);
        // 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
        if(outputStream.toByteArray().length / IMAGE_1024_SIZE > IMAGE_1024_SIZE){
            outputStream.reset();
            bitmap.compress(Bitmap.CompressFormat.JPEG,QUALITY_50,outputStream);
        }

        BitmapFactory.Options newOptions = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设为true了，只拿数据，不拿具体size
        newOptions.inJustDecodeBounds = true;
        int height = newOptions.outHeight;
        int width = newOptions.outWidth;
        int inSampleSize = 1;
        int reqHeight=800;
        int reqWidth=480;

        if (width > height && width > reqWidth)
        {// 如果宽度大的话根据宽度固定大小缩放
            inSampleSize = (int) (newOptions.outWidth / reqWidth);
        }
        else if (width < height && height > reqHeight)
        {// 如果高度高的话根据宽度固定大小缩放
            inSampleSize = (int) (newOptions.outHeight / reqHeight);
        }
        if (inSampleSize <= 0) {
            inSampleSize = 1;
        }
        // 设置缩放比例
        newOptions.inSampleSize = inSampleSize;

        newOptions.inJustDecodeBounds = false;

        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        Bitmap  bitmap2 = BitmapFactory.decodeStream(inputStream, null, newOptions);
        return compressImageWithQuality(bitmap2);
    }

    private static File compressImageWithQuality(Bitmap bitmap){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,QUALITY_100,outputStream);
        int options =100;
        while (outputStream.toByteArray().length / IMAGE_1024_SIZE > IMAGE_200_SIZE){
            outputStream.reset();
            options -= 10;
            bitmap.compress(Bitmap.CompressFormat.JPEG,options,outputStream);
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date(System.currentTimeMillis());
        String filename = format.format(date);
        File file = new File(Environment.getExternalStorageDirectory(),filename+".png");

        try {
            FileOutputStream fos = new FileOutputStream(file);
            try {
                fos.write(outputStream.toByteArray());
                fos.flush();
                fos.close();
            } catch (IOException e) {

                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }
        recycleBitmap(bitmap);
        return file;
    }

    public static void recycleBitmap(Bitmap... bitmaps) {
        if (bitmaps==null) {
            return;
        }
        for (Bitmap bm : bitmaps) {
            if (null != bm && !bm.isRecycled()) {
                bm.recycle();
            }
        }
    }
}
