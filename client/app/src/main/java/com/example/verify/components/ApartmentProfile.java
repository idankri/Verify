package com.example.verify.components;

public class ApartmentProfile {
    private String mCity;
    private String mStreet;
    private String mBuilding;
    private String mFloor;
    private String mApartment;

    public ApartmentProfile(String city, String street, String building, String floor, String apartment){
        mCity = city;
        mStreet = street;
        mBuilding = building;
        mFloor = floor;
        mApartment = apartment;
    }

    public String getCity(){
        return mCity;
    }

    public String getStreet(){
        return mStreet;
    }

    public String getBuilding(){
        return mBuilding;
    }

    public String getFloor(){
        return mFloor;
    }

    public String getApartment(){
        return mApartment;
    }

}
