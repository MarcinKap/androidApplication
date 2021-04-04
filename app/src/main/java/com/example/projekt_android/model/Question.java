package com.example.projekt_android.model;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Question {
    public Question(@Nullable String nameAndSurname, @Nullable String senderEmail, String topic , String textMessage) {
        this.senderEmail = senderEmail;
        this.nameAndSurname = nameAndSurname;
        this.textMessage = textMessage;
        this.topic = topic;
    }

    @SerializedName("id")
    @Expose
    private Long externalId;

    @SerializedName("senderEmail")
    @Expose
    @Nullable
    private String senderEmail;

    @SerializedName("nameAndSurname")
    @Expose
    @Nullable
    private String nameAndSurname;

    @SerializedName("textMsg")
    @Expose
    private String textMessage;

    @SerializedName("subject")
    @Expose
    private String topic;

    public Long getExternalId() {
        return externalId;
    }

    public void setExternalId(Long externalId) {
        this.externalId = externalId;
    }

    @Nullable
    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(@Nullable String senderEmail) {
        this.senderEmail = senderEmail;
    }

    @Nullable
    public String getNameAndSurname() {
        return nameAndSurname;
    }

    public void setNameAndSurname(@Nullable String nameAndSurname) {
        this.nameAndSurname = nameAndSurname;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
