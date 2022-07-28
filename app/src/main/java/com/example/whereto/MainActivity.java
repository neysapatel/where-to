package com.example.whereto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.whereto.fragments.HomeFragment;
import com.example.whereto.fragments.ExploreFragment;
import com.example.whereto.fragments.NotificationsFragment;
import com.example.whereto.fragments.PlanTripFragment;
import com.example.whereto.fragments.RatingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    final FragmentManager fragmentManager = getSupportFragmentManager();
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.action_plan_trip:
                        fragment = new PlanTripFragment();
                        break;
                    case R.id.action_home:
                        fragment = new HomeFragment();
                        break;
                    case R.id.action_others:
                        fragment = new ExploreFragment();
                        break;
                    case R.id.action_rating:
                        fragment = new RatingsFragment();
                        break;
                    default:
                        fragment = new NotificationsFragment();
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.container_view, fragment).commit();
                return true;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.action_plan_trip);
    }
}