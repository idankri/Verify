package com.example.verify.components;

import android.content.Context;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Dictionary;
import java.util.List;

public class ApartmentReview {
    public TenantType TenantType;
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
        TenantType = tenantType;
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

    public static ApartmentReview fromJson(){
        // TODO: parse JSON
        return null;
    }
}
