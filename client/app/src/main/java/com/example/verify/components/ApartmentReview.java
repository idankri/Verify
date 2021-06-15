package com.example.verify.components;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Dictionary;
import java.util.List;

public class ApartmentReview {

    // TODO: change fields to private and create getters
    public TenantType tenantType;
    public String StayingPeriod;
    public String EntryDate;
    public String LeaveDate;

    public double GeneralRating;
    public double ApartmentHolderRating;
    public double MaintenanceRating;
    public double AroundRating;

    public List<String> ApartmentHolderPros;
    public List<String> MaintenancePros;
    public List<String> AroundPros;

    public List<String> ApartmentHolderCons;
    public List<String> MaintenanceCons;
    public List<String> AroundCons;

    public Dictionary<TextReviewCategory, String> TextualReviews;

    public ApartmentReview(){

    }

    public ApartmentReview(
            TenantType tenantType,
            String stayingPeriod,
            String entryDate,
            String leaveDate,
            double generalRating,
            double apartmentHolderRating,
            double maintenanceRating,
            double aroundRating,
            List<String> apartmentHolderPros,
            List<String> maintenancePros,
            List<String> aroundPros,
            List<String> apartmentHolderCons,
            List<String> maintenanceCons,
            List<String> aroundCons,
            Dictionary<TextReviewCategory, String> textualReviews
            ){
        this.tenantType = tenantType;
        StayingPeriod = stayingPeriod;
        EntryDate = entryDate;
        LeaveDate = leaveDate;
        GeneralRating = generalRating;
        ApartmentHolderRating = apartmentHolderRating;
        MaintenanceRating = maintenanceRating;
        AroundRating = aroundRating;
        ApartmentHolderPros = apartmentHolderPros;
        MaintenancePros = maintenancePros;
        AroundPros = aroundPros;
        ApartmentHolderCons = apartmentHolderCons;
        MaintenanceCons = maintenanceCons;
        AroundCons = aroundCons;
        TextualReviews = textualReviews;
    }

    public static ApartmentReview fromJson(JSONObject json) throws JSONException{

        boolean isStillLiving = json.getBoolean("Still living in the apartment");

        String city = json.getString("City");
        String addressAndNumber = json.getString("Address and number");
        int floor = json.getInt("Floor");
        int apartmentNumber = json.getInt("Apartment number");;
        String tenantsComposition;
        int landlordRating;
        JSONArray landlordTags;
        int maintenanceRating;
        int aroundRating;
        JSONObject aroundTags;
        JSONObject maintenanceTags;
        JSONArray picturesDetails;

        // TODO: complete all calculations and adaptations

        TenantType tenantType = enumerateTenantType(json.getString("Tenants composition"));
        String stayingPeriod = json.getString("Staying period");
        String enterDate = dateParser(json.getString("Enter date"));
        String exitDate = dateParser(json.getString("Exit date"));

        return new ApartmentReview();
    }

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
