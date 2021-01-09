package com.geeks4ever.blogpostapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseUser;

public class HomeViewModel extends AndroidViewModel {

    final MutableLiveData<FirebaseUser>

    public HomeViewModel(@NonNull Application application) {
        super(application);
    }
}
