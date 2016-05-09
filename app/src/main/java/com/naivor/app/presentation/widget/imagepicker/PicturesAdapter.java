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

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.naivor.app.R;
import com.naivor.app.presentation.adapter.BaseAbsListAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by tianlai on 16-4-12.
 */
public class PicturesAdapter extends BaseAbsListAdapter<String,PicturesViewHolder> {
    private Set<String> selectedPictures;

    private OnPictrueSelectListener  onPictrueSelectListener;

    private int maxNum;

    public PicturesAdapter(Context context, LayoutInflater inflater) {

        super(context, inflater);

        selectedPictures=new HashSet<>();
    }

    @Override
    public PicturesViewHolder onCreateViewHolder(ViewGroup parent, int viewType, LayoutInflater inflater) {
        return new PicturesViewHolder(inflater.inflate(R.layout.grid_item_select_image,null),this);
    }


    public Set<String> getSelectedPictures() {
        return selectedPictures;
    }

    public void setSelectedPictures(Set<String> selectedPictures) {
        this.selectedPictures = selectedPictures;
    }

    public void addSelectedPicture(String path){
        selectedPictures.add(path);

        notifySelectedItem();
    }

    public void removeSelectedPicture(String path){
        selectedPictures.remove(path);
        
        notifySelectedItem();
    }

    /**
     * 有item选中或者取消选中时的事件
     */
    private void notifySelectedItem() {
        if (onPictrueSelectListener!=null){
            int size = selectedPictures.size();
            onPictrueSelectListener.selectedSingleItem(size==1);
            onPictrueSelectListener.hasItemSelected(size >0,size);
        }
    }

    public boolean isSlected(String path){
        Log.i("picture",selectedPictures.toString());
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

    public boolean isCanSelectMore(){
        return selectedPictures.size()<maxNum;
    }

    public boolean isSelectedSingleItem(){
        return selectedPictures.size()==1;
    }

    public boolean isSamePicture(String path){
        if (isSelectedSingleItem()){
            if (new ArrayList<String>(selectedPictures).get(0).equals(path)){
                return true;
            }
        }

        return  false;
    }

    /**
     * 获取要剪裁的图片路径
     *
     * @return
     */
    public String getCorpPicturePath(){
        if (selectedPictures.size()==1){
            return new ArrayList<>(selectedPictures).get(0);
        }

        return null;
    }

    /**
     * 清空选中的图片
     */
    public void clearSelectedPictures(){
        selectedPictures.clear();
    }

    public static interface OnPictrueSelectListener{
        public void selectedSingleItem(boolean selectedSingleItem);

        public void hasItemSelected(boolean hasItemSelected, int selectNum);
    }
}
