package com.example.verify.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.verify.R;
import com.example.verify.components.ApartmentReview;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ApartmentReviewOverviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ApartmentReviewOverviewFragment extends Fragment {

    private ApartmentReview mReview;

    public ApartmentReviewOverviewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ApartmentReviewOverviewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ApartmentReviewOverviewFragment newInstance(String param1, String param2) {
        ApartmentReviewOverviewFragment fragment = new ApartmentReviewOverviewFragment();
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
        return inflater.inflate(R.layout.fragment_apartment_review_overview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Uncomment and add URL to enable image view functionality
        /*
        try {
            URL url = new URL();
            final ImageSwitcher _ImageSwitcher = ((ImageSwitcher) Objects.requireNonNull(getView()).findViewById(R.id.apartment_review_image_switcher));
            _ImageSwitcher.setFactory((android.widget.ViewSwitcher.ViewFactory) getContext());
            _ImageSwitcher.setImageDrawable(new BitmapDrawable(BitmapFactory.decodeStream(url.openConnection().getInputStream())));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        createFromReview(mReview);
    }

    public void setReview(ApartmentReview review){
        mReview = review;

    }

    private void createFromReview(ApartmentReview review){
        ((TextView) Objects.requireNonNull(getView().findViewById(R.id.apartment_review_entry_date))).setText(review.getEntryDate());
        ((TextView) Objects.requireNonNull(getView().findViewById(R.id.apartment_review_leave_date))).setText(review.getLeaveDate());
        ((TextView) Objects.requireNonNull(getView().findViewById(R.id.apartment_review_tenant_type))).setText(review.getTenantsComposition());
        ((TextView) Objects.requireNonNull(getView().findViewById(R.id.apartment_review_general_rating))).setText(Double.toString((int)review.getGeneralRating()));
        ((RatingBar) Objects.requireNonNull(getView().findViewById(R.id.apartment_tenant_rating))).setRating((int)review.getGeneralRating());
        ((TextView) Objects.requireNonNull(getView().findViewById(R.id.apartment_review_apartment_holder_rating_num))).setText(Double.toString(review.getLandlordRating()));
        ((TextView) Objects.requireNonNull(getView().findViewById(R.id.apartment_review_maintenance_rating_num))).setText(Double.toString(review.getMaintenanceRating()));
        ((TextView) Objects.requireNonNull(getView().findViewById(R.id.apartment_review_around_rating_num))).setText(Double.toString(review.getAroundRating()));
        ((LinearProgressIndicator) Objects.requireNonNull(getView().findViewById(R.id.apartment_review_apartment_holder_rating_progress))).setProgress(review.getLandlordRating() * 20);
        ((LinearProgressIndicator) Objects.requireNonNull(getView().findViewById(R.id.apartment_review_maintenance_rating_progress))).setProgress(review.getMaintenanceRating() * 20);
        ((LinearProgressIndicator) Objects.requireNonNull(getView().findViewById(R.id.apartment_review_around_rating_progress))).setProgress(review.getAroundRating() * 20);

    }



}