package com.example.nearbyrestaurants;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.nearbyrestaurants.PlaceModels.ListImplementation;
import com.example.nearbyrestaurants.PlaceModels.PlaceDataList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.nearbyrestaurants.AppConfiguration.OK;
import static com.example.nearbyrestaurants.AppConfiguration.STATUS;
import static com.example.nearbyrestaurants.AppConfiguration.PHONE;
import static com.example.nearbyrestaurants.AppConfiguration.ZERO_RESULTS;

public class InfoActivity extends AppCompatActivity {
    public PlaceDataList restaurants;
    String iPosition= "hello";
    TextView restName, restAdd, restPhone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        restaurants = ListImplementation.sharedInstance();
        Intent mIntent = getIntent();
        int positionNumb = mIntent.getIntExtra("itemPosition", 0);


        restName = (TextView) findViewById(R.id.rest_name);
        iPosition = restaurants.getPlaces().get(positionNumb).getName();
        restName.setText(iPosition);

        restAdd = (TextView) findViewById(R.id.rest_address);
        iPosition = restaurants.getPlaces().get(positionNumb).getAddress();
        restAdd.setText(iPosition);

        loadDetails();



    }

    private void loadDetails(){
        Intent mIntent = getIntent();
        int positionNumb = mIntent.getIntExtra("itemPosition", 0);

        //https://maps.googleapis.com/maps/api/place/details/json?placeid=ChIJ5V-QXKiwj4ARv5e-BSB9fiA&fields=formatted_phone_number,photo&key=AIzaSyCEpLRjoupchPtJoXt9Wd50OXWRtkQ4Fgk
        StringBuilder restaurantUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/details/json?");
        restaurantUrl.append("placeid=" +restaurants.getPlaces().get(positionNumb).getID());
        restaurantUrl.append("&fields=formatted_phone_number,photo");
        restaurantUrl.append("&key=AIzaSyCEpLRjoupchPtJoXt9Wd50OXWRtkQ4Fgk");

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, restaurantUrl.toString(), (String)null,

                new Response.Listener<JSONObject>() {
                    @Override

                    public void onResponse(JSONObject result) {
                        jsonParse(result);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        AppController.getInstance().addToRequestQueue(request);

    }

    private void jsonParse(JSONObject result) {
        String phoneNumber = "N/A";

        try {

            JSONObject jObj = result.getJSONObject("result");
            if (result.getString(STATUS).equalsIgnoreCase(OK)) {

                phoneNumber = jObj.getString("formatted_phone_number");

                restPhone = (TextView) findViewById(R.id.rest_phone);
                restPhone.setText(phoneNumber);
            }
        }catch(JSONException e){
            e.printStackTrace();
        }
    }
}
