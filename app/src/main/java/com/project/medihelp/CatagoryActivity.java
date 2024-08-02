package com.project.medihelp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class CatagoryActivity extends AppCompatActivity {

    CardView Neurologist,Cardiologist,Psychiatrist,surgeon;
    TextView Profile;

    Button logout;
    LinearLayout profiled;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catagory);
//        Electrician = findViewById(R.id.electrician);
        Neurologist = findViewById(R.id.education);
        Cardiologist = findViewById(R.id.TransPortcard);
        Psychiatrist = findViewById(R.id.medical);
        Profile = findViewById(R.id.profile);
        logout= findViewById(R.id.LogOut);
        profiled = findViewById(R.id.profileClick);
        surgeon = findViewById(R.id.Surgeon);


        profiled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CatagoryActivity.this, User_profiles.class);
                startActivity(intent);
            }
        });

        FirebaseFirestore dbroot = FirebaseFirestore.getInstance();
        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        System.out.println("check_id: "+currentuser);
        DocumentReference documentReference = dbroot.collection("users").document(currentuser);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    Profile.setText(documentSnapshot.getString("username"));
                }else Toast.makeText(CatagoryActivity.this, "User name not found", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CatagoryActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
            }
        });

        surgeon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatagoryActivity.this, MainActivity.class);
                intent.putExtra("key", "Surgeon");
                startActivity(intent);
            }
        });

        Neurologist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CatagoryActivity.this, MainActivity.class);
                intent.putExtra("key","Neurologist");
                startActivity(intent);
            }
        });



        Cardiologist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CatagoryActivity.this, MainActivity.class);
                intent.putExtra("key","Cardiologist");
                startActivity(intent);
            }
        });

        Psychiatrist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CatagoryActivity.this, MainActivity.class);
                intent.putExtra("key","Psychiatrist");
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CatagoryActivity.this,Login_activity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}