package com.dscjss.lineup.util;

import com.dscjss.lineup.location.Neighbour;
import com.dscjss.lineup.location.dto.LocationDto;
import com.dscjss.lineup.location.dto.NeighbourDto;
import com.dscjss.lineup.location.model.RecentLocation;
import com.dscjss.lineup.users.dto.UserBean;
import com.dscjss.lineup.users.model.User;

public class Mapper {


    public static UserBean getUserBean(User user) {
        if(user == null){
            return null;
        }
        UserBean userBean = new UserBean();
        userBean.setUsername(user.getUsername());
        userBean.setFirstName(user.getFirstName());
        userBean.setLastName(user.getLastName());
        userBean.setPhone(user.getPhone());
        userBean.setScore(user.getScore());
        return userBean;
    }

    public static LocationDto getRecentLocationDto(RecentLocation recentLocation) {
        if (recentLocation == null) {
            return null;
        }
        LocationDto locationDto = new LocationDto();
        locationDto.setUserBean(getUserBean(recentLocation.getUser()));
        locationDto.setLat(recentLocation.getLat());
        locationDto.setLng(recentLocation.getLng());
        locationDto.setLastUpdatedAt(recentLocation.getLastUpdatedAt());
        return locationDto;
    }

    public static NeighbourDto getLocationDto(Neighbour neighbour) {

        if(neighbour == null){
            return null;
        }
        NeighbourDto neighbourDto = new NeighbourDto();
        neighbourDto.setDistance(neighbour.getDistance());
        return neighbourDto;
    }
}
