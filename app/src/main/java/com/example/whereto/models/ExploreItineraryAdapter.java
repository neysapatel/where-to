package com.example.whereto.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.whereto.R;

import java.util.List;

public class ExploreItineraryAdapter extends RecyclerView.Adapter<ExploreItineraryAdapter.ViewHolder> {
    Context context;
    List<ExploreItinerary> itineraries;

    public ExploreItineraryAdapter(Context context, List<ExploreItinerary> itineraries) {
        this.context = context;
        this.itineraries = itineraries;
    }

    public void clear() {
        itineraries.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<ExploreItinerary> list) {
        itineraries.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_explore_itinerary, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ExploreItinerary itinerary = itineraries.get(position);
        holder.bind(itinerary);
    }

    @Override
    public int getItemCount() {
        return itineraries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivAttractionImage;
        TextView tvAttractionName;
        TextView tvAddress;
        TextView tvDistance;
        TextView tvRating;
        TextView tvCategories;
        ImageView ivProfilePic;
        TextView tvUsername;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvUsername = itemView.findViewById(R.id.tvUsername);
            ivProfilePic = itemView.findViewById(R.id.ivProfilePic);
            ivAttractionImage = itemView.findViewById(R.id.ivImage);
            tvAttractionName = itemView.findViewById(R.id.tvName);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvDistance = itemView.findViewById(R.id.tvDistance);
            tvRating = itemView.findViewById(R.id.tvRating);
            tvCategories = itemView.findViewById(R.id.tvCategories);
        }

        public void bind(ExploreItinerary itinerary) {
            tvUsername.setText(itinerary.getUsername());
            if (itinerary.getProfilePic() != null) {
                final int RADIUS = 75;
                Glide.with(context).load(itinerary.getProfilePic()).apply(new RequestOptions().centerCrop().transform(new RoundedCorners(RADIUS))).into(ivProfilePic);
                ivProfilePic.setVisibility(View.VISIBLE);
            } else {
                ivProfilePic.setVisibility(View.GONE);
            }

            tvAttractionName.setText(itinerary.getName());
            tvAddress.setText(itinerary.getDisplay_address());
            tvDistance.setText(itinerary.getDistanceAway() + " miles away");
            tvRating.setText("Rating: " + itinerary.getRating());
            tvCategories.setText(itinerary.getCategories());

            if (itinerary.getImageUrl() != null) {
                final int RADIUS = 75;
                Glide.with(context).load(itinerary.getImageUrl()).apply(new RequestOptions().centerCrop().transform(new RoundedCorners(RADIUS))).into(ivAttractionImage);
                ivAttractionImage.setVisibility(View.VISIBLE);
            } else {
                ivAttractionImage.setVisibility(View.GONE);
            }
        }
    }
}

