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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.naivor.adapter.AdapterOperator;
import com.naivor.adapter.ListAdapter;
import com.naivor.adapter.ListHolder;
import com.naivor.app.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by tianlai on 16-4-12.
 */
public class ParentPathsAdapter extends ListAdapter<ImageCollection> {

    public ParentPathsAdapter(Context context, LayoutInflater inflater) {
        super(context, inflater);
    }

    @Override
    public ListHolder<ImageCollection> onCreateViewHolder(View view, int i) {
        return new ParentPathsViewHolder(view);
    }

    /**
     * 获取布局资源
     *
     * @param viewType
     * @return
     */
    @Override
    public int getLayoutRes(int viewType) {
        return R.layout.list_item_parent_paths;
    }

    /**
     * Created by tianlai on 16-4-12.
     */
    static class ParentPathsViewHolder extends ListHolder<ImageCollection> {

        SimpleDraweeView sdvImage;

        TextView tvPath;

        public ParentPathsViewHolder(View convertView) {
            super(convertView);

            sdvImage = (SimpleDraweeView) find(R.id.sdv_image);
            tvPath = (TextView) find(R.id.tv_path);
        }

        @Override
        public void bindData(AdapterOperator<ImageCollection> operator, int position, ImageCollection itemData) {
            super.bindData(operator, position, itemData);

            List<String> pictures = new ArrayList<>(itemData.getPictures());
            if (pictures != null) {
                int size = pictures.size();
                if (size > 0) {
                    sdvImage.setImageURI(Uri.parse("file://" + pictures.get(0)));
                    tvPath.setText(itemData.getRootName() + "(" + size + ")");
                }
            }
        }


    }
}
