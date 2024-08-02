package com.project.medihelp.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.medihelp.Activity_doctor_profile;
import com.project.medihelp.R;
import com.project.medihelp.model.PsychiatristHelper;

public class PsychiatristAdapter extends FirebaseRecyclerAdapter<PsychiatristHelper, PsychiatristAdapter.myviewholder>{





    public PsychiatristAdapter(@NonNull FirebaseRecyclerOptions<PsychiatristHelper> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull PsychiatristAdapter.myviewholder holder, int position, @NonNull PsychiatristHelper model) {



        holder.Name.setText(model.getName());
        holder.designation.setText(model.getSpecialist());

        Glide.with(holder.imgl.getContext()).load(model.getPic()).into(holder.imgl);

        holder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.imgl.getContext());
                builder.setTitle("Delete Post");
                builder.setMessage("Delete....!");
                builder.setPositiveButton("Yes",(dialog, i) -> {
                    String currentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();

                    FirebaseDatabase.getInstance().getReference().child("Psychiatrist")
                            .orderByChild("curentUID")
                            .equalTo(currentUserID)
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                    Log.d(TAG, "onDataChange called");
                                    for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
//                                        Log.d(TAG, "childSnapshot: " + childSnapshot.getValue());
                                        System.out.println("childSnapshot: " + childSnapshot.getValue());
                                        String key = childSnapshot.getKey();
                                        if (key.equals(getRef(position).getKey())) {
                                            // Remove the child node
                                            childSnapshot.getRef().removeValue();
//                                            Log.d(, "Data deleted successfully");
                                            break;
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    // Handle the error

//                                    Log.e(NeurologistMyAdapter, "Error deleting data", databaseError.toException());
                                    System.out.println("Error deleting data"+ databaseError.toException());
                                }
                            });
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                    }
                });

                builder.show();


            }
        });


        holder.viewLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, Activity_doctor_profile.class);
                intent.putExtra("name", model.getName());
                intent.putExtra("degree", model.getDegree());
                intent.putExtra("specialistArea", model.getSpecialist());
                intent.putExtra("chamber", model.getChamber());
                intent.putExtra("Email", model.getEmail());
                intent.putExtra("phone", model.getMobileNumber());
                intent.putExtra("Address", model.getAdrees());
                intent.putExtra("picurl", model.getPic());
                intent.putExtra("key", "Psychiatrist");

                context.startActivity(intent);

                // finish the current activity
                ((Activity) context).finish();
            }
        });


    }

    @NonNull
    @Override
    public PsychiatristAdapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);
        return new PsychiatristAdapter.myviewholder(view);
    }



    public class myviewholder extends RecyclerView.ViewHolder{


        ImageView imgl;
        TextView Name,designation;
        Button Delete;
        View viewLayout;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            imgl = (ImageView) itemView.findViewById(R.id.imagep);
            Name = itemView.findViewById(R.id.card_name);
            designation = itemView.findViewById(R.id.card_d);
            viewLayout = itemView.findViewById(R.id.cardLayout);
            Delete = itemView.findViewById(R.id.delete);


        }
    }

}
