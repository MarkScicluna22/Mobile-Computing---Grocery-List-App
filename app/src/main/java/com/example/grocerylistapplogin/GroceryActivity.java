package com.example.grocerylistapplogin;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class GroceryActivity extends AppCompatActivity {


    Button saveButton;
    EditText listName, listAmount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_grocery);

        saveButton = findViewById(R.id.saveButton);
        listName = findViewById(R.id.listName);
        listAmount = findViewById(R.id.listAmount);

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {

                        if(result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();

                        } else{
                            Toast.makeText(GroceryActivity.this, "No Name Entered", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );


        ///////////////// Add item to list

        saveButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){

                uploadData();
            }
        });



    }


    public void uploadData(){

        String name = listName.getText().toString().trim();
        String amount = listAmount.getText().toString();


        if (name.isEmpty()) {
            Toast.makeText(GroceryActivity.this, "Please enter a name", Toast.LENGTH_SHORT).show();
            return;
        }



        // Get the current user's ID
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser == null){
            // User not logged in
            Toast.makeText(GroceryActivity.this, "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = currentUser.getUid();

        DatabaseReference userListsRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("Lists");


        DataClass dataClass = new DataClass(name, amount);


        /////////// Save the data under the user's lists node
        userListsRef.child(name).setValue(dataClass)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(GroceryActivity.this, "Added", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(GroceryActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}