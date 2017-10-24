package com.example.user.tourassistant.page_fragment;



import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.user.tourassistant.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlartFragment extends Fragment {

    int PLACE_PICKER_REQUEST = 1;
    View view;
    Button x;
    public AlartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view=inflater.inflate(R.layout.fragment_alart, container, false);
        x=view.findViewById(R.id.getPlaceBt);
        x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    Intent intent =builder.build(getActivity());
                    startActivityForResult(intent, PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }

            }
        });
//        try {
//            PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
//            Intent intent = intentBuilder.build(getActivity());
//            // Start the Intent by requesting a result, identified by a request code.
//            startActivityForResult(intent, PLACE_PICKER_REQUEST);
//
//
//
//        } catch (GooglePlayServicesRepairableException e) {
//            GooglePlayServicesUtil
//                    .getErrorDialog(e.getConnectionStatusCode(), getActivity(), 0);
//        } catch (GooglePlayServicesNotAvailableException e) {
//            Toast.makeText(getActivity(), "Google Play Services is not available.",
//                    Toast.LENGTH_LONG)
//                    .show();
//        }


        return  view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, getContext());
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(getActivity(), toastMsg, Toast.LENGTH_LONG).show();
            }
        }
    }
}
