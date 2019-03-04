package com.dscjss.lineup.location;

import com.dscjss.lineup.users.model.User;

import java.time.Instant;

public interface Neighbour {

    User getUser();

    double getDistance();

    Instant getLastUpdatedAt();

    double getLat();

    double getLng();

    double getBearing();


}
