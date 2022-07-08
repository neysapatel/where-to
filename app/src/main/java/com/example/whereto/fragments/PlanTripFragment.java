package com.example.whereto.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.daprlabs.cardstack.SwipeDeck;

import com.example.whereto.Question;
import com.example.whereto.QuestionsAdapter;
import com.example.whereto.R;
import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.Slider;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;

public class PlanTripFragment extends Fragment {
    private SwipeDeck cardStack;
    private ArrayList<Question> questionList;
    EditText startDate;
    EditText endDate;
    DatePickerDialog datePickerDialog;

    public float budget = 0;
    public float radius = 0;
    public String tripStart;
    public String tripEnd;
    public boolean food = false;
    public boolean hotels = false;
    public boolean tours = false;
    public boolean athletic = false;
    public boolean arts = false;
    public boolean shopping = false;
    public boolean nightlife = false;
    public boolean beauty = false;

    public PlanTripFragment() {}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getQuestionDeck(view);
        getTripStartDate();
        getTripEndDate();
        getBudget();

        Button planTripButton;
        planTripButton = view.findViewById(R.id.planButton);

        planTripButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                planTrip();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_plan, container, false);
    }

    public void getQuestionDeck(final View view) {
        questionList = new ArrayList<>();
        cardStack = (SwipeDeck) view.findViewById(R.id.swipe_deck);

        questionList.add(new Question("food/restaurants"));
        questionList.add(new Question("hotels"));
        questionList.add(new Question("tours"));
        questionList.add(new Question("active/athletic activities"));
        questionList.add(new Question("arts & entertainment"));
        questionList.add(new Question("outlet stores/shopping centres/souvenir shops"));
        questionList.add(new Question("nightlife/bars"));
        questionList.add(new Question("beauty & spas"));

        final QuestionsAdapter adapter = new QuestionsAdapter(questionList, view.getContext());

        cardStack.setAdapter(adapter);

        cardStack.setEventCallback(new SwipeDeck.SwipeEventCallback() {
            @Override
            public void cardSwipedLeft(int position) {}

            @Override
            public void cardSwipedRight(int position) {
                String prompt = (questionList.get(position)).getPrompt();

                switch (prompt) {
                    case "food/restaurants":
                        food = true;
                        break;
                    case "hotels":
                        hotels = true;
                        break;
                    case "tours":
                        tours = true;
                        break;
                    case "active/athletic activities":
                        athletic = true;
                        break;
                    case "arts & entertainment":
                        arts = true;
                        break;
                    case "outlet stores/shopping centres/souvenir shops":
                        shopping = true;
                        break;
                    case "nightlife/bars":
                        nightlife = true;
                        break;
                    default:
                        beauty = true;
                        break;
                }
            }

            @Override
            public void cardClicked(int position) {
                Toast.makeText(view.getContext(), "Please swipe left or right", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void cardsDepleted() {}

            public void cardActionDown() {
                Toast.makeText(view.getContext(), "Please do not swipe down", Toast.LENGTH_SHORT).show();
            }

            public void cardActionUp() {
                Toast.makeText(view.getContext(), "Please do not swipe up", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getTripStartDate() {

        startDate = (EditText) getView().findViewById(R.id.startDate);
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        tripStart = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        startDate.setText(tripStart);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
    }

    public void getTripEndDate() {
        endDate = (EditText) getView().findViewById(R.id.endDate);
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        tripEnd = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        endDate.setText(tripEnd);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
    }

    public float getBudget() {
        Slider budgetSlider = getView().findViewById(R.id.budgetSlider);
        budgetSlider.setLabelFormatter(new LabelFormatter() {
            @NonNull
            @Override
            public String getFormattedValue(float value) {
                NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
                currencyFormat.setCurrency(Currency.getInstance("USD"));
                return currencyFormat.format(value);
            }
        });

        budgetSlider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                budget = value;
            }
        });
        return budget;
    }

    public float getRadius() {
        Slider radiusSlider = getView().findViewById(R.id.radiusSlider);
        radiusSlider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                radius = value;
            }
        });
        return radius;
    }

    public void planTrip() {
        // TODO: use tripStart, tripEnd, budget, radius, and preference booleans to plan trip
    }
}