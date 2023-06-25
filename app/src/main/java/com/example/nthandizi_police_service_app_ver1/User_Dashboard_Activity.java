package com.example.nthandizi_police_service_app_ver1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class User_Dashboard_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
        replaceFragment(new FragmentBottom_home());

        //creating object and initialisation
        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout_id);
        NavigationView navigationView = findViewById(R.id.navigation_view);

        //when navigation menu icon clicked should open
        toolbar.setNavigationOnClickListener(view -> drawerLayout.openDrawer(GravityCompat.START));

        //when drawer navigation items clicked or selected
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            item.setChecked(true);
            drawerLayout.closeDrawer(GravityCompat.START);

            if(id== R.id.personal_profile_id){
                replaceFragment(new Fragment_PersonProfile());
            }
            else if(id==R.id.comm_profile_id){
                replaceFragment(new Fragment_CommunityProfile());
            }
            else if(id== R.id.ps_profile_id){
                replaceFragment(new Fragment_PoliceProfile());
            }
            else if(id==R.id.viewReport_id){
                replaceFragment(new Fragment_ViewReports());
            }
            else if(id== R.id.GeneratedReport_id){
                replaceFragment(new Fragment_GeneratedReports());
            }
            else if(id==R.id.NpsInfo_id){
                replaceFragment(new Fragment_Nps_info());
            }
            else{
                return true;
            }

            return false;
        });

        //when bottom navigation items clicked or selected
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_menu_id);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int bottomNav_id = item.getItemId();

            if (bottomNav_id == R.id.btm_home) {
                replaceFragment(new FragmentBottom_home());
            } else if (bottomNav_id == R.id.btm_message) {
                replaceFragment(new FragmentBottom_text_report());
            } else if (bottomNav_id == R.id.btm_picture) {
                replaceFragment(new FragmentBottom_image_report());
            } else if (bottomNav_id == R.id.btm_video) {
                replaceFragment(new FragmentBottom_video_report());
            }

            return true;
        });

    }
    //to replace framework with fragment
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();
    }
}