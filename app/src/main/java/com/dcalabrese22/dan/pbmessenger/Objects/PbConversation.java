package com.dcalabrese22.dan.pbmessenger.Objects;

/**
 * Created by dan on 9/7/17.
 */

public class PbConversation {

    private String mId;
    private String mTitle;
    private String mUser;
    private String mUserImage;
    private String mLastMessage;

    public PbConversation() {}

    public PbConversation(String id, String title, String user, String lastMessage, String userImage) {
        mTitle = title;
        mId = id;
        mUser = user;
        mLastMessage = lastMessage;
        mUserImage = userImage;

    }

    public String getUserImage() {
        return mUserImage;
    }

    public String getLastMessage() {
        return mLastMessage;
    }

    public String getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getUser() {
        return mUser;
    }

    public void setId(String id) {
        mId = id;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setUser(String user) {
        mUser = user;
    }

    public void setLastMessage(String message) {
        mLastMessage = message;
    }

    public void setUserImage(String url) {
        mUserImage = url;
    }

    @Override
    public String toString() {
        return mTitle;
    }

}

