package com.dscjss.lineup.util;

import com.dscjss.lineup.admin.Settings;
import com.dscjss.lineup.game.dto.SettingsDto;
import com.dscjss.lineup.location.Neighbour;
import com.dscjss.lineup.location.dto.LocationDto;
import com.dscjss.lineup.location.dto.NeighbourDto;
import com.dscjss.lineup.location.model.RecentLocation;
import com.dscjss.lineup.users.Player;
import com.dscjss.lineup.users.dto.UserBean;
import com.dscjss.lineup.users.model.User;

public class Mapper {

    private final static  String[] directions = {"N", "NE", "E", "SE", "S", "SW", "W", "NW"};

    public static UserBean getUserBean(User user) {
        if(user == null){
            return null;
        }
        UserBean userBean = new UserBean();
        userBean.setId(user.getId());
        userBean.setUsername(user.getUsername());
        userBean.setFirstName(user.getFirstName());
        userBean.setLastName(user.getLastName());
        userBean.setUniqueCode(user.getUniqueCode());
        userBean.setPhone(user.getPhone());
        userBean.setScore(user.getScore());
        userBean.setTotalTimeTaken(user.getDuration().toMillis());
        return userBean;
    }

    public static UserBean getUserBean(Player player) {
        if(player == null){
            return null;
        }
        UserBean userBean = new UserBean();
        userBean.setId(player.getId());
        userBean.setUsername(player.getUsername());
        userBean.setFirstName(player.getFirstName());
        userBean.setLastName(player.getLastName());
        userBean.setPhone(player.getPhone());
        userBean.setUniqueCode(player.getUniqueCode());
        userBean.setTotalTimeTaken(player.getDuration());
        userBean.setScore(player.getScore());
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
        neighbourDto.setDirection(directions[ ((int)Math.round(neighbour.getBearing() / 45)) % 8]);
        return neighbourDto;
    }

    public static SettingsDto getSettingsDto(Settings settings){
        if(settings == null){
            return null;
        }

        SettingsDto settingsDto = new SettingsDto();
        settingsDto.setStartTime(settings.getStartTime());
        settingsDto.setEndTime(settings.getEndTime());
        settingsDto.setSignUpStartTime(settings.getSignUpStartTime());
        settingsDto.setSignUpEndTime(settings.getSignUpEndTime());

        return settingsDto;

    }
}
