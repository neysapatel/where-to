package com.example.whereto.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.whereto.LoginActivity;
import com.example.whereto.R;
import com.example.whereto.models.Review;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class RatingsFragment extends Fragment {
    Button postReviewButton;
    EditText etReview;

    public RatingsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ratings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        postReviewButton = view.findViewById(R.id.postReviewButton);
        etReview = view.findViewById(R.id.review);

        postReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String review = etReview.getText().toString();
                ParseUser currentUser = ParseUser.getCurrentUser();
                saveReview(review, currentUser);
            }
        });
    }

    public void saveReview(String userReview, ParseUser currentUser) {
        Review review = new Review();
        review.setKeyUserReview(userReview);
        review.setKeyUser(currentUser);
        review.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Toast.makeText(getContext(), "Error posting", Toast.LENGTH_SHORT).show();
                }
                etReview.setText("");
                Toast.makeText(getView().getContext(), "Success!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}