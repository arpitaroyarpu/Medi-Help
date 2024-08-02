package com.project.medihelp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.project.medihelp.model.model;


import java.io.InputStream;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Signin_activity extends AppCompatActivity {

    private AlertDialog progressAlertDialog;
    EditText username, email, address, pass, confirmpass;
    Button Register, logedIn;
    ImageView imgupl;
    Uri imageUri;
    Bitmap bitmap;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        address = findViewById(R.id.address);
        pass = findViewById(R.id.pass);
        confirmpass = findViewById(R.id.confirmpass);
        Register = findViewById(R.id.Register);
        logedIn = findViewById(R.id.logedIn);
        imgupl = findViewById(R.id.imguploadp);





        imgupl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Image File"), 1);

            }
        });


        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Username = username.getText().toString();
                String Email = email.getText().toString().trim();
                String Address = address.getText().toString();
                String Pass = pass.getText().toString();
                String Confirmpass = confirmpass.getText().toString();

                String emailRegex = "^[a-zA-Z0-9._%+-]+@(gmail|yahoo)\\.com$";
                Pattern pattern = Pattern.compile(emailRegex);
                Matcher matcher = pattern.matcher(Email);

//                String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,}$\n";
//                Pattern passpattern = Pattern.compile(passwordRegex);
//                Matcher passmatcher = passpattern.matcher(Pass.trim());

                if (Username.isEmpty()) {
                    username.setError("Username can't empty");
                    username.requestFocus();
                } else if (TextUtils.isEmpty(Email)) {
                    email.setError("Email Can't Empty");
                    email.requestFocus();
                } else if (TextUtils.isEmpty(Address)) {
                    address.setError("Adress Can't Empty");
                    address.requestFocus();
                } else if (TextUtils.isEmpty(Pass)) {
                    pass.setError("Password Can't Empty");
                    pass.requestFocus();
                }
//                else if (!passmatcher.matches()) {
//                    pass.setError("one lowercase letter!, one uppercase letter!, one digit!, minimum 6 digit");
//                    pass.requestFocus();
//                }
                else if (TextUtils.isEmpty(Confirmpass)) {
                    confirmpass.setError("CnfirmPass Can't Empty");
                    confirmpass.requestFocus();
                }

                else {

                    if(matcher.matches()) {

                        FirebaseStorage storage = FirebaseStorage.getInstance();
                        StorageReference uploder = storage.getReference("Image1" + new Random().nextInt(50));

                        uploder.putFile(imageUri)
                                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                        if (progressAlertDialog == null) {
                                            AlertDialog.Builder builder2 = new AlertDialog.Builder(Signin_activity.this);
                                            builder2.setTitle("Alert !");
                                            builder2.setMessage("Uploaded: 0%");
                                            builder2.setPositiveButton(
                                                    "Ok",
                                                    new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {
                                                            dialog.cancel();
                                                        }
                                                    });

                                            progressAlertDialog = builder2.create();
                                            progressAlertDialog.show();
                                        }

                                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                                        progressAlertDialog.setMessage("Uploaded: " + (int) progress + "%");
                                    }
                                })
                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                        uploder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {

                                                firebaseAuth.createUserWithEmailAndPassword(Email, Pass)
                                                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                                            @Override
                                                            public void onSuccess(AuthResult authResult) {
                                                                firebaseFirestore.collection("users")
                                                                        .document(FirebaseAuth.getInstance().getUid())
                                                                        .set(new model(Username, Email, Address, Pass, Confirmpass, uri.toString()));
                                                                Toast.makeText(Signin_activity.this, "Registration successfull!", Toast.LENGTH_LONG).show();
                                                                Intent intent = new Intent(Signin_activity.this, CatagoryActivity.class);
                                                                startActivity(intent);
                                                                finish();

                                                            }
                                                        }).addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Toast.makeText(Signin_activity.this, "Registration failed!", Toast.LENGTH_LONG).show();

                                                            }
                                                        });
                                            }
                                        });

                                    }
                                });

                        //old code
                    }else{
                        email.setError("Enter valid email");
                        email.requestFocus();
                    }

                }
            }
        });

        logedIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Signin_activity.this, Login_activity.class);
                startActivity(myIntent);
                finish();
            }
        });
    }


    // image uri and bitmap

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 1 && resultCode == RESULT_OK) {

            imageUri = data.getData();

            try {
                InputStream inputStream = Signin_activity.this.getContentResolver().openInputStream(imageUri);
                bitmap = BitmapFactory.decodeStream(inputStream);
                imgupl.setImageBitmap(bitmap);

            } catch (Exception e) {

            }
        }

    }
}