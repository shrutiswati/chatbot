package com.shrutiswati.banasthalibot.models;

/**
 * Created by Rohit Gupta on 19/1/18.
 */

public class ChatMessage {

    private String message;
    private long timestamp;
    private String sentBy;

    public ChatMessage(String message, long timestamp, String sentBy) {
        this.message = message;
        this.timestamp = timestamp;
        this.sentBy = sentBy;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSentBy() {
        return sentBy;
    }

    public void setSentBy(String sentBy) {
        this.sentBy = sentBy;
    }
}
