package com.example.verify.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.verify.R;
import com.example.verify.components.ApartmentProfileEnriched;
import com.example.verify.components.ApartmentReview;
import com.example.verify.data.ApartmentDetailsFetcher;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ApartmentProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ApartmentProfileFragment extends Fragment {


    private ApartmentProfileEnriched mProfile;
    private ApartmentProfileFragmentListener mApartmentProfileFragmentListener;

    public ApartmentProfileFragment() {
        // Required empty public constructor
    }

    public void setProfile(ApartmentProfileEnriched profile){
        mProfile = profile;
    }

    public void createFromApartmentProfile(ApartmentProfileEnriched apartmentProfile){
        // TODO: update to be null safe and make more abstract6
        ((TextView)Objects.requireNonNull(getView()).findViewById(R.id.apartment_address)).setText(
                "רחוב " + apartmentProfile.getStreet() + " " + apartmentProfile.getBuilding()
        + ", " + apartmentProfile.getCity());
        ((TextView)Objects.requireNonNull(getView()).findViewById(R.id.floor_number_text)).setText(
                Integer.toString(apartmentProfile.getFloor())
        );
        ((TextView)Objects.requireNonNull(getView()).findViewById(R.id.apartment_number_text)).setText(
                Integer.toString(apartmentProfile.getApartment())
        );
        ((TextView)Objects.requireNonNull(getView()).findViewById(R.id.apartment_profile_review_num_text)).setText(
                "(" + apartmentProfile.getNumReviews() + " ביקורות)"
        );
        ((TextView)Objects.requireNonNull(getView()).findViewById(R.id.apartment_general_rating_num)).setText(
                Double.toString((int)apartmentProfile.getGeneralRating())
        );
        ((TextView)Objects.requireNonNull(getView()).findViewById(R.id.apartment_profile_apartment_holder_rating_num)).setText(
                Double.toString(apartmentProfile.getApartmentHolderRating())
        );
        ((LinearProgressIndicator)Objects.requireNonNull(getView()).findViewById(R.id.apartment_profile_apartment_holder_rating_progress)).setProgress(
                (int)(apartmentProfile.getApartmentHolderRating() * 20.0)
        );
        ((TextView)Objects.requireNonNull(getView()).findViewById(R.id.apartment_profile_maintenance_rating_num)).setText(
                Double.toString(apartmentProfile.getMaintenanceRating())
        );
        ((LinearProgressIndicator)Objects.requireNonNull(getView()).findViewById(R.id.apartment_profile_maintenance_rating_progress)).setProgress(
                (int)(apartmentProfile.getMaintenanceRating() * 20.0)
        );
        ((TextView)Objects.requireNonNull(getView()).findViewById(R.id.apartment_profile_around_rating_num)).setText(
                Double.toString(apartmentProfile.getAroundRating())
        );
        ((LinearProgressIndicator)Objects.requireNonNull(getView()).findViewById(R.id.apartment_profile_around_rating_progress)).setProgress(
                (int)(apartmentProfile.getAroundRating() * 20.0)
        );

        LinearLayout reviewsList = (LinearLayout) Objects.requireNonNull(getView()).findViewById(R.id.apartment_profile_review_container_constraint);
        reviewsList.removeAllViews();
        View currentView;
        for(ApartmentReview currentReview : apartmentProfile.getReviews()){
            currentView = getLayoutInflater().inflate(getResources().getLayout(R.layout.fragment_apartment_profile_review_shortcut), null);
            // TODO: extract to method
            ((TextView) Objects.requireNonNull(currentView.findViewById(R.id.apartment_review_entry_date))).setText(currentReview.getEntryDate());
            ((TextView) Objects.requireNonNull(currentView.findViewById(R.id.apartment_review_leave_date))).setText(currentReview.getLeaveDate());
            ((TextView) Objects.requireNonNull(currentView.findViewById(R.id.review_shortcut_tenant_kind))).setText(currentReview.getTenantsComposition());
            ((RatingBar) Objects.requireNonNull(currentView.findViewById(R.id.review_shortcut_rating_bar))).setRating((float)currentReview.getGeneralRating());

            ViewGroup.LayoutParams a = currentView.getLayoutParams();
            currentView.setMinimumWidth(222);
            currentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mApartmentProfileFragmentListener != null) {
                        String headerText = "רחוב " +
                                mProfile.getStreet() +
                                " " +
                                mProfile.getBuilding() +
                                ", דירה " +
                                mProfile.getApartment() +
                                ", קומה " +
                                mProfile.getFloor();
                        mApartmentProfileFragmentListener.onReviewClick(headerText, currentReview);
                    }
                }
            });
            reviewsList.addView(currentView);
        }

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
        createFromApartmentProfile(mProfile);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof ApartmentProfileFragmentListener){
            mApartmentProfileFragmentListener = (ApartmentProfileFragmentListener) context;
        }
        else{
            throw new RuntimeException(context.toString() + " must imlement DummySearchFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mApartmentProfileFragmentListener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_apartment_profile, container, false);
    }

    public interface ApartmentProfileFragmentListener{
        void onReviewClick(String headerText, ApartmentReview review);
    }
}