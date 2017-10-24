package com.example.user.tourassistant;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.user.tourassistant.activities.SampleActivityBase;
import com.example.user.tourassistant.google_place.AllmapActivity;
import com.example.user.tourassistant.google_place.Example;
import com.example.user.tourassistant.google_place.MapsActivity;
import com.example.user.tourassistant.google_place.RetrofitMaps;
import com.example.user.tourassistant.page_fragment.AlartFragment;
import com.example.user.tourassistant.page_fragment.EventFragment;
import com.example.user.tourassistant.page_fragment.HomeFragment;
import com.example.user.tourassistant.page_fragment.SigninFragment;
import com.example.user.tourassistant.page_fragment.UserFragment;
import com.example.user.tourassistant.page_fragment.WeatherFragment;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends SampleActivityBase {



    private ImageView homeImage;
    private String type="restaurant";
    double latitude=23.777176;
    double longitude=90.399452;

    public int sbFlag=0;
    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;
    private boolean signoutflag;
    private FirebaseAuth mFirebaseAuth;
    private SharedPreferences sharedPref;



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    HomeFragment homeFragment = new HomeFragment();
                    ft.replace(R.id.homeFragmentView,homeFragment);
                    ft.addToBackStack(null);
                    ft.commit();
                    return true;
                case R.id.navigation_mytrip:
                    goMyTrip();
                    return true;
                case R.id.navigation_notifications:
                    FragmentManager fma = getSupportFragmentManager();
                    FragmentTransaction fta = fma.beginTransaction();
                    AlartFragment alartFragment = new AlartFragment();
                    fta.replace(R.id.homeFragmentView,alartFragment);
                    fta.addToBackStack(null);
                    fta.commit();
                    return true;

                case R.id.navigation_account:
                    FragmentManager fmac = getSupportFragmentManager();
                    FragmentTransaction fac = fmac.beginTransaction();
                    UserFragment userFragment = new UserFragment();
                    fac.replace(R.id.homeFragmentView,userFragment);
                    fac.addToBackStack(null);
                    fac.commit();
                    return true;
            }
            return false;
        }

    };


    public void goMyTrip(){

        sharedPref = getPreferences(Context.MODE_PRIVATE);

        String userfl=sharedPref.getString("user",null);

        if(userfl==null){

            FragmentManager fme = getSupportFragmentManager();
            FragmentTransaction fte = fme.beginTransaction();
            SigninFragment signinFragment = new SigninFragment();
            fte.replace(R.id.homeFragmentView,signinFragment);
            fte.addToBackStack(null);
            fte.commit();

            }
        if(userfl!=null){

            FragmentManager fme = getSupportFragmentManager();
            FragmentTransaction fte = fme.beginTransaction();
            EventFragment eventFragment = new EventFragment();
            fte.replace(R.id.homeFragmentView,eventFragment);
            fte.addToBackStack(null);
            fte.commit();


        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        HomeFragment homeFragment = new HomeFragment();
        ft.add(R.id.homeFragmentView,homeFragment);
        ft.commit();

        sharedPref = getPreferences(Context.MODE_PRIVATE);
        String placeName=sharedPref.getString("placeName","Dhaka");
        latitude=sharedPref.getFloat("latitude",23);
        longitude=sharedPref.getFloat("longitude",90);
        //getGooglePlaceInfo(type, latitude,longitude);
//        homepageCityBt = (Button) findViewById(R.id.HomepageCityBt);
//        homepageCityBt.setText(placeName.toString());







    }

    public void showMapCtg(View view) {

        sharedPref = getPreferences(Context.MODE_PRIVATE);
        String placeName=sharedPref.getString("placeName","Dhaka");
        latitude=sharedPref.getFloat("latitude",23);
        longitude=sharedPref.getFloat("longitude",90);

        Intent intentMap=new Intent(HomeActivity.this,AllmapActivity.class);
        intentMap.putExtra("latitude", latitude);
        intentMap.putExtra("longitude",longitude);
        switch (view.getId()){
            case R.id.Hotel:
                intentMap.putExtra("type","hotels");
                break;
            case R.id.Cafe:
                intentMap.putExtra("type","cafe");
                break;
            case R.id.Restaurant:
                intentMap.putExtra("type","restaurant");
                break;
            case R.id.Grocery:
                intentMap.putExtra("type","grocery_or_supermarket");
                break;
            case R.id.Atm:
                intentMap.putExtra("type","atm");
                break;
            case R.id.Bank:
                intentMap.putExtra("type","bank");
                break;
            case R.id.Pharmacy:
                intentMap.putExtra("type","pharmacy");
                break;


        }

        startActivity(intentMap);


    }

    public void showWeather(View view)  {
        FragmentManager fmw = getSupportFragmentManager();
        FragmentTransaction ftw = fmw.beginTransaction();
        WeatherFragment weatherFragment = new WeatherFragment();
        ftw.replace(R.id.homeFragmentView,weatherFragment);
        ftw.addToBackStack(null);
        ftw.commit();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out_menu:
                AuthUI.getInstance().signOut(this);
                sharedPref = getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("user",null);
                editor.commit();
                editor.apply();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                HomeFragment homeFragment = new HomeFragment();
                ft.replace(R.id.homeFragmentView,homeFragment);
                ft.addToBackStack(null);
                ft.commit();


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


//
//    public void getPlace(View view) throws GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
//        openAutocompleteActivity();
//    }

    public void getGooglePlaceInfo(String type, double latitude,double longitude){

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

                String placeName = response.body().getResults().get(0).getName();
                homeImage= (ImageView) findViewById(R.id.homeImage);
                if(response.body().getResults().get(0).getPhotos().get(0).getPhotoReference()!=null) {
                    String photoUrl = String.format("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=" + response.body().getResults().get(0).getPhotos().get(0).getPhotoReference() + "&key=AIzaSyA1GDN-skUP2mxHOAJiaJiIdpvKMKJuJEA");
                    Glide.with(getApplicationContext()).load(photoUrl).asBitmap()
                            .error(R.drawable.coxbazer).centerCrop().into(homeImage);
                }

            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {

            }
        });



    }




}
