package com.example.nearbyrestaurants.PlaceModels;

import java.util.List;

public interface PlaceDataList {
    List<PlaceData> getPlaces();
    void addPlace(PlaceData newPlace);
}