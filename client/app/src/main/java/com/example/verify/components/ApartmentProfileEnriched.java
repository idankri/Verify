package com.example.verify.components;

import java.util.List;

public class ApartmentProfileEnriched extends ApartmentProfile {
    private List<ApartmentReview> mReviews;
    private double mGeneralRating;
    private double mLandlordRating;
    private double mMaintenanceRating;
    private double mAroundRating;

    public ApartmentProfileEnriched(String city, String street, int building, int floor, int apartment,
                                    List<ApartmentReview> reviews) {
        super(city, street, building, floor, apartment);
        mReviews = reviews;

        int numOfReviews = mReviews.size();

        double[] aroundRatings = new double[numOfReviews];
        double[] maintenanceRatings = new double[numOfReviews];
        double[] landlordRatings = new double[numOfReviews];

        for (int i=0; i<numOfReviews; i++) {
            aroundRatings[i] = mReviews.get(i).getAroundRating();
            maintenanceRatings[i] = mReviews.get(i).getMaintenanceRating();
            landlordRatings[i] = mReviews.get(i).getLandlordRating();
        }

        mLandlordRating = ApartmentReview.calculateAverage(landlordRatings);
        mMaintenanceRating = ApartmentReview.calculateAverage((maintenanceRatings));
        mAroundRating = ApartmentReview.calculateAverage(aroundRatings);

        mGeneralRating = ApartmentReview.calculateAverage(new double[]{mLandlordRating, mAroundRating, mMaintenanceRating});
    }

    /* (why is this necessary?)
    public static ApartmentProfileEnriched fromApartmentProfile(ApartmentProfile profile){
        return new ApartmentProfileEnriched(
                profile.getCity(),
                profile.getStreet(),
                profile.getBuilding(),
                profile.getFloor(),
                profile.getApartment());
    }
     */


    public double getGeneralRating(){
        return mGeneralRating;
    }

    public double getApartmentHolderRating(){
        return mLandlordRating;
    }

    public double getMaintenanceRating(){
        return mMaintenanceRating;
    }

    public double getAroundRating(){
        return mAroundRating;
    }

    public int getNumReviews(){
        return mReviews.size();
    }

    public List<ApartmentReview> getReviews(){
        return mReviews;
    }
}
