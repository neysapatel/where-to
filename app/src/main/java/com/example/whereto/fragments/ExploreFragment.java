package com.example.whereto.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whereto.R;
import com.example.whereto.models.Itinerary;
import com.example.whereto.models.ItineraryAdapter;
import com.example.whereto.models.OthersAdapter;
import com.example.whereto.models.SharedViewModel;
import com.example.whereto.models.UserPreferences;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class ExploreFragment extends Fragment {
    private SharedViewModel model;
    private UserPreferences userPreferences;
    protected ItineraryAdapter itineraryAdapter;
    protected List<Itinerary> allItineraries = new ArrayList<>();
    protected OthersAdapter othersAdapter;
    protected List<ParseUser> allUsers = new ArrayList<>();
    RecyclerView rvItinerary;
    RecyclerView rvOthers;

    public ExploreFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        model.item.observe(getActivity(), new Observer<UserPreferences>() {

            @Override
            public void onChanged(@Nullable UserPreferences updatedObject) {
                if (updatedObject != null) userPreferences = updatedObject;
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_explore, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        displayOtherUsers();
        othersAdapter = new OthersAdapter(getContext(), allUsers);
        rvOthers = view.findViewById(R.id.rvOthers);
        rvOthers.setLayoutManager(new LinearLayoutManager(getContext()));
        rvOthers.setAdapter(othersAdapter);

        displayOtherItineraries();
        itineraryAdapter = new ItineraryAdapter(getContext(), allItineraries);
        rvItinerary = view.findViewById(R.id.rvItinerary);
        rvItinerary.setLayoutManager(new LinearLayoutManager(getContext()));
        rvItinerary.setAdapter(itineraryAdapter);
    }

    public void displayOtherUsers() {
        ParseQuery<ParseUser> query = ParseQuery.getQuery(ParseUser.class);
        query.include("username");
        query.include("destination");
        query.include("profile_pic");
        query.addDescendingOrder("createdAt");
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> users, ParseException e) {
                if (e != null) {
                    return;
                }

                othersAdapter.addAll(users);
                othersAdapter.notifyDataSetChanged();
            }
        });
    }

    public void displayOtherItineraries() {
        ParseRelation<ParseUser> following = ParseUser.getCurrentUser().getRelation("following");
        ParseQuery<ParseUser> followingQuery = following.getQuery();

        followingQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> user, ParseException e) {
                ParseQuery<Itinerary> query = new ParseQuery<Itinerary>("Itinerary");
                query.whereContainedIn("user", user);

                // remove all the itineraries of people that have their privacy sharing settings to no one
                query.whereNotEqualTo("privacy_control", "none");

                // remove all the itineraries of people that have blocked this user
                for (ParseUser currentUser : user) {
                    ParseRelation<ParseUser> blocked = currentUser.getRelation("blocked");
                    ParseQuery<ParseUser> blockedQuery = blocked.getQuery();
                    blockedQuery.findInBackground(new FindCallback<ParseUser>() {
                        @Override
                        public void done(List<ParseUser> blockedUsers, ParseException e) {
                            for (ParseUser parseUser : blockedUsers) {
                                if (parseUser == ParseUser.getCurrentUser()) {
                                    query.whereNotEqualTo(Itinerary.KEY_USER, parseUser);
                                }
                            }
                        }
                    });
                }

                query.findInBackground(new FindCallback<Itinerary>() {
                    @Override
                    public void done(List<Itinerary> itineraries, ParseException e) {
                        allItineraries.addAll(itineraries);
                        itineraryAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }
}