package com.something.mabdullahk.ocrapp;

/**
 * Created by mabdullahk on 31/01/2019.
 */

public class workshop {
    Boolean present;
    String id;

    public workshop(Boolean present, String id) {
        this.present = present;
        this.id = id;
    }

    public workshop() {
    }

    public Boolean getpresent() {
        return present;
    }

    public void setpresent(Boolean present) {
        this.present = present;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
