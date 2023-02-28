package com.example.makemytrip;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Trip_details extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference reference;
    Bus_Adapter bus_adapter;
    FirebaseAuth auth;
    Toolbar toolbar;
//    ProgressDialog dialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);

        toolbar = findViewById(R.id.toobar_id);
        recyclerView = findViewById(R.id.triprecyclerview_id);

//        dialog = new ProgressDialog(this);
//        dialog.setTitle("Loding Data");
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.create();
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        auth = FirebaseAuth.getInstance();
        FirebaseRecyclerOptions<BusModal> options = new FirebaseRecyclerOptions.Builder<BusModal>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("TicketBooking").child(auth.getCurrentUser().getUid()), BusModal.class)
                .build();

        bus_adapter = new Bus_Adapter(options);

        recyclerView.setAdapter(bus_adapter);
    }

    @Override
    protected void onStart() {
        bus_adapter.startListening();
        super.onStart();
    }

    @Override
    protected void onStop() {
        bus_adapter.stopListening();
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Trip_details.this,MainActivity.class));
    }
}