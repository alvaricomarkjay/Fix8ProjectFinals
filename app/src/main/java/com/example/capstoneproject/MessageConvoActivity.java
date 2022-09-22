package com.example.capstoneproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MessageConvoActivity extends AppCompatActivity {

    String dchat_id, ddate_date, ddate_day, ddate_time, dmessage, dstatus,  duser1,  duser2;

    ListView chatLV;
    EditText chatET;
    ImageButton sendButton;
    TextView chatmsg, chattime, chatFullName;
    ImageView chatImage, chatBackArrow;

    String thisUsername;
    String fullname, fullName, myFullName;
    String chatId;

    String uname, cphoto, cphotoNew;

    private MessageCustomAdapter adapter;
    private List<MessageItems> mMessageList;

    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference chatRoot = db.getReference("chat");

    private DatabaseReference chatListRoot = db.getReference("chatList");

    DateFormat dfDay = new SimpleDateFormat("EEE");
    DateFormat dfTime = new SimpleDateFormat("h:mm a");
    DateFormat dfDate = new SimpleDateFormat("MMM d yyyy");

    String postTime;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_message_convo);


        chatLV = (ListView) findViewById(R.id.convoListView);
        chatET = (EditText) findViewById(R.id.messageEditor);
        sendButton = (ImageButton) findViewById(R.id.sendButton);
        chatFullName = (TextView) findViewById(R.id.chatFullName);
        chatBackArrow = (ImageView) findViewById(R.id.chatBackArrow);

        chatImage = (ImageView) findViewById(R.id.chatImg);
        chatmsg = (TextView) findViewById(R.id.chatMessage);
        chattime = (TextView) findViewById(R.id.chattime);

        mMessageList = new ArrayList<>();

        adapter = new MessageCustomAdapter(getApplicationContext(), mMessageList, MessageConvoActivity.this);
        chatLV.setAdapter(adapter);


        Intent intentt = getIntent();
        uname = intentt.getStringExtra("uname");
        final String visituname = intentt.getStringExtra("visituname");

        final DatabaseReference mRootRef_users = FirebaseDatabase.getInstance().getReference("users");
        mRootRef_users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    thisUsername = snapshot1.child("username").getValue().toString();
                    fullName = snapshot1.child("fullname").getValue().toString();
                    cphoto = snapshot1.child("photo").getValue().toString();


                    if (visituname.equals(thisUsername)){
                        fullName = fullname;
                        cphotoNew = cphoto;

                        chatFullName.setText(fullName);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference("chat");
        mRootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1  : snapshot.getChildren()) {

                    dchat_id = snapshot1.child("chat_id").getValue().toString();
                    ddate_date  = snapshot1.child("date_date").getValue().toString();
                    ddate_day  = snapshot1.child("date_day").getValue().toString();
                    ddate_time  = snapshot1.child("date_time").getValue().toString();
                    dmessage = snapshot1.child("message").getValue().toString();
                    dstatus = snapshot1.child("status").getValue().toString();
                    duser1 = snapshot1.child("user1").getValue().toString();
                    duser2 = snapshot1.child("user2").getValue().toString();



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






                    if (visituname.equals(duser2) && (uname.equals(duser1))) {
                        dstatus = "cr";
                        mMessageList.add(new MessageItems(fullName, dchat_id, cphotoNew, postTime, dmessage, dstatus, duser1, duser2));
                        adapter.notifyDataSetChanged();
                    }
                    if (visituname.equals(duser1) && (uname.equals(duser2))) {
                        dstatus = "c";
                        mMessageList.add(new MessageItems(fullName, dchat_id, cphotoNew, postTime, dmessage, dstatus, duser1, duser2));
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
