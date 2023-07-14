package com.example.binhdv35.fishing_sales_manage.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.binhdv35.fishing_sales_manage.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StatisticalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatisticalFragment extends Fragment {

    public StatisticalFragment() {
        // Required empty public constructor
    }

    public static StatisticalFragment newInstance() {
        StatisticalFragment fragment = new StatisticalFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_statistical, container, false);
    }
}