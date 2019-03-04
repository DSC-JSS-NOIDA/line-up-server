package com.dscjss.lineup.game.dto;

import java.time.Instant;

public class SettingsDto {

    private Instant startTime;

    private Instant endTime;

    private Instant signUpStartTime;

    private Instant signUpEndTime;

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
