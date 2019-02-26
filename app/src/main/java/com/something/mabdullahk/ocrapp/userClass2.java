package com.something.mabdullahk.ocrapp;

public class userClass2 {
    String name;
    String cnic;
    String time;

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public userClass2(String name, String cnic, String time) {
        this.name = name;
        this.cnic = cnic;
        this.time = time;
    }
}
