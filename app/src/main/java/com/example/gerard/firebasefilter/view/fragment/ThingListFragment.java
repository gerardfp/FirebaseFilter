package com.example.gerard.firebasefilter.view.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gerard.firebasefilter.R;
import com.example.gerard.firebasefilter.model.Thing;
import com.example.gerard.firebasefilter.view.recycleradapter.ThingListAdapter;
import com.example.gerard.firebasefilter.viewmodel.SearchViewModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public abstract class ThingListFragment extends Fragment {

    DatabaseReference mReference;
    RecyclerView recyclerView;

    public ThingListFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        mReference = FirebaseDatabase.getInstance().getReference();

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Poner un adaptador sin terminoDeBusqueda
        setAdapter(null);

        // Observar cuando cambie el terminoDeBusqueda y entonces cambiar el adaptador
        SearchViewModel searchViewModel = ViewModelProviders.of(getActivity()).get(SearchViewModel.class);
        searchViewModel.getTerminoDeBusqueda().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String terminoDeBusqueda) {
                setAdapter(terminoDeBusqueda);
            }
        });

        return view;
    }

    void setAdapter(String terminoDeBusqueda){
        // La consulta se establece en los fragments que heredan de este fragment
        Query query = setQuery();

        // si hay un terminoDeBusqueda, aplicamos el filtro a la "query"
        if(terminoDeBusqueda != null && !terminoDeBusqueda.isEmpty()){
            query = query.orderByValue().startAt(terminoDeBusqueda).endAt(terminoDeBusqueda + "\uf8ff");
        }

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Thing>()
                .setIndexedQuery(query, mReference.child("things/data"), Thing.class)
                .setLifecycleOwner(this)
                .build();

        recyclerView.setAdapter(new ThingListAdapter(getActivity(), options));
    }

    abstract Query setQuery();

}
