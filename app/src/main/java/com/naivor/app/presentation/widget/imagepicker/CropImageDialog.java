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

package com.naivor.app.presentation.widget.imagepicker;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.isseiaoki.simplecropview.CropImageView;
import com.isseiaoki.simplecropview.callback.CropCallback;
import com.isseiaoki.simplecropview.callback.LoadCallback;
import com.isseiaoki.simplecropview.callback.SaveCallback;
import com.naivor.app.BuildConfig;
import com.naivor.app.R;

import java.io.File;

/**
 * Created by tianlai on 16-4-13.
 */
public class CropImageDialog extends Dialog {

    private Context context;

    private LayoutInflater inflater;

    // Views ///////////////////////////////////////////////////////////////////////////////////////
    private CropImageView mCropView;
    private LinearLayout mRootLayout;
    private ProgressBar progressBar;

    private String cachePath;
    private String cropPath;

    private boolean isFromPick;

    private CropImageListener cropImageListener;

    public CropImageDialog(Context context) {
        super(context, R.style.AppTheme_Dialog);

        init(context);

    }

    public CropImageDialog(Context context, int theme) {
        super(context, theme);


        init(context);
    }

    private void init(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        View contentView = inflater.inflate(R.layout.dialog_cropimage, null);

        setContentView(contentView);

        // bind Views
        bindViews(contentView);

        mCropView.setDebug(BuildConfig.DEBUG);
    }


    private void bindViews(View view) {
        progressBar = (ProgressBar) view.findViewById(R.id.progress);
        progressBar.getIndeterminateDrawable().setColorFilter(getContext().getResources().getColor(R.color.colorAccent),
                PorterDuff.Mode.SRC_IN);
        mRootLayout = (LinearLayout) view.findViewById(R.id.layout_root);

        mCropView = (CropImageView) view.findViewById(R.id.cropImageView);
        mCropView.setOutputMaxSize(300, 300);

        view.findViewById(R.id.top_home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        view.findViewById(R.id.top_action).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        view.findViewById(R.id.buttonDone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgress();
                mCropView.startCrop(createSaveUri(), mCropCallback, mSaveCallback);
            }
        });
        view.findViewById(R.id.buttonFitImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCropView.setCropMode(CropImageView.CropMode.FIT_IMAGE);
            }
        });
        view.findViewById(R.id.button1_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCropView.setCropMode(CropImageView.CropMode.SQUARE);
            }
        });
        view.findViewById(R.id.button3_4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCropView.setCropMode(CropImageView.CropMode.RATIO_3_4);
            }
        });
        view.findViewById(R.id.button4_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCropView.setCropMode(CropImageView.CropMode.RATIO_4_3);
            }
        });
        view.findViewById(R.id.button9_16).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCropView.setCropMode(CropImageView.CropMode.RATIO_9_16);
            }
        });
        view.findViewById(R.id.button16_9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCropView.setCropMode(CropImageView.CropMode.RATIO_16_9);
            }
        });
        view.findViewById(R.id.buttonFree).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCropView.setCropMode(CropImageView.CropMode.FREE);
            }
        });
        view.findViewById(R.id.buttonPickImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cropImageListener != null) {
                    cropImageListener.onPickImage();
                }
            }
        });
        view.findViewById(R.id.buttonRotateLeft).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCropView.rotateImage(CropImageView.RotateDegrees.ROTATE_M90D);
            }
        });
        view.findViewById(R.id.buttonRotateRight).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCropView.rotateImage(CropImageView.RotateDegrees.ROTATE_90D);
            }
        });
        view.findViewById(R.id.buttonCustom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCropView.setCustomRatio(7, 5);
            }
        });
        view.findViewById(R.id.buttonCircle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCropView.setCropMode(CropImageView.CropMode.CIRCLE);
            }
        });
        view.findViewById(R.id.buttonShowCircleButCropAsSquare).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCropView.setCropMode(CropImageView.CropMode.CIRCLE_SQUARE);
            }
        });

    }

    /**
     * 创建保存剪裁图片的Uri
     *
     * @return
     */
    public Uri createSaveUri() {
        try {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                //检查路径是否存在
                File dir = new File(cachePath);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                //检查文件是否存在
                File file = new File(cachePath, "cropped.jpg");
                if (!file.exists()) {
                    file.createNewFile();
                }

                cropPath = file.getAbsolutePath();

                return Uri.fromFile(file);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (isFromPick) {
            if (cropImageListener != null) {
                cropImageListener.onPickImage();

                return;
            }
        }

        dismiss();

    }

    private void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void dismissProgress() {
        progressBar.setVisibility(View.GONE);
    }

    /**
     * @param cachePath
     * @param pickImageListener
     */
    public void initCropDialog(String cachePath, CropImageListener pickImageListener) {
        this.cropImageListener = pickImageListener;
        this.cachePath = cachePath;
    }

    /**
     * 开始剪裁
     *
     * @param pictureUri 要剪裁的图片的Uri
     */
    public void show(Uri pictureUri, boolean isFromPick) {

        Log.i("CropImageDialog", pictureUri.toString());

        this.isFromPick = isFromPick;

        super.show();
        setDialogWindowAttr();

        showProgress();
        mCropView.startLoad(pictureUri, mLoadCallback);

    }
//    /**
//     * 开始剪裁
//     *
//     * @param pictureUri
//     * @param isFromPick
//     */
//    public void show(Uri pictureUri, boolean isFromPick) {
//        super.show();
//
//        setDialogWindowAttr();
//
//        Log.i("CropImageDialog", pictureUri.toString());
//
//        this.isFromPick = isFromPick;
//
//        showProgress();
//        mCropView.setImageURI(pictureUri);
//
//    }

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

    public String getCachePath() {
        return cachePath;
    }

    public void setCachePath(String cachePath) {
        this.cachePath = cachePath;
    }

    public CropImageListener getCropImageListener() {
        return cropImageListener;
    }

    public void setCropImageListener(CropImageListener cropImageListener) {
        this.cropImageListener = cropImageListener;
    }

    // Callbacks ///////////////////////////////////////////////////////////////////////////////////

    private final LoadCallback mLoadCallback = new LoadCallback() {
        @Override
        public void onSuccess() {
            dismissProgress();
        }

        @Override
        public void onError() {
            dismissProgress();
        }
    };


    private final CropCallback mCropCallback = new CropCallback() {
        @Override
        public void onSuccess(Bitmap cropped) {
        }

        @Override
        public void onError() {
        }
    };

    private final SaveCallback mSaveCallback = new SaveCallback() {
        @Override
        public void onSuccess(Uri outputUri) {
            dismissProgress();

            if (cropImageListener != null) {
                cropImageListener.onCropSucceed(cropPath);
            }
        }

        @Override
        public void onError() {
            dismissProgress();
        }
    };

    public static interface CropImageListener {
        /**
         * 选择图片
         */
        public void onPickImage();

        /**
         * 剪裁成功
         *
         * @param path
         */
        public void onCropSucceed(String path);
    }
}
