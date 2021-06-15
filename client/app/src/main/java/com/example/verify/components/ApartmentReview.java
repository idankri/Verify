package com.example.verify.components;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApartmentReview {
    private String mReviewId;
    private TenantType mTenantType;
    private String mTenantsComposition;
    private String mStayingPeriod;
    private String mEntryDate;
    private String mLeaveDate;

    private double mGeneralRating;
    private int mLandlordRating;
    private int mMaintenanceRating;
    private int mAroundRating;

    private List<String> mLandlordPros;
    private List<String> mMaintenancePros;
    private List<String> mAroundPros;

    private List<String> mLandlordCons;
    private List<String> mMaintenanceCons;
    private List<String> mAroundCons;

    private Map<TextReviewCategory, String> mTextualReviews;

    public ApartmentReview(){

    }

    public ApartmentReview(
            TenantType tenantType,
            String reviewId,
            String tenantsComposition,
            String stayingPeriod,
            String entryDate,
            String leaveDate,
            double generalRating,
            int landlordRating,
            int maintenanceRating,
            int aroundRating,
            List<String>landlordPros,
            List<String> maintenancePros,
            List<String> aroundPros,
            List<String> landlordCons,
            List<String> maintenanceCons,
            List<String> aroundCons,
            Map<TextReviewCategory, String> textualReviews
            ){
        mTenantType = tenantType;
        mReviewId = reviewId;
        mTenantsComposition = tenantsComposition;
        mStayingPeriod = stayingPeriod;
        mEntryDate = entryDate;
        mLeaveDate = leaveDate;
        mGeneralRating = generalRating;
        mLandlordRating = landlordRating;
        mMaintenanceRating = maintenanceRating;
        mAroundRating = aroundRating;
        mLandlordPros = landlordPros;
        mMaintenancePros = maintenancePros;
        mAroundPros = aroundPros;
        mLandlordCons = landlordCons;
        mMaintenanceCons = maintenanceCons;
        mAroundCons = aroundCons;
        mTextualReviews = textualReviews;
    }

    public ApartmentReview(JSONObject json) throws JSONException{

        boolean isStillLiving = json.getBoolean("Still living in the apartment");
        mLeaveDate = isStillLiving ? "עדיין שם" : dateParser(json.getString("Exit date"));

        mReviewId = json.getString("Id");
        mTenantsComposition = json.getString("Tenants composition");
        mTenantType = enumerateTenantType(mTenantsComposition);
        mStayingPeriod = json.getString("Staying period");
        mEntryDate = dateParser(json.getString("Enter date"));

        mLandlordRating = json.getInt("Landlord rating");
        mMaintenanceRating = json.getInt("Maintenance rating");
        mAroundRating = json.getInt("Around rating");
        mGeneralRating = calculateAverage(new double[]{mLandlordRating, mAroundRating, mMaintenanceRating});


        // TODO: Complete all of the following fields:

        mLandlordPros = new ArrayList<String>();
        mMaintenancePros = new ArrayList<String>();
        mAroundPros = new ArrayList<String>();

        mLandlordCons = new ArrayList<String>();
        mMaintenanceCons = new ArrayList<String>();
        mAroundCons = new ArrayList<String>();

        mTextualReviews = new HashMap<TextReviewCategory, String>();
    }

    public String getReviewId(){ return mReviewId; }

    public TenantType getTenantType(){ return mTenantType; }

    public String getTenantsComposition(){ return mTenantsComposition; }

    public String getStayingPeriod(){ return mStayingPeriod; }

    public String getEntryDate(){ return mEntryDate; }

    public String getLeaveDate(){ return mLeaveDate; }

    public double getGeneralRating(){ return mGeneralRating; }

    public int getLandlordRating(){ return mLandlordRating; }

    public int getMaintenanceRating(){ return mMaintenanceRating; }

    public int getAroundRating(){ return mAroundRating; }

    public List<String> getLandlordPros(){ return mLandlordPros; }

    public List<String> getLandlordCons(){ return mLandlordCons; }

    public List<String> getMaintenancePros(){ return mMaintenancePros; }

    public List<String> getMaintenanceCons(){ return mMaintenanceCons; }

    public List<String> getAroundPros(){ return mAroundPros; }

    public List<String> getAroundCons(){ return mAroundCons; }

    public Map<TextReviewCategory, String> getTextualReviews(){ return mTextualReviews; }


    private static String dateParser(String date){
        String year = date.substring(0,3);
        String month = date.substring(5,6);
        String wordMonth = "";

        switch(month){
            case "01":
                wordMonth = "ינואר";
                break;
            case "02":
                wordMonth = "פברואר";
                break;
            case "03":
                wordMonth = "מרץ";
                break;
            case "04":
                wordMonth = "אפריל";
                break;
            case "05":
                wordMonth = "מאי";
                break;
            case "06":
                wordMonth = "יוני";
                break;
            case "07":
                wordMonth = "יולי";
                break;
            case "08":
                wordMonth = "אוגוסט";
                break;
            case "09":
                wordMonth = "ספטמבר";
                break;
            case "10":
                wordMonth = "אוקטובר";
                break;
            case "11":
                wordMonth = "נובמבר";
                break;
            case "12":
                wordMonth = "דצמבר";
                break;
        }

        return wordMonth + " " + year;
    }

    public static double calculateAverage(double[] ratings){
        double sum = 0;

        for(int i=0; i<ratings.length; i++){
            sum += ratings[i];
        }

        return sum/ratings.length;
    }

    private static TenantType enumerateTenantType(String tenantsComposition){
        TenantType result = TenantType.Single;

        switch(tenantsComposition){
            case "דייר/ת יחיד/ה":
                result = TenantType.Single;
                break;
            case "זוג":
                result = TenantType.Couple;
                break;
            case "שותפים/שותפות":
                result = TenantType.Roommates;
                break;
            case "משפחה":
                result = TenantType.Family;
                break;
        }

        return result;
    }
}
