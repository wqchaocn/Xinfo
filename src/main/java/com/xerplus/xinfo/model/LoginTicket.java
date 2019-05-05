package com.xerplus.xinfo.model;

import java.util.Date;

public class LoginTicket {
    private int id;
    private int userId;
    private String ticket;
    private Date expired;
    private int status;

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public void setExpired(Date expired) {
        this.expired = expired;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getTicket() {
        return ticket;
    }

    public Date getExpired() {
        return expired;
    }

    public int getStatus() {
        return status;
    }
}
