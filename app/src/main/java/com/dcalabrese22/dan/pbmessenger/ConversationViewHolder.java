package com.dcalabrese22.dan.pbmessenger;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.dcalabrese22.dan.pbmessenger.interfaces.ConversationClickListener;
import com.squareup.picasso.Picasso;

/**
 * Created by dan on 9/13/17.
 */

public class ConversationViewHolder extends RecyclerView.ViewHolder{


    private TextView mSubject;
    private TextView mUser;
    private TextView mLastMessage;
    private ImageView mAvatar;

    private ConversationClickListener mClickListener;
    private FloatingActionButton mFab;
    private Context mContext;
    private int mSelectedPosition;

    public void setOnClickListener(ConversationClickListener listener) {
        mClickListener = listener;

    }

    public void setMembers(FloatingActionButton fab, Context context, int position) {
        mFab = fab;
        mContext = context;
        mSelectedPosition = position;
    }



    public ConversationViewHolder(View view) {
        super(view);
        mAvatar = (ImageView) view.findViewById(R.id.user_avatar);
        mSubject = (TextView) view.findViewById(R.id.tv_conversation_subject);
        mUser = (TextView) view.findViewById(R.id.tv_conversation_user);
        mLastMessage = (TextView) view.findViewById(R.id.tv_conversation_last_message);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onConversationClick(v, getAdapterPosition());
            }
        });

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
//                v.findViewById(R.id.conversation_top).setSelected(true);
                mFab.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.rotate));
                mFab.setImageResource(R.drawable.ic_delete_white_24px);
                return true;
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

    public void setAvatar(String urlToImage, Context context) {

        if (urlToImage != null || !urlToImage.equals("")) {
            Picasso.with(context).load(urlToImage).into(mAvatar);
        }

    }
}
