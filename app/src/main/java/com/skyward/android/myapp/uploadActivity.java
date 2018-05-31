package com.skyward.android.myapp;

import android.accounts.NetworkErrorException;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import com.skyward.android.myapp.adapter.PhotoAdapter;
import com.skyward.android.myapp.api.ApiConfig;
import com.skyward.android.myapp.bean.UploadImageResultBean;
import com.skyward.android.myapp.utils.RecyclerItemClickListener;
import com.skyward.android.sdk.base.BaseObserver;
import com.skyward.android.sdk.network.request.RxJavaCustomTransform;
import com.skyward.android.sdk.network.request.SubNetworkRequest;
import com.skyward.android.sdk.network.upload.UploadImage;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class uploadActivity extends AppCompatActivity {



    private String sid = "20180423203102827831";
    private ArrayList<String> selectedPhotos = new ArrayList<>();
    private PhotoAdapter mPhotoAdapter;
    private RecyclerView mRecyclerView;
    private File mFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        mRecyclerView = findViewById(R.id.add_photo_recycle);

        mPhotoAdapter = new PhotoAdapter(this, selectedPhotos);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, OrientationHelper.VERTICAL));
        mRecyclerView.setAdapter(mPhotoAdapter);



        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        if (mPhotoAdapter.getItemViewType(position) == PhotoAdapter.TYPE_ADD) {
                            selectPhoto();
                        } else {
                            PhotoPreview.builder()
                                    .setPhotos(selectedPhotos)
                                    .setCurrentItem(position)
                                    .start(uploadActivity.this);
                        }
                    }


                }));


    }

    private void selectPhoto(){
        mFile = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!mFile.getParentFile().exists()) {
            mFile.getParentFile().mkdirs();
        }

        PhotoPicker.builder()
                .setPhotoCount(PhotoAdapter.MAX)
                .setShowCamera(true)
                .setPreviewEnabled(false)
                .setSelected(selectedPhotos)
                .start(uploadActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && (requestCode == PhotoPicker.REQUEST_CODE || requestCode == PhotoPreview.REQUEST_CODE)) {

            List<String> photos = null;
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
            }
            selectedPhotos.clear();

            if (photos != null) {
                Map<String, RequestBody>  partMap = new HashMap<>();
                List<MultipartBody.Part> parts = new ArrayList<>();
                for (int i = 0; i < photos.size(); i++) {



//                    SubNetworkRequest.getInstance().request().create(ApiConfig.class).uploadSigle(sid, UploadImage.uploadSingleImage(files))
//                    .compose(RxJavaCustomTransform.<UploadImageResultBean>defaultSchedulers())
//                    .subscribe(new BaseObserver<UploadImageResultBean>() {
//                        @Override
//                        public void onSuccess(UploadImageResultBean uploadImageResultBean) throws Exception {
//                            Log.i("myLog","onSuccess: "+uploadImageResultBean.toString());
//                        }
//
//                        @Override
//                        public void netWorkError(Throwable e) throws NetworkErrorException {
//                            Log.i("myLog","netWorkError: "+e.getMessage());
//                        }
//
//                        @Override
//                        public void dataError(Throwable e) throws Exception {
//                            Log.i("myLog","dataError: "+e.getMessage());
//                        }
//                    });
                    Log.i("myLog", "photos: " + photos.get(i));
                }


                SubNetworkRequest.getInstance().request().create(ApiConfig.class).uploadMulti(sid, UploadImage.uploadMultiImages(photos))
                    .compose(RxJavaCustomTransform.<UploadImageResultBean>defaultSchedulers())
                    .subscribe(new BaseObserver<UploadImageResultBean>() {
                        @Override
                        public void onSuccess(UploadImageResultBean uploadImageResultBean) throws Exception {
                            Log.i("myLog","onSuccess: "+uploadImageResultBean.toString());
                        }

                        @Override
                        public void netWorkError(Throwable e) throws NetworkErrorException {
                            Log.i("myLog","netWorkError: "+e.getMessage());
                        }

                        @Override
                        public void dataError(Throwable e) throws Exception {
                            Log.i("myLog","dataError: "+e.getMessage());
                        }
                    });

                selectedPhotos.addAll(photos);
            }
            mPhotoAdapter.notifyDataSetChanged();
        }
    }



}
