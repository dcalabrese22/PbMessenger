package com.dcalabrese22.dan.pbmessenger.Objects;

/**
 * Created by dan on 9/7/17.
 */

public class PbConversation {

    private String id;
    private String title;
    private String user;
    private String userImage;
    private String lastMessage;
    private String lastMessageType;
    private Long timeStamp;

    public PbConversation() {}

    public PbConversation(String id, String title, String user, String lastMessage, String userImage,
                          String lastMessageType, Long timeStamp) {
        this.title = title;
        this.id = id;
        this.user = user;
        this.lastMessage = lastMessage;
        this.userImage = userImage;
        this.lastMessageType = lastMessageType;
        this.timeStamp = timeStamp;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public String getLastMessageType() {
        return lastMessageType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public void setLastMessageType(String lastMessageType) {
        this.lastMessageType = lastMessageType;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return title;
    }

    @Override
    public boolean equals(Object obj) {
        PbConversation c = (PbConversation) obj;
        if (c.getId().equals(id)) {
            return true;
        } else {
            return false;
        }
    }
}

