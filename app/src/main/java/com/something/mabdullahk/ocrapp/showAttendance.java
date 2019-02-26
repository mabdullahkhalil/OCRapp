package com.something.mabdullahk.ocrapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class showAttendance extends AppCompatActivity {

    TabLayout tabLayout;
    TabLayout tabLayout1;
    List<userClass2> allUsersPresent;
    List<userClass2> allUsersAbsent;
    String currentUpperTabSelected;
    String currentLowerTabSelected;
    DatabaseReference databaseUsers;
    RecyclerView recyclerView;
    TextView count;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_attendance);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout1 = (TabLayout) findViewById(R.id.tablayoutPresent);
        databaseUsers = FirebaseDatabase.getInstance().getReference("users");
        allUsersPresent = new ArrayList<>();
        allUsersAbsent  = new ArrayList<>();
        currentUpperTabSelected = "Conference";
        currentLowerTabSelected = "Present";
        recyclerView = findViewById(R.id.recyclerView);
        count = (TextView) findViewById(R.id.count);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");

        GridLayoutManager paymentsGrid = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(paymentsGrid);

        addTabs();

        showAttendanceFunct();

        tabLayout.getTabAt(0).select();
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                allUsersPresent = new ArrayList<>();
                allUsersAbsent  = new ArrayList<>();
                currentUpperTabSelected = (String) tab.getText();
                System.out.println(currentUpperTabSelected+" is upper tab");
                showAttendanceFunct();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        tabLayout1.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                allUsersPresent = new ArrayList<>();
                allUsersAbsent  = new ArrayList<>();
                currentLowerTabSelected = (String) tab.getText();
                System.out.println(currentLowerTabSelected+" is lower tab");
                showAttendanceFunct();


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        tabLayout.getTabAt(0).select();
        tabLayout1.getTabAt(0).select();
    }

    private void addTabs(){

        tabLayout.addTab(tabLayout.newTab().setText("Conference"));
        tabLayout.addTab(tabLayout.newTab().setText("Google Design"));
        tabLayout.addTab(tabLayout.newTab().setText("Immersive User Research"));
        tabLayout.addTab(tabLayout.newTab().setText("Gamification"));
        tabLayout.addTab(tabLayout.newTab().setText("The Glassworker"));
        tabLayout.addTab(tabLayout.newTab().setText("Play and Clay"));
        tabLayout.addTab(tabLayout.newTab().setText("Storytelling"));
        tabLayout.addTab(tabLayout.newTab().setText("Service Design"));
        tabLayout.addTab(tabLayout.newTab().setText("Making Concept Proofs"));
        tabLayout1.addTab(tabLayout1.newTab().setText("present"));
        tabLayout1.addTab(tabLayout1.newTab().setText("absent"));
        tabLayout1.setTabTextColors(Color.parseColor("#ffffff"),Color.parseColor("#33e66c"));

    }

    private void showAttendanceFunct(){
        dialog.show();
        System.out.println("check1"+currentUpperTabSelected);
        System.out.println("check2"+currentLowerTabSelected);
        databaseUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    System.out.println(noteDataSnapshot);
                    user currentDBUser = noteDataSnapshot.getValue(user.class);

                        System.out.println("markakmrkmarkmk,,,,,"+currentDBUser.getConferenceAttendace());


                        if (currentUpperTabSelected == "Conference"){
                            String name = currentDBUser.getFirst_name()+" "+currentDBUser.getLast_name();
                            String cnic = currentDBUser.getCnic();
                            String time = currentDBUser.getConferenceAttendace().get(currentDBUser.getConferenceAttendace().size() - 1);
                            if (currentDBUser.getConferenceAttendace().size() == 1) {
                                time = "absent";
                                allUsersAbsent.add(new userClass2(name,cnic,time));
                            } else {
                                allUsersPresent.add(new userClass2(name,cnic,time));

                            }
                        } else {

                            List<workshop> database = currentDBUser.getqr_code();
                            System.out.println("database:"+database);
                            if (database != null){
                                for (workshop w : database){

                                    if (w.getId().toLowerCase().contains(currentUpperTabSelected.toLowerCase())){
                                        String name = currentDBUser.getFirst_name()+" "+currentDBUser.getLast_name();
                                        String cnic = currentDBUser.getCnic();
                                        String time = w.getpresent();

                                        if (w.getpresent().toLowerCase().equals("absent") && currentLowerTabSelected.toLowerCase().equals("absent")){

                                            allUsersAbsent.add(new userClass2(name,cnic,time));
                                        } else if (!w.getpresent().toLowerCase().equals("absent")){
                                            allUsersPresent.add(new userClass2(name,cnic,time));
                                        }

                                    }


                                }
                            }


                        }

                        System.out.println(currentDBUser+" is the currentDBUserr");




                }

                if (currentLowerTabSelected.toLowerCase() == "absent"){
                    attendanceCardAdapter myAdapter = new attendanceCardAdapter(showAttendance.this, allUsersAbsent,currentLowerTabSelected.toLowerCase());
                    recyclerView.setAdapter(myAdapter);
                    count.setText(Integer.toString(allUsersAbsent.size()));
                    dialog.dismiss();
                } else {
                    attendanceCardAdapter myAdapter = new attendanceCardAdapter(showAttendance.this, allUsersPresent,currentLowerTabSelected.toLowerCase());
                    recyclerView.setAdapter(myAdapter);
                    count.setText(Integer.toString(allUsersPresent.size()));
                    dialog.dismiss();
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



}
