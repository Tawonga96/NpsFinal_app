package com.example.nthandizi_police_service_app_ver1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class User_Dashboard_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        //creating object and initialisation
        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout_id);
        NavigationView navigationView = findViewById(R.id.navigation_view);

        //when navigation menu icon clicked should open
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        //when navigation items clicked or selected
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                item.setChecked(true);
                drawerLayout.closeDrawer(GravityCompat.START);
                switch (id) {

                    case R.id.personal_profile_id:
                        replaceFragment(new Fragment_PersonProfile()); break;

                    case R.id.comm_profile_id:
                        replaceFragment(new Fragment_CommunityProfile()); break;

                    case R.id.ps_profile_id:
                        replaceFragment(new Fragment_PoliceProfile()); break;

                    case R.id.viewReport_id:
                        replaceFragment(new Fragment_ViewReports()); break;

                    case R.id.GeneratedReport_id:
                        replaceFragment(new Fragment_GeneratedReports()); break;

                    case R.id.NpsInfo_id:
                        replaceFragment(new FragmentBottom_text_report()); break;

                    default:
                        return true;
                }
                return false;
            }
        });
    }
    //to replace framework with fragment
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();
    }

//    public void OnRadioBtnClicked(View view) {
//        RadioGroup radioGroup = view.findViewById(R.id.radioGroup_Id);
//        RadioButton radioButton = view.findViewById(radioGroup.getCheckedRadioButtonId());
//        Toast.makeText(this, radioButton.getText()+" is selected ", Toast.LENGTH_SHORT).show();
//    }
}