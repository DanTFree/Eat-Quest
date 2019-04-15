package com.example.nearbyrestaurants.PlaceModels;

import com.google.android.gms.maps.model.LatLng;

public class PlaceData {
    private String name;
    private String address;
    private String id;

    private double rating;
    private LatLng latLng;
<<<<<<< HEAD
    private double distance;
=======
>>>>>>> c14a2bea848367d7ff4972148f8c7bb34f28c94f

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

<<<<<<< HEAD
    public double getDistance() {
        return distance;
    }

    public String getID(){
        return id;
    }

    public PlaceData(String name, String address, String id, double rating, LatLng latLng, double distance) {
=======
    public PlaceData(String name, String address, String id, double rating, LatLng latLng) {
>>>>>>> c14a2bea848367d7ff4972148f8c7bb34f28c94f
        this.name = name;
        this.address = address;
        this.id = id;
        this.rating = rating;
        this.latLng = latLng;
<<<<<<< HEAD
        this.distance = distance;
=======
>>>>>>> c14a2bea848367d7ff4972148f8c7bb34f28c94f
    }

    @Override
    public String toString() {
        return "PlaceDetail{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", id='" + id + '\'' +
                ", rating=" + rating +
                ", latLng=" + latLng +
<<<<<<< HEAD
                ", distance=" + distance +
=======
>>>>>>> c14a2bea848367d7ff4972148f8c7bb34f28c94f
                '}';
    }
}