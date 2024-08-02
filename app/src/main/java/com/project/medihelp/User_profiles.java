package com.project.medihelp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.project.medihelp.admin.admin_home;

public class User_profiles extends AppCompatActivity {

    TextView Uname,Uadress,Umail;

    ImageView prPic;
    Button editpof;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profiles);

        Uname = findViewById(R.id.uname);
        Uadress = findViewById(R.id.uaddress);
        Umail  = findViewById(R.id.umail);
        prPic = findViewById(R.id.imguploadpd);
        editpof = findViewById(R.id.editPro);

        editpof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(User_profiles.this, admin_home.class);
                startActivity(intent);
                finish();
            }
        });

        FirebaseFirestore dbroot = FirebaseFirestore.getInstance();
        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = dbroot.collection("users").document(currentuser);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    Uname.setText(documentSnapshot.getString("username"));
                    Uadress.setText(documentSnapshot.getString("address"));
                    Umail.setText(documentSnapshot.getString("email"));
                    Glide.with(User_profiles.this).load(documentSnapshot.getString("pic")).into(prPic);
                }else Toast.makeText(User_profiles.this, "User name not found", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(User_profiles.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
            }
        });


    }
}