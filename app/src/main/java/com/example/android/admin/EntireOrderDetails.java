package com.example.android.admin;

public class EntireOrderDetails {

    private String name;
    private int village;
    private int gender;
    private String phone;
    private String mConsumerNo;
    private String mDate;
    private String mTime;
    private int mConfirmationStatus;
    private int mDeliveryStatus;
    private String mExpectedDeliveryDay;

    public EntireOrderDetails(String s1, int i, int j, String s2, String s3, String s4, String s5, int k, int l, String s6){
        this.name = s1;
        this.village = i;
        this.gender = j;
        this.phone = s2;
        this.mConsumerNo = s3;
        this.mDate = s4;
        this.mTime = s5;
        this.mConfirmationStatus = k;
        this.mDeliveryStatus = l;
        this.mExpectedDeliveryDay = s6;

    }


    public EntireOrderDetails(){

    }

    public String getName(){
        return name;
    }

    public int getVillage(){
        return village;
    }

    public int getGender(){
        return gender;
    }

    public String getmConsumerNo() {
        return mConsumerNo;
    }

    public String getmDate(){
        return mDate;
    }

    public String getmTime(){
        return mTime;
    }

    public int getmConfirmationStatus() {
        return mConfirmationStatus;
    }

    public int getmDeliveryStatus() {
        return mDeliveryStatus;
    }

    public String getPhone() {
        return phone;
    }

    public String getmExpectedDeliveryDay(){
        return mExpectedDeliveryDay;
    }
}
