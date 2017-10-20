package com.dcalabrese22.dan.pbmessenger.Objects;

/**
 * Created by dan on 9/7/17.
 */

public class PbMessage {

    private String messageId;
    private String body;
    private String date;
    private String sender;
    private String type;
    private Long timeStamp;

    public PbMessage() {}

    public PbMessage(String id, String body, String date, String sender, String type, Long timeStamp) {
        messageId = id;
        this.body = body;
        this.date = date;
        this.sender = sender;
        this.type = type;
        this.timeStamp = timeStamp;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getBody() {
        return body;
    }

    public String getDate() {
        return date;
    }

    public String getSender() {
        return sender;
    }

    public String getType() {
        return type;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return body;
    }
}

