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
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.tourassistant.activities.SampleActivityBase;
import com.example.user.tourassistant.google_place.AllmapActivity;
import com.example.user.tourassistant.google_place.MapsActivity;
import com.example.user.tourassistant.page_fragment.BlogFragment;
import com.example.user.tourassistant.page_fragment.HomeFragment;
import com.example.user.tourassistant.page_fragment.SigninFragment;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

public class HomeActivity extends SampleActivityBase implements PlaceSelectionListener {


    private TextView mPlaceDetailsText;

    private TextView mPlaceAttribution;
    public int sbFlag=0;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    FragmentManager fm1 = getSupportFragmentManager();
                    FragmentTransaction ft1 = fm1.beginTransaction();
                    HomeFragment homeFragment = new HomeFragment();
                    ft1.replace(R.id.homeFragmentView,homeFragment);
                    ft1.addToBackStack(null);
                    ft1.commit();
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




        if(sbFlag==1 ){
            // Retrieve the PlaceAutocompleteFragment.
            PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                    getFragmentManager().findFragmentById(R.id.autocomplete_fragment);
            autocompleteFragment.setOnPlaceSelectedListener(this);

            //mPlaceDetailsText = (TextView) findViewById(R.id.place_details);
            mPlaceAttribution = (TextView) findViewById(R.id.place_attribution);
        }

    }

    public void showHotel(View view) {
        Intent intentMap=new Intent(HomeActivity.this,MapsActivity.class);
        startActivity(intentMap);
    }

    public void showWeather(View view) {
        Intent intentWeather=new Intent(HomeActivity.this,TestWeather.class);
        startActivity(intentWeather);
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

    @Override
    public void onPlaceSelected(Place place) {
        CharSequence attributions = place.getAttributions();
        if (!TextUtils.isEmpty(attributions)) {
            mPlaceAttribution.setText(Html.fromHtml(attributions.toString()));
        } else {
            mPlaceAttribution.setText("");
        }
    }

    @Override
    public void onError(Status status) {

        Toast.makeText(this, "Place selection failed: " + status.getStatusMessage(),
                Toast.LENGTH_SHORT).show();
    }
}
