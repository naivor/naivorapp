package com.naivor.app.presentation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.naivor.app.R;
import com.naivor.app.presentation.adapter.viewholder.HomeViewHolder;

import javax.inject.Inject;

/**
 * Created by naivor on 16-5-21.
 */
public class HomeListAdapter extends BaseAbsListAdapter<String,HomeViewHolder> {

    @Inject
    public HomeListAdapter(Context context, LayoutInflater inflater) {
        super(context, inflater);
    }

    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType, LayoutInflater inflater) {
        return new HomeViewHolder(inflater.inflate(R.layout.list_item_home,null),this);
    }
}
