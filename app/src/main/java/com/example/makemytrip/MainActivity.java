package com.example.makemytrip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;

import com.example.makemytrip.Fragments.Advance_booking;
import com.example.makemytrip.Fragments.Home;
import com.example.makemytrip.Fragments.View_booking;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawerlayout_id);
        navigationView = findViewById(R.id.navigationview_id);
        toolbar = findViewById(R.id.toobar_id);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_id, new Home()).commit();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;

                switch (item.getItemId()) {
                    case R.id.home_id:
                        fragment = new Home();
                        break;

                    case R.id.view_booking_id:
                        fragment = new View_booking();
                        break;

                    case R.id.advance_booking_id:
                        fragment = new Advance_booking();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_id, fragment).commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}