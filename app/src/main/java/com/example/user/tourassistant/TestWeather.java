package com.example.user.tourassistant;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.tourassistant.google_place.MapsActivity;
import com.example.user.tourassistant.page_fragment.HomeFragment;
import com.example.user.tourassistant.weather.ApiTool;
import com.example.user.tourassistant.weather.Example;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TestWeather extends AppCompatActivity {

    double latitude,longitude;



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

                    return true;
                case R.id.navigation_notifications:
                    Intent intent=new Intent(getApplication(),MapsActivity.class);
                    startActivity(intent);
                    return true;
            }
            return false;
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_weather);

        latitude=23.777176;
        longitude=90.399452;

        showWeather(latitude,longitude);
    }



    public void showWeather(double lat,double lon){

        TextView temp0,temp1,temp2,temp3,temp4,temp5,temp6,temp7,temp8,temp9,temp10,temp11,crntTempUnitT,crntTempRangeUnitT,day0TempUnitT,day1TempUnitT,day2TempUnitT,day3TempUnitT,day4TempUnitT, lastUpdateT,detailsTextT,  minTempt,maxTempt,avgTempt,humidityt,pressuret,visivilityt,uvt,windt,windDirt,wConditiontt,sunriset,sunsett,locationt,timet,feelTmpt,day0t,day0datet,day0hight,day0lowt,
                day1t,day1datet,day1hight,day1lowt,day2t,day2datet,day2hight,day2lowt,day3t,day3datet,day3hight,day3lowt,day4t,day4datet,day4hight,day4lowt;



        List<Double> loc=new ArrayList<>();
        loc.add(lat);
        loc.add(lon);
        String url = "http://api.apixu.com/v1/";


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiTool service = retrofit.create(ApiTool.class);

        Call<Example> call = service.getAWeather(loc);
        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {



                String hourCondText0 = response.body().getForecast().getForecastday().get(0).getHour().get(0).getCondition().getText().toString();
                String hourCondText1 = response.body().getForecast().getForecastday().get(0).getHour().get(2).getCondition().getText().toString();
                String hourCondText2 = response.body().getForecast().getForecastday().get(0).getHour().get(4).getCondition().getText().toString();
                String hourCondText3 = response.body().getForecast().getForecastday().get(0).getHour().get(6).getCondition().getText().toString();
                String hourCondText4 = response.body().getForecast().getForecastday().get(0).getHour().get(8).getCondition().getText().toString();
                String hourCondText5 = response.body().getForecast().getForecastday().get(0).getHour().get(10).getCondition().getText().toString();
                String hourCondText6 = response.body().getForecast().getForecastday().get(0).getHour().get(12).getCondition().getText().toString();
                String hourCondText7 = response.body().getForecast().getForecastday().get(0).getHour().get(14).getCondition().getText().toString();
                String hourCondText8 = response.body().getForecast().getForecastday().get(0).getHour().get(16).getCondition().getText().toString();
                String hourCondText9 = response.body().getForecast().getForecastday().get(0).getHour().get(18).getCondition().getText().toString();
                String hourCondText10 = response.body().getForecast().getForecastday().get(0).getHour().get(20).getCondition().getText().toString();
                String hourCondText11 = response.body().getForecast().getForecastday().get(0).getHour().get(22).getCondition().getText().toString();


                String hour0 = response.body().getForecast().getForecastday().get(0).getHour().get(0).getTime().substring(11,16).toString();
                String hour1 = response.body().getForecast().getForecastday().get(0).getHour().get(2).getTime().substring(11,16).toString();
                String hour2 = response.body().getForecast().getForecastday().get(0).getHour().get(4).getTime().substring(11,16).toString();
                String hour3 = response.body().getForecast().getForecastday().get(0).getHour().get(6).getTime().substring(11,16).toString();
                String hour4 = response.body().getForecast().getForecastday().get(0).getHour().get(8).getTime().substring(11,16).toString();
                String hour5 = response.body().getForecast().getForecastday().get(0).getHour().get(10).getTime().substring(11,16).toString();
                String hour6 = response.body().getForecast().getForecastday().get(0).getHour().get(12).getTime().substring(11,16).toString();
                String hour7 = response.body().getForecast().getForecastday().get(0).getHour().get(14).getTime().substring(11,16).toString();
                String hour8 = response.body().getForecast().getForecastday().get(0).getHour().get(16).getTime().substring(11,16).toString();
                String hour9 = response.body().getForecast().getForecastday().get(0).getHour().get(18).getTime().substring(11,16).toString();
                String hour10 = response.body().getForecast().getForecastday().get(0).getHour().get(20).getTime().substring(11,16).toString();
                String hour11 = response.body().getForecast().getForecastday().get(0).getHour().get(22).getTime().substring(11,16).toString();



                TextView time0 = (TextView) findViewById(R.id.time0);
                TextView time1 = (TextView) findViewById(R.id.time1);
                TextView time2 = (TextView) findViewById(R.id.time2);
                TextView time3 = (TextView) findViewById(R.id.time3);
                TextView time4 = (TextView) findViewById(R.id.time4);
                TextView time5 = (TextView) findViewById(R.id.time5);
                TextView time6 = (TextView) findViewById(R.id.time6);
                TextView time7 = (TextView) findViewById(R.id.time7);
                TextView time8 = (TextView) findViewById(R.id.time8);
                TextView time9 = (TextView) findViewById(R.id.time9);
                TextView time10 = (TextView) findViewById(R.id.time10);
                TextView time11 = (TextView) findViewById(R.id.time11);

                time0.setText(hour0);
                time1.setText(hour1);
                time2.setText(hour2);
                time3.setText(hour3);
                time4.setText(hour4);
                time5.setText(hour5);
                time6.setText(hour6);
                time7.setText(hour7);
                time8.setText(hour8);
                time9.setText(hour9);
                time10.setText(hour10);
                time11.setText(hour11);

                TextView cond0 = (TextView) findViewById(R.id.condition0);
                TextView cond1 = (TextView) findViewById(R.id.condition1);
                TextView cond2 = (TextView) findViewById(R.id.condition2);
                TextView cond3 = (TextView) findViewById(R.id.condition3);
                TextView cond4 = (TextView) findViewById(R.id.condition4);
                TextView cond5 = (TextView) findViewById(R.id.condition5);
                TextView cond6 = (TextView) findViewById(R.id.condition6);
                TextView cond7 = (TextView) findViewById(R.id.condition7);
                TextView cond8 = (TextView) findViewById(R.id.condition8);
                TextView cond9 = (TextView) findViewById(R.id.condition9);
                TextView cond10 = (TextView) findViewById(R.id.condition10);
                TextView cond11 = (TextView) findViewById(R.id.condition11);

                cond0.setText(hourCondText0);
                cond1.setText(hourCondText1);
                cond2.setText(hourCondText2);
                cond3.setText(hourCondText3);
                cond4.setText(hourCondText4);
                cond5.setText(hourCondText5);
                cond6.setText(hourCondText6);
                cond7.setText(hourCondText7);
                cond8.setText(hourCondText8);
                cond9.setText(hourCondText9);
                cond10.setText(hourCondText10);
                cond11.setText(hourCondText11);





            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Toast.makeText(getApplication(),"Please connect to internet",Toast.LENGTH_LONG).show();


            }
        });



    }


}
