package com.example.user.with_family;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.Window;

import com.example.user.with_family.ui.f_album.Album_Fragment;
import com.example.user.with_family.ui.f_calendar.Calendar_Fragment;
import com.example.user.with_family.ui.f_chat.Chat_Fragment;
import com.example.user.with_family.ui.f_control.ControlFragment;
import com.example.user.with_family.ui.f_home.HomeFragment;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        init();
        BottomNavigationView navigation = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

    }
    public void init(){
        selectNavigationView(HomeFragment.newInstance());
        onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_home:
                        selectNavigationView(HomeFragment.newInstance());
                        return true;
                    case R.id.navigation_album:
                        selectNavigationView(Album_Fragment.newInstance());
                        return true;
                    case R.id.navigation_chat:
                        selectNavigationView(Chat_Fragment.newInstance());
                        return true;
                    case R.id.navigation_calendar:
                        selectNavigationView(Calendar_Fragment.newInstance());
                        return true;
                    case R.id.navigation_control:
                        selectNavigationView(ControlFragment.newInstance());
                        return true;
                }
                return false;
            }
        };
    }

    private void selectNavigationView(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.fragment, fragment);
        fragmentTransaction.commit();
    }
}
