package com.example.verify.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.verify.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DummySearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DummySearchFragment extends BaseFragment {

    private DummySearchFragmentListener mDummySearchFragmentListener;

    public DummySearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DummySearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BaseFragment newInstance(String param1, String param2) {
        BaseFragment fragment = new DummySearchFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View button = view.findViewById(R.id.dummy_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mDummySearchFragmentListener != null){
                    mDummySearchFragmentListener.onDummyButtonClick();
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dummy_search, container, false);
    }



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof DummySearchFragmentListener){
            mDummySearchFragmentListener = (DummySearchFragmentListener) context;
        }
        else{
            throw new RuntimeException(context.toString() + " must imlement DummySearchFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mDummySearchFragmentListener = null;
    }

    public interface DummySearchFragmentListener{
        void onDummyButtonClick();
    }

}