package com.geeks4ever.blogpostapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.geeks4ever.blogpostapp.R;
import com.geeks4ever.blogpostapp.model.postModel;
import com.geeks4ever.blogpostapp.view.viewholder.PostViewHolder;
import com.geeks4ever.blogpostapp.viewmodel.HomeViewModel;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

/*
 *  Created by Praveen Kumar on 17/2/21 9:22 PM for BlogPostApp.
 *  Copyright (c) 2021.
 *  Last modified 17/2/21 9:21 PM.
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

public class Home extends AppCompatActivity {

    private HomeViewModel viewModel;
    private FrameLayout progress;
    private MaterialTextView errorText;

    private FirebaseRecyclerAdapter<postModel, PostViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Home");

        progress = findViewById(R.id.home_screen_progress);
        RecyclerView recyclerView = findViewById(R.id.home_screen_posts_recycler_view);
        errorText = findViewById(R.id.home_screen_status_text);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        viewModel = new ViewModelProvider(this, new ViewModelProvider
                .AndroidViewModelFactory(this.getApplication())).get(HomeViewModel.class);
        viewModel.getCurrentUser().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
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

        viewModel.getErrorStatus().observeForever(new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s != null){
                    if(errorText.getVisibility() != View.VISIBLE)
                        errorText.setVisibility(View.VISIBLE);
                    errorText.setText(s);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            errorText.setText("");
                            if(errorText.getVisibility() == View.VISIBLE)
                                errorText.setVisibility(View.GONE);
                        }
                    }, 2000);
                }
            }
        });

        findViewById(R.id.home_screen_add_post_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoAddPost();
            }
        });

        adapter = viewModel.getAdapter();
        recyclerView.setAdapter(adapter);

    }

    private void gotoAddPost() { startActivity(new Intent(this, AddPost.class)); }

    @Override
    protected void onStart() {
        super.onStart();
        if(adapter != null)
            adapter.startListening();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(adapter != null)
            adapter.startListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int itemId = item.getItemId();
        if (itemId == R.id.menu_item_logout) {
            logout();
            return true;
        } else if (itemId == R.id.menu_item_exit) {
            exit();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    private void exit() { finish(); }

    private void logout() { viewModel.logout(); }

    private void gotoLogin() { startActivity(new Intent(this, Login.class)); }
}