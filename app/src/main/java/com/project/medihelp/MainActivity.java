package com.project.medihelp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.project.medihelp.Adapter.NeurologistMyAdapter;
import com.project.medihelp.Adapter.PsychiatristAdapter;
import com.project.medihelp.model.PsychiatristHelper;
import com.project.medihelp.Adapter.CardiologistAdapter;
import com.project.medihelp.model.userHelper;
import com.project.medihelp.model.modelAdapter;


public class MainActivity extends AppCompatActivity {

    RecyclerView recview;
    Button srcbtn;
    EditText edSec;
    FirebaseRecyclerAdapter adapter;
   SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recview = findViewById(R.id.recview);
        searchView = findViewById(R.id.searchA);
        searchView.setFocusable(true);
        searchView.requestFocusFromTouch();
        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        String data = getIntent().getExtras().getString("key","defkey");


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String newText) {
                if(data.equals("Neurologist")){
                    filterList(newText);
                }
                else if(data.equals("Cardiologist")){
                    filterListT(newText);
                }
                else if(data.equals("Psychiatrist")){
                    filterListTM(newText);
                }
                else if(data.equals("Surgeon")){
                    filterListTM(newText);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(data.equals("Neurologist")){
                    filterList(newText);
                }
                else if(data.equals("Cardiologist")){
                    filterListT(newText);
                }
                else if(data.equals("Psychiatrist")){
                    filterListTM(newText);
                }
                else if(data.equals("Surgeon")){
                    filterListTM(newText);
                }
                return false;
            }
        });

        if(data.equals("Neurologist")){
            recview.setLayoutManager((new LinearLayoutManager(MainActivity.this)));
            FirebaseRecyclerOptions<modelAdapter> options =
                    new FirebaseRecyclerOptions.Builder<modelAdapter>()
                            .setQuery(FirebaseDatabase.getInstance().getReference().child("Neurologis"), modelAdapter.class)
                            .build();

            adapter = new NeurologistMyAdapter(options);
            recview.setAdapter(adapter);

        }else if(data.equals("Cardiologist")){
            recview.setLayoutManager((new LinearLayoutManager(MainActivity.this)));
            FirebaseRecyclerOptions<userHelper> toptions =
                    new FirebaseRecyclerOptions.Builder<userHelper>()
                            .setQuery(FirebaseDatabase.getInstance().getReference().child("Cardiologist"), userHelper.class)
                            .build();

            adapter = new CardiologistAdapter(toptions);
            recview.setAdapter(adapter);
        }else if(data.equals("Psychiatrist")){
            recview.setLayoutManager((new LinearLayoutManager(MainActivity.this)));
            FirebaseRecyclerOptions<PsychiatristHelper> toptions =
                    new FirebaseRecyclerOptions.Builder<PsychiatristHelper>()
                            .setQuery(FirebaseDatabase.getInstance().getReference().child("Psychiatrist"), PsychiatristHelper.class)
                            .build();

            adapter = new PsychiatristAdapter(toptions);
            recview.setAdapter(adapter);

        } else if (data.equals("Surgeon")) {
            recview.setLayoutManager((new LinearLayoutManager(MainActivity.this)));
            FirebaseRecyclerOptions<modelAdapter> options =
                    new FirebaseRecyclerOptions.Builder<modelAdapter>()
                            .setQuery(FirebaseDatabase.getInstance().getReference().child("Surgeon"), modelAdapter.class)
                            .build();

            adapter = new NeurologistMyAdapter(options);
            recview.setAdapter(adapter);
            
        }

    }

    private void filterListTM(String newText) {

        FirebaseRecyclerOptions<PsychiatristHelper> toptions =
                new FirebaseRecyclerOptions.Builder<PsychiatristHelper>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Psychiatrist").orderByChild("specialist").startAt(newText), PsychiatristHelper.class)
                        .build();
        adapter = new PsychiatristAdapter(toptions);
        adapter.startListening();
        recview.setAdapter(adapter);
    }

    private void filterListT(String newText) {
        FirebaseRecyclerOptions<userHelper> toptions =
                new FirebaseRecyclerOptions.Builder<userHelper>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Cardiologist").orderByChild("specialist").startAt(newText), userHelper.class)
                        .build();
        adapter = new CardiologistAdapter(toptions);
        adapter.startListening();
        recview.setAdapter(adapter);
    }

    private void filterList(String newText) {

        FirebaseRecyclerOptions<modelAdapter> options =
                new FirebaseRecyclerOptions.Builder<modelAdapter>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Neurologis").orderByChild("name").startAt(newText), modelAdapter.class)
                        .build();
        adapter = new NeurologistMyAdapter(options);
        adapter.startListening();
        recview.setAdapter(adapter);

    }

    private void filterListS(String newText) {

        FirebaseRecyclerOptions<modelAdapter> options =
                new FirebaseRecyclerOptions.Builder<modelAdapter>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Surgeon").orderByChild("name").startAt(newText), modelAdapter.class)
                        .build();
        adapter = new NeurologistMyAdapter(options);
        adapter.startListening();
        recview.setAdapter(adapter);

    }

    @Override
    protected void onStart(){
        super.onStart();
        adapter.startListening();
    }
    @Override
    protected void onStop()
    {
        super.onStop();
        adapter.stopListening();
    }



}