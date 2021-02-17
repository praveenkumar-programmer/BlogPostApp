package com.geeks4ever.blogpostapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.geeks4ever.blogpostapp.R;
import com.geeks4ever.blogpostapp.viewmodel.HomeViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseUser;

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

public class AddPost extends AppCompatActivity {

    private HomeViewModel viewModel;
    private FrameLayout progress;
    private FirebaseUser currentUser;

    private TextInputEditText postEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Add Post");

        progress = findViewById(R.id.add_post_screen_progress);
        postEditText = findViewById(R.id.add_post_screen_post_edit_text);

        viewModel = new ViewModelProvider(this, new ViewModelProvider
                .AndroidViewModelFactory(this.getApplication())).get(HomeViewModel.class);
        viewModel.getCurrentUser().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                currentUser = firebaseUser;
                if(firebaseUser == null)
                    gotoLogin();
            }
        });

        viewModel.getLoadingStatus().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if ((aBoolean)) {
                    progress.setVisibility(View.VISIBLE);
                } else {
                    progress.setVisibility(View.INVISIBLE);
                }
            }
        });

        findViewById(R.id.add_post_screen_post_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(postEditText.getText() != null){
                    viewModel.addPost(currentUser.getUid() , postEditText.getText().toString());
                    finish();
                }
            }
        });
    }


    private void gotoLogin() { startActivity(new Intent(this, Login.class)); }
}