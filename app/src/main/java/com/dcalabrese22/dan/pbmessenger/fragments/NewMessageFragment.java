package com.dcalabrese22.dan.pbmessenger.fragments;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dcalabrese22.dan.pbmessenger.R;


public class NewMessageFragment extends Fragment {


    public NewMessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_new_message, container, false);
        return rootView;
    }

}
