package com.example.grocerylistapplogin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.grocerylistapplogin.models.UserModel;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.nullness.qual.NonNull;

public class AccountActivity extends AppCompatActivity {


    Button homePage;

    TextView profileName, profileEmail, profilePassword;
    FirebaseAuth auth;
    DatabaseReference userReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);



        homePage = findViewById(R.id.home);

        homePage.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){

                startActivity(new Intent(AccountActivity.this,MainActivity.class));
            }
        });


//////////////////////////////////////// Display Account Details

        profileName = findViewById(R.id.account_name);
        profileEmail = findViewById(R.id.account_email);
        profilePassword = findViewById(R.id.account_password);

        auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();


        if (currentUser != null) {
            String userId = currentUser.getUid();
            userReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
            userReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        UserModel userModel = dataSnapshot.getValue(UserModel.class);
                        if (userModel != null) {
                            profileName.setText(userModel.getName());
                            profileEmail.setText(userModel.getEmail());
                            profilePassword.setText(userModel.getPassword());
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                    Log.e("AccountActivity", "Database error: " + databaseError.getMessage());

                    Toast.makeText(AccountActivity.this, "Failed to retrieve user data", Toast.LENGTH_SHORT).show();
                }
            });
        }

        ////////////////////////////////////////// Logout

        Button logoutButton = findViewById(R.id.logout_btn);


        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();

                startActivity(new Intent(AccountActivity.this, LoginActivity.class));

                finish();
            }
        });
    }

}