package com.something.mabdullahk.ocrapp;

/**
 * Created by mabdullahk on 31/01/2019.
 */

public class workshop {
    String name;
    String id;

    public workshop(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
