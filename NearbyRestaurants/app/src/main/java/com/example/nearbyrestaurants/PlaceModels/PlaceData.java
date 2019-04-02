package com.example.nearbyrestaurants.PlaceModels;

import com.google.android.gms.maps.model.LatLng;

public class PlaceData {
    private String name;
    private String address;
    private String id;

    private double rating;
    private LatLng latLng;

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public double getRating() {
        return rating;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public PlaceData(String name, String address, String id, double rating, LatLng latLng) {
        this.name = name;
        this.address = address;
        this.id = id;
        this.rating = rating;
        this.latLng = latLng;
    }

    @Override
    public String toString() {
        return "PlaceDetail{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", id='" + id + '\'' +
                ", rating=" + rating +
                ", latLng=" + latLng +
                '}';
    }
}