package com.example.user.with_family.ui.f_calendar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.with_family.R;

/**
 * Created by User on 2017-11-11.
 */

public class Calendar_Fragment   extends Fragment {

    public Calendar_Fragment(){

    }
    public static Calendar_Fragment newInstance() {
        Bundle args = new Bundle();
        Calendar_Fragment fragment = new Calendar_Fragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar,container,false);
        return view;
    }
}