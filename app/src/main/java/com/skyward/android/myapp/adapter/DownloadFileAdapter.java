package com.skyward.android.myapp.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.skyward.android.myapp.R;
import com.skyward.android.sdk.base.BaseRecyclerAdapter;
import com.skyward.android.sdk.base.BaseRecyclerViewHolder;
import com.skyward.android.sdk.custom.toast.MyToast;
import com.skyward.android.sdk.custom.view.HorizontalProgressbar;
import com.skyward.android.sdk.network.dowload.DownloadFile;
import com.skyward.android.sdk.network.dowload.OnDownloadListener;

import java.io.File;
import java.util.List;

/**
 * @author: skyward
 * date: 2018/5/14
 * desc:
 */
public class DownloadFileAdapter extends BaseRecyclerAdapter<FileInfo,BaseRecyclerViewHolder> {




    public DownloadFileAdapter(int layoutResId, List<FileInfo> data, Context context) {
        super(layoutResId, data);


    }

    @Override
    protected void bindTheData(BaseRecyclerViewHolder holder, final FileInfo data, int position) {
        Button btn_start2 = holder.findViewById(R.id.btn_start2);
        Button btn_stop2 = holder.findViewById(R.id.btn_stop2);

        final HorizontalProgressbar progress2 = holder.findViewById(R.id.progress2);





        DownloadFile.getInstance().initDownload(data.getUrl(), new OnDownloadListener() {
            @Override
            public void onSuccess(File file) {
                MyToast.getInstance().success("下载完成");
            }

            @Override
            public void progress(float progress) {
                progress2.setProgress((int) (progress * 100));
            }

            @Override
            public void failure(Throwable e) {
                MyToast.getInstance().error("下载错误"+e.getMessage());
            }

            @Override
            public void onPause() {
                MyToast.getInstance().info("下载暂停");
            }

            @Override
            public void onCancel() {
                MyToast.getInstance().error("下载取消");
            }
        });


        btn_start2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              DownloadFile.getInstance().download(data.getUrl());
            }
        });

        btn_stop2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadFile.getInstance().pause(data.getUrl());
            }
        });


    }
}
