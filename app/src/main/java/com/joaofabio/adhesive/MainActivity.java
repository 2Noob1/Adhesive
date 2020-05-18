package com.joaofabio.adhesive;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getWindow().setStatusBarColor(getResources().getColor(R.color.secoundaryColor));
        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.FragmentView,
                    new fragment_home()).commit();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.menu_main_Home:
                            selectedFragment = new fragment_home();
                            break;
                        case R.id.menu_main_Gallery:
                            selectedFragment = new fragment_gallery();
                            break;
                        case R.id.menu_main_Contacts:
                            selectedFragment = new fragment_contacts();
                            break;
                        case R.id.menu_main_Settings:
                            selectedFragment = new fragment_settings();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.FragmentView, selectedFragment).commit();
                    return true;
                }
    };
}
