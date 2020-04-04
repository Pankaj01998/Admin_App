package com.example.android.admin;

public class Order_Details {

    private String mConsumerNo;
    private String mDate;
    private String mTime;
    private int mConfirmationStatus;
    private int mDeliveryStatus;

    public Order_Details() {
    }

    public Order_Details(String s1, String s2, String s3, int i, int j){

        this.mConsumerNo = s1;
        this.mDate = s2;
        this.mTime = s3;
        this.mConfirmationStatus = i;
        this.mDeliveryStatus = j;
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
}
