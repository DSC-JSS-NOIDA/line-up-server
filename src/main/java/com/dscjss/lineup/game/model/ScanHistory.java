package com.dscjss.lineup.game.model;


import com.dscjss.lineup.users.model.User;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "scan_history")
public class ScanHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private User scannedBy; // who scans the code

    @ManyToOne
    private User target; // whose code is scanned

    private String code; // scanned code

    private int status;

    private Instant scannedAt;

    private double lat;

    private double lng;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getScannedBy() {
        return scannedBy;
    }

    public void setScannedBy(User scannedBy) {
        this.scannedBy = scannedBy;
    }

    public User getTarget() {
        return target;
    }

    public void setTarget(User target) {
        this.target = target;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Instant getScannedAt() {
        return scannedAt;
    }

    public void setScannedAt(Instant scannedAt) {
        this.scannedAt = scannedAt;
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
}
