package com.finalprojectgroupae.immunization.data.model;

import androidx.annotation.ColorRes;

public class Reminder {
    private final String channel;
    private final String recipient;
    private final String message;
    private final String status;
    @ColorRes
    private final int channelColorRes;

    public Reminder(String channel,
                    String recipient,
                    String message,
                    String status,
                    @ColorRes int channelColorRes) {
        this.channel = channel;
        this.recipient = recipient;
        this.message = message;
        this.status = status;
        this.channelColorRes = channelColorRes;
    }

    public String getChannel() {
        return channel;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    @ColorRes
    public int getChannelColorRes() {
        return channelColorRes;
    }
}

