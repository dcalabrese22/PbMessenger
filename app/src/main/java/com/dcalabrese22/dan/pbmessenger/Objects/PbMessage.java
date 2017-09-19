package com.dcalabrese22.dan.pbmessenger.Objects;

/**
 * Created by dan on 9/7/17.
 */

public class PbMessage {

    private String body;
    private String date;
    private String sender;

    public PbMessage() {}

    public PbMessage(String b, String d, String s) {
        body = b;
        date = d;
        sender = s;
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

