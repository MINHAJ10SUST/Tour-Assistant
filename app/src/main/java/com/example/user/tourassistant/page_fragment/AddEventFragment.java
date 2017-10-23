package com.example.user.tourassistant.page_fragment;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.user.tourassistant.R;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddEventFragment extends Fragment {
    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;
    private EditText destinationEt,fromDateEt,toDateET,budgetEt;
    private ImageButton closeButton;
    private DatePicker fromDateEtd,toDateETd;
    private Button eventSavebt;
    private View view;
    FirebaseDatabase database;
    DatabaseReference myEventRef;
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;


    public AddEventFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Add Event");
        mAuth = FirebaseAuth.getInstance();



        database = FirebaseDatabase.getInstance();
        view=inflater.inflate(R.layout.fragment_add_event, container, false);
        destinationEt=view.findViewById(R.id.destinationEt);
        fromDateEt=view.findViewById(R.id.fromDateEt);
        toDateET=view.findViewById(R.id.toDateET);
        budgetEt=view.findViewById(R.id.budgetEt);
        destinationEt.setInputType(InputType.TYPE_NULL);
        destinationEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAutocompleteActivity();
            }
        });
        destinationEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {

                }
            }
        });

        closeButton=view.findViewById(R.id.backFromAddEvent);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm3 = getActivity().getSupportFragmentManager();
                FragmentTransaction ft3 = fm3.beginTransaction();
                EventFragment eventFragment = new EventFragment();
                ft3.replace(R.id.homeFragmentView,eventFragment);
                ft3.commit();
            }
        });




        // Inflate the layout for this fragment
        return view;
    }


    private void openAutocompleteActivity() {
        try {

            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                    .build(getActivity());
            startActivityForResult(intent, REQUEST_CODE_AUTOCOMPLETE);
        } catch (GooglePlayServicesRepairableException e) {

            GoogleApiAvailability.getInstance().getErrorDialog(getActivity(), e.getConnectionStatusCode(),
                    0 /* requestCode */).show();
        } catch (GooglePlayServicesNotAvailableException e) {

            String message = "Google Play Services is not available: " +
                    GoogleApiAvailability.getInstance().getErrorString(e.errorCode);


        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check that the result was from the autocomplete widget.
        if (requestCode == REQUEST_CODE_AUTOCOMPLETE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(getActivity(), data);
                destinationEt.setText(place.getName());

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getActivity(), data);

            } else if (resultCode == RESULT_CANCELED) {

            }
        }
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
                myEventRef = database.getReference("Events").child(mAuth.getCurrentUser().getUid()).push();
                myEventRef.child("Destination").setValue(destinationEt.getText().toString());
                myEventRef.child("FromDate").setValue(fromDateEt.getText().toString());
                myEventRef.child("ToDate").setValue(toDateET.getText().toString());
                myEventRef.child("budget").setValue(budgetEt.getText().toString());

                FragmentManager fm3 = getActivity().getSupportFragmentManager();
                FragmentTransaction ft3 = fm3.beginTransaction();
                EventFragment eventFragment = new EventFragment();
                ft3.replace(R.id.homeFragmentView,eventFragment);
                ft3.commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}
