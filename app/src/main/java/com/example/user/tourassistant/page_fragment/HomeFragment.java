package com.example.user.tourassistant.page_fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.user.tourassistant.R;
import com.example.user.tourassistant.TopPlace;
import com.example.user.tourassistant.TopPlaceAdapter;
import com.example.user.tourassistant.google_place.Example;
import com.example.user.tourassistant.google_place.RetrofitMaps;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    BottomNavigationView bottomNavigationView;
    double latitude=23.777176;
    double longitude=90.399452;
    private ArrayList<TopPlace> topPlaces;
    private RecyclerView topPlaceRecyclerView;
    private TopPlaceAdapter topPlaceAdapter;


    public HomeFragment() {
        // Required empty public constructor

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("Tour Assistant");
        final View v = inflater.inflate(R.layout.fragment_home, container, false);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        topPlaces = getTopPlaceInfo("restaurant", latitude,longitude);
        topPlaceRecyclerView = (RecyclerView)getView().findViewById(R.id.top_places_recyclerview);
        topPlaceAdapter = new TopPlaceAdapter(getActivity(),topPlaces);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        topPlaceRecyclerView.setLayoutManager(mLayoutManager);
        topPlaceRecyclerView.setItemAnimator(new DefaultItemAnimator());
        topPlaceRecyclerView.setAdapter(topPlaceAdapter);

    }

    public ArrayList<TopPlace> getTopPlaceInfo(String type, double latitude, double longitude){

        final ArrayList<TopPlace>topPlaces = new ArrayList<>();

        String url = "https://maps.googleapis.com/maps/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitMaps service = retrofit.create(RetrofitMaps.class);
        Call<Example> call=service.getNearbyPlaces(type,latitude+","+longitude,10000);
        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {

                for (int i = 0; i <response.body().getResults().size(); i++) {
                    String placeName = response.body().getResults().get(i).getName();
                    String photoUrl = String.format("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=" + response.body().getResults().get(i).getPhotos().get(0).getPhotoReference() + "&key=AIzaSyA1GDN-skUP2mxHOAJiaJiIdpvKMKJuJEA");
                    topPlaces.add(new TopPlace(placeName,photoUrl));
                }
            }
            @Override
            public void onFailure(Call<Example> call, Throwable t) {

            }
        });

        return topPlaces;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out_menu:
                AuthUI.getInstance().signOut(getActivity());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
