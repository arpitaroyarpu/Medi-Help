package com.project.medihelp.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.project.medihelp.R;
import com.project.medihelp.User_profiles;

import java.util.concurrent.TimeUnit;

public class admin_home extends AppCompatActivity {
    LinearLayout Ed,tr,md,st,paymnet,Surgeon;
    Dialog myDialog;
    LinearLayout firstL, scndL,thridL,missingL;
    ProgressBar progressBar;
    private String verificationId;
    private FirebaseAuth mAuth;
    EditText editTextCountryCode, editTextPhone,fammount,OTP,pinv;
    Button buttonContinue,buttonVerify,EndButton,buttonPin;
    TextView Name,vnumber,adress,drivincLicence,number,txtclose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        Ed = findViewById(R.id.EducationBtn);
        tr = findViewById(R.id.TransportBtn);
        md = findViewById(R.id.infoAdd);
        st = findViewById(R.id.statistics);
        paymnet =  findViewById(R.id.PAYMENTad);
        Surgeon = findViewById(R.id.surgeon);




        paymnet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog = new Dialog(admin_home.this);
                myDialog.setContentView(R.layout.activity_paymentsystem);
                txtclose =(TextView) myDialog.findViewById(R.id.txtclose);
                txtclose.setText("X");
                mAuth = FirebaseAuth.getInstance();
                txtclose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();
                    }
                });
                // new code

                firstL =(LinearLayout) myDialog.findViewById(R.id.firstpase);
                scndL = (LinearLayout) myDialog.findViewById(R.id.scndpase);
                thridL =  (LinearLayout)myDialog.findViewById(R.id.thridpase);
                missingL = (LinearLayout) myDialog.findViewById(R.id.missingStep);

                //first step part
                fammount =  (EditText) myDialog.findViewById(R.id.ammount);
                editTextCountryCode = (EditText) myDialog.findViewById(R.id.editTextCountryCode);
                editTextPhone = (EditText) myDialog.findViewById(R.id.editTextPhone);
                pinv = (EditText)myDialog.findViewById(R.id.editTextPin);


                buttonContinue =(Button) myDialog.findViewById(R.id.buttonContinue);
                buttonVerify =  (Button)myDialog.findViewById(R.id.buttonVerify);
                buttonPin = (Button)myDialog.findViewById(R.id.buttonPin);
                EndButton = (Button) myDialog.findViewById(R.id.done);
                //otp part
                progressBar = (ProgressBar) myDialog.findViewById(R.id.progressbar);
                OTP =(EditText) myDialog.findViewById(R.id.editTextCode);

                buttonContinue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String code = editTextCountryCode.getText().toString().trim();
                        String number = editTextPhone.getText().toString().trim();

                        if (number.isEmpty() || number.length() != 10) {
                            editTextPhone.setError("Valid number is required");
                            editTextPhone.requestFocus();
                            return;
                        }

                        String phoneNumber = code + number;
                        sendVerificationCode(phoneNumber);

                        firstL.setVisibility(View.GONE);
                        scndL.setVisibility(View.VISIBLE);
                    }
                });

                buttonVerify.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String code = OTP.getText().toString().trim();

                        if (code.isEmpty() || code.length() < 6) {

                            OTP.setError("Enter code...");
                            OTP.requestFocus();
                            return;
                        }
                        verifyCode(code);
                    }
                });

                buttonPin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String pin = pinv.getText().toString();

                        if(pin.isEmpty()){
                            pinv.setError("Enter you pin");
                            pinv.requestFocus();
                        }else {
                            missingL.setVisibility(View.GONE);
                            thridL.setVisibility(View.VISIBLE);
                        }

                    }
                });

                EndButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();
                    }
                });


                //new code end
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.show();
            }
        });


        md.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_home.this, admin_Neurologist.class);
                startActivity(intent);
            }
        });



        Ed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_home.this, admin_Cardiologist.class);
                startActivity(intent);
            }
        });


        tr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent trintent = new Intent(admin_home.this, admin_Psychiatrist.class);
                startActivity(trintent);
            }
        });
        Surgeon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent trintent = new Intent(admin_home.this, Surgeon.class);
                startActivity(trintent);
            }
        });

        st.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_home.this,Statistics.class);
                startActivity(intent);
            }
        });



    }
    public void onBackPressed() {
        Intent intent = new Intent(admin_home.this, User_profiles.class);
        intent.putExtra("key","Teacher");
        startActivity(intent);
        finish();
    }

    // FIREBASE PHONE AUTHENTICATION
    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            scndL.setVisibility(View.GONE);
                            missingL.setVisibility(View.VISIBLE);


                        } else {
                            Toast.makeText(admin_home.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }

    private void sendVerificationCode(String number) {
        progressBar.setVisibility(View.VISIBLE);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60L,
                TimeUnit.SECONDS,
                admin_home.this,
                mCallBack
        );

        progressBar.setVisibility(View.GONE);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                OTP.setText(code);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(admin_home.this, e.getMessage(), Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
        }
    };
}