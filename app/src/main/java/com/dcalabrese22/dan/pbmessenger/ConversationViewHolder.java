package com.dcalabrese22.dan.pbmessenger;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dcalabrese22.dan.pbmessenger.interfaces.ConversationClickListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by dan on 9/13/17.
 */

public class ConversationViewHolder extends RecyclerView.ViewHolder {


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
                RelativeLayout container = (RelativeLayout) v.findViewById(R.id.image_container);
                Object o = container.getTag();
                final boolean isChecked = o.equals("checked");
                if (isChecked) {
                    container.setTag("unchecked");
                } else {
                    container.setTag("checked");
                }
                Log.d("ConversationViewHolder", container.getTag().toString());
                Log.d("isChecked", String.valueOf(isChecked));

                v.findViewById(R.id.conversation_top).setSelected(true);
                CircleImageView avatar = (CircleImageView) v.findViewById(R.id.user_avatar);

                final CircleImageView check = (CircleImageView) v.findViewById(R.id.user_message_checked);
                ObjectAnimator flipAvatarForwards = ObjectAnimator.ofFloat(avatar, "rotationY", 0f, 90f);
                final ObjectAnimator flipAvatarBack = ObjectAnimator.ofFloat(avatar, "rotationY", 90f, 0f);
                final ObjectAnimator flipCheck = ObjectAnimator.ofFloat(check, "rotationY", 90f, 180f);
                flipCheck.setDuration(150);
                flipAvatarBack.setDuration(150);

                flipAvatarForwards.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (!isChecked) {
                            Log.d("AndimaationEnd", String.valueOf(isChecked));

                            flipCheck.start();
                            check.setVisibility(View.VISIBLE);
                        } else {
                            Log.d("AndimaationEnd Else", String.valueOf(isChecked));
                            flipAvatarBack.start();
                            check.setVisibility(View.GONE);

                            mAvatar.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                flipAvatarForwards.setDuration(150);
                flipAvatarForwards.start();

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
