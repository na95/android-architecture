package com.anle.todomvp.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.common.base.Objects;
import com.google.common.base.Strings;

import java.util.UUID;

/**
 * Immutable model class for a com.anle.todomvp.data.local.Task.
 */
@Entity(tableName = "tasks")
public final class Task {

    @PrimaryKey
    @ColumnInfo(name = "entryid")
    @NonNull
    private final String mId;

    @Nullable
    @ColumnInfo(name = "title")
    private final String mTitle;

    @Nullable
    @ColumnInfo(name = "content")
    private final String mContent;

    @ColumnInfo(name = "completed")
    private final boolean mCompleted;

    /**
     * Use this constructor to create a new active com.anle.todomvp.data.local.Task.
     *
     * @param title       title of the task
     * @param description description of the task
     */
    @Ignore
    public Task(@Nullable String title, @Nullable String description) {
        this(title, description, UUID.randomUUID().toString(), false);
    }

    /**
     * Use this constructor to create an active com.anle.todomvp.data.local.Task if the com.anle.todomvp.data.local.Task already has an id (copy of another
     * com.anle.todomvp.data.local.Task).
     *
     * @param title       title of the task
     * @param description description of the task
     * @param id          id of the task
     */
    @Ignore
    public Task(@Nullable String title, @Nullable String description, @NonNull String id) {
        this(title, description, id, false);
    }

    /**
     * Use this constructor to create a new completed com.anle.todomvp.data.local.Task.
     *
     * @param title       title of the task
     * @param description description of the task
     * @param completed   true if the task is completed, false if it's active
     */
    @Ignore
    public Task(@Nullable String title, @Nullable String description, boolean completed) {
        this(title, description, UUID.randomUUID().toString(), completed);
    }

    /**
     * Use this constructor to specify a completed com.anle.todomvp.data.local.Task if the com.anle.todomvp.data.local.Task already has an id (copy of
     * another com.anle.todomvp.data.local.Task).
     *
     * @param title     title of the task
     * @param content   content of the task
     * @param id        id of the task
     * @param completed true if the task is completed, false if it's active
     */
    public Task(@Nullable String title, @Nullable String content,
                @NonNull String id, boolean completed) {
        mId = id;
        mTitle = title;
        mContent = content;
        mCompleted = completed;
    }

    @NonNull
    public String getId() {
        return mId;
    }

    @Nullable
    public String getTitle() {
        return mTitle;
    }

    @Nullable
    public String getTitleForList() {
        if (!Strings.isNullOrEmpty(mTitle)) {
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

    @Nullable
    public String getContent() {
        return mContent;
    }

    public boolean isCompleted() {
        return mCompleted;
    }

    public boolean isActive() {
        return !mCompleted;
    }

    public boolean isEmpty() {
        return Strings.isNullOrEmpty(mTitle) &&
                Strings.isNullOrEmpty(mContent);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equal(mId, task.mId) &&
                Objects.equal(mTitle, task.mTitle) &&
                Objects.equal(mContent, task.mContent);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mId, mTitle, mContent);
    }

    @Override
    public String toString() {
        return "com.anle.todomvp.data.local.Task with title " + mTitle;
    }
}
