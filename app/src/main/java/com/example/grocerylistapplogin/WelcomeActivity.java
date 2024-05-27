package com.example.grocerylistapplogin;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Menu;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.grocerylistapplogin.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

public class WelcomeActivity extends AppCompatActivity {


    ProgressBar progressBar;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*binding = ActivityMainBinding.inflate(getLayoutInflater());*/
        setContentView(R.layout.activity_welcome);

        auth = FirebaseAuth.getInstance();

        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);



        if(auth.getCurrentUser() != null){

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.VISIBLE);
                    Toast.makeText(WelcomeActivity.this, "Please Wait, Logging In", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                }
            }, 2000);
        }


    }


    public void login(View view) {
        startActivity(new Intent(WelcomeActivity.this,LoginActivity.class));
    }

    public void registration(View view) {

        startActivity(new Intent(WelcomeActivity.this,RegistrationActivity.class));
    }
}