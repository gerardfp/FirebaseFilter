package com.example.gerard.firebasefilter.view.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.gerard.firebasefilter.R;

/**
 * Created by gerard on 22/02/2018.
 */

public class ThingViewHolder extends RecyclerView.ViewHolder {
    public TextView name;

    public ThingViewHolder(View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.name);
    }
}
