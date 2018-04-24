package com.sunny.fhaf.view.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.adapter.ImagePageAdapter;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.sunny.fhaf.R;
import com.sunny.fhaf.adapter.ImagePickerAdapter;
import com.sunny.fhaf.utils.PageUtils;
import com.sunny.fhaf.utils.PicassoImageLoader;
import com.sunny.uilib.base.BaseActivity;
import com.sunny.uilib.utils.ToastUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.lzy.imagepicker.ImagePicker.REQUEST_CODE_PREVIEW;

/**
 * ------------------------------------------------
 * Copyright © 2014-2018 CLife. All Rights Reserved.
 * Shenzhen H&T Intelligent Control Co.,Ltd.
 * -----------------------------------------------
 *
 * @author Sunny
 * @version v1.1.6
 * @date 2018/4/23 11:39
 * @annotation Android拍照，相册浏览
 */
public class FHAF_CameraActivity extends BaseActivity implements ImagePickerAdapter.OnRecyclerViewItemClickListener {
    private static final int IMAGE_PICKER = 0x1000;
    private static final int TAKE_PHOTO = IMAGE_PICKER;
    private RxPermissions rxPermissions;
    @BindView(R.id.app_camera_rv)
    RecyclerView mRvGrid;
    private ImagePickerAdapter adapter;
    private List<ImageItem> selImageList;
    private ArrayList<ImageItem> images;
    private int maxImgCount = 8;               //允许选择图片最大数

    @Override
    protected int getLayoutId() {
        setTitle(R.string.app_title_camera);
        return R.layout.app_activity_camera;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initRecyclerView();
        handlePermissions();
    }

    private void initRecyclerView() {
        selImageList = new ArrayList<>();
        adapter = new ImagePickerAdapter(this, selImageList, maxImgCount);
        adapter.setOnItemClickListener(this);

        mRvGrid.setLayoutManager(new GridLayoutManager(this, 4));
        mRvGrid.setHasFixedSize(true);
        mRvGrid.setAdapter(adapter);
    }

    /**
     * 选择本地相片
     *
     * @param view
     */
    public void selectPic(View view) {
        Intent intent = new Intent(this, ImageGridActivity.class);
        startActivityForResult(intent, IMAGE_PICKER);
    }

    /**
     * 拍照
     *
     * @param view
     */
    public void takePhoto(View view) {
        Intent intent = new Intent(this, ImageGridActivity.class);
        intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
        startActivityForResult(intent, TAKE_PHOTO);
    }

    /**
     * 动态权限管理
     */
    private void handlePermissions() {
        rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.CAMERA)
                .subscribe(granted -> {
                    if (!granted) {
                        ToastUtil.showToast(this, "请确认开启相机权限");
                        finish();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMAGE_PICKER) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null) {
                    selImageList.addAll(images);
                    adapter.setImages(selImageList);
                }
            } else if (requestCode == REQUEST_CODE_PREVIEW) {
                //预览图片返回
                if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                    images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                    if (images != null) {
                        selImageList.clear();
                        selImageList.addAll(images);
                        adapter.setImages(selImageList);
                    }
                }
            } else {
                ToastUtil.showToast(this, "没有数据");
            }
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        if (position == -1)
            selectPic(view);
        else {
            //打开预览
            Intent intentPreview = new Intent(this, ImagePreviewDelActivity.class);
            intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) adapter.getImages());
            intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
            intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
            startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
        }
    }
}
