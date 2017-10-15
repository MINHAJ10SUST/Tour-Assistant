package com.example.user.tourassistant.page_fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.user.tourassistant.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddEventFragment extends Fragment {
    private EditText destinationEt,fromDateEt,toDateET,budgetEt;
    private Button eventSavebt;
    private View view;
    FirebaseDatabase database;
    DatabaseReference myEventRef;

    public AddEventFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        database = FirebaseDatabase.getInstance();
        view=inflater.inflate(R.layout.fragment_add_event, container, false);
        destinationEt=view.findViewById(R.id.destinationEt);
        fromDateEt=view.findViewById(R.id.fromDateEt);
        toDateET=view.findViewById(R.id.toDateET);
        budgetEt=view.findViewById(R.id.budgetEt);

        eventSavebt=view.findViewById(R.id.saveBt);
        eventSavebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myEventRef = database.getReference("Events").push();
                myEventRef.child("Destination").setValue(destinationEt.getText().toString());
                myEventRef.child("FromDate").setValue(fromDateEt.getText().toString());
                myEventRef.child("ToDate").setValue(toDateET.getText().toString());
                myEventRef.child("budget").setValue(budgetEt.getText().toString());

            }
        });

        // Inflate the layout for this fragment
        return view;
    }

}
