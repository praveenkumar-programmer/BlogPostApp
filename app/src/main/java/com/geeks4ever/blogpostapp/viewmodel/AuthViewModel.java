package com.geeks4ever.blogpostapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.geeks4ever.blogpostapp.model.myFirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/*
 *  Created by Praveen Kumar on 17/1/21 7:38 PM for BlogPostApp.
 *  Copyright (c) 2021.
 *  Last modified 17/1/21 7:37 PM.
 *
 *  This file/part of BlogPostApp is OpenSource.
 *
 *  BlogPostApp is free software: you can redistribute it and/or modify it under the terms of
 *  the GNU General Public License as published by the Free Software Foundation,
 *  either version 3 of the License, or (at your option) any later version.
 *
 *  BlogPostApp is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 *  without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *  See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with Foobar.
 *  If not, see http://www.gnu.org/licenses/.
 */

public class AuthViewModel extends AndroidViewModel {

    private final MutableLiveData<FirebaseUser> firebaseuser = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();

    private final MutableLiveData<String> error = new MutableLiveData<>();

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

    public LiveData<String> getErrorStatus() { return error; }

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
