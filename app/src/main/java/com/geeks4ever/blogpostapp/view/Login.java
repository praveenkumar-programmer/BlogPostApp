package com.geeks4ever.blogpostapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.geeks4ever.blogpostapp.R;
import com.geeks4ever.blogpostapp.viewmodel.AuthViewModel;
import com.google.android.material.snackbar.Snackbar;
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

public class Login extends AppCompatActivity {

    private AuthViewModel viewModel;
    private FrameLayout progress;
    private TextInputEditText emailEditText, passwordEditText;
    private CoordinatorLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Login");

        progress = findViewById(R.id.login_screen_progress);
        root = findViewById(R.id.login_screen_root_layout);

        findViewById(R.id.login_screen_login_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        findViewById(R.id.login_screen_signup_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

        emailEditText = findViewById(R.id.login_screen_email_edit_text);
        passwordEditText = findViewById(R.id.login_screen_password_edit_text);

        viewModel = new ViewModelProvider(this, new ViewModelProvider
                .AndroidViewModelFactory(this.getApplication())).get(AuthViewModel.class);
        viewModel.getCurrentUser().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if(firebaseUser != null)
                    gotoHome();
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

    }

    private void login(){
        if(emailEditText.getText() != null && passwordEditText.getText() != null)
            viewModel.login(emailEditText.getText().toString(), passwordEditText.getText().toString());


        viewModel.getErrorStatus().observeForever(new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s != null){
                    Snackbar.make(root, s, Snackbar.LENGTH_LONG).show();
                }
            }
        });

    }

    private void signUp(){
        startActivity(new Intent(this, signUp.class)); finish();
    }

    private void gotoHome() { startActivity(new Intent(this, Home.class)); finish();}
}