package com.example.whereto.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.whereto.R;
import com.parse.ParseFile;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.Collections;
import java.util.List;

public class OthersAdapter extends RecyclerView.Adapter<OthersAdapter.ViewHolder> {
    Context context;
    List<ParseUser> users;

    public OthersAdapter(Context context, List<ParseUser> users) {
        this.context = context;
        this.users = users;
    }

    public void clear() {
        users.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<ParseUser> list) {
        users.addAll(list);
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
        ParseUser user = users.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void swapItem(int fromPosition, int toPosition) {
        Collections.swap(users, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivProfilePic;
        TextView tvUsername;
        TextView tvCity;
        Button followButton;
        Button blockButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivProfilePic = itemView.findViewById(R.id.ivProfilePic);
            tvUsername = itemView.findViewById(R.id.tvName);
            tvCity = itemView.findViewById(R.id.tvDestination);
            followButton = itemView.findViewById(R.id.followButton);
            blockButton = itemView.findViewById(R.id.blockButton);
        }

        public void bind(ParseUser user) {
            tvUsername.setText(user.getUsername());
            tvCity.setText(user.getString("destination"));

            ParseFile profilePic = user.getParseFile("profile_pic");
            if (profilePic != null) {
                Glide.with(context).load(profilePic.getUrl()).apply(RequestOptions.circleCropTransform()).into(ivProfilePic);
            }

            followButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ParseRelation followingRelation = ParseUser.getCurrentUser().getRelation("following");
                    followingRelation.add(user);
                }
            });

            blockButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ParseRelation blockedRelation = ParseUser.getCurrentUser().getRelation("blocked");
                    blockedRelation.add(user);
                }
            });
        }
    }
}

