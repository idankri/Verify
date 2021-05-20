package com.example.verify.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.verify.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IntroductionFragmentInternal3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IntroductionFragmentInternal3 extends Fragment {

    public IntroductionFragmentInternal3() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IntroductionFragmentInternal3.
     */
    // TODO: Rename and change types and number of parameters
    public static IntroductionFragmentInternal3 newInstance(String param1, String param2) {
        IntroductionFragmentInternal3 fragment = new IntroductionFragmentInternal3();
        Bundle args = new Bundle();
        fragment.setArguments(args);
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
        return inflater.inflate(R.layout.fragment_introduction_internal3, container, false);
    }
}