package com.geeks4ever.blogpostapp.viewmodel;

import android.app.Application;

import com.geeks4ever.blogpostapp.model.myFirebaseAuth;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.google.firebase.auth.FirebaseUser;

public class HomeViewModel extends AndroidViewModel {

    final MutableLiveData<FirebaseUser> firebaseuser = new MutableLiveData<>();
    final MutableLiveData<Boolean> loading = new MutableLiveData<>();

    private myFirebaseAuth firebaseAuth;

    public HomeViewModel(@NonNull Application application) {
        super(application);

        firebaseAuth = myFirebaseAuth.getInstance();
        firebaseAuth.getCurrentUser().observeForever(new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                firebaseuser.setValue(firebaseUser);
            }
        });
        firebaseAuth.getLoadingStatus().observeForever(new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                loading.setValue(aBoolean);
            }
        });
    }

    public LiveData<Boolean> getLoadingStatus(){
        return loading;
    }

    public LiveData<FirebaseUser> getCurrentUser(){
        return firebaseuser;
    }

    public void logout(){
        firebaseAuth.logOut();
    }

}
