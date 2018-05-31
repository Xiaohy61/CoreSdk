package com.skyward.android.sdk.network.dowload;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @author: skyward
 * date: 2018/5/14
 * desc:
 */
public class DownloadTask {

    /**
     * 下载文件实体类
     */
    private DownloadFileInfo mFileInfo;

    /**
     * 下载线程数量
     */
    private final int THREAD_COUNT = 4;
    /**
     * 下载文件长度
     */
    private long fileLength;

    private boolean isDownloading = false;
    /**
     * 子线程取消数量
     */
    private int childCancelCount;
    /**
     * 子线程停止数量
     */
    private int childPauseCount;
    /**
     * 子线程完成数量
     */
    private int childFinishCount;

    private DownloadHttpUtil mHttpUtil;

    private long[] mProgress;
    private File[] mCacheFiles;
    private File mTmpFile;

    private boolean pause;
    private boolean cancel;

    private final int MSG_PROGRESS = 1;
    private final int MSG_FINISH = 2;
    private final int MSG_PAUSE = 3;
    private final int MSG_CANCEL = 4;

    private OnDownloadListener mListener;
    private final int HTTP_SC_OK =200;
    private final int HTTP_SECTION_OK = 206;

    public DownloadTask(DownloadFileInfo fileInfo, OnDownloadListener listener) {
        mFileInfo = fileInfo;
        mListener = listener;
        mProgress = new long[THREAD_COUNT];
        mCacheFiles = new File[THREAD_COUNT];
        mHttpUtil = DownloadHttpUtil.getInstance();

        Log.i("myLog","DownloadTask: "+fileInfo.toString());
    }

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (null == mListener) {
                return true;
            }
            switch (msg.what) {
                case MSG_PROGRESS:
                    long progress = 0;
                    for (int i = 0, length = mProgress.length; i < length; i++) {
                        progress += mProgress[i];
                    }
                    mListener.progress(progress * 1.0f / fileLength);

                    break;
                case MSG_PAUSE:
                    childPauseCount++;
                    if (childPauseCount % THREAD_COUNT != 0) {
                        break;
                    }
                    resetStatus();
                    mListener.onPause();
                    break;
                case MSG_FINISH:
                    childFinishCount++;
                    if (childFinishCount % THREAD_COUNT != 0) {
                        break;
                    }
                    mTmpFile.renameTo(new File(mFileInfo.getFilePath(), mFileInfo.getFileName()));
                    resetStatus();
                    mListener.onSuccess(mTmpFile);
                    break;
                case MSG_CANCEL:
                    childCancelCount++;
                    if (childCancelCount % THREAD_COUNT != 0) {
                        break;
                    }
                    resetStatus();
                    mProgress = new long[THREAD_COUNT];
                    mListener.progress(0);
                    mListener.onCancel();
                    break;
                default:
                    break;
            }
            return true;
        }
    });


    private void resetStatus() {
        pause = false;
        cancel = false;
        isDownloading = false;
    }

    public void startDownload(){

        if (isDownloading) {
            return;
        }
        isDownloading = true;

        mHttpUtil.getContentLength(mFileInfo.getUrl(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.code() != HTTP_SC_OK){
                    close(response.body());
                    resetStatus();
                    return;
                }

                //获取下载资源的大小
                fileLength = response.body().contentLength();
                close(response.body());

                //创建一个和下载资源同样大小的临时文件



                mTmpFile = new File(mFileInfo.getFilePath(), mFileInfo.getFileName());

                if (!mTmpFile.getParentFile().exists()) {
                    mTmpFile.getParentFile().mkdirs();
                }
                RandomAccessFile randomAccessFile = new RandomAccessFile(mTmpFile,"rwd");
                randomAccessFile.setLength(fileLength);

                //计算每个线程理论上下载的数量
                long singleThreadDownloadSize = fileLength / THREAD_COUNT;

                //为每个线程分配下载的量
                for (int threadId = 0; threadId < THREAD_COUNT; threadId++) {
                    //每个线程开始下载的位置
                    long startIndex = threadId * singleThreadDownloadSize;
                    //每个线程结束下载的位置
                    long endIndex = (threadId +1) * singleThreadDownloadSize -1;

                    //有些长度可能是除不尽的，所以如果是最后一个线程，将剩下的文件全部交给这个线程下载
                    if((threadId +1) == THREAD_COUNT){
                        endIndex = fileLength;
                    }
                    //开始下载
                    try {
                        download(startIndex,endIndex,threadId);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    private void download(final long startIndex, long endIndex, final int threadId) throws Exception{
        long newStartIndex = startIndex;
        // 分段请求网络连接,分段将文件保存到本地.
        // 加载下载位置缓存文件
        final File cacheFile = new File(mFileInfo.getFilePath(), "thread" + threadId + "_" + mFileInfo.getFileName() + ".cache");
        mCacheFiles[threadId] = cacheFile;
        final RandomAccessFile cacheAccessFile = new RandomAccessFile(cacheFile, "rwd");
        // 如果文件存在
        if (cacheFile.exists()) {
            String startIndexStr = cacheAccessFile.readLine();
            try {
                //重新设置下载起点
                newStartIndex = Integer.parseInt(startIndexStr);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        final long finalStartIndex = newStartIndex;
        mHttpUtil.downloadFileByRange(mFileInfo.getUrl(), finalStartIndex, endIndex, new okhttp3.Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // 206：请求部分资源成功码
                if (response.code() != HTTP_SECTION_OK) {
                    resetStatus();
                    return;
                }
                InputStream is = response.body().byteStream();
                // 获取前面已创建的文件.
                RandomAccessFile tmpAccessFile = new RandomAccessFile(mTmpFile, "rw");
                // 文件写入的开始位置.
                tmpAccessFile.seek(finalStartIndex);
                  /*  将网络流中的文件写入本地*/
                byte[] buffer = new byte[1024 << 2];
                int length = -1;
                // 记录本次下载文件的大小
                int total = 0;
                long progress = 0;
                while ((length = is.read(buffer)) > 0) {
                    if (cancel) {
                        //关闭资源
                        close(cacheAccessFile, is, response.body());
                        cleanFile(cacheFile);
                        mHandler.sendEmptyMessage(MSG_CANCEL);
                        return;
                    }
                    if (pause) {
                        //关闭资源
                        close(cacheAccessFile, is, response.body());
                        //发送暂停消息
                        mHandler.sendEmptyMessage(MSG_PAUSE);
                        return;
                    }
                    tmpAccessFile.write(buffer, 0, length);
                    total += length;
                    progress = finalStartIndex + total;

                    //将当前现在到的位置保存到文件中
                    cacheAccessFile.seek(0);
                    cacheAccessFile.write((progress + "").getBytes("UTF-8"));
                    //发送进度消息
                    mProgress[threadId] = progress - startIndex;
                    mHandler.sendEmptyMessage(MSG_PROGRESS);
                }
                //关闭资源
                close(cacheAccessFile, is, response.body());
                // 删除临时文件
                cleanFile(cacheFile);
                //发送完成消息
                mHandler.sendEmptyMessage(MSG_FINISH);
            }

            @Override
            public void onFailure(Call call, IOException e) {
                isDownloading = false;
            }

        });
    }


    /**
     * 关闭释放资源
     * @param closeables closeables
     */
    private void close(Closeable... closeables) {
        int length = closeables.length;
        try {
            for (int i = 0; i < length; i++) {
                Closeable closeable = closeables[i];
                if (null != closeable){
                    closeables[i].close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            for (int i = 0; i < length; i++) {
                closeables[i] = null;
            }
        }
    }

    private void cleanFile(File... files) {
        for (int i = 0, length = files.length; i < length; i++) {
            if (null != files[i]) {
                files[i].delete();
            }

        }
    }

    /**
     * 暂停
     */
    public void pause() {
        pause = true;
        Log.i("myLog","pause");
    }

    /**
     * 取消
     */
    public void cancel() {
        cancel = true;
        cleanFile(mTmpFile);
        if (!isDownloading) {
            if (null != mListener) {
                cleanFile(mCacheFiles);
                resetStatus();
                mListener.onCancel();

            }
        }
    }

    public boolean isDownloading() {
        return isDownloading;
    }
}
