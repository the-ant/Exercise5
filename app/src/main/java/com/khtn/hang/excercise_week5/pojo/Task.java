package com.khtn.hang.excercise_week5.pojo;

import com.khtn.hang.excercise_week5.Constants;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Task extends RealmObject {

    @PrimaryKey
    private String id;
    private String name;
    private Date dueToDate;
    private int priority;
    private boolean isDone;

    public Task() {
    }

    public Task(String id, String name, Date dueToDate, int priority, boolean isDone) {
        this.id = id;
        this.name = name;
        this.dueToDate = dueToDate;
        this.priority = priority;
        this.isDone = isDone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDueToDate() {
        return dueToDate;
    }

    public void setDueToDate(Date dueToDate) {
        this.dueToDate = dueToDate;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public String getPriorityString() {
        String result = "";
        switch (priority) {
            case Constants.PRIORITY_LOW_ID:
                result = Constants.PRIORITY_LOW;
                break;
            case Constants.PRIORITY_NORMAL_ID:
                result = Constants.PRIORITY_NORMAL;
                break;
            case Constants.PRIORITY_HIGH_ID:
                result = Constants.PRIORITY_HIGH;
                break;
        }
        return result;
    }
}
