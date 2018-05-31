package com.skyward.android.sdk.utils;

import android.os.Environment;

import java.io.File;
import java.io.IOException;

/**
 * @author: skyward
 * date: 2018/4/25
 * desc:
 */
public class CreateSaveDirectory {

    public static String create(String dirName){
        File file = new File(Environment.getExternalStorageDirectory(),dirName);
        if(!file.mkdirs()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file.getAbsolutePath();
    }
}
