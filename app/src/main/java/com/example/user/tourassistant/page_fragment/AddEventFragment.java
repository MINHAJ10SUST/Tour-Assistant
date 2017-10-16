package com.example.user.tourassistant.page_fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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



        // Inflate the layout for this fragment
        return view;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(
            Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.save_manu, menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saveEvent:
                myEventRef = database.getReference("Events").push();
                myEventRef.child("Destination").setValue(destinationEt.getText().toString());
                myEventRef.child("FromDate").setValue(fromDateEt.getText().toString());
                myEventRef.child("ToDate").setValue(toDateET.getText().toString());
                myEventRef.child("budget").setValue(budgetEt.getText().toString());

                FragmentManager fm3 = getActivity().getSupportFragmentManager();
                FragmentTransaction ft3 = fm3.beginTransaction();
                EventFragment eventFragment = new EventFragment();
                ft3.replace(R.id.homeFragmentView,eventFragment);
                ft3.addToBackStack(null);
                ft3.commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
