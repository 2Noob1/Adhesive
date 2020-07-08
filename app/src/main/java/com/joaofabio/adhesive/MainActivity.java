package com.joaofabio.adhesive;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    public static int ToWhere = 0;
    public static boolean needsChange = false;

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

    @Override
    protected void onResume() {
        super.onResume();
        if (needsChange && ToWhere == 1) {
            getSupportFragmentManager().beginTransaction().replace(R.id.FragmentView, new fragment_contacts()).commit();
            ((BottomNavigationView) findViewById(R.id.bottom_navigation)).setSelectedItemId(R.id.menu_main_Contacts);
            needsChange = false;
            ToWhere = 0;
        }
    }
}
