package com.project.medihelp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login_activity extends AppCompatActivity {
    EditText userMail,userPass;
    TextView ForgetPassword;
    Button Login, signup;
    FirebaseAuth mAuth;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userMail = findViewById(R.id.email);
        userPass = findViewById(R.id.passwords);
        Login = findViewById(R.id.signIn);
        signup = findViewById(R.id.signUp);
        ForgetPassword = findViewById(R.id.forgetPass);



        //forgetPassword


            ForgetPassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
               /* progressDialog.setTitle("Email Sending...");
                progressDialog.show();*/
                    String mail = userMail.getText().toString().trim();
                    if (mail.isEmpty())
                    {
                        userMail.setError("Email Can't empty");
                        userMail.requestFocus();
                    }
                    else {
                        mAuth.sendPasswordResetEmail(userMail.getText().toString().trim())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        //progressDialog.cancel();
                                        Toast.makeText(Login_activity.this, "Send Email", Toast.LENGTH_SHORT).show();

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        //progressDialog.cancel();
                                        Toast.makeText(Login_activity.this, "Email Send Failed", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }
            });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = userMail.getText().toString().trim();
                String pass = userPass.getText().toString();

                String emailRegex = "^[a-zA-Z0-9._%+-]+@(gmail|yahoo)\\.com$";
                Pattern pattern = Pattern.compile(emailRegex);
                Matcher matcher = pattern.matcher(email);

//                String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,}$\n";
//                Pattern passpattern = Pattern.compile(passwordRegex);
//                Matcher passmatcher = passpattern.matcher(pass.trim());


                if(TextUtils.isEmpty(email)){
                    userMail.setError("Email can't empty");
                    userMail.requestFocus();
                }else if(TextUtils.isEmpty(pass)){
                    userPass.setError("Password Can't Empty");
                    userPass.requestFocus();
                }
//                else if (!passmatcher.matches()) {
//                    userPass.setError("one lowercase letter!, one uppercase letter!, one digit!, minimum 6 digit");
//                    userPass.requestFocus();
//
//                }
                else {

                    if(matcher.matches()) {
                        mAuth = FirebaseAuth.getInstance();

                        mAuth.signInWithEmailAndPassword(email, pass)
                                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {


                                        Intent myintent = new Intent(Login_activity.this, CatagoryActivity.class);
                                        startActivity(myintent);

                                        Toast.makeText(Login_activity.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                        Toast.makeText(Login_activity.this, "Login Failed", Toast.LENGTH_SHORT).show();

                                    }
                                });

                    }
                    else{
                        userMail.setError("Enter valid email");
                        userMail.requestFocus();
                    }
                }

            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Login_activity.this, Signin_activity.class);
                startActivity(myIntent);
            }
        });
    }
}