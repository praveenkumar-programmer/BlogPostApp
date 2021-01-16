package com.geeks4ever.blogpostapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.geeks4ever.blogpostapp.R;
import com.geeks4ever.blogpostapp.viewmodel.HomeViewModel;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class Home extends AppCompatActivity {

    private HomeViewModel viewModel;

    private FrameLayout progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Home");

        progress = findViewById(R.id.home_screen_progress);

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

    private void exit() {
        finish();
    }

    private void logout() {
        viewModel.logout();
    }

    private void gotoLogin() {

        startActivity(new Intent(this, Login.class));

    }
}