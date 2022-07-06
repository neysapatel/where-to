package com.example.whereto.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
//import com.daprlabs.cardstack.SwipeDeck;

import com.daprlabs.cardstack.SwipeDeck;
import com.example.whereto.Question;
import com.example.whereto.QuestionsAdapter;
import com.example.whereto.R;

import java.util.ArrayList;

public class PlanTripFragment extends Fragment {
    private SwipeDeck cardStack;
    private ArrayList<Question> questionList;

    public PlanTripFragment() {
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        questionList = new ArrayList<>();
        cardStack = (SwipeDeck) view.findViewById(R.id.swipe_deck);

        // TODO: replace this with API data
        questionList.add(new Question("Golden Gate Bridge", "San Francisco", "A bridge"));
        questionList.add(new Question("Walk of Fame", "Los Angeles", "sidewalk with stars"));

        final QuestionsAdapter adapter = new QuestionsAdapter(questionList, view.getContext());

        cardStack.setAdapter(adapter);

        cardStack.setEventCallback(new SwipeDeck.SwipeEventCallback() {
            @Override
            public void cardSwipedLeft(int position) {
                Toast.makeText(view.getContext(), "Card Swiped Left", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void cardSwipedRight(int position) {
                Toast.makeText(view.getContext(), "Card Swiped Right", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void cardClicked(int position) {
                Toast.makeText(view.getContext(), "Please swipe left or right", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void cardsDepleted() {
                Toast.makeText(view.getContext(), "No more questions left", Toast.LENGTH_SHORT).show();
            }

            public void cardActionDown() {
                Toast.makeText(view.getContext(), "Please do not swipe down", Toast.LENGTH_SHORT).show();
            }

            public void cardActionUp() {
                Toast.makeText(view.getContext(), "Please do not swipe up", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_plan, container, false);
    }
}