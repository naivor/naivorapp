/*
 * Copyright (c) 2016. Naivor.All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.naivor.app.others.widget.imagepicker;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.naivor.app.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import rx.Observable;

/**
 * 图片选择弹出对话框
 * <p/>
 * Created by tianlai on 16-4-12.
 */
public class PicKImageDialog extends Dialog {
    private Context context;

    private LayoutInflater inflater;

    private ImageView topHome;

    private TextView topTitle;

    private TextView topCenter;

    private TextView topAction;

    private GridView gvPictures;

    private ViewPager vpPictures;

    private ViewSwitcher vsPictures;

    private TextView bottomPreview;

    private TextView bottomCrop;

    private TextView bottomSure;

    private ListView lvPaths;

    private ViewSwitcher vsLayout;

    private List<ImageCollection> imagePaths;

    private Set<String> parentPaths;

    private ParentPathsAdapter parentPathsAdapter;

    private PicturesAdapter picturesAdapter;

    private PicturesPagerAdapter picturesPagerAdapter;

    private int maxNum;

    private PickImageListener pickImageListener;


    public PicKImageDialog(Context context) {
        super(context, R.style.AppTheme_Dialog);

        init(context);

    }

    public PicKImageDialog(Context context, int theme) {
        super(context, theme);


        init(context);
    }

    private void init(Context context) {
        this.context = context;

        inflater = LayoutInflater.from(context);

        imagePaths = new ArrayList<>();
        parentPaths = new HashSet<>();

        picturesAdapter = new PicturesAdapter(context, inflater);
        parentPathsAdapter = new ParentPathsAdapter(context, inflater);

        scanPictures(context);
    }

    /**
     * 扫描手机中的图片
     *
     * @param context
     */
    private void scanPictures(final Context context) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver mContentResolver = context.getContentResolver();
                String[] projections = {
                        MediaStore.Images.Media.DISPLAY_NAME,
                        MediaStore.Images.Media._ID,
                        MediaStore.Images.Media.DATA,
                        MediaStore.Images.Media.SIZE
                };
                String selection = MediaStore.Images.Media.MIME_TYPE + "=?";
                String[] selectionArgs = {"image/jpeg"};
                String sortOrder = MediaStore.Images.Media.DATE_MODIFIED + " desc";

