package com.dscjss.lineup.admin;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "settings")
public class Settings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Instant startTime;

    private Instant endTime;

    private Instant signUpStartTime;

    private Instant signUpEndTime;

    public Settings() {
    }

    public Settings(Instant startTime, Instant endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public Instant getSignUpStartTime() {
        return signUpStartTime;
    }

    public void setSignUpStartTime(Instant signUpStartTime) {
        this.signUpStartTime = signUpStartTime;
    }

    public Instant getSignUpEndTime() {
        return signUpEndTime;
    }

    public void setSignUpEndTime(Instant signUpEndTime) {
        this.signUpEndTime = signUpEndTime;
    }
}

