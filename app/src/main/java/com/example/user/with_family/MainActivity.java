package com.example.user.with_family;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.with_family.Interfaces.DdayListener;
import com.example.user.with_family.db.DBManager;
import com.example.user.with_family.ui.drawlayout.DrawAdapter;
import com.example.user.with_family.ui.drawlayout.DrawItem;
import com.example.user.with_family.ui.f_album.Album_Fragment;
import com.example.user.with_family.ui.f_calendar.Calendar_Fragment;
import com.example.user.with_family.ui.f_chat.Chat_Fragment;
import com.example.user.with_family.ui.f_control.ControlFragment;
import com.example.user.with_family.ui.f_home.HomeFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener ,DdayListener{
    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;
    private Album_Fragment albumFragment;
    private Calendar_Fragment calendarFragment;
    private Chat_Fragment chatFragment;
    private ControlFragment controlFragment;
    private HomeFragment homeFragment;
    private DrawerLayout drawerLayout;
    private TextView opendrawlayout;
    private TextView set_Dday;
    private ArrayList<DrawItem> drawItems;
    private RecyclerView drawlayout_recyclerview;
    private DrawAdapter drawAdapter;
    private DBManager dbManager;
    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initStatusbar();
        init();
        setDrawerLayout();
        initView();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

    }

    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;
        if (drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
            Log.e("asdsd","asdsds");
            drawerLayout.closeDrawer(Gravity.RIGHT);
        } else {
            if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime) {
                super.onBackPressed();
            } else {
                backPressedTime = tempTime;
                Toast.makeText(getApplicationContext(), "종료하시려면 한번더 눌러주세요", Toast.LENGTH_SHORT).show();
            }
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initStatusbar() {
        View view = getWindow().getDecorView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (view != null) {
                view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                getWindow().setStatusBarColor(Color.parseColor("#ffc0cb"));
            }
        } else getWindow().setStatusBarColor(Color.parseColor("#000"));
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

    private void setDrawerLayout() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawlayout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        opendrawlayout = (TextView) findViewById(R.id.open_draw);
        opendrawlayout.setOnClickListener(this);
        set_Dday = (TextView) findViewById(R.id.dday);
        drawlayout_recyclerview = (RecyclerView) findViewById(R.id.drawlayout_recyclerview);
        drawlayout_recyclerview.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        drawItems = new ArrayList<DrawItem>();
        drawItems.add(new DrawItem("aaaaaaa"));
        drawAdapter = new DrawAdapter(getApplicationContext(), drawItems);
        drawlayout_recyclerview.setAdapter(drawAdapter);

        dbManager = new DBManager(getApplicationContext(), "Write", null, 1);



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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.open_draw:
                drawerLayout.openDrawer(drawlayout_recyclerview);
                break;
        }
    }

    @Override
    public void DdayFresh() {

        String a  =  dbManager.all_table_name();
        String[] splites = a.split(",");
        for(int i=0;i<splites.length;i++){
            Log.e("qqqqqqqqqqqqqqq",splites[i]);
            drawItems.add(new DrawItem("asdsdsd"));
            drawItems.add(new DrawItem(dbManager.getDday(splites[i])));
            drawItems.add(new DrawItem(dbManager.getDday(splites[i])));
        }
        drawAdapter.notifyDataSetChanged();


    }
}
