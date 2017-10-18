package com.dcalabrese22.dan.pbmessenger.Objects;

import android.view.View;

import com.dcalabrese22.dan.pbmessenger.ConversationViewHolder;

/**
 * Created by dcalabrese on 10/9/2017.
 */

public class SelectedConversation {

    private View mView;
    private ConversationViewHolder mViewHolder;
    private int mPosition;
    private PbConversation mConversation;

    public SelectedConversation(View view, ConversationViewHolder viewHolder,
                                int position, PbConversation conversation) {
        mView = view;
        mViewHolder = viewHolder;
        mPosition = position;
        mConversation = conversation;
    }

    public View getSelectedView() {
        return mView;
    }

    public ConversationViewHolder getViewHolder() {
        return mViewHolder;
    }

    public int getPosition() {
        return mPosition;
    }

    public PbConversation getConversation() {
        return mConversation;
    }

    @Override
    public String toString() {
        return mConversation.toString();
    }
}
