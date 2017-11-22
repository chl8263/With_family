package com.example.user.with_family;

import android.annotation.SuppressLint;
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

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Album_Fragment albumFragment;
    private Calendar_Fragment calendarFragment;
    private Chat_Fragment chatFragment;
    private ControlFragment controlFragment;
    private HomeFragment homeFragment;
    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        init();
        initView();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

    }

    public void init() {
        onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        firstSelectNavView("home");
                        return true;
                    case R.id.navigation_album:
                        firstSelectNavView("album");
                        return true;
                    case R.id.navigation_chat:
                        firstSelectNavView("chat");
                        return true;
                    case R.id.navigation_calendar:
                        firstSelectNavView("calendar");
                        return true;
                    case R.id.navigation_control:
                        firstSelectNavView("control");
                        return true;
                }
                return false;
            }
        };
    }

    public void initView() {
        albumFragment = Album_Fragment.newInstance();
        calendarFragment = Calendar_Fragment.newInstance();
        chatFragment = Chat_Fragment.newInstance();
        controlFragment = ControlFragment.newInstance();
        homeFragment = HomeFragment.newInstance();
        initNavView();
    }

    private void initNavView() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.fragment, homeFragment, "home");
        fragmentTransaction.add(R.id.fragment, albumFragment, "album");
        fragmentTransaction.add(R.id.fragment, chatFragment, "chat");
        fragmentTransaction.add(R.id.fragment, calendarFragment, "calendar");
        fragmentTransaction.add(R.id.fragment, controlFragment, "control");
        fragmentTransaction.hide(albumFragment);
        fragmentTransaction.hide(chatFragment);
        fragmentTransaction.hide(calendarFragment);
        fragmentTransaction.hide(controlFragment);
        fragmentTransaction.commit();
    }

    private void firstSelectNavView(String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        @SuppressLint("RestrictedApi") List<Fragment> fragments = fragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            if (fragment.getTag().equals(tag)) {
                fragmentTransaction.show(fragment);
            } else {
                fragmentTransaction.hide(fragment);
            }
        }
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }

    private void selectNavView(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.fragment, fragment);
        fragmentTransaction.commit();
    }
}
