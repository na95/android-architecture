package com.anle.todomvp.data;

import androidx.annotation.Nullable;

import java.util.Objects;
import java.util.UUID;

public class Task {

    private String mId;
    @Nullable
    private String mTitle;
    @Nullable
    private String mContent;
    private boolean mCompleted;

    /**
     * Use this constructor to create new active task
     *
     * @param mTitle
     * @param mContent
     */
    public Task(@Nullable String mTitle, @Nullable String mContent) {
        this.mId = UUID.randomUUID().toString();
        this.mTitle = mTitle;
        this.mContent = mContent;
        this.mCompleted = false;
    }

    /**
     * Use this constructor to create new task that has an Id (in db)
     *
     * @param title
     * @param content
     * @param itemId
     * @param completed
     */
    public Task(String title, String content, String itemId, boolean completed) {
        this.mId = itemId;
        this.mTitle = title;
        this.mContent = content;
        this.mCompleted = completed;
    }

    /***
     * Use this constructor to create new activate task that has an Id (in repository)
     * @param title
     * @param content
     * @param id
     */
    public Task(String title, String content, String id) {
        this.mId = id;
        this.mTitle = title;
        this.mContent = content;
        this.mCompleted = false;
    }

    public String getId() {
        return mId;
    }

    public void setId(String mId) {
        this.mId = mId;
    }

    @Nullable
    public String getTitle() {
        return mTitle;
    }

    public void setTitle(@Nullable String _title) {
        this.mTitle = _title;
    }

    @Nullable
    public String getContent() {
        return mContent;
    }

    public void setContent(@Nullable String mContent) {
        this.mContent = mContent;
    }

    public boolean isActive() {
        return !mCompleted;
    }

    public boolean isCompleted() {
        return mCompleted;
    }

    public void setCompleted(boolean mCompleted) {
        this.mCompleted = mCompleted;
    }

    public boolean isEmpty() {
        return (mTitle == null || mTitle.equals("")) &&
                (mContent == null || mContent.equals(""));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(mId, task.mId) &&
                Objects.equals(mTitle, task.mTitle) &&
                Objects.equals(mContent, task.mContent);
    }

    @Override
    public String toString() {
        return "com.anle.todomvp.data.local.Task with title " + mTitle;
    }

    @Nullable
    public String getTitleForList() {
        if (mTitle != null && !mTitle.equals("")) {
            return mTitle;
        } else {
            return mContent;
        }
    }

    @Nullable
    public String getContentForList() {
        if (mContent != null && !mContent.equals("")) {
            return mContent;
        } else {
            return "";
        }
    }
}
