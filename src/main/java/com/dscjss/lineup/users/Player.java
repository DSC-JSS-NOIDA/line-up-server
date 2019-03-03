package com.dscjss.lineup.users;

import java.time.Duration;

public interface Player {

    int getId();

    String getFirstName();

    String getLastName();

    String getUsername();

    String getUniqueCode();

    int getScore();

    long getDuration();

    String getPhone();

    int getPosition();

    double getLat();

    double getLng();

}
