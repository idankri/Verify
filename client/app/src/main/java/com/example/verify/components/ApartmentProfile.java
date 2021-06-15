package com.example.verify.components;

public class ApartmentProfile {
    private String mCity;
    private String mStreet;
    private int mBuilding;
    private int mFloor;
    private int mApartment;

    public ApartmentProfile(String city, String street, int building, int floor, int apartment){
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

    public int getBuilding(){
        return mBuilding;
    }

    public int getFloor(){
        return mFloor;
    }

    public int getApartment(){
        return mApartment;
    }

}