                // 只查询jpeg和png的图片
                Cursor mCursor = mContentResolver.query(mImageUri, projections,
                        selection,
                        selectionArgs, sortOrder);
                if (mCursor != null) {
                    ImageCollection imageCollection;
                    while (mCursor.moveToNext()) {
                        imageCollection = new ImageCollection();
                        // 获取图片的uri路径
                        String path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA));
                        if (path != null) {
                            File file = new File(path);
                            if (file != null && file.exists()) {

                                // 获取该图片的父路径名
                                File parentFile = file.getParentFile();
                                String parentPath = parentFile.getAbsolutePath();
                                if (parentPaths.contains(parentPath)) {
                                    continue;
                                } else {
                                    parentPaths.add(parentPath);
                                    imageCollection.setRootPath(parentPath);
                                    imageCollection.setRootName(parentFile.getName());

                                    File[] files = parentFile.listFiles();
                                    for (File f : files) {
                                        String fName = f.getName();
                                        if (fName.endsWith(".jpg") || fName.endsWith(".png")) {
                                            imageCollection.addPicture(f.getAbsolutePath());
                                        }
                                    }

                                    imagePaths.add(imageCollection);

                                }

                            }
                        }
                    }
                    mCursor.close();

                }
            }
        }).start();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.dialog_pickimage);

        initView();

    }

    /**
     * 初始化view
     */
    private void initView() {
        topHome = (ImageView) find(R.id.top_home);
        topTitle = (TextView) find(R.id.top_title);
        topCenter = (TextView) find(R.id.top_center);
        topAction = (TextView) find(R.id.top_action);
        gvPictures = (GridView) find(R.id.gv_pictures);
        vpPictures = (ViewPager) find(R.id.vp_pictures);
        vsPictures = (ViewSwitcher) find(R.id.vs_pictures);
        bottomPreview = (TextView) find(R.id.bottom_title);
        bottomCrop = (TextView) find(R.id.bottom_center);
        bottomSure = (TextView) find(R.id.bottom_action);
        lvPaths = (ListView) find(R.id.lv_paths);
        vsLayout = (ViewSwitcher) find(R.id.vs_layout);


        setListeners();

        gvPictures.setAdapter(picturesAdapter);
        lvPaths.setAdapter(parentPathsAdapter);

        parentPathsAdapter.setItems(imagePaths);
        List<String> items = transToFullPath(imagePaths);
        parentPathsAdapter.addItem(0, new ImageCollection(new HashSet<String>(items), "所有图片"));
        picturesAdapter.setItems(items);
    }

    /**
     * 设置监听器
     */
    private void setListeners() {
        //返回
        topHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //取消
        topAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        //预览
        bottomPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPreviews(new ArrayList<String>(picturesAdapter.getSelectedPictures()));
            }
        });
        //剪裁
        bottomCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pickImageListener != null) {
                    pickImageListener.toCropImage(picturesAdapter.getCorpPicturePath());
                    picturesAdapter.clearSelectedPictures();
                }
            }
        });
        //确定
        bottomSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pickImageListener != null) {
                    pickImageListener.onPickFinish(new ArrayList<String>(picturesAdapter.getSelectedPictures()));
                    picturesAdapter.clearSelectedPictures();
                }
            }
        });

        picturesAdapter.setOnPictrueSelectListener(new PicturesAdapter.OnPictrueSelectListener() {
            @Override
            public void selectedSingleItem(boolean selectedSingleItem) {
                if (selectedSingleItem) {
                    bottomCrop.setEnabled(true);
                } else {
                    bottomCrop.setEnabled(false);
                }
            }

            @Override
            public void hasItemSelected(boolean hasItemSelected, int selectNum) {
                if (hasItemSelected) {
                    bottomPreview.setEnabled(true);
                    bottomSure.setEnabled(true);
                    bottomSure.setText("确定(" + selectNum + "/" + maxNum + ")");
                } else {
                    bottomPreview.setEnabled(false);
                    bottomSure.setEnabled(false);
                    bottomSure.setText("确定");
                }
            }
        });

        //点击预览单张图片
        gvPictures.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<String> paths = new ArrayList<String>();
                String item = picturesAdapter.getItem(position);
                paths.add(item);

                showPreviews(paths);

                //判断是否可以剪裁
                if (bottomCrop.isEnabled() && !picturesAdapter.isSamePicture(item)) {
                    bottomCrop.setEnabled(false);
                }
            }
        });

        //点击查看某个目录下面的图片
        lvPaths.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageCollection imageCollection = parentPathsAdapter.getItem(position);
                picturesAdapter.setItems(new ArrayList<String>(imageCollection.getPictures()));
                topCenter.setText(imageCollection.getRootName());
                vsLayout.showNext();

            }
        });

    }

    private void showPreviews(List<String> paths) {
        PicturesPagerAdapter adapter = new PicturesPagerAdapter(paths, inflater);
        vpPictures.setAdapter(adapter);

        vsPictures.showNext();

        //隐藏预览按钮
        bottomPreview.setVisibility(View.GONE);

    }

    /**
     * @param imagePaths
     * @return
     */
    private List<String> transToFullPath(List<ImageCollection> imagePaths) {

        Observable<String> observable = null;
        if (imagePaths != null) {
            int size = imagePaths.size();
            if (size > 0) {
                Observable<String> observableMerge;
                for (int i = 0; i < size; i++) {
                    Set<String> pictures = imagePaths.get(i).getPictures();
                    if (pictures == null) {
                        continue;
                    } else {
                        observableMerge = Observable.from(pictures);
                        if (observable == null) {
                            observable = observableMerge;
                        } else {
                            observable = Observable.merge(observable, observableMerge);
                        }
                    }

                }

                return observable.distinct().toList().toBlocking().single();


            }
        }

        return Collections.emptyList();
    }

    /**
     * 初始化dialog
     *
     * @param maxNum
     * @param pickImageListener
     */
    public void initPickDialog(int maxNum, PickImageListener pickImageListener) {
        this.maxNum = maxNum;
        this.pickImageListener = pickImageListener;

    }

    @Override
    public void show() {
        picturesAdapter.setMaxNum(maxNum);
        super.show();

        setDialogWindowAttr();
    }

    /**
     * 调整dialog的大小
     */
    public void setDialogWindowAttr() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.gravity = Gravity.CENTER;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;//宽高可设置具体大小
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(lp);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (lvPaths.isShown()) {
            dismiss();
        } else if (vpPictures.isShown()) {
            vsPictures.showNext();

            //显示预览按钮
            bottomPreview.setVisibility(View.VISIBLE);

            //判断是否可以剪裁
            if (!bottomCrop.isEnabled() && picturesAdapter.isSelectedSingleItem()) {
                bottomCrop.setEnabled(true);
            }
        } else {
            vsLayout.showNext();
        }
    }

    public int getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(int maxNum) {
        this.maxNum = maxNum;
    }

    public PickImageListener getPickImageListener() {
        return pickImageListener;
    }

    public void setPickImageListener(PickImageListener pickImageListener) {
        this.pickImageListener = pickImageListener;
    }

    /**
     * 获取view
     *
     * @param viewId
     * @return
     */
    public View find(int viewId) {
        return findViewById(viewId);
    }

    /**
     * 获取view
     *
     * @param viewId
     * @return
     */
    public View find(View parent, int viewId) {
        return parent.findViewById(viewId);
    }

    public static interface PickImageListener {
        /**
         * 选择图片完成
         */
        public void onPickFinish(List<String> paths);

        /**
         * 剪裁图片
         */
        public void toCropImage(String path);
    }

}
