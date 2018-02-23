package com.example.gerard.firebasefilter.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

/**
 * Created by gerard on 23/02/2018.
 */

public class SearchViewModel extends AndroidViewModel {

    MutableLiveData<String> terminoDeBusqueda;


    public SearchViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<String> getTerminoDeBusqueda(){
        if(terminoDeBusqueda == null) {
            terminoDeBusqueda = new MutableLiveData<>();
        }
        return terminoDeBusqueda;
    }
}
