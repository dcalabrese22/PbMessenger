package com.dcalabrese22.dan.pbmessenger;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.dcalabrese22.dan.pbmessenger.interfaces.ConversationClickListener;

import junit.framework.Test;

/**
 * Created by dan on 9/13/17.
 */

public class ConversationViewHolder extends RecyclerView.ViewHolder {


    TextView mSubject;
    TextView mUser;
    TextView mLastMessage;

    ConversationClickListener mClickListener;

    public void setOnClickListener(ConversationClickListener listener) {
        mClickListener = listener;
    }

    public ConversationViewHolder(View view) {
        super(view);
        mSubject = (TextView) view.findViewById(R.id.tv_conversation_subject);
        mUser = (TextView) view.findViewById(R.id.tv_conversation_user);
        mLastMessage = (TextView) view.findViewById(R.id.tv_conversation_last_message);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onConversationClick(v, getAdapterPosition());
            }
        });

    }

    public void setSubject(String subject) {
        mSubject.setText(subject);
    }

    public void setUser(String user) {
        mUser.setText(user);
    }

    public void setLastMessage(String message) {
        mLastMessage.setText(message);
    }
}
