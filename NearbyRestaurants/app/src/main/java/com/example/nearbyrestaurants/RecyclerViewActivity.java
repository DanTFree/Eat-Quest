package com.example.nearbyrestaurants;

<<<<<<< HEAD
import android.content.Intent;
=======
>>>>>>> c14a2bea848367d7ff4972148f8c7bb34f28c94f
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
<<<<<<< HEAD
import android.view.View;
import android.widget.Button;

import com.example.nearbyrestaurants.PlaceModels.ListImplementation;
import com.example.nearbyrestaurants.PlaceModels.PlaceDataList;

public class RecyclerViewActivity extends AppCompatActivity {
    private Button ratingSortButton;
    private Button distanceSortButton;
    public PlaceDataList restaurants;
=======

public class RecyclerViewActivity extends AppCompatActivity {
>>>>>>> c14a2bea848367d7ff4972148f8c7bb34f28c94f

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
<<<<<<< HEAD
        restaurants = ListImplementation.sharedInstance();
        ratingSortButton = (Button) findViewById(R.id.get_list_sort_rating);
        distanceSortButton = (Button) findViewById(R.id.get_list_sort_distance);
=======
>>>>>>> c14a2bea848367d7ff4972148f8c7bb34f28c94f
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
<<<<<<< HEAD
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
        adapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getBaseContext(), InfoActivity.class);
                intent.putExtra("itemPosition", position);
                startActivity(intent);
            }
        });
    }
    private void sortByDistance(){
        restaurants.sortDistance();
        RecyclerView myRecycler = findViewById(R.id.recyclerview);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter();
        myRecycler.setAdapter(adapter);
        myRecycler.setLayoutManager(new LinearLayoutManager(this));
        adapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getBaseContext(), InfoActivity.class);
                intent.putExtra("itemPosition", position);
                startActivity(intent);
            }
        });
=======
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
>>>>>>> c14a2bea848367d7ff4972148f8c7bb34f28c94f
    }
}