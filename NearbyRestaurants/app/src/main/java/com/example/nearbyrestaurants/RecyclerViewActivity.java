package com.example.nearbyrestaurants;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.nearbyrestaurants.PlaceModels.ListImplementation;
import com.example.nearbyrestaurants.PlaceModels.PlaceDataList;

public class RecyclerViewActivity extends AppCompatActivity {
    Button ratingSortButton;
    Button distanceSortButton;
    public PlaceDataList restaurants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        restaurants = ListImplementation.sharedInstance();
        ratingSortButton = (Button) findViewById(R.id.get_list_sort_rating);
        distanceSortButton = (Button) findViewById(R.id.get_list_sort_distance);
        initRecyclerView();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void initRecyclerView() {
        sortByRating();
        distanceSortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortByDistance();
            }
        });
        ratingSortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortByRating();
            }
        });
    }

    private void sortByRating(){
        restaurants.sortRating();
        RecyclerView myRecycler = findViewById(R.id.recyclerview);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter();
        myRecycler.setAdapter(adapter);
        myRecycler.setLayoutManager(new LinearLayoutManager(this));
    }
    private void sortByDistance(){
        restaurants.sortDistance();
        RecyclerView myRecycler = findViewById(R.id.recyclerview);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter();
        myRecycler.setAdapter(adapter);
        myRecycler.setLayoutManager(new LinearLayoutManager(this));
    }
}