package com.dcalabrese22.dan.pbmessenger.Objects;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by dan on 9/7/17.
 */

public class PbConversation {

    private String mId;
    private String mTitle;
    private String mUser;
    private String mLastMessage;

    public PbConversation() {}

    public PbConversation(String id, String title, String user, String lastMessage) {
        mTitle = title;
        mId = id;
        mUser = user;
        mLastMessage = lastMessage;

    }

    public PbConversation(String i, String t) {
        mTitle = t;
        mId = i;
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

    @Override
    public String toString() {
        return mTitle;
    }

}
