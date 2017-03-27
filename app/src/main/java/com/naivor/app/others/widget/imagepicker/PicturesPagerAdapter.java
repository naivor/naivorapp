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

import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.naivor.app.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tianlai on 16-4-12.
 */
public class PicturesPagerAdapter extends PagerAdapter {
    private List<View> images;

    private List<String> picturePaths;

    private LayoutInflater inflater;

    public PicturesPagerAdapter(List<String> picturePaths, LayoutInflater inflater) {
        this.inflater = inflater;
        this.picturePaths=picturePaths;

        images = new ArrayList<View>();

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup
                .LayoutParams.MATCH_PARENT);

        View view;

        for (String path : picturePaths) {
            view=inflater.inflate(R.layout.vp_item_picture,null);
            ((SimpleDraweeView)view.findViewById(R.id.vp_picture)).setImageURI(Uri.parse("file://"+path));
            images.add(view);
        }


    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = images.get(position);
        container.addView(view);

        return view;
    }

    public List<String> getPicturePaths() {
        return picturePaths;
    }

    public void setPicturePaths(List<String> picturePaths) {
        this.picturePaths = picturePaths;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(images.get(position));
    }


}
