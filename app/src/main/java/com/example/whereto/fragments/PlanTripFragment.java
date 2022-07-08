package com.example.whereto.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
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
import com.example.whereto.models.UserPreferences;
import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.Slider;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;

public class PlanTripFragment extends Fragment {
    private SwipeDeck cardStack;
    private ArrayList<Question> questionList;
    EditText startDate;
    EditText endDate;
    DatePickerDialog datePickerDialog;

    public PlanTripFragment() {}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        UserPreferences userPreferences = new UserPreferences();

        getQuestionDeck(view, userPreferences);
        getTripStartDate(userPreferences);
        getTripEndDate(userPreferences);
        getBudget(userPreferences);

        Button planTripButton;
        planTripButton = view.findViewById(R.id.planButton);

        planTripButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                planTrip(userPreferences);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_plan, container, false);
    }

    public void getQuestionDeck(final View view, UserPreferences userPreferences) {
        questionList = new ArrayList<>();
        cardStack = (SwipeDeck) view.findViewById(R.id.swipe_deck);

        final String FOOD = "food/restaurants";
        final String HOTELS = "hotels";
        final String TOURS = "tours";
        final String ATHLETIC = "active/athletic activities";
        final String ARTS = "arts & entertainment";
        final String SHOPPING = "outlet stores/shopping centres/souvenir shops";
        final String NIGHTLIFE = "nightlife/bars";
        final String SPAS = "beauty & spas";

        questionList.add(new Question(FOOD));
        questionList.add(new Question(HOTELS));
        questionList.add(new Question(TOURS));
        questionList.add(new Question(ATHLETIC));
        questionList.add(new Question(ARTS));
        questionList.add(new Question(SHOPPING));
        questionList.add(new Question(NIGHTLIFE));
        questionList.add(new Question(SPAS));

        final QuestionsAdapter adapter = new QuestionsAdapter(questionList, view.getContext());

        cardStack.setAdapter(adapter);

        cardStack.setEventCallback(new SwipeDeck.SwipeEventCallback() {
            @Override
            public void cardSwipedLeft(int position) {}

            @Override
            public void cardSwipedRight(int position) {
                String prompt = (questionList.get(position)).getPrompt();

                switch (prompt) {
                    case FOOD:
                        userPreferences.setFood(true);
                        break;
                    case HOTELS:
                        userPreferences.setHotels(true);
                        break;
                    case TOURS:
                        userPreferences.setTours(true);
                        break;
                    case ATHLETIC:
                        userPreferences.setAthletic(true);
                        break;
                    case ARTS:
                        userPreferences.setArts(true);
                        break;
                    case SHOPPING:
                        userPreferences.setShopping(true);
                        break;
                    case NIGHTLIFE:
                        userPreferences.setNightlife(true);
                        break;
                    default:
                        userPreferences.setBeauty(true);
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

    public void getTripStartDate(UserPreferences userPreferences) {
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
                        String tripStartDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        Date today = Calendar.getInstance().getTime();
                        Date start = null;
                        try {
                            start = new SimpleDateFormat("dd/MM/yyyy").parse(tripStartDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if (today.after(start)) {
                            Toast.makeText(view.getContext(), "Please select a start date in the future", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (userPreferences.getTripEnd() != null) {
                            Date end = null;
                            try {
                                end = new SimpleDateFormat("dd/MM/yyyy").parse(userPreferences.getTripEnd());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            if (end.before(start)) {
                                Toast.makeText(view.getContext(), "Please select a start date before your end date", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }

                        userPreferences.setTripStart(tripStartDate);
                        startDate.setText(userPreferences.getTripStart());
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
    }

    public void getTripEndDate(UserPreferences userPreferences) {
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
                        String tripEndDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        Date today = Calendar.getInstance().getTime();
                        Date end = null;
                        try {
                            end = new SimpleDateFormat("dd/MM/yyyy").parse(tripEndDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if (userPreferences.getTripStart() != null) {
                            Date start = null;
                            try {
                                start = new SimpleDateFormat("dd/MM/yyyy").parse(userPreferences.getTripStart());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            if (start.after(end)) {
                                Toast.makeText(view.getContext(), "Please select an end date after the start date", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }


                        if (today.after(end)) {
                            Toast.makeText(view.getContext(), "Please select an end date in the future", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        userPreferences.setTripEnd(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                        endDate.setText(userPreferences.getTripEnd());
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
    }

    public void getBudget(UserPreferences userPreferences) {
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
                userPreferences.setBudget(value);
            }
        });
    }

    public void getRadius(UserPreferences userPreferences) {
        Slider radiusSlider = getView().findViewById(R.id.radiusSlider);
        radiusSlider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                userPreferences.setRadius(value);
            }
        });
    }

    public void planTrip(UserPreferences userPreferences) {
        // TODO: use tripStart, tripEnd, budget, radius, and preference booleans to plan trip
    }
}