package com.pao.laboratory03.bonus;

import com.pao.laboratory03.bonus.enums.Priority;
import com.pao.laboratory03.bonus.enums.Status;

public class Task {

    Task(String id, String title, Priority priority) {
        this.id = id;
        this.title = title;
        this.status = Status.TO_DO;
        this.priority = priority;
        this.asignee = null;
    }

    Task(String id, String title, Priority priority, String assignee) {
        this.id = id;
        this.title = title;
        this.status = Status.TO_DO;
        this.priority = priority;
        this.asignee = assignee;
    }

    public String getId() {return id;}
    public String getTitle() {return title;}
    public Status getStatus() {return status;}
    public Priority getPriority() {return priority;}
    public String getAsignee() {return asignee;}

    public void setStatus(Status status) {this.status = status;}
    public void setPriority(Priority priority) {this.priority = priority;}
    public void setAsignee(String asignee) {this.asignee = asignee;}
    public void setTitle(String title) {this.title = title;}
    public void setId(String id) {this.id = id;}

    private String id;
    private String title;
    private Status status;
    private Priority priority;
    private String asignee;
}
