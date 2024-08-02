package com.project.medihelp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.textview.MaterialTextView;

public class Activity_doctor_profile extends AppCompatActivity {

    MaterialTextView Name,SpArea,adress,number,email,chamber,study;
    ImageView img;
    private static final int REQUEST_CALL = 1;

    String Type ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);


        img = findViewById(R.id.imageView3);
        Name = findViewById(R.id.doctor_name);
        SpArea = findViewById(R.id.doctor_specialite);
        adress = findViewById(R.id.doctor_address);
        number = findViewById(R.id.doctor_phone);
        email = findViewById(R.id.doctor_email);
        study = findViewById(R.id.areaofstudy);
        chamber = findViewById(R.id.doctorChamber);
        Type = getIntent().getExtras().getString("key","defaultKey");






        String name = getIntent().getExtras().getString("name","defaultKey");
        String spArea = getIntent().getExtras().getString("specialistArea","defaultKey");
        String Address = getIntent().getExtras().getString("Address","defaultKey");
        String phone = getIntent().getExtras().getString("phone","defaultKey");
        String picurl = getIntent().getExtras().getString("picurl","defaultKey");
        String Email = getIntent().getExtras().getString("Email","defaultKey");
        String Degree = getIntent().getExtras().getString("degree","defaultKey");
        String Chamber = getIntent().getExtras().getString("chamber","defaultKey");


        adress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String location = adress.getText().toString();
                // Create an intent with the address as a query parameter
                Uri uri = Uri.parse("geo:0,0?q=" + Uri.encode(location));
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(Intent.createChooser(intent, "Open with"));


            }
        });
        number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String PhoneNumber = number.getText().toString();
                callNumber(PhoneNumber);

            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailAddress = email.getText().toString();



                // Create an Intent to send an email
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", emailAddress, null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Test mail");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "My email message");

                // Launch the email app
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }
        });





        SpArea.setText(spArea);
        Name.setText(name);
        email.setText(Email);
        adress.setText(Address);
        number.setText(phone);
        chamber.setText(Chamber);
        study.setText(Degree);
        Glide.with(Activity_doctor_profile.this).load(picurl).into(img);


    }

    private void callNumber(String number) {

        Intent callIntent = new Intent(Intent.ACTION_CALL);

        callIntent.setData(Uri.parse("tel:" + number));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(Activity_doctor_profile.this, "Please grant the call permission to proceed.", Toast.LENGTH_LONG).show();
            return;
        }
        startActivityForResult(callIntent, REQUEST_CALL);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CALL) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(Activity_doctor_profile.this, "Call sent successfully.", Toast.LENGTH_LONG).show();
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(Activity_doctor_profile.this, "Call failed. Please try again.", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void onBackPressed() {
        System.out.println("CheckType: "+Type);
        Intent intent = new Intent(Activity_doctor_profile.this,MainActivity.class);
        intent.putExtra("key",Type);
        startActivity(intent);
        finish();
    }
}