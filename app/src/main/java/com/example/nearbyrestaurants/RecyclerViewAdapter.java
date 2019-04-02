package com.example.nearbyrestaurants;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nearbyrestaurants.PlaceModels.PlaceDataList;
import com.example.nearbyrestaurants.PlaceModels.ListImplementation;

class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private final PlaceDataList restaurants;

    public RecyclerViewAdapter() {
        restaurants = ListImplementation.sharedInstance();
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        try {
            String rating = String.valueOf(restaurants.getPlaces().get(position).getRating());
            holder.RestaurantName.setText(restaurants.getPlaces().get(position).getName());
            holder.RestaurantAddress.setText(restaurants.getPlaces().get(position).getAddress());
            holder.RestaurantRating.setText("Rating " + rating + "/5");

        } catch (NullPointerException e) {
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView RestaurantName;
        TextView RestaurantRating;
        TextView RestaurantAddress;

        public ViewHolder(View itemView) {
            super(itemView);
            RestaurantName = (TextView) itemView.findViewById(R.id.restaurantName);
            RestaurantRating = (TextView) itemView.findViewById(R.id.restaurant_rating);
            RestaurantAddress = (TextView) itemView.findViewById(R.id.restaurant_address);
        }
    }

    @Override
    public int getItemCount() {
        return restaurants.getPlaces().size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
