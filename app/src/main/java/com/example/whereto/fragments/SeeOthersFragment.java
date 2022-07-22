package com.example.whereto.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whereto.R;
import com.example.whereto.models.Itinerary;
import com.example.whereto.models.ItineraryAdapter;
import com.example.whereto.models.SharedViewModel;
import com.example.whereto.models.UserPreferences;
import com.example.whereto.models.YelpBusiness;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.List;

public class SeeOthersFragment extends Fragment {
    private SharedViewModel model;
    private UserPreferences userPreferences;
    protected ItineraryAdapter adapter;
    protected List<YelpBusiness> allItineraries;
    RecyclerView rvItinerary;

    public SeeOthersFragment() {
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

        displayOthers();
        adapter = new ItineraryAdapter(getContext(), allItineraries);
        rvItinerary = getView().findViewById(R.id.rvItinerary);
        rvItinerary.setLayoutManager(new LinearLayoutManager(getContext()));
        rvItinerary.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_others, container, false);
    }

    public void displayOthers() {
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
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }
}