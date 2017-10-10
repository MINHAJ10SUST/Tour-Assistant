package com.example.user.tourassistant;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.user.tourassistant.activities.Log;
import com.example.user.tourassistant.activities.SampleActivityBase;
import com.example.user.tourassistant.google_place.AllmapActivity;
import com.example.user.tourassistant.google_place.Example;
import com.example.user.tourassistant.google_place.MapsActivity;
import com.example.user.tourassistant.google_place.RetrofitMaps;
import com.example.user.tourassistant.page_fragment.BlogFragment;
import com.example.user.tourassistant.page_fragment.HomeFragment;
import com.example.user.tourassistant.page_fragment.SigninFragment;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;

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
                case R.id.navigation_dashboard:
                    FragmentManager fm2 = getSupportFragmentManager();
                    FragmentTransaction ft2 = fm2.beginTransaction();
                    SigninFragment signinFragment = new SigninFragment();
                    ft2.replace(R.id.homeFragmentView,signinFragment);
                    ft2.addToBackStack(null);
                    ft2.commit();
                    return true;
                case R.id.navigation_notifications:
                    FragmentManager fm3 = getSupportFragmentManager();
                    FragmentTransaction ft3 = fm3.beginTransaction();
                    BlogFragment blogFragment = new BlogFragment();
                    ft3.replace(R.id.homeFragmentView,blogFragment);
                    ft3.addToBackStack(null);
                    ft3.commit();
                    return true;
            }
            return false;
        }

    };

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
        ft.addToBackStack(null);
        ft.commit();






    }

    public void showHotel(View view) {
        Intent intentMap=new Intent(HomeActivity.this,AllmapActivity.class);
        intentMap.putExtra("latitude", latitude);
        intentMap.putExtra("longitude",longitude);
        intentMap.putExtra("type",type);
        startActivity(intentMap);
    }

    public void showWeather(View view) throws GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        openAutocompleteActivity();
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
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out_menu:
                AuthUI.getInstance().signOut(this);
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
                homeImage=findViewById(R.id.homeImage);
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
