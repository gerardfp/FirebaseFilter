package com.example.gerard.firebasefilter.view.recycleradapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gerard.firebasefilter.R;
import com.example.gerard.firebasefilter.model.Thing;
import com.example.gerard.firebasefilter.view.viewholder.ThingViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

/**
 * Created by gerard on 22/02/2018.
 */

public class ThingListAdapter extends FirebaseRecyclerAdapter<Thing, ThingViewHolder> {

    Context context;

    public ThingListAdapter(Context context, @NonNull FirebaseRecyclerOptions<Thing> options) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ThingViewHolder holder, int position, @NonNull Thing thing) {
        holder.name.setText(thing.name);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // set things/user-fav/$uid$/$thingkey=true/false
            }
        });
    }

    @Override
    public ThingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ThingViewHolder(LayoutInflater.from(context).inflate(R.layout.item_thing, parent, false));
    }
}
