package com.example.whereto.models;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.whereto.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class BusinessAdapter extends RecyclerView.Adapter<BusinessAdapter.ViewHolder> {
    Context context;
    List<YelpBusiness> businesses;
    List<YelpEvent> events;

    public BusinessAdapter(Context context, List<YelpBusiness> businesses) {
        this.context = context;
        this.businesses = businesses;
    }

    public void clear() {
        businesses.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<YelpBusiness> list) {
        businesses.addAll(list);
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
        YelpBusiness business = businesses.get(position);
        holder.bind(business);
    }

    @Override
    public int getItemCount() {
        return businesses.size();
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

        public void bind(YelpBusiness business) {
            tvAttractionName.setText(business.getName());

            String address = "";
            if (business.getAddress() != null) {
                for (String addressLine : business.getAddress()) {
                    address += addressLine;
                }
                tvAddress.setText(address);
            }

            tvDistance.setText("" + business.getDistanceAway());
            tvRating.setText("" + business.getRating());

            String categories = "";
            for (YelpCategory category : business.getCategories()) {
                categories += category.getTitle() + ", ";
            }
            tvCategories.setText(categories);

            if (business.getImageUrl() != null) {
                final int RADIUS = 75;
                Glide.with(context).load(business.getImageUrl()).apply(new RequestOptions().centerCrop().transform(new RoundedCorners(RADIUS))).into(ivAttractionImage);
                ivAttractionImage.setVisibility(View.VISIBLE);
            } else {
                ivAttractionImage.setVisibility(View.GONE);
            }
        }
    }
}

