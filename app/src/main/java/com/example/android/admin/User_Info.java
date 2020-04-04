package com.example.android.admin;

public class User_Info {
    private String name;
    private int village;
    private int gender;

    public User_Info(){
    }

    public User_Info(String s1, int i, int j){
        this.name = s1;
        this.village = i;
        this.gender = j;
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


}
