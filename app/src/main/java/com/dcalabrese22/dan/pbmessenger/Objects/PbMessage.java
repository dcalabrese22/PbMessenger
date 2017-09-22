package com.dcalabrese22.dan.pbmessenger.Objects;

/**
 * Created by dan on 9/7/17.
 */

public class PbMessage {

    private String messageId;
    private String body;
    private String date;
    private String sender;

    public PbMessage() {}

    public PbMessage(String id, String body, String date, String sender) {
        messageId = id;
        this.body = body;
        this.date = date;
        this.sender = sender;
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
}

