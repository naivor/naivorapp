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
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.naivor.app.R;
import com.naivor.app.presentation.adapter.BaseAbsListAdapter;


/**
 * Created by tianlai on 16-4-12.
 */
public class ParentPathsAdapter extends BaseAbsListAdapter<ImageCollection,ParentPathsViewHolder> {

    public ParentPathsAdapter(Context context, LayoutInflater inflater) {
        super(context, inflater);
    }

    @Override
    public ParentPathsViewHolder onCreateViewHolder(ViewGroup parent, int viewType, LayoutInflater inflater) {
        return new ParentPathsViewHolder(inflater.inflate(R.layout.list_item_parent_paths,null),this);
    }
}
