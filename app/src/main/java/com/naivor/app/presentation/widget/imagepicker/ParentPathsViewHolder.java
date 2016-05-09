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
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.naivor.app.R;
import com.naivor.app.presentation.adapter.BaseAbsListAdapter;
import com.naivor.app.presentation.adapter.viewholder.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tianlai on 16-4-12.
 */
public class ParentPathsViewHolder extends BaseViewHolder<ImageCollection> {

    SimpleDraweeView sdvImage;

    TextView tvPath;

    public ParentPathsViewHolder(View convertView, BaseAbsListAdapter absListAdapter) {
        super(convertView, absListAdapter);

        sdvImage= (SimpleDraweeView) find(R.id.sdv_image);
        tvPath= (TextView) find(R.id.tv_path);
    }

    @Override
    public void loadDataToView(int position, ImageCollection data) {
        List<String> pictures = new ArrayList<>(data.getPictures());
        if (pictures != null) {
            int size = pictures.size();
            if (size > 0) {
                sdvImage.setImageURI(Uri.parse("file://" + pictures.get(0)));
                tvPath.setText(data.getRootName() + "(" + size + ")");
            }
        }
    }

}
