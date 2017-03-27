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

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.naivor.app.common.base.AdapterOperator;
import com.naivor.app.common.base.ListAdapter;
import com.naivor.app.common.base.ListHolder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by tianlai on 16-4-12.
 */
public class PicturesAdapter extends ListAdapter<String> {
    private Set<String> selectedPictures;

    private OnPictrueSelectListener onPictrueSelectListener;

    private int maxNum;

    public PicturesAdapter(Context context, LayoutInflater inflater) {

        super(context, inflater);

        selectedPictures = new HashSet<>();
    }

    @Override
    public PicturesViewHolder onCreateViewHolder(ViewGroup parent, int viewType, LayoutInflater inflater) {
        return new PicturesViewHolder(inflater.inflate(R.layout.grid_item_select_image, null));
    }


    public Set<String> getSelectedPictures() {
        return selectedPictures;
    }

    public void setSelectedPictures(Set<String> selectedPictures) {
        this.selectedPictures = selectedPictures;
    }

    public void addSelectedPicture(String path) {
        selectedPictures.add(path);

        notifySelectedItem();
    }

    public void removeSelectedPicture(String path) {
        selectedPictures.remove(path);

        notifySelectedItem();
    }

    /**
     * 有item选中或者取消选中时的事件
     */
    private void notifySelectedItem() {
        if (onPictrueSelectListener != null) {
            int size = selectedPictures.size();
            onPictrueSelectListener.selectedSingleItem(size == 1);
            onPictrueSelectListener.hasItemSelected(size > 0, size);
        }
    }

    public boolean isSlected(String path) {
        Log.i("picture", selectedPictures.toString());
        return selectedPictures.contains(path);
    }

    public OnPictrueSelectListener getOnPictrueSelectListener() {
        return onPictrueSelectListener;
    }

    public void setOnPictrueSelectListener(OnPictrueSelectListener onPictrueSelectListener) {
        this.onPictrueSelectListener = onPictrueSelectListener;
    }

    public int getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(int maxNum) {
        this.maxNum = maxNum;
    }

    public boolean isCanSelectMore() {
        return selectedPictures.size() < maxNum;
    }

    public boolean isSelectedSingleItem() {
        return selectedPictures.size() == 1;
    }

    public boolean isSamePicture(String path) {
        if (isSelectedSingleItem()) {
            if (new ArrayList<String>(selectedPictures).get(0).equals(path)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 获取要剪裁的图片路径
     *
     * @return
     */
    public String getCorpPicturePath() {
        if (selectedPictures.size() == 1) {
            return new ArrayList<>(selectedPictures).get(0);
        }

        return null;
    }

    /**
     * 清空选中的图片
     */
    public void clearSelectedPictures() {
        selectedPictures.clear();
    }

    public static interface OnPictrueSelectListener {
        public void selectedSingleItem(boolean selectedSingleItem);

        public void hasItemSelected(boolean hasItemSelected, int selectNum);
    }

    /**
     * Created by tianlai on 16-4-12.
     */
    static class PicturesViewHolder extends ListHolder<String> implements CompoundButton
            .OnCheckedChangeListener {

        SimpleDraweeView sdvPicture;

        CheckBox cbSelect;

        private ImageRequest imageRequest;
        private DraweeController draweeController;

        public PicturesViewHolder(View convertView) {
            super(convertView);

            sdvPicture = (SimpleDraweeView) find(R.id.sdv_picture);
            cbSelect = (CheckBox) find(R.id.cb_select);

            cbSelect.setOnCheckedChangeListener(this);
        }

        @Override
        public void bindData(AdapterOperator<String> operator, int position, String itemData) {
            super.bindData(operator, position, itemData);

            imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse("file://" + itemData))
                    .setResizeOptions(new ResizeOptions(128, 128))
                    .setLocalThumbnailPreviewsEnabled(true)
                    .build();
            draweeController = Fresco.newDraweeControllerBuilder()
                    .setImageRequest(imageRequest)
                    .setOldController(sdvPicture.getController())
                    .build();

            sdvPicture.setController(draweeController);

            if (((PicturesAdapter) adapter).isSlected(itemData)) {
                cbSelect.setChecked(true);
                Log.i("picture", "位置" + position + "--已选中");
            } else {
                cbSelect.setChecked(false);
                Log.i("picture", "位置" + position + "--未选中");
            }
        }


        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                if (((PicturesAdapter) adapter).isCanSelectMore() || ((PicturesAdapter) adapter).isSlected(itemData)) {
                    ((PicturesAdapter) adapter).addSelectedPicture(itemData);
                    Log.i("picture", "位置" + position + "--正在点击选中");
                } else {
                    Toast.makeText(context, "最多只能选择" + ((PicturesAdapter) adapter).getMaxNum() + "张图片", Toast
                            .LENGTH_SHORT).show();
                    buttonView.setChecked(false);
                }
            } else {
                ((PicturesAdapter) adapter).removeSelectedPicture(itemData);
                Log.i("picture", "位置" + position + "--正在取消选中");
            }
        }

    }
}
