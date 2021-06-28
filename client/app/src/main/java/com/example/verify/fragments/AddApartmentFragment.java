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
 * Use the {@link AddApartmentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddApartmentFragment extends BaseFragment {

    private AddApartmentFragmentListener mAddApartmentFragmentListener;

    public AddApartmentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddApartmentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddApartmentFragment newInstance(String param1, String param2) {
        AddApartmentFragment fragment = new AddApartmentFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_apartment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View button = view.findViewById(R.id.add_apr_review_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAddApartmentFragmentListener != null){
                    mAddApartmentFragmentListener.onAddApartmentButtonClick();
                }
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof DummySearchFragment.DummySearchFragmentListener){
            mAddApartmentFragmentListener = (AddApartmentFragmentListener) context;
        }
        else{
            throw new RuntimeException(context.toString() + " must imlement DummySearchFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mAddApartmentFragmentListener = null;
    }

    public interface AddApartmentFragmentListener{
        void onAddApartmentButtonClick();
    }
}