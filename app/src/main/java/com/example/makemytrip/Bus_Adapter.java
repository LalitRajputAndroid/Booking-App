package com.example.makemytrip;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;


public class Bus_Adapter extends FirebaseRecyclerAdapter<BusModal, Bus_Adapter.Bus_ViewHolder> {
    public Bus_Adapter(@NonNull FirebaseRecyclerOptions<BusModal> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull Bus_ViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull BusModal model) {
        holder.name.setText("passengerName"+" :" + model.getPassengerName());
        holder.from.setText(model.getFrom());
        holder.to.setText(model.getTo());
        holder.gender.setText("Gender - " + model.getGender());
        holder.btype.setText("BusType - " + model.getBusType());
        holder.date.setText("Booking Date : " + model.getBooking_Date());

        holder.editbtn.setOnClickListener(v -> {

            DialogPlus dialogPlus = DialogPlus.newDialog(holder.editbtn.getContext())
                    .setContentHolder(new ViewHolder(R.layout.editlayout))
                    .setExpanded(true, 1100)
                    .create();

            View view = dialogPlus.getHolderView();
            EditText passengername = view.findViewById(R.id.p_name_id);
            Button update = view.findViewById(R.id.updatebtn_id);

            passengername.setText(model.getPassengerName());

            dialogPlus.show();

            update.setOnClickListener(view1 -> {
                Map<String, Object> map = new HashMap();
                map.put("passengerName", passengername.getText().toString());

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                FirebaseAuth  auth = FirebaseAuth.getInstance();

                reference.child("TicketBooking").child(auth.getCurrentUser()
                                .getUid()).child(getRef(position).getKey())
                        .updateChildren(map)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                dialogPlus.dismiss();
                            notifyDataSetChanged();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                dialogPlus.dismiss();
                            }
                        });
            });

        });

        holder.deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.deletebtn.getContext());
                builder.setTitle("Are you Sure");
                builder.setMessage("Delete....?");
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                FirebaseAuth  auth = FirebaseAuth.getInstance();

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       reference .child("TicketBooking").child(auth.getCurrentUser().getUid()).child(getRef(position).getKey())
                                .removeValue();
                       notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
            }
        });
    }

    @NonNull
    @Override
    public Bus_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row, parent, false);
        return new Bus_ViewHolder(view);
    }

    public static class Bus_ViewHolder extends RecyclerView.ViewHolder {

        TextView name, from, to, date, btype, gender;
        Button deletebtn, editbtn;
        CardView singlecard;

        public Bus_ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name_id);
            from = itemView.findViewById(R.id.from_id);
            to = itemView.findViewById(R.id.to_id);
            date = itemView.findViewById(R.id.booking_date_id);
            btype = itemView.findViewById(R.id.bustype_id);
            gender = itemView.findViewById(R.id.gender_id);
            deletebtn = itemView.findViewById(R.id.deletebtn_id);
            editbtn = itemView.findViewById(R.id.editbtn_id);
            singlecard = itemView.findViewById(R.id.signuptext_id);
        }
    }
}
