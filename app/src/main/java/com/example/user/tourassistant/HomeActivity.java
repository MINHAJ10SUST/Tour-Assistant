package com.example.user.tourassistant;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.user.tourassistant.google_place.MapsActivity;
import com.example.user.tourassistant.page_fragment.HomeFragment;
import com.example.user.tourassistant.page_fragment.SigninFragment;

public class HomeActivity extends AppCompatActivity {

    //private TextView mTextMessage;




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
                    Intent intentLogin=new Intent(HomeActivity.this,MainActivity.class);
                    startActivity(intentLogin);
                    return true;
                case R.id.navigation_notifications:
                    Intent intent=new Intent(HomeActivity.this,MapsActivity.class);
                    startActivity(intent);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        HomeFragment homeFragment = new HomeFragment();
        ft.add(R.id.homeFragmentView,homeFragment);
        ft.addToBackStack(null);
        ft.commit();
    }

}
