package com.example.makemytrip.Fragments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.makemytrip.R;
import com.example.makemytrip.Trip_details;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;

public class Advance_booking extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public Advance_booking() {

    }

    public static Advance_booking newInstance(String param1, String param2) {
        Advance_booking fragment = new Advance_booking();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    EditText passengername;
    Spinner fromspinner, tospinner, bustype_spinner;
    ArrayAdapter arrayAdapter;
    TextView datepiker;
    Button searchbtn;
    RadioGroup gender;
    RadioButton radioButton;

    int year;
    int month;
    int day;
    int hour;
    int min;

    private DatabaseReference reference;
    private FirebaseAuth auth;

    String city[] = {"Ahmedabad", "Hyderabad", "Surat", "Delhi", "Bangalore", "Pune", "Lucknow", "Kolkata", "Kanpur", "Jaipur", "Udaipur", "Patna", "Nagpur",
            "Agra", "Allahbad", "Vadodara", "Indore", "Varanasi", "Jabalpur", "Chennai", "Thane", "Bhopal", "Vishakhapatnam", "Nashik", "Faridabad", "Srinagar", "Aurangabad",
            "Ranchi", "Jodhpur", "Chandigarh", "Kota", "Solapur", "Bareilly", "Aligarh", "Jalandhar", "Bikaner", "Ajmer", "Dehradun", "Jammu", "Goa",
    };

    String bustype[] = {"A/C", "Non A/C"};


    String fromcity_name;
    String tocity_name;
    String B_type;
    String result;
    String userID;

    TextView ticketcout;
    String gende = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_advance_booking, container, false);
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();

        ticketcout = view.findViewById(R.id.ticket_cout_id);

        Tcout();
        fromspinner = view.findViewById(R.id.fromspinner_id);
        tospinner = view.findViewById(R.id.tospinner_id);
        datepiker = view.findViewById(R.id.datepiker_id);
        searchbtn = view.findViewById(R.id.searchbtn_id);
        passengername = view.findViewById(R.id.p_name_id);

        bustype_spinner = view.findViewById(R.id.bustype_sid);
        gender = view.findViewById(R.id.gender_id);

        arrayAdapter = new ArrayAdapter(getContext().getApplicationContext(), android.R.layout.simple_list_item_1, city);
        fromspinner.setAdapter(arrayAdapter);
        tospinner.setAdapter(arrayAdapter);

        arrayAdapter = new ArrayAdapter(getContext().getApplicationContext(), android.R.layout.simple_list_item_1, bustype);
        bustype_spinner.setAdapter(arrayAdapter);


        fromspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fromcity_name = city[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        tospinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tocity_name = city[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        bustype_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                B_type = bustype[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        datepiker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();

                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                hour = calendar.get(Calendar.HOUR_OF_DAY);
                min = calendar.get(Calendar.MINUTE);

                DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

//                        datepiker.setText(SimpleDateFormat.getDateInstance().format(calendar.getTime()));

                        datepiker.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);
                dialog.show();
            }
        });


        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                adddata();
                Toast.makeText(getActivity(), "click", Toast.LENGTH_SHORT).show();
            }
        });

//        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int i) {
//                switch (i){
//                    case R.id.male_id:
//                       gende="male";
//                        break;
//                    case R.id.female_id:
//                        gende ="female";
//                        break;
//                }
//            }
//        });

        return view;
    }

    private void Tcout() {

        Query query = reference.child("TicketBooking").child(auth.getCurrentUser().getUid());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long r = snapshot.getChildrenCount();

                ticketcout.setText(String.valueOf(r));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "E::" + error, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void adddata() {

        radioButton = gender.findViewById(gender.getCheckedRadioButtonId());
        if (radioButton == null) {
            Toast.makeText(getActivity(), "Select Gender", Toast.LENGTH_SHORT).show();
        } else {
            result = radioButton.getText().toString();
        }

        String Passengername = passengername.getText().toString();
        String date = datepiker.getText().toString();

        if (Passengername.isEmpty() || date.isEmpty()) {
            Toast.makeText(getActivity(), "Fill the detail", Toast.LENGTH_SHORT).show();
        } else {

            addtofirebasr();
        }
    }

    private void addtofirebasr() {
        long c = Long.parseLong(ticketcout.getText().toString());
        if (c < 5) {
            userID = auth.getCurrentUser().getUid();
            String Ticketid = reference.push().getKey().toString();

            HashMap list = new HashMap();

            list.put("passengerName", passengername.getText().toString());
            list.put("From", fromcity_name);
            list.put("To", tocity_name);
            list.put("Booking_Date", datepiker.getText().toString());
            list.put("Gender", result);
            list.put("BusType", B_type);
            list.put("UserId", userID);
            list.put("TicketId", Ticketid);


            reference.child("TicketBooking").child(userID).child(Ticketid).setValue(list);

            Toast.makeText(getActivity(), "Ticker Booking successfully", Toast.LENGTH_SHORT).show();


            Intent intent = new Intent(getActivity(), Trip_details.class);
            startActivity(intent);
            Tcout();
            passengername.setText("");
            datepiker.setText("");
        } else {
            Toast.makeText(getContext(), "Maximum 5 tickets can be booked from 1 user ID", Toast.LENGTH_SHORT).show();
        }


    }
}