package com.dcalabrese22.dan.pbmessenger;

import android.content.Context;

import com.dcalabrese22.dan.pbmessenger.Objects.PbConversation;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

/**
 * Created by dcalabrese on 10/5/2017.
 */

public class MessageListAdapter extends FirebaseRecyclerAdapter<PbConversation, ConversationViewHolder> {

    private Class<PbConversation> mPbConversationClass;
    private int mLayout;
    private Class<ConversationViewHolder> mConversationViewHolderCLass;
    private Query mQuery;
    private Context mContext;
    private int mSelectedPosition;

    public MessageListAdapter(Class<PbConversation> pbConversationClass, int layout,
                              Class<ConversationViewHolder> conversationViewHolderClass,
                              Query query, Context context) {
        super(pbConversationClass, layout, conversationViewHolderClass, query);
        mPbConversationClass = pbConversationClass;
        mLayout = layout;
        mConversationViewHolderCLass = conversationViewHolderClass;
        mQuery = query;
        mContext = context;
    }


    @Override
    protected void populateViewHolder(ConversationViewHolder viewHolder, PbConversation model, int position) {
        viewHolder.setSubject(model.getTitle());
        viewHolder.setUser(model.getUser());
        viewHolder.setLastMessage(model.getLastMessage());
        viewHolder.setAvatar(model.getUserImage(), mContext);
    }

}
