package com.skyward.android.myapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.skyward.android.myapp.adapter.DownloadFileAdapter;
import com.skyward.android.myapp.adapter.FileInfo;
import com.skyward.android.sdk.custom.view.HorizontalProgressbar;

import java.util.ArrayList;
import java.util.List;

public class DownLoadActivity extends AppCompatActivity {

    private String url = "http://tym.ty-2009.com/File/App/app.apk";
    private String url2="http://dldir1.qq.com/weixin/android/weixin657android1040.apk";
    Button btn_start;
    Button btn_stop;

    private HorizontalProgressbar progressbar;
    private HorizontalProgressbar mProgressbar2;

    private RecyclerView mRecyclerView;

    private DownloadFileAdapter mDownloadFileAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_load);
        btn_start = findViewById(R.id.btn_start);
        btn_stop = findViewById(R.id.btn_stop);
        progressbar = findViewById(R.id.progress);


        mRecyclerView = findViewById(R.id.recycleview);

        List<FileInfo> mList = new ArrayList<>();
        mList.add(new FileInfo(url,10));
        mList.add(new FileInfo(url2,20));


        mDownloadFileAdapter = new DownloadFileAdapter(R.layout.item_download_flie,mList,DownLoadActivity.this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mDownloadFileAdapter);


//        DownloadFile.getInstance(DownLoadActivity.this).initDownload(new OnDownloadListener() {
//            @Override
//            public void onSuccess(File file) {
//                MyToast.getInstance().success("下载完成");
//            }
//
//            @Override
//            public void progress(float progress) {
//                progressbar.setProgress((int) (progress * 100));
//
//            }
//
//            @Override
//            public void failure(Throwable e) {
//                MyToast.getInstance().error("下载错误"+e.getMessage());
//            }
//
//            @Override
//            public void onPause() {
//                MyToast.getInstance().info("下载暂停");
//            }
//
//            @Override
//            public void onCancel() {
//                MyToast.getInstance().error("下载取消");
//
//            }
//        });


        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  DownloadFile.getInstance(DownLoadActivity.this).start(url);
            }
        });

        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //   DownloadFile.getInstance(DownLoadActivity.this).pause(url);
            }
        });

        findViewById(R.id.again).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // DownloadFile.getInstance(DownLoadActivity.this).cancel(url);
            }
        });


//        DownloadFile.getInstance(DownLoadActivity.this).initDownload(new OnDownloadListener() {
//            @Override
//            public void onSuccess(File file) {
//                MyToast.getInstance().success("下载完成2");
//            }
//
//            @Override
//            public void progress(float progress) {
//                mProgressbar2.setProgress((int) (progress * 100));
//
//            }
//
//            @Override
//            public void failure(Throwable e) {
//                MyToast.getInstance().error("下载错误2"+e.getMessage());
//            }
//
//            @Override
//            public void onPause() {
//                MyToast.getInstance().info("下载暂停2");
//            }
//
//            @Override
//            public void onCancel() {
//                MyToast.getInstance().error("下载取消2");
//
//            }
//        });


    }


}
