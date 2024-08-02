package com.project.medihelp.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.project.medihelp.R;

import java.util.ArrayList;
import java.util.List;

public class Statistics extends AppCompatActivity {

    PieChart pieChart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        PieChart pieChart = findViewById(R.id.pieChart);
        List<PieEntry> pieEntries = new ArrayList<>();

        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
        Query medicalQuery = databaseRef.child("Neurologis");
        Query teacherQuery = databaseRef.child("Cardiologist");
        Query transportQuery = databaseRef.child("Psychiatrist");

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    long countData = snapshot.getChildrenCount();
                    String category = "";
                    switch (snapshot.getRef().getKey()) {
                        case "Neurologis":
                            category = "Neurologis";
                            break;
                        case "Cardiologist":
                            category = "Cardiologist";
                            break;
                        case "Psychiatrist":
                            category = "Psychiatrist";
                            break;
                    }
                    pieEntries.add(new PieEntry(countData, category));
                    PieDataSet pieDataSet = new PieDataSet(pieEntries, "");
                    pieDataSet.setValueTextSize(16f);
                    pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                    PieData pieData = new PieData(pieDataSet);
                    pieChart.setData(pieData);
                    pieChart.invalidate();
                    pieChart.getDescription().setText("Statistics");
                    pieChart.getDescription().setTextSize(16f); // Set
                } else {
                    Toast.makeText(Statistics.this, "No data found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Statistics.this, "Database Error", Toast.LENGTH_SHORT).show();
            }
        };

        medicalQuery.addValueEventListener(listener);
        teacherQuery.addValueEventListener(listener);
        transportQuery.addValueEventListener(listener);

        pieChart.animateY(1000);

    }



    }
