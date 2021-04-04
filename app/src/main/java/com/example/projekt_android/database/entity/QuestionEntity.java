package com.example.projekt_android.database.entity;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@Entity(tableName = "question")
public class QuestionEntity {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    @ColumnInfo(name = "sender_Email")
    @Nullable
    private String senderEmail;

    @ColumnInfo(name = "name_And_Surname")
    @Nullable
    private String nameAndSurname;

    @ColumnInfo(name = "text_Msg")
    @Expose
    private String textMessage;

    @ColumnInfo(name = "subject")
    private String topic;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
