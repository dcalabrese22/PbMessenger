package com.dcalabrese22.dan.pbmessenger;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
    LinearLayout mLinearLayout;

    public ConversationViewHolder(View view) {
        super(view);
        mAvatar = (ImageView) view.findViewById(R.id.user_avatar);
        mSubject = (TextView) view.findViewById(R.id.tv_conversation_subject);
        mUser = (TextView) view.findViewById(R.id.tv_conversation_user);
        mLastMessage = (TextView) view.findViewById(R.id.tv_conversation_last_message);
        mLinearLayout = (LinearLayout) view.findViewById(R.id.conversation_top);

//        view.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
////                flipAvatar(v);
//                return true;
//            }
//
//        });

    }

    public void flipAvatar(View v) {
        RelativeLayout container = (RelativeLayout) v.findViewById(R.id.image_container);
        Object o = container.getTag();
        boolean isActivated = !o.equals("checked");
        v.setActivated(isActivated);

        final boolean isChecked = !o.equals("checked");
        if (isChecked) {
            container.setTag("checked");
        } else {
            container.setTag("unchecked");
        }

        v.findViewById(R.id.conversation_top).setSelected(true);
        final CircleImageView avatar = (CircleImageView) v.findViewById(R.id.user_avatar);

        final CircleImageView check = (CircleImageView) v.findViewById(R.id.user_message_checked);
        ObjectAnimator flipAvatarForwards = ObjectAnimator.ofFloat(avatar, "rotationY", 0f, 90f);
        final ObjectAnimator flipAvatarBack = ObjectAnimator.ofFloat(avatar, "rotationY", 90f, 0f);
        final ObjectAnimator flipCheckForwards = ObjectAnimator.ofFloat(check, "rotationY", 90f, 180f);
        final ObjectAnimator flipCheckBackwards = ObjectAnimator.ofFloat(check, "rotationY", 180f, 90f);
        flipCheckForwards.setDuration(150);
        flipAvatarBack.setDuration(150);
        flipAvatarForwards.setDuration(150);
        flipCheckBackwards.setDuration(150);

        flipAvatarForwards.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                flipCheckForwards.start();
                check.setVisibility(View.VISIBLE);
                avatar.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        flipCheckBackwards.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                flipAvatarBack.start();
                check.setVisibility(View.GONE);
                avatar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        if (isChecked) {
            flipAvatarForwards.start();
        } else {
            flipCheckBackwards.start();
        }

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

        if (urlToImage != null || !urlToImage.equals("null")) {
            Picasso.with(context).load(urlToImage).into(mAvatar);
        }
    }
}
