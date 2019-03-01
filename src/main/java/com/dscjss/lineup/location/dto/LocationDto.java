package com.dscjss.lineup.location.dto;

import com.dscjss.lineup.users.dto.UserBean;

import java.time.Instant;

public class LocationDto {

    private UserBean userBean;
    private double lat;
    private double lng;
    private Instant lastUpdatedAt;

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public void setLastUpdatedAt(Instant lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public Instant getLastUpdatedAt() {
        return lastUpdatedAt;
    }
}
