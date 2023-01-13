package com.example.makemytrip.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.makemytrip.R;

public class Advance_booking extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

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
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    Spinner fromspinner, tospinner;
    ArrayAdapter arrayAdapter;

    String city[] = {"Ahmedabad", "Hyderabad", "Surat", "Delhi", "Bangalore", "Pune", "Lucknow", "Kolkata", "Kanpur", "Jaipur", "Udaipur", "Patna", "Nagpur",
            "Agra", "Allahbad", "Vadodara", "Indore", "Varanasi", "Jabalpur", "Chennai", "Thane", "Bhopal", "Vishakhapatnam", "Nashik", "Faridabad", "Srinagar", "Aurangabad",
            "Ranchi", "Jodhpur", "Chandigarh", "Kota", "Solapur", "Bareilly", "Aligarh", "Jalandhar", "Bikaner", "Ajmer", "Dehradun", "Jammu", "Goa",
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_advance_booking, container, false);


        fromspinner = view.findViewById(R.id.fromspinner_id);
        tospinner = view.findViewById(R.id.topinner_id);


        arrayAdapter = new ArrayAdapter(getContext().getApplicationContext(), android.R.layout.simple_list_item_1, city);
        fromspinner.setAdapter(arrayAdapter);
        tospinner.setAdapter(arrayAdapter);


        fromspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String city_name = city[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        tospinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String city_name = city[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }
}