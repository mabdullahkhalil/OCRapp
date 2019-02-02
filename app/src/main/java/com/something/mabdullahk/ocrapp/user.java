package com.something.mabdullahk.ocrapp;

/**
 * Created by mabdullahk on 31/01/2019.
 */

public class user {
    String name;
    String cnic;
    String mobile;
    int[] workshops;


    public user(String name, String cnic, String mobile, int[] workshops) {
        this.name = name;
        this.cnic = cnic;
        this.mobile = mobile;
        this.workshops = workshops;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int[] getWorkshops() {
        return workshops;
    }

    public void setWorkshops(int[] workshops) {
        this.workshops = workshops;
    }
}
