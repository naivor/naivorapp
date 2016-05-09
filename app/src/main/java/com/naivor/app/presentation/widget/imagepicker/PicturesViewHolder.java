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

import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.naivor.app.R;
import com.naivor.app.presentation.adapter.BaseAbsListAdapter;
import com.naivor.app.presentation.adapter.viewholder.BaseViewHolder;

/**
 * Created by tianlai on 16-4-12.
 */
public class PicturesViewHolder extends BaseViewHolder<String> implements  CompoundButton
        .OnCheckedChangeListener {

    SimpleDraweeView sdvPicture;

    CheckBox cbSelect;

    private ImageRequest imageRequest;
    private DraweeController draweeController;

    public PicturesViewHolder(View convertView, BaseAbsListAdapter absListAdapter) {
        super(convertView, absListAdapter);

        sdvPicture = (SimpleDraweeView) find(R.id.sdv_picture);
        cbSelect = (CheckBox) find(R.id.cb_select);

        cbSelect.setOnCheckedChangeListener(this);
    }

    @Override
    public void loadDataToView(int position, String data) {
        super.loadDataToView(position,data);

        imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse("file://" + data))
                .setResizeOptions(new ResizeOptions(128,128))
                .setLocalThumbnailPreviewsEnabled(true)
                .build();
        draweeController = Fresco.newDraweeControllerBuilder()
                .setImageRequest(imageRequest)
                .setOldController(sdvPicture.getController())
                .build();

        sdvPicture.setController(draweeController);

        if (((PicturesAdapter) absListAdapter).isSlected(data)) {
            cbSelect.setChecked(true);
            Log.i("picture","位置"+position+"--已选中");
        }else {
            cbSelect.setChecked(false);
            Log.i("picture","位置"+position+"--未选中");
        }

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                if (((PicturesAdapter) absListAdapter).isCanSelectMore()||((PicturesAdapter) absListAdapter).isSlected(data)){
                    ((PicturesAdapter) absListAdapter).addSelectedPicture(data);
                    Log.i("picture","位置"+position+"--正在点击选中");
                }else {
                    Toast.makeText(context,"最多只能选择"+((PicturesAdapter) absListAdapter).getMaxNum()+"张图片",Toast
                            .LENGTH_SHORT).show();
                    buttonView.setChecked(false);
                }
            } else {
                ((PicturesAdapter) absListAdapter).removeSelectedPicture(data);
                Log.i("picture","位置"+position+"--正在取消选中");
            }
    }

}
