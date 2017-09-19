package com.dcalabrese22.dan.pbmessenger;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by dan on 9/14/17.
 */

public class ChatViewHolder extends RecyclerView.ViewHolder {

    private TextView mChatBody;
    private TextView mChatSender;
    private TextView mTimeSent;

    public ChatViewHolder(View view) {
        super(view);
        mChatBody = (TextView) view.findViewById(R.id.tv_chat_body);
        mChatSender = (TextView) view.findViewById(R.id.tv_sender);
        mTimeSent = (TextView) view.findViewById(R.id.tv_time_sent);

    }

    public void setChatBody(String body) {
        mChatBody.setText(body);
    }

    public void setChatSender(String sender) {
        mChatSender.setText(sender);
    }

    public void setTimeSent(String timeSent) {
        mTimeSent.setText(timeSent);
    }
}
