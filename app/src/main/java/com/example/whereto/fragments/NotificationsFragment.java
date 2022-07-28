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

import com.example.whereto.Constants;
import com.example.whereto.R;
import com.example.whereto.models.BusinessAdapter;
import com.example.whereto.models.NotificationAdapter;
import com.example.whereto.models.NotificationsInterface;
import com.example.whereto.models.SharedViewModel;
import com.example.whereto.models.UserPreferences;
import com.example.whereto.models.WeatherNotification;
import com.example.whereto.models.WeatherService;
import com.example.whereto.models.YelpBusiness;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NotificationsFragment extends Fragment {
    private SharedViewModel model;
    UserPreferences userPreferences;
    NotificationAdapter adapter;
    RecyclerView rvNotifications;
    List<WeatherNotification> notifications = new ArrayList<>();


    public NotificationsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notifications, container, false);
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new NotificationAdapter(getContext(), notifications);
        rvNotifications = view.findViewById(R.id.rvNotifications);
        rvNotifications.setLayoutManager(new LinearLayoutManager(getContext()));
        rvNotifications.setAdapter(adapter);

        getNotifications();
    }

    public void getNotifications() {
        final Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.NOTIFICATION_BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        final NotificationsInterface notificationsInterface = retrofit.create(NotificationsInterface.class);

        Call<WeatherService> weatherServiceCall = notificationsInterface.getWeather(Constants.NOTIFICATION_API_KEY, userPreferences.getDestination());

        weatherServiceCall.enqueue(new Callback<WeatherService>() {
            @Override
            public void onResponse(Call<WeatherService> call, Response<WeatherService> response) {
                if (response.body() != null) {
                    WeatherNotification notification = new WeatherNotification();
                    WeatherService.CurrentWeather weather = response.body().getCurrentWeather();

                    notification.setCity(userPreferences.getDestination());
                    notification.setTemp(weather.getTemp());
                    notification.setFeelsLike(weather.getFeelsLike());
                    notification.setWind(weather.getWind());
                    notification.setDirection(weather.getDirection());
                    notification.setPrecipitation(weather.getPrecipitation());
                    notification.setHumidity(weather.getHumidity());
                    notification.setUv(weather.getUv());
                    notification.setCondition(weather.getWeatherCondition());
                    notification.setIcon(weather.getWeatherIcon());

                    notifications.add(notification);
                    adapter.addAll(notifications);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<WeatherService> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}