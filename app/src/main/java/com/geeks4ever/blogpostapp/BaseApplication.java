package com.geeks4ever.blogpostapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.google.firebase.auth.FirebaseUser;

public class BaseApplication extends AppCompatActivity {

    LiveData<FirebaseUser> currentUser;

}
