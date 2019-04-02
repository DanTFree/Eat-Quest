package com.example.nearbyrestaurants.PlaceModels;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ListImplementation implements PlaceDataList {

    private static ListImplementation sInstance;
    private  List<PlaceData> restaurantList;

    synchronized public static PlaceDataList sharedInstance() {
        if (sInstance == null) {
            sInstance = new ListImplementation();
        }
        return sInstance;
    }

    private ListImplementation() {
        restaurantList = new ArrayList<>();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public List<PlaceData> getPlaces() {
        restaurantList.sort(new Comparator<PlaceData>() {
            @Override
            public int compare(PlaceData o1, PlaceData o2) {
                if(o1.getRating() > o2.getRating()){return -1;}
                if(o1.getRating() < o2.getRating()){return 1;}
                return 0;
            }
        });
        return restaurantList;
    }

    @Override
    public void addPlace(PlaceData newPlace) {
        restaurantList.add(newPlace);
    }
}