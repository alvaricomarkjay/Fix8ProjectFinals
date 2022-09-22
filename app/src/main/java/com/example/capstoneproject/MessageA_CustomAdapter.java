package com.example.capstoneproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class MessageA_CustomAdapter extends BaseAdapter {

    View v;

    String dCusername, dCFullname, dCfullname, dCPhoto;

    private Context mContext;
    private List<MessageA_Item> mMessageAList;
    private Activity parentActivity;

    public MessageA_CustomAdapter(Context mContext, List<MessageA_Item> mMessageAList, Activity parentActivity) {
        this.mContext = mContext;
        this.mMessageAList = mMessageAList;
        this.parentActivity = parentActivity;
    }


    @Override
    public int getCount() {
        return mMessageAList.size();
    }

    @Override
    public Object getItem(int position) {
        return mMessageAList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        v = View.inflate(mContext, R.layout.h_message_content, null);


        LinearLayout messageOpenerLL = (LinearLayout) v.findViewById(R.id.messageLL);
        final ImageView userImage = (ImageView) v.findViewById(R.id.messageProfilepic);
        final TextView fullName = (TextView) v.findViewById(R.id.nameMessage);
        TextView cMessage = (TextView) v.findViewById(R.id.detailMessage);
        TextView cTime = (TextView) v.findViewById(R.id.messageTime);


        cMessage.setText(mMessageAList.get(position).getcMessage());
        cTime.setText(mMessageAList.get(position).getTime());

        final String CUsername = mMessageAList.get(position).getUsername();
        final String CMyUsername = mMessageAList.get(position).getUname();

        DatabaseReference chatListRoot = FirebaseDatabase.getInstance().getReference("users");
        chatListRoot.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 : snapshot.getChildren()){

                    dCusername = snapshot1.child("username").getValue().toString();
                    dCFullname = snapshot1.child("fullname").getValue().toString();
                    dCPhoto = snapshot1.child("photo").getValue().toString();



                    if (CUsername.equals(dCusername)){
                        dCFullname = dCfullname;
                        fullName.setText(dCFullname+ mMessageAList.get(position).getNullName());
                        Glide.with(mContext).load(dCPhoto).into(userImage);
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        messageOpenerLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentMsg = new Intent(mContext, MessageConvoActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);;
                intentMsg.putExtra("uname", CMyUsername);
                intentMsg.putExtra("visituname", CUsername);
                v.getContext().startActivity(intentMsg);
            }
        });
        return v;
    }


}
