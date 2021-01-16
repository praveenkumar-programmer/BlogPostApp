package com.geeks4ever.blogpostapp.model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class myFirebaseAuth {

    FirebaseAuth firebaseAuth;
    final MutableLiveData<FirebaseUser> currentUser = new MutableLiveData<>();
    final MutableLiveData<Boolean> loading = new MutableLiveData<>();

    final MutableLiveData<String> error = new MutableLiveData<>();

    static myFirebaseAuth myFirebaseAuth;

    private myFirebaseAuth(){

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser.setValue(firebaseAuth.getCurrentUser());
        loading.setValue(false);

    }

    public static myFirebaseAuth getInstance(){

        if(myFirebaseAuth == null){
            myFirebaseAuth = new myFirebaseAuth();
        }
        return myFirebaseAuth;

    }

    public LiveData<String> getError() { return error; }

    public LiveData<FirebaseUser> getCurrentUser(){
        return currentUser;
    }

    public LiveData<Boolean> getLoadingStatus(){
        return loading;
    }

    public void logOut(){
        loading.setValue(true);
        firebaseAuth.signOut();
        currentUser.setValue(null);
        loading.setValue(false);
    }

    public void signUp(String email, String password){

        loading.setValue(true);

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(!task.isSuccessful()) {
                            error.setValue(Objects.requireNonNull(task.getException()).getMessage());
                        }
                        else
                            currentUser.setValue(firebaseAuth.getCurrentUser());
                        loading.setValue(false);
                    }
                });

    }

    public void logIn(String email, String password){

        loading.setValue(true);

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(!task.isSuccessful()) {
                            error.setValue(Objects.requireNonNull(task.getException()).getMessage());
                        }
                        else
                            currentUser.setValue(firebaseAuth.getCurrentUser());
                        loading.setValue(false);
                    }
                });
    }

}
