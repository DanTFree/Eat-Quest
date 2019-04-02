package com.example.nearbyrestaurants;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.nearbyrestaurants.PlaceModels.PlaceData;
import com.example.nearbyrestaurants.PlaceModels.PlaceDataList;
import com.example.nearbyrestaurants.PlaceModels.ListImplementation;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.nearbyrestaurants.AppConfiguration.GEOMETRY;
import static com.example.nearbyrestaurants.AppConfiguration.LATITUDE;
import static com.example.nearbyrestaurants.AppConfiguration.LOCATION;
import static com.example.nearbyrestaurants.AppConfiguration.LONGITUDE;
import static com.example.nearbyrestaurants.AppConfiguration.NAME;
import static com.example.nearbyrestaurants.AppConfiguration.OK;
import static com.example.nearbyrestaurants.AppConfiguration.PLACE_ID;
import static com.example.nearbyrestaurants.AppConfiguration.PROXIMITY_RADIUS;
import static com.example.nearbyrestaurants.AppConfiguration.RATING;
import static com.example.nearbyrestaurants.AppConfiguration.STATUS;
import static com.example.nearbyrestaurants.AppConfiguration.VICINITY;
import static com.example.nearbyrestaurants.AppConfiguration.ZERO_RESULTS;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 123;
    private static final float DEFAULT_ZOOM = 15f;
    private static final float SHOWING_RESTAURANTS_ZOOM = 14f;
    private boolean mLocationPermissionGranted = false;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private GoogleMap mMap;
    private ImageView mGPS;
    private Button getListButton;
    private PlaceData mPlace;
    private LatLng currentlatlng;
    public PlaceDataList restaurants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        restaurants = ListImplementation.sharedInstance();
        mGPS = (ImageView) findViewById(R.id.gps);
        getListButton = (Button) findViewById(R.id.get_list);
        getLocationPermission();
    }

    private void init() {
        mGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDeviceLocation();
            }
        });

        getListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RecyclerViewActivity.class);
                startActivity(i);
            }
        });
    }

    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(@NonNull PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                places.release();
                return;
            }
            final Place place = places.get(0);
            try {
                mPlace = new PlaceData(place.getName().toString(), place.getAddress().toString(),
                        place.getId().toString(), place.getRating(), place.getLatLng());

            } catch (NullPointerException e) {
            }
            currentlatlng = mPlace.getLatLng();
            moveCameraZoom(currentlatlng, DEFAULT_ZOOM);
            places.release();
        }
    };

    private void getLocationPermission() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MainActivity.this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionGranted = false;
                            return;
                        }
                    }
                    mLocationPermissionGranted = true;
                }
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (mLocationPermissionGranted) {
            getDeviceLocation();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);

            init();
        }
    }

    private void getDeviceLocation() {
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            if (mLocationPermissionGranted) {
                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Location currentLocation = (Location) task.getResult();
                            currentlatlng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                            moveCameraZoom(currentlatlng, DEFAULT_ZOOM);
                            loadNearByPlaces(currentlatlng.latitude, currentlatlng.longitude);
                        } else {
                            Toast.makeText(MainActivity.this, "Unable to get current location.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } catch (SecurityException e) {
        }
    }

    private void moveCameraZoom(LatLng latLng, float zoom) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        MarkerOptions options = new MarkerOptions().position(latLng).title("Current Location");
        mMap.addMarker(options);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    private void loadNearByPlaces(double latitude, double longitude) {
        String type = "restaurant";
        StringBuilder restaurantUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        restaurantUrl.append("location=").append(latitude).append(",").append(longitude);
        restaurantUrl.append("&radius=").append(PROXIMITY_RADIUS);
        restaurantUrl.append("&types=").append(type);
        restaurantUrl.append("&sensor=true");
        restaurantUrl.append("&key=" + getResources().getString(R.string.places_api_key));

        JsonObjectRequest request = new JsonObjectRequest(restaurantUrl.toString(),

                new Response.Listener<JSONObject>() {
                    @Override

                    public void onResponse(JSONObject result) {
                        parseLocationResult(result);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });

        AppController.getInstance().addToRequestQueue(request);
    }

    private void parseLocationResult(JSONObject result) {
        String place_id, placeAddress = "", placeName = null;
        double placeRating = 0;
        double latitude, longitude;

        try {
            JSONArray jsonArray = result.getJSONArray("results");

            if (result.getString(STATUS).equalsIgnoreCase(OK)) {

                mMap.clear();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject place = jsonArray.getJSONObject(i);

                    place_id = place.getString(PLACE_ID);
                    if (!place.isNull(NAME)) {
                        placeName = place.getString(NAME);
                    }
                    if (!place.isNull(RATING)) {
                        placeRating = place.getDouble(RATING);
                    }
                    if (!place.isNull(VICINITY)) {
                        placeAddress = place.getString(VICINITY);
                    }

                    latitude = place.getJSONObject(GEOMETRY).getJSONObject(LOCATION).getDouble(LATITUDE);
                    longitude = place.getJSONObject(GEOMETRY).getJSONObject(LOCATION).getDouble(LONGITUDE);
                    LatLng latLng = new LatLng(latitude, longitude);
                    PlaceData places = new PlaceData(placeName, placeAddress, place_id, placeRating, latLng);
                    restaurants.addPlace(places);

                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(latLng);
                    markerOptions.title(placeName);
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

                    mMap.addMarker(markerOptions);
                    moveCameraZoom(currentlatlng, SHOWING_RESTAURANTS_ZOOM);
                }

                Toast.makeText(getBaseContext(), jsonArray.length() + " restaurants found!", Toast.LENGTH_LONG).show();

            } else if (result.getString(STATUS).equalsIgnoreCase(ZERO_RESULTS)) {
                Toast.makeText(getBaseContext(), "No restaurants found in 2KM radius!",
                        Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}