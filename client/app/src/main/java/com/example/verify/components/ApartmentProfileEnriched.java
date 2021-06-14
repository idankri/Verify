package com.example.verify.components;

import java.util.ArrayList;
import java.util.List;

public class ApartmentProfileEnriched extends ApartmentProfile {
    private List<ApartmentReview> mReviews;
    private final double mGeneralRating;
    private final double mApartmentHolderRating;
    private final double mMaintenanceRating;
    private final double mAroundRating;

    public ApartmentProfileEnriched(String city, String street, String building, String floor, String apartment) {
        super(city, street, building, floor, apartment);
        mReviews = new ArrayList<>();
        mGeneralRating = 2.5;
        mApartmentHolderRating = 2.5;
        mMaintenanceRating = 2.5;
        mAroundRating = 2.5;
    }


    public static ApartmentProfileEnriched fromApartmentProfile(ApartmentProfile profile){
        return new ApartmentProfileEnriched(
                profile.getCity(),
                profile.getStreet(),
                profile.getBuilding(),
                profile.getFloor(),
                profile.getApartment());
    }

    public double getGeneralRating(){
        return mGeneralRating;
    }

    public double getApartmentHolderRating(){
        return mApartmentHolderRating;
    }

    public double getMaintenanceRating(){
        return mMaintenanceRating;
    }

    public double getAroundRating(){
        return mAroundRating;
    }
}
