package com.example.user.tourassistant.page_fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.tourassistant.R;
import com.example.user.tourassistant.weather.ApiTool;
import com.example.user.tourassistant.weather.Example;
import com.example.user.tourassistant.weather.Movie;
import com.example.user.tourassistant.weather.MoviesAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherFragment extends Fragment {

    double latitude,longitude;
    private List<Movie> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MoviesAdapter mAdapter;
    View view;
    public WeatherFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_weather, container, false);
        latitude=23.777176;
        longitude=90.399452;
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        mAdapter = new MoviesAdapter(movieList,getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        //prepareMovieData();

        showWeather(latitude,longitude);
        return view;
    }

    private void prepareMovieData() {
        Movie movie = new Movie("Mad Max: Fury Road", "Action & Adventure", "2015");
        movieList.add(movie);

        movie = new Movie("Inside Out", "Animation, Kids & Family", "2015");
        movieList.add(movie);

        movie = new Movie("Star Wars: Episode VII - The Force Awakens", "Action", "2015");
        movieList.add(movie);

        movie = new Movie("Shaun the Sheep", "Animation", "2015");
        movieList.add(movie);

        movie = new Movie("The Martian", "Science Fiction & Fantasy", "2015");
        movieList.add(movie);

        movie = new Movie("Mission: Impossible Rogue Nation", "Action", "2015");
        movieList.add(movie);

        movie = new Movie("Up", "Animation", "2009");
        movieList.add(movie);

        movie = new Movie("Star Trek", "Science Fiction", "2009");
        movieList.add(movie);

        movie = new Movie("The LEGO Movie", "Animation", "2014");
        movieList.add(movie);

        movie = new Movie("Iron Man", "Action & Adventure", "2008");
        movieList.add(movie);

        movie = new Movie("Aliens", "Science Fiction", "1986");
        movieList.add(movie);

        movie = new Movie("Chicken Run", "Animation", "2000");
        movieList.add(movie);

        movie = new Movie("Back to the Future", "Science Fiction", "1985");
        movieList.add(movie);

        movie = new Movie("Raiders of the Lost Ark", "Action & Adventure", "1981");
        movieList.add(movie);

        movie = new Movie("Goldfinger", "Action & Adventure", "1965");
        movieList.add(movie);

        movie = new Movie("Guardians of the Galaxy", "Science Fiction & Fantasy", "2014");
        movieList.add(movie);

        mAdapter.notifyDataSetChanged();
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



                for (int i=0;i<5;i++){
                    String day = response.body().getForecast().getForecastday().get(0).getDate().toString();



                    String image =response.body().getForecast().getForecastday().get(0).getHour().get(i).getCondition().getIcon().toString();

                    movieList.add(new Movie(day,image,"yyy"));
                }
                mAdapter.notifyDataSetChanged();











            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Toast.makeText(getActivity(),"Please connect to internet",Toast.LENGTH_LONG).show();


            }
        });



    }

}
