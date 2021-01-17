package com.geeks4ever.blogpostapp.viewmodel;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.geeks4ever.blogpostapp.R;
import com.geeks4ever.blogpostapp.model.myFirebaseAuth;
import com.geeks4ever.blogpostapp.model.postModel;
import com.geeks4ever.blogpostapp.model.repository.myRepository;
import com.geeks4ever.blogpostapp.view.viewholder.PostViewHolder;
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

public class HomeViewModel extends AndroidViewModel {

    private final MutableLiveData<FirebaseUser> firebaseuser = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();

    private FirebaseRecyclerAdapter<postModel, PostViewHolder> adapter;

    private myFirebaseAuth firebaseAuth;
    private myRepository repository;

    public HomeViewModel(@NonNull Application application) {
        super(application);

        firebaseAuth = myFirebaseAuth.getInstance();
        repository = myRepository.getInstance();
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

        repository.getLoadingStatus().observeForever(new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                loading.setValue(aBoolean);
            }
        });

        repository.getError().observeForever(new Observer<String>() {
            @Override
            public void onChanged(String s) {
                error.setValue(s);
            }
        });

        adapter = new FirebaseRecyclerAdapter<postModel, PostViewHolder>(repository.getPostRecyclerOptions()) {
            @NonNull
            @Override
            public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.post_item, parent, false);

                return new PostViewHolder(view);
            }


            @Override
            protected void onBindViewHolder(PostViewHolder holder, final int position, postModel model) {
                holder.setUserId(model.getUserId());
                holder.setBody(model.getBody());
            }

        };

    }

    public LiveData<Boolean> getLoadingStatus(){
        return loading;
    }

    public LiveData<String> getErrorStatus() { return error; }

    public LiveData<FirebaseUser> getCurrentUser(){
        return firebaseuser;
    }

    public void logout(){
        firebaseAuth.logOut();
    }

    public void addPost(String UserId, String Body) { repository.addPost(UserId, Body); }

    public FirebaseRecyclerAdapter<postModel, PostViewHolder> getAdapter() { return adapter; }
}
