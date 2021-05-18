package com.example.verify.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.verify.R;
import com.example.verify.components.ApartmentProfile;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ApartmentProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ApartmentProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ApartmentProfile mProfile;

    public ApartmentProfileFragment() {
        // Required empty public constructor
    }

    public void setProfile(ApartmentProfile profile){
        mProfile = profile;
    }

    public void createFromApartmentProfile(ApartmentProfile apartmentProfile){
        // TODO: update to be null safe and make more abstract6
        ((TextView)Objects.requireNonNull(getView()).findViewById(R.id.apartment_address)).setText(
                "רחוב " + apartmentProfile.getStreet() + " " + apartmentProfile.getBuilding()
        + ", " + apartmentProfile.getCity());
        ((TextView)Objects.requireNonNull(getView()).findViewById(R.id.floor_number_text)).setText(
                apartmentProfile.getFloor()
        );
        ((TextView)Objects.requireNonNull(getView()).findViewById(R.id.apartment_number_text)).setText(
                apartmentProfile.getApartment()
        );

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ApartmentProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ApartmentProfileFragment newInstance(String param1, String param2) {
        ApartmentProfileFragment fragment = new ApartmentProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        createFromApartmentProfile(mProfile);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_apartment_profile, container, false);
    }
}