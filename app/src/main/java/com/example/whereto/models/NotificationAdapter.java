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

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    Context context;
    List<WeatherNotification> notifications;

    public NotificationAdapter(Context context, List<WeatherNotification> notifications) {
        this.context = context;
        this.notifications = notifications;
    }

    public void clear() {
        notifications.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<WeatherNotification> list) {
        notifications.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_notification, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WeatherNotification notification = notifications.get(position);
        holder.bind(notification);
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvCity;
        TextView tvTemp;
        TextView tvFeelsLike;
        TextView tvCondition;
        ImageView ivIcon;
        TextView tvWind;
        TextView tvUv;
        TextView tvPrecipitation;
        TextView tvHumidity;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCity = itemView.findViewById(R.id.tvDestination);
            tvTemp = itemView.findViewById(R.id.tvTemp);
            tvFeelsLike = itemView.findViewById(R.id.tvFeelsLike);
            tvCondition = itemView.findViewById(R.id.tvCondition);
            ivIcon = itemView.findViewById(R.id.ivIcon);
            tvWind = itemView.findViewById(R.id.tvWind);
            tvUv = itemView.findViewById(R.id.tvWind);
            tvPrecipitation = itemView.findViewById(R.id.tvPercipitation);
            tvHumidity = itemView.findViewById(R.id.tvHumidity);
        }

        public void bind(WeatherNotification notification) {
            tvCity.setText(notification.getCity());
            tvTemp.setText("" + notification.getTemp());
            tvFeelsLike.setText("" + notification.getFeelsLike());
            tvCondition.setText("" + notification.getCondition());
            tvWind.setText("" + notification.getWind());
            tvUv.setText("" + notification.getUv());
            tvPrecipitation.setText("" + notification.getPrecipitation());
            tvHumidity.setText("" + notification.getHumidity());

            if (notification.getIcon() != null) {
                final int RADIUS = 75;
                Glide.with(context).load(notification.getIcon()).apply(new RequestOptions().centerCrop().transform(new RoundedCorners(RADIUS))).into(ivIcon);
                ivIcon.setVisibility(View.VISIBLE);
            } else {
                ivIcon.setVisibility(View.GONE);
            }
        }
    }
}

