package com.anle.simplequotes.data.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Quote {
    @NonNull
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    private int id;

    @NonNull
    @ColumnInfo(name = "content")
    private String content;

    @Nullable
    @ColumnInfo(name = "author")
    private String author;

    @Nullable
    @ColumnInfo(name = "topic")
    private String topic;

    @Ignore
    public Quote() {
    }

    // @Ignore
    public Quote(@Nullable int id, @NonNull String content, @Nullable String author, @Nullable String topic) {
        this.id = id;
        this.content = content;
        this.author = author;
        this.topic = topic;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        content = content.replace(".",".\n");
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @NonNull
    @Override
    public String toString() {
        return "[ id = " + id +
                ", content = " + content +
                ", author = " + author +
                ", topic = " + topic +
                ",]";
    }
}
