package com.example.user.tourassistant.page_fragment;



import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.tourassistant.R;

import com.example.user.tourassistant.firebase.GeoFence;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlartFragment extends Fragment {
    private RecyclerView eventListView;
    private FirebaseRecyclerAdapter<GeoFence,EventViewHolder12> firebaseRecyclerAdapter;
    private DatabaseReference eventDatabase,userDatabase;
    FirebaseDatabase database;
    FirebaseAuth mAuth;
    DatabaseReference myEventRef;

    String eventkey;
    String keyid;
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


        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        eventDatabase = FirebaseDatabase.getInstance().getReference().child("GeoFence").child(mAuth.getCurrentUser().getUid());
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setReverseLayout(true);
        llm.setStackFromEnd(true);

        eventListView = (RecyclerView)view.findViewById(R.id.recyclerview_expenseList2);
        eventListView.setHasFixedSize(true);
        eventListView.setLayoutManager(llm);


        return  view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                DatabaseReference myGeoRef;
                mAuth = FirebaseAuth.getInstance();
                database = FirebaseDatabase.getInstance();
                Place place = PlacePicker.getPlace(data, getContext());
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(getActivity(), toastMsg, Toast.LENGTH_LONG).show();
                myGeoRef =database.getReference().child("GeoFence").child(mAuth.getCurrentUser().getUid()).push();


                myGeoRef.child("placeName").setValue(place.getName().toString());
                myGeoRef.child("latitude").setValue(place.getLatLng().latitude);
                myGeoRef.child("longitude").setValue(place.getLatLng().longitude);
                myGeoRef.child("radius").setValue(100);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<GeoFence, EventViewHolder12>(
                GeoFence.class, R.layout.geo_fence_row,  EventViewHolder12.class, eventDatabase
        ) {
            @Override
            protected void populateViewHolder( EventViewHolder12 viewHolder, GeoFence model, int position) {

                viewHolder.setEventName(""+model.getPlaceName());
                viewHolder.setFromDate(""+model.getLatitude());
                viewHolder.setToDate(""+model.getLongitude());
                viewHolder.setRadius(""+model.getRadius());

            }
        };
        eventListView.setAdapter(firebaseRecyclerAdapter);



    }

    public static class EventViewHolder12 extends RecyclerView.ViewHolder{

        View mView;
        public EventViewHolder12(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setEventName(String value){
            TextView eventName=mView.findViewById(R.id.placeNameTv);
            eventName.setText(value);

        }

        public void setFromDate(String value){
            TextView fromDate=mView.findViewById(R.id.latTv);
            fromDate.setText(value);
        }

        public void setToDate(String value){
            TextView toDate=mView.findViewById(R.id.lonTv);
            toDate.setText(value);
        }
        public void setRadius(String value){
            TextView radius=mView.findViewById(R.id.rediusTv);
            radius.setText(value);
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
        inflater.inflate(R.menu.event_manu, menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addEvent:
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    Intent intent =builder.build(getActivity());
                    startActivityForResult(intent, PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
