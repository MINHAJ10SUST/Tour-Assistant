package com.example.user.tourassistant;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends SampleActivityBase {


    private Button homepageCityBt;
    private ImageView homeImage;
    private String type="restaurant";
    double latitude=23.777176;
    double longitude=90.399452;

    public int sbFlag=0;
    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;
    private boolean signoutflag;
    private FirebaseAuth mFirebaseAuth;
    private SharedPreferences sharedPref;
    private ArrayList<TopPlace> topPlaces;
    private RecyclerView topPlaceRecyclerView;
    private TopPlaceAdapter topPlaceAdapter;



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    HomeFragment homeFragment = new HomeFragment();
                    ft.replace(R.id.homeFragmentView,homeFragment);
                    ft.addToBackStack(null);
                    ft.commit();
                    return true;

                case R.id.navigation_mytrip:
                    goMyTrip();
                    return true;

                case R.id.navigation_notifications:
                    AlartFragment alartFragment = new AlartFragment();
                    ft.replace(R.id.homeFragmentView,alartFragment);
                    ft.addToBackStack(null);
                    ft.commit();
                    return true;

                case R.id.navigation_account:
                    UserFragment userFragment = new UserFragment();
                    ft.replace(R.id.homeFragmentView,userFragment);
                    ft.addToBackStack(null);
                    ft.commit();
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
       // ft.addToBackStack(null);
        ft.commit();



        /*topPlaces = getTopPlaceInfo("restaurant", latitude,longitude);
        topPlaceRecyclerView = (RecyclerView)findViewById(R.id.top_places_recyclerview);
        topPlaceAdapter = new TopPlaceAdapter(this,topPlaces);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        topPlaceRecyclerView.setLayoutManager(mLayoutManager);
        topPlaceRecyclerView.setItemAnimator(new DefaultItemAnimator());
        topPlaceRecyclerView.setAdapter(topPlaceAdapter);*/



    }

    public void showMapCtg(View view) {
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

//        FragmentManager fm = getSupportFragmentManager();
//        FragmentTransaction ft = fm.beginTransaction();
//        MapFragment mapFragment = new MapFragment();
//
//        Bundle sendKey = new Bundle();
//        sendKey.putFloat("latitude", (float) latitude);
//        sendKey.putFloat("longitude",(float) longitude);
//        sendKey.putString("type",type);
//        mapFragment.setArguments(sendKey);
//        ft.replace(R.id.homeFragmentView,mapFragment);
//        ft.addToBackStack(null);
//        ft.commit();
    }

    public void showWeather(View view)  {
        FragmentManager fmw = getSupportFragmentManager();
        FragmentTransaction ftw = fmw.beginTransaction();
        WeatherFragment weatherFragment = new WeatherFragment();
        ftw.replace(R.id.homeFragmentView,weatherFragment);
        ftw.addToBackStack(null);
        ftw.commit();

    }

    private void openAutocompleteActivity() {
        try {

            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                    .build(this);
            startActivityForResult(intent, REQUEST_CODE_AUTOCOMPLETE);
        } catch (GooglePlayServicesRepairableException e) {

            GoogleApiAvailability.getInstance().getErrorDialog(this, e.getConnectionStatusCode(),
                    0 /* requestCode */).show();
        } catch (GooglePlayServicesNotAvailableException e) {

            String message = "Google Play Services is not available: " +
                    GoogleApiAvailability.getInstance().getErrorString(e.errorCode);


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check that the result was from the autocomplete widget.
        if (requestCode == REQUEST_CODE_AUTOCOMPLETE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                LatLng queriedLocation = place.getLatLng();
                homepageCityBt=(Button) findViewById(R.id.HomepageCityBt);
                homepageCityBt.setText(place.getName());
                latitude=queriedLocation.latitude;
                longitude=queriedLocation.longitude;
                getGooglePlaceInfo("restaurant", latitude,longitude);
                CharSequence attributions = place.getAttributions();

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);

            } else if (resultCode == RESULT_CANCELED) {

            }
        }
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



    public void getPlace(View view) throws GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        openAutocompleteActivity();
    }
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


   /* public ArrayList<TopPlace> getTopPlaceInfo(String type, double latitude, double longitude){

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

                for (int i = 0; i <10; i++) {
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
*/



}
