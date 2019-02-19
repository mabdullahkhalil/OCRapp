package com.something.mabdullahk.ocrapp;

import java.util.List;

/**
 * Created by mabdullahk on 31/01/2019.
 */

public class user {

    String first_name;
    String last_name;
    String phone;
    String pass_type;
    String cnic;
    List<workshop> qr_code;
    List<String> conferenceAttendance;

    public user() {
    }

    public user(String first_name, String last_name, String phone, String pass_type, String cnic, List<workshop> qr_code, List<String> conferenceAttendance) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone = phone;
        this.pass_type = pass_type;
        this.cnic = cnic;
        this.qr_code = qr_code;
        this.conferenceAttendance = conferenceAttendance;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPass_type() {
        return pass_type;
    }

    public void setPass_type(String pass_type) {
        this.pass_type = pass_type;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public List<workshop> getqr_code() {
        return qr_code;
    }

    public void setqr_code(List<workshop> qr_code) {
        this.qr_code = qr_code;
    }

    public List<String> getConferenceAttendance() {
        return conferenceAttendance;
    }

    public void setConferenceAttendance(List<String> conferenceAttendance) {
        this.conferenceAttendance = conferenceAttendance;
    }
}
