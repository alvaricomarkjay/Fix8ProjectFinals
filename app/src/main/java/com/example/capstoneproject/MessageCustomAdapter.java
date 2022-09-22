package com.example.capstoneproject;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MessageCustomAdapter {

    String status;
    View v;
    String stat;
    String un2;


    private Context mContext;
    private List<MessageItems> mMessageList;
    private Activity parentActivity;

    public MessageCustomAdapter(Context mContext, List<MessageItems> mMessageList, Activity parentActivity) {
        this.mContext = mContext;
        this.mMessageList = mMessageList;
        this.parentActivity = parentActivity;
    }

    public int getCount() {
        return mMessageList.size();
    }

    public Object getItem(int position) {
        return mMessageList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        stat = mMessageList.get(position).getDstatus();
        un2 = mMessageList.get(position).getDuser2();

        if (stat.equals("c")) {


            v = View.inflate(mContext, R.layout.message_convo_content, null);


            final TextView message = (TextView) v.findViewById(R.id.chatMessage);
            final TextView time = (TextView) v.findViewById(R.id.chattime);
            ImageView photo = (ImageView) v.findViewById(R.id.chatImg);


            String cPhoto = mMessageList.get(position).getPhoto();

            message.setText(mMessageList.get(position).getDmessage());
            time.setText(mMessageList.get(position).getDdate_time());
            Glide.with(this.mContext).load(cPhoto).into(photo);

        }






        else if(stat.equals("cr")){
            v = View.inflate(mContext, R.layout.message_convo_contentreply, null);


            final TextView message = (TextView) v.findViewById(R.id.chatMessage);
            final TextView time = (TextView) v.findViewById(R.id.chattime);


            message.setText(mMessageList.get(position).getDmessage());
            time.setText(mMessageList.get(position).getDdate_time());

        }


        v.setTag(mMessageList.get(position).getChat_id());
        return v;
    }
}
