package com.geeks4ever.blogpostapp.model.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.geeks4ever.blogpostapp.model.postModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/*
 *  Created by Praveen Kumar on 17/2/21 9:22 PM for BlogPostApp.
 *  Copyright (c) 2021.
 *  Last modified 17/2/21 9:02 PM.
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

public class myRepository {

    private static myRepository myfirebase;
    private FirebaseDatabase firebaseDatabase;

    final MutableLiveData<Boolean> loading = new MutableLiveData<>();

    final MutableLiveData<String> error = new MutableLiveData<>();

    private FirebaseRecyclerOptions<postModel> postRecyclerOptions;

    private myRepository(){

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.setPersistenceEnabled(true);
        firebaseDatabase.getReference().child("posts").keepSynced(true);

        postRecyclerOptions = new FirebaseRecyclerOptions.Builder<postModel>()
                    .setQuery(firebaseDatabase.getReference().child("posts"),
                            new SnapshotParser<postModel>() {
                        @NonNull
                        @Override
                        public postModel parseSnapshot(@NonNull DataSnapshot snapshot) {
                            return new postModel(Objects.requireNonNull(snapshot.child("post_id").getValue()).toString(),
                                    Objects.requireNonNull(snapshot.child("user_id").getValue()).toString(),
                                    Objects.requireNonNull(snapshot.child("body").getValue()).toString());
                        }
                    })
                    .build();

    }

    public static myRepository getInstance(){

        if(myfirebase == null)
            myfirebase = new myRepository();
        return myfirebase;

    }

    public void addPost(String userId, String body){

        loading.setValue(true);

        DatabaseReference databaseReference = firebaseDatabase.getReference().child("posts").push();
        Map<String, Object> map = new HashMap<>();
        map.put("post_id", databaseReference.getKey());
        map.put("user_id", userId);
        map.put("body", body);

        databaseReference.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(!task.isSuccessful())
                    error.setValue(Objects.requireNonNull(task.getException()).getMessage());
                loading.setValue(false);
            }
        });

        loading.setValue(false);
    }


    public LiveData<String> getError() { return error; }

    public LiveData<Boolean> getLoadingStatus(){
        return loading;
    }

    public FirebaseRecyclerOptions<postModel> getPostRecyclerOptions() {
        return postRecyclerOptions;
    }


}
