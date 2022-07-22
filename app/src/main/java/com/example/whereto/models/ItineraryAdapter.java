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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ItineraryAdapter extends RecyclerView.Adapter<ItineraryAdapter.ViewHolder> {
    Context context;
    List<Itinerary> itineraries;

    public ItineraryAdapter(Context context, List<Itinerary> itineraries) {
        this.context = context;
        this.itineraries = itineraries;
    }

    public void clear() {
        itineraries.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Itinerary> list) {
        itineraries.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_itinerary, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Itinerary itinerary = itineraries.get(position);
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivAttractionImage = itemView.findViewById(R.id.ivImage);
            tvAttractionName = itemView.findViewById(R.id.tvName);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvDistance = itemView.findViewById(R.id.tvDistance);
            tvRating = itemView.findViewById(R.id.tvRating);
            tvCategories = itemView.findViewById(R.id.tvCategories);
        }

        public void bind(Itinerary itinerary) {
            tvAttractionName.setText(itinerary.getBusinessName());

            String address = "";
            if (itinerary.getBusinessAddress() != null) {
                for (String addressLine : itinerary.getBusinessAddress()) {
                    address += addressLine;
                }
                tvAddress.setText(address);
            }

            tvDistance.setText("" + itinerary.getBusinessDistanceAway());
            tvRating.setText("" + itinerary.getBusinessRating());

            String categories = "";
            for (YelpCategory category : itinerary.getBusinessCategories()) {
                categories += category.getTitle() + ", ";
            }
            tvCategories.setText(categories);

            if (itinerary.getBusinessImageUrl() != null) {
                final int RADIUS = 75;
                Glide.with(context).load(itinerary.getBusinessImageUrl()).apply(new RequestOptions().centerCrop().transform(new RoundedCorners(RADIUS))).into(ivAttractionImage);
                ivAttractionImage.setVisibility(View.VISIBLE);
            } else {
                ivAttractionImage.setVisibility(View.GONE);
            }
        }
    }
}

