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

    public PbConversation() {}

    public PbConversation(String id, String title, String user, String lastMessage, String userImage) {
        this.title = title;
        this.id = id;
        this.user = user;
        this.lastMessage = lastMessage;
        this.userImage = userImage;

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

    @Override
    public String toString() {
        return title;
    }

}

