package com.something.mabdullahk.ocrapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class markAttendance extends AppCompatActivity {
    String main;
    List<String> workshops;
    List<workshop> detailWorkshop;
    DatabaseReference databaseWorshops;
    DatabaseReference databaseUsers;
    Spinner spinner;
    TextView name;
    TextView cnic;
    TextView mobile;
    TextView workshopView;
    Button markAttendance;
    JSONObject jsonObject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_attendance);

        Intent intent = getIntent();
        main = intent.getStringExtra("stringcame");
        FirebaseApp.initializeApp(this);
        databaseWorshops = FirebaseDatabase.getInstance().getReference("workshops");
        databaseUsers = FirebaseDatabase.getInstance().getReference("users");
        workshops = new ArrayList<String>();
        spinner = (Spinner) findViewById(R.id.spinner);
        name = (TextView) findViewById(R.id.name);
        cnic = (TextView) findViewById(R.id.cnic);
        mobile = (TextView) findViewById(R.id.mobile);
        workshopView = (TextView) findViewById(R.id.workshops);
        detailWorkshop = new ArrayList<workshop>();
        markAttendance = (Button) findViewById(R.id.buttonMarkattendance);
        jsonObject  = new JSONObject();


        markAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attendanceMarkFn();
            }
        });

        onStartAct();


    }


    private void attendanceMarkFn(){

        String Workshop = (String) spinner.getSelectedItem();
        System.out.println(jsonObject+" from the jsooonnn");

        System.out.println(Workshop);

    }

    private  void showData() throws JSONException {
        List<String> datalist = Arrays.asList(main.split(","));
        name.setText(datalist.get(0)+" "+datalist.get(1));
        cnic.setText(datalist.get(2));
        mobile.setText(datalist.get(3));

        String workshopNames = "";
        for (int i=4;i<datalist.size();i++){
            workshopNames+=jsonObject.get(datalist.get(i))+"\n";
        }



        System.out.println(workshopNames+" is he workshop names");

        workshopView.setText(workshopNames);
//        System.out.println(jsonObject.get(datalist.get(4)));



    }
    private void populateWorkshops(DataSnapshot dataSnapshot) throws JSONException {

        for(DataSnapshot data : dataSnapshot.getChildren()){

            workshop workshop1= new workshop(data.getKey().toString(),data.getValue().toString());
            detailWorkshop.add(workshop1);
            workshops.add(data.getValue().toString());
            jsonObject.put(data.getKey().toString(),data.getValue().toString());

        }

        System.out.println("the worksops are:"+workshops);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, workshops);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        System.out.println(workshops);

        try {
            showData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void onStartAct(){

        databaseWorshops.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    populateWorkshops(dataSnapshot);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
