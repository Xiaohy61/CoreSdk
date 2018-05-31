package com.skyward.android.sdk.network.update.app;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skyward.android.sdk.BuildConfig;
import com.skyward.android.sdk.R;
import com.skyward.android.sdk.base.BaseDialogFragment;
import com.skyward.android.sdk.custom.toast.MyToast;
import com.skyward.android.sdk.custom.view.HorizontalProgressbar;
import com.skyward.android.sdk.network.dowload.DownloadFile;
import com.skyward.android.sdk.network.dowload.OnDownloadListener;
import com.skyward.android.sdk.utils.GetMd5;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;

/**
 * @author skyward
 */
public class UpdateAppDialogFragment extends BaseDialogFragment implements View.OnClickListener {

    private ImageView ivTopImage;
    private TextView versionText;
    private TextView appSizeText;
    private RecyclerView mRecyclerView;
    private ImageView ivClose;
    private Button updateBtn;
    private HorizontalProgressbar mProgressbar;
    private TextView updateLogsTitle;
    private LinearLayout closeLayout;
    private static final String DOWNLOAD_URL = "downloadUrl";
    private static final String VERSION = "version";
    private static final String APP_SIZE = "appSize";
    private static final String APP_UPDATE_BUTTON_TEXT = "appUpdateButtonText";
    private static final String UPDATE_LOGS = "updateLogs";
    private static final String TOP_IMAGE = "topImage";
    private static final String IS_FORCE_UPDATE = "isForcedUpdate";
    private static final String UPDATE_BUTTON_BACKGROUND = "updateButtonBackground";


    private final String DIR_NAME = "appUpdate";

    private @DrawableRes
    int topImage = 0;
    private @DrawableRes
    int updateButtonBackground = 0;
    private String downloadUrl;
    private String version;
    private String appSize;
    private String appUpdateButtonText;
    private ArrayList<String> updateLogs;
    private boolean isForcedUpdate;
    private UpdateAppLogsAdapter mUpdateAppLogsAdapter;
    private Context mContext;

    public UpdateAppDialogFragment() {
        // Required empty public constructor

    }


    public static UpdateAppDialogFragment newInstance(String downloadUrl, String version, String appSize,
                                                      String appUpdateButtonText, ArrayList<String> updateLogs,
                                                      @DrawableRes int topImage, boolean isForcedUpdate,
                                                      @DrawableRes int updateButtonBackground) {

        Bundle args = new Bundle();
        UpdateAppDialogFragment fragment = new UpdateAppDialogFragment();
        args.putString(DOWNLOAD_URL, downloadUrl);
        args.putString(VERSION, version);
        args.putString(APP_SIZE, appSize);
        args.putString(APP_UPDATE_BUTTON_TEXT, appUpdateButtonText);
        args.putStringArrayList(UPDATE_LOGS, updateLogs);
        args.putInt(TOP_IMAGE, topImage);
        args.putBoolean(IS_FORCE_UPDATE, isForcedUpdate);
        args.putInt(UPDATE_BUTTON_BACKGROUND, updateButtonBackground);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            downloadUrl = getArguments().getString(DOWNLOAD_URL);
            version = getArguments().getString(VERSION);
            appSize = getArguments().getString(APP_SIZE);
            appUpdateButtonText = getArguments().getString(APP_UPDATE_BUTTON_TEXT);
            updateLogs = getArguments().getStringArrayList(UPDATE_LOGS);
            topImage = getArguments().getInt(TOP_IMAGE);
            isForcedUpdate = getArguments().getBoolean(IS_FORCE_UPDATE);
            updateButtonBackground = getArguments().getInt(UPDATE_BUTTON_BACKGROUND);
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_update_app_dialog;
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    @Override
    protected void initView(View view) {
        ivTopImage = view.findViewById(R.id.iv_top);
        versionText = view.findViewById(R.id.version_name);
        appSizeText = view.findViewById(R.id.app_size);
        mRecyclerView = view.findViewById(R.id.update_logs_list);
        ivClose = view.findViewById(R.id.iv_close);
        updateBtn = view.findViewById(R.id.update_btn);
        mProgressbar = view.findViewById(R.id.update_progress);
        updateLogsTitle = view.findViewById(R.id.update_logs_title);
        closeLayout = view.findViewById(R.id.close_layout);

    }

    @Override
    protected void initLogic(Bundle savedInstanceState) {
        super.initLogic(savedInstanceState);

        if (isForcedUpdate) {
            closeLayout.setVisibility(View.GONE);
        } else {
            closeLayout.setVisibility(View.VISIBLE);
        }

        if (topImage != 0) {
            ivTopImage.setImageResource(topImage);
        }

        if (!TextUtils.isEmpty(appUpdateButtonText)) {
            updateBtn.setText(appUpdateButtonText);
        }

        if (!TextUtils.isEmpty(version)) {
            versionText.setText(MessageFormat.format("最新版本：{0}", version));
        }

        if (!TextUtils.isEmpty(appSize)) {
            appSizeText.setText(MessageFormat.format("新版本大小：{0}", appSize));
        }

        if (updateLogs.size() != 0) {
            updateLogsTitle.setVisibility(View.VISIBLE);
        } else {
            updateLogsTitle.setVisibility(View.GONE);
        }
        if (updateButtonBackground != 0) {

            updateBtn.setBackground(ContextCompat.getDrawable(mContext, updateButtonBackground));
        }
        mUpdateAppLogsAdapter = new UpdateAppLogsAdapter(R.layout.app_update_logs_item, updateLogs);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mUpdateAppLogsAdapter);


        DownloadFile.getInstance().initDownload(downloadUrl, new OnDownloadListener() {
            @Override
            public void onSuccess(File file) {
                dismiss();
                checkPackage(mContext, file);
            }

            @Override
            public void progress(float progress) {
                mProgressbar.setProgress((int) (progress * 100));
            }

            @Override
            public void failure(Throwable e) {
                MyToast.getInstance().error("更新下载失败，请稍后重试!");
            }

            @Override
            public void onPause() {

            }

            @Override
            public void onCancel() {

            }
        });
    }

    @Override
    protected void initListener() {
        super.initListener();
        ivClose.setOnClickListener(this);
        updateBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.iv_close) {
            dismiss();
        } else if (i == R.id.update_btn) {
            updateBtn.setVisibility(View.GONE);
            mProgressbar.setVisibility(View.VISIBLE);
            downloadApp();
        }
    }

    private void downloadApp() {
        DownloadFile.getInstance().download(downloadUrl);
    }

    public void checkPackage(Context context, File file) {

        String newMd5 = GetMd5.getFileMD5(file);

        Log.i("myLog", "md5: " + newMd5);
        //启动安装app
        installApk(context, file);
    }

    private static void installApk(Context context, File file) {

        Intent intent = new Intent(Intent.ACTION_VIEW);
        //判断是否是AndroidN以及更高的版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileProvider", file);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    @SuppressLint("ObsoleteSdkInt")
    @Override
    public void show(FragmentManager manager, String tag) {

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            if (manager.isDestroyed()) {
                return;
            }
        }
        try {
            super.show(manager, tag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
