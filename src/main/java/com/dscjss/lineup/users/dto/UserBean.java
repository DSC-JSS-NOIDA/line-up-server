package com.dscjss.lineup.users.dto;

import java.time.Duration;

public class UserBean {

    private int id;
    private String username;
    private int totalScans;
    private Duration totalTimeTaken;
    private int score;

    public UserBean() {
    }

    public UserBean(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
