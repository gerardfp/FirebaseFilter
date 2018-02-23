package com.example.gerard.firebasefilter.view.fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.Query;

/**
 * Created by gerard on 22/02/2018.
 */

public class FavThingListFragment extends ThingListFragment  {
    @Override
    Query setQuery() {
        return mReference.child("things/fav-things").child(FirebaseAuth.getInstance().getUid());
    }
}
