package com.something.mabdullahk.ocrapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
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
    Button markConferenceAtt;
    JSONObject jsonObject;
    ProgressDialog dialog;
    String markAtt;


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
        spinner = findViewById(R.id.spinner);
        name = findViewById(R.id.name);
        cnic = findViewById(R.id.cnic);
        mobile = findViewById(R.id.mobile);
        workshopView = findViewById(R.id.workshops);
        detailWorkshop = new ArrayList<workshop>();
        markAttendance = findViewById(R.id.buttonMarkattendance);
        markConferenceAtt = findViewById(R.id.buttonMarkattendanceConference);

        jsonObject  = new JSONObject();
        dialog = new ProgressDialog(this);



        markAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                markAtt = "workshop";
                attendanceMarkFn();
            }
        });

        markConferenceAtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                markAtt = "conference";
                attendanceMarkFn();
            }
        });
        dialog.setMessage("Please Wait...");
        dialog.show();

        onStartAct();


    }


    private void attendanceMarkFn(){

        String Workshop = (String) spinner.getSelectedItem();
        System.out.println(jsonObject+" from the jsooonnn");

        System.out.println(Workshop);

        databaseUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    System.out.println(noteDataSnapshot);
                    user Inspector = noteDataSnapshot.getValue(user.class);

                    if (Inspector.getCnic().equals(cnic.getText().toString())) {
                        System.out.println("markakmrkmarkmk,,,,,"+markAtt);
                        System.out.println("markakmrkmarkmk,,,,,"+Inspector.getConferenceAttendace());


                        if (markAtt == "conference"){
                            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                            Date date = new Date();
                            databaseUsers.child(noteDataSnapshot.getKey()).child("conferenceAttendace").child(Integer.toString(Inspector.getConferenceAttendace().size())).setValue(dateFormat.format(date));
                            Toast.makeText(markAttendance.this,"The attendance for conference is marked.",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(markAttendance.this,MainActivity.class));
                            return;
                        }

                        System.out.println(Inspector+" is the inspectorr");


                        List<workshop> database = Inspector.getqr_code();
                        System.out.println("database:"+database);
                        int i=0;
                        for (workshop w : database){

                            if (w.getId().equals(spinner.getSelectedItem()) && markAtt == "workshop"){
                                System.out.println("markakmrkmarkmk,,,,,"+markAtt);


                                if (w.getpresent().equals("absent") ){
                                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                                    Date date = new Date();
                                    databaseUsers.child(noteDataSnapshot.getKey()).child("qr_code").child(Integer.toString(i)).child("present").setValue(dateFormat.format(date));
                                    Toast.makeText(markAttendance.this,"The attendance has been marked. ",Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(markAttendance.this,"The attendance is already marked. ",Toast.LENGTH_LONG).show();

                                }

                                startActivity(new Intent(markAttendance.this,MainActivity.class));
                                return;

                            } else {
                                Toast.makeText(markAttendance.this,"This workshop selected is not right. try choosing another workshop.",Toast.LENGTH_LONG).show();
                            }
                            i++;
                        }

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private  void showData() throws JSONException {
        List<String> datalist = Arrays.asList(main.split(","));
        name.setText(datalist.get(0)+" "+datalist.get(1));
        cnic.setText(datalist.get(2));
        mobile.setText(datalist.get(3));

        String workshopNames = "";
        for (int i=5;i<datalist.size();i++){
            workshopNames+=jsonObject.get(datalist.get(i))+"\n";
        }



        System.out.println(workshopNames+" is he workshop names");

        workshopView.setText(workshopNames);

        dialog.dismiss();

    }
    private void populateWorkshops(DataSnapshot dataSnapshot) throws JSONException {


        for(DataSnapshot data : dataSnapshot.getChildren()){

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
