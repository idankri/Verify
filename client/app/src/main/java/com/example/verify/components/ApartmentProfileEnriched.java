package com.example.verify.components;

import java.util.List;

public class ApartmentProfileEnriched extends ApartmentProfile {
    private List<ApartmentReview> mReviews;
    private final double mGeneralRating;
    private final double mApartmentHolderRating;
    private final double mMaintenanceRating;
    private final double mAroundRating;

    public ApartmentProfileEnriched(String city, String street, int building, int floor, int apartment,
                                    List<ApartmentReview> reviews) {
        super(city, street, building, floor, apartment);
        mReviews = reviews;

        // TODO: iterate list and use calculate method for average
        mApartmentHolderRating = 2.5;
        mMaintenanceRating = 2.5;
        mAroundRating = 2.5;

        mGeneralRating = calculateAverage(new double[]{mApartmentHolderRating, mAroundRating, mMaintenanceRating});
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

    private static double calculateAverage(double[] ratings){
        double sum = 0;

        for(int i=0; i<ratings.length; i++){
            sum += ratings[i];
        }

        return sum/ratings.length;
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

    public int getNumReviews(){
        return mReviews.size();
    }

    public List<ApartmentReview> getReviews(){
        return mReviews;
    }
}
