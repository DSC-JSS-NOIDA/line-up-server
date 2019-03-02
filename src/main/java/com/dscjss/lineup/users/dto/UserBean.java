package com.dscjss.lineup.users.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Duration;

public class UserBean {

    public UserBean() {
    }

    public UserBean(String username) {
        this.username = username;
    }

    private int id;

    @JsonProperty("zeal_id")
    private String username;

    private String firstName;

    private String lastName;

    private String phone;

    private int totalScans;

    private Duration totalTimeTaken;

    private int score;

    @JsonIgnore
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getTotalScans() {
        return totalScans;
    }

    public void setTotalScans(int totalScans) {
        this.totalScans = totalScans;
    }

    public Duration getTotalTimeTaken() {
        return totalTimeTaken;
    }

    public void setTotalTimeTaken(Duration totalTimeTaken) {
        this.totalTimeTaken = totalTimeTaken;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
