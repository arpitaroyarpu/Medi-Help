package com.project.medihelp.admin;

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
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.project.medihelp.model.userHelper;
import com.project.medihelp.R;

import java.io.InputStream;
import java.util.Random;

public class admin_Cardiologist extends AppCompatActivity {

    private AlertDialog progressAlertDialog;
    TextInputLayout name,designation,afs,email,phone,adress,degree,chamber;
    Button srcbtn;
    ImageView imgupl;
    Uri imageUri;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_cardiologist);
        name = findViewById(R.id.textField1);
        designation = findViewById(R.id.textField2);
        afs = findViewById(R.id.textField3);
        email = findViewById(R.id.textField4);
        phone = findViewById(R.id.textField5);
        adress = findViewById(R.id.textField6);
        srcbtn = findViewById(R.id.submitBtn);
        imgupl = findViewById(R.id.imgupload);
        degree = findViewById(R.id.textField1221);
        chamber = findViewById(R.id.textField1425);

        imgupl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                                Intent intent =  new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent, "Select Image File"), 1);

            }
        });

        srcbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String Name = name.getEditText().getText().toString();
                String Designation = designation.getEditText().getText().toString();
                String Aera_of_study = afs.getEditText().getText().toString();
                String Email = email.getEditText().getText().toString();
                String Phone = phone.getEditText().getText().toString().trim();
                String Adress = adress.getEditText().getText().toString();
                String Degree = degree.getEditText().getText().toString();
                String Chamber = chamber.getEditText().getText().toString();

//                String k_picurl = imageUri.toString();


                if(TextUtils.isEmpty(Name)){
                    name.setError("Name cannot be empty");
                    name.requestFocus();
                }else if(TextUtils.isEmpty(Designation)){
                    designation.setError("Designation cannot be empty");
                    designation.requestFocus();
                }else if(TextUtils.isEmpty(Aera_of_study)){
                    afs.setError("Aera_of_study cannot be empty");
                    afs.requestFocus();
                }else if(TextUtils.isEmpty(Email)){
                    email.setError("Email cannot be empty");
                    email.requestFocus();

                }else if(TextUtils.isEmpty(Phone)){
                    phone.setError("Number cannot be empty");
                    phone.requestFocus();

                }else if(TextUtils.isEmpty(Adress)){
                    adress.setError("Shop Address cannot be empty");
                    adress.requestFocus();
                }
                else if(TextUtils.isEmpty(Degree)){
                    adress.setError("Degree cannot be empty");
                    adress.requestFocus();

                }
                else if(TextUtils.isEmpty(Chamber)){
                    adress.setError("Chamber cannot be empty");
                    adress.requestFocus();

                }
                else if(imageUri == null){
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(admin_Cardiologist.this);
                    builder1.setTitle("Alert !");
                    builder1.setMessage("Image can't selected ! Please Select Image.");
//                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });



                    AlertDialog alert11 = builder1.create();
                    alert11.show();

                }
                else{


                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    StorageReference uploder = storage.getReference("Image1"+new Random().nextInt(50));

                    uploder.putFile(imageUri)
                            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                    if (progressAlertDialog == null) {
                                        AlertDialog.Builder builder2 = new AlertDialog.Builder(admin_Cardiologist.this);
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

                                            String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                            FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
                                            DatabaseReference root = rootNode.getReference("Cardiologist");
                                            userHelper helper = new userHelper(Name,Designation,Aera_of_study,Email,Phone,Adress,currentuser,uri.toString(),Degree,Chamber);

                                            String key = root.push().getKey();
                                            root.child(key).setValue(helper);

                                            name.getEditText().setText("");
                                            designation.getEditText().setText("");
                                            adress.getEditText().setText("");
                                            afs.getEditText().setText("");
                                            phone.getEditText().setText("");
                                            email.getEditText().setText("");
                                            imgupl.setImageResource(R.drawable.empty_profile_img);
                                            Toast.makeText(admin_Cardiologist.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }
                            });
                }
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode ==1 && resultCode == RESULT_OK ){

            imageUri = data.getData();

            try {
                InputStream inputStream  = admin_Cardiologist.this.getContentResolver().openInputStream(imageUri);
                bitmap = BitmapFactory.decodeStream(inputStream);
                imgupl.setImageBitmap(bitmap);

            }catch (Exception e)
            {

            }



        }
    }

}