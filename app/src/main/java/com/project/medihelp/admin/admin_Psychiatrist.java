package com.project.medihelp.admin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.project.medihelp.model.PsychiatristHelper;
import com.project.medihelp.R;

import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class admin_Psychiatrist extends AppCompatActivity {
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private AlertDialog progressAlertDialog;
    TextInputLayout name, specialist, mobileNumber, email, location,degree,chamber;
    Button srcbtn;
    ImageView imgupl, GeoL;
    Uri imageUri;
    Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psychiatrist);
        name = findViewById(R.id.driverName);
        specialist = findViewById(R.id.Vnumber);
        mobileNumber = findViewById(R.id.Mnumber);
        email = findViewById(R.id.DlNumber);
        location = findViewById(R.id.location);
        imgupl = findViewById(R.id.imguploadT);
        srcbtn = findViewById(R.id.trsubmitBtn);
        degree = findViewById(R.id.textField12fd1);
        chamber = findViewById(R.id.textFieldd125);



        imgupl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Image File"), 1);

            }
        });

        srcbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String Name = name.getEditText().getText().toString();
                String Specialist = specialist.getEditText().getText().toString();
                String MobileNumber = mobileNumber.getEditText().getText().toString();
                String Email = email.getEditText().getText().toString();
                String Adrees = location.getEditText().getText().toString().trim();
                String Degree = degree.getEditText().getText().toString();
                String Chamber = chamber.getEditText().getText().toString();




                if (TextUtils.isEmpty(Name)) {
                    name.setError("Name cannot be empty");
                    name.requestFocus();
                } else if (TextUtils.isEmpty(Specialist)) {
                    specialist.setError("VihicleNumber cannot be empty");
                    specialist.requestFocus();
                } else if (TextUtils.isEmpty(MobileNumber)) {
                    mobileNumber.setError("MobileNumber cannot be empty");
                    mobileNumber.requestFocus();
                } else if (TextUtils.isEmpty(Email)) {
                    email.setError("DrivingLNumber cannot be empty");
                    email.requestFocus();

                } else if (TextUtils.isEmpty(Adrees)) {
                    location.setError("Location cannot be empty");
                    location.requestFocus();

                }
                else if(TextUtils.isEmpty(Degree)){
                    degree.setError("Degree cannot be empty");
                    degree.requestFocus();

                }
                else if(TextUtils.isEmpty(Chamber)){
                    chamber.setError("Chamber cannot be empty");
                    chamber.requestFocus();

                }
                else {


                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    StorageReference uploder = storage.getReference("Image1" + new Random().nextInt(50));

                    uploder.putFile(imageUri)
                            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                    if (progressAlertDialog == null) {
                                        AlertDialog.Builder builder2 = new AlertDialog.Builder(admin_Psychiatrist.this);
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
                                            DatabaseReference root = rootNode.getReference("Psychiatrist");
                                            PsychiatristHelper helper = new PsychiatristHelper(Name, Specialist, MobileNumber, Email, Adrees,currentuser, uri.toString(),Degree,Chamber);

                                            String key = root.push().getKey();
                                            root.child(key).setValue(helper);

                                            name.getEditText().setText("");
                                            specialist.getEditText().setText("");
                                            mobileNumber.getEditText().setText("");
                                            email.getEditText().setText("");
                                            location.getEditText().setText("");
                                            imgupl.setImageResource(R.drawable.add_img_24);
                                            Toast.makeText(admin_Psychiatrist.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }
                            });
                }
            }
        });
    }


    private void getlocation() {
        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        client.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    TextInputLayout Location;
                    Location = findViewById(R.id.location);
                    Location.getEditText().setText("");
                    // location found
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    // update your UI or save the location to a database
                    try {
                        Geocoder geocoder = new Geocoder(admin_Psychiatrist.this, Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        String address = addresses.get(0).getAddressLine(0);
                        Location.getEditText().setText(address);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    // location not found, try again or show an error message
                    Toast.makeText(admin_Psychiatrist.this, "location not found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }




    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode ==1 && resultCode == RESULT_OK ){

            imageUri = data.getData();

            try {
                InputStream inputStream  = admin_Psychiatrist.this.getContentResolver().openInputStream(imageUri);
                bitmap = BitmapFactory.decodeStream(inputStream);
                imgupl.setImageBitmap(bitmap);

            }catch (Exception e)
            {

            }



        }
    }


}