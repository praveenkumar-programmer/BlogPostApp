package com.geeks4ever.blogpostapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.geeks4ever.blogpostapp.R;
import com.geeks4ever.blogpostapp.viewmodel.AuthViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class signUp extends AppCompatActivity {

    private AuthViewModel viewModel;
    private FrameLayout progress;

    private TextInputEditText emailEditText, passwordEditText, confirmPasswordEditText;

    private CoordinatorLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Sign Up");

        progress = findViewById(R.id.sign_up_screen_progress);
        emailEditText = findViewById(R.id.sign_up_screen_email_edit_text);
        passwordEditText = findViewById(R.id.sign_up_screen_password_edit_text);
        confirmPasswordEditText = findViewById(R.id.sign_up_screen_confirm_password_edit_text);
        root = findViewById(R.id.sign_up_screen_root_layout);

        findViewById(R.id.sign_up_screen_sign_up_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

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

    private void signup(){

        if(emailEditText.getText() != null && passwordEditText.getText() != null && confirmPasswordEditText.getText() != null){
            String Email = emailEditText.getText().toString();
            String Password = passwordEditText.getText().toString();
            String ConfirmPassword = confirmPasswordEditText.getText().toString();

            if(Password.equals(ConfirmPassword))
                viewModel.signUp(Email, Password);
            else
                Toast.makeText(this, "Passwords Don't Match!", Toast.LENGTH_SHORT).show();

        }

        viewModel.getError().observeForever(new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s != null){
                    Snackbar.make(root, s, Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    private void gotoHome() { startActivity(new Intent(this, Home.class)); finish();}
}