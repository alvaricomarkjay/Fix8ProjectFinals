package com.example.capstoneproject;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MessageActivity extends AppCompatActivity {

    private MessageA_CustomAdapter adapter;
    private List<MessageA_Item> mMessageAList;

    ListView messageALV;

    String uname;

    String dCuser1, dCuser2, dCmessage, dchatId;
    String dUsername, dUserFname, dUserLname;

    String chatMate, trapChatMate;

    DateFormat dfDay = new SimpleDateFormat("EEE");
    DateFormat dfTime = new SimpleDateFormat("h:mm a");
    DateFormat dfDate = new SimpleDateFormat("MMM d yyyy");
    String postTime,  ddate_date, ddate_day, ddate_time;

    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference chatRoot = db.getReference("chat");
    DatabaseReference usersRoot = db.getReference("users");

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_message);

        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#FFFFFF"));
        actionBar.setBackgroundDrawable(colorDrawable);


        messageALV = (ListView) findViewById(R.id.messageListView);

        mMessageAList = new ArrayList<>();

        adapter = new MessageA_CustomAdapter(getApplicationContext(), mMessageAList, MessageActivity.this);
        messageALV.setAdapter(adapter);

        Intent intentt = getIntent();
        uname = intentt.getStringExtra("uname");

        DatabaseReference chatListRoot = FirebaseDatabase.getInstance().getReference("chatlist").child(uname);

        chatListRoot.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 : snapshot.getChildren()){

                    dUsername = snapshot1.child("chatmate").getValue().toString();
                    dCmessage = snapshot1.child("message").getValue().toString();
                    ddate_date  = snapshot1.child("date_date").getValue().toString();
                    ddate_day  = snapshot1.child("date_day").getValue().toString();
                    ddate_time  = snapshot1.child("date_time").getValue().toString();





                    String dateNow = dfDate.format(Calendar.getInstance().getTime());

                    Calendar yesterday = Calendar.getInstance();
                    yesterday.add(Calendar.DATE, -1);
                    String dateYesterday =  dfDate.format(yesterday.getTime());

                    Calendar twoDaysAgo = Calendar.getInstance();
                    twoDaysAgo.add(Calendar.DATE, -2);
                    String dateTwoDaysAgo =  dfDate.format(twoDaysAgo.getTime());

                    Calendar threeDaysAgo = Calendar.getInstance();
                    threeDaysAgo.add(Calendar.DATE, -3);
                    String dateThreeDaysAgo =  dfDate.format(threeDaysAgo.getTime());

                    Calendar fourDaysAgo = Calendar.getInstance();
                    fourDaysAgo.add(Calendar.DATE, -4);
                    String dateFourDaysAgo =  dfDate.format(fourDaysAgo.getTime());

                    Calendar fiveDaysAgo = Calendar.getInstance();
                    fiveDaysAgo.add(Calendar.DATE, -5);
                    String dateFiveDaysAgo =  dfDate.format(fiveDaysAgo.getTime());


                    if(dateNow.equals(ddate_date)){
                        postTime = "Today " + ddate_time;
                    }
                    else if (dateYesterday.equals(ddate_date)){
                        postTime = "Yesterday " + ddate_time;
                    }
                    else if (dateTwoDaysAgo.equals(ddate_date) || dateThreeDaysAgo.equals(ddate_date)
                            || dateFourDaysAgo.equals(ddate_date) || dateFiveDaysAgo.equals(ddate_date)){

                        postTime = ddate_day +" "+ ddate_time;
                    }
                    else {
                        postTime = ddate_date +" "+ddate_time;
                    }



                    mMessageAList.add(new MessageA_Item(" ", dUsername, dCmessage, postTime, " ", uname));
                    adapter.notifyDataSetChanged();



                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem);

    }

}
