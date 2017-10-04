package com.dcalabrese22.dan.pbmessenger.Objects;

/**
 * Created by dan on 9/7/17.
 */

public class PbMessage {

    private String mMessageId;
    private String mBody;
    private String mDate;
    private String mSender;
    private Boolean mIsSentByMe;

    public PbMessage() {}

    public PbMessage(String id, String body, String date, String sender, Boolean isSentByMe) {
        mMessageId = id;
        mBody = body;
        mDate = date;
        mSender = sender;
        mIsSentByMe = isSentByMe;
    }

    public String getMessageId() {
        return mMessageId;
    }

    public String getBody() {
        return mBody;
    }

    public String getDate() {
        return mDate;
    }

    public String getSender() {
        return mSender;
    }

    public Boolean getIsSentByMe() { return  mIsSentByMe; }

    public void setIsSentByMe(Boolean isSentByMe) {
        mIsSentByMe = isSentByMe;
    }

    public void setMessageId(String messageId) {
        mMessageId = messageId;
    }

    public void setBody(String body) {
        mBody = body;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public void setSender(String sender) {
        mSender = sender;
    }

}

