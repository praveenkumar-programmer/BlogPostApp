package com.geeks4ever.blogpostapp.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.geeks4ever.blogpostapp.model.myFirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthViewModel extends AndroidViewModel {

    final MutableLiveData<FirebaseUser> firebaseuser = new MutableLiveData<>();
    final MutableLiveData<Boolean> loading = new MutableLiveData<>();

    final MutableLiveData<String> error = new MutableLiveData<>();

    private myFirebaseAuth firebaseAuth;

    public AuthViewModel(@NonNull Application application) {
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
        firebaseAuth.getError().observeForever(new Observer<String>() {
            @Override
            public void onChanged(String s) {
                error.setValue(s);
            }
        });

    }

    public LiveData<String> getError() { return error; }

    public LiveData<Boolean> getLoadingStatus(){
        return loading;
    }

    public LiveData<FirebaseUser> getCurrentUser(){
        return firebaseuser;
    }

    public void login(String email, String password){
        firebaseAuth.logIn(email, password);
    }

    public void signUp(String email, String password){
        firebaseAuth.signUp(email, password);
    }

}
