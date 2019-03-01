package com.dscjss.lineup.location;

import com.dscjss.lineup.location.dto.LocationDto;
import com.dscjss.lineup.users.dto.UserBean;

import java.util.List;

public interface LocationService {
    void log(UserBean userBean, double lat, double lng);

    List<LocationDto> getTeamLocation(UserBean userBean);
}
