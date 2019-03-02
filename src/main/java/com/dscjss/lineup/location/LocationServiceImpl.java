package com.dscjss.lineup.location;

import com.dscjss.lineup.location.dto.LocationDto;
import com.dscjss.lineup.location.model.RecentLocation;
import com.dscjss.lineup.users.UserRepository;
import com.dscjss.lineup.users.dto.UserBean;
import com.dscjss.lineup.users.model.User;
import com.dscjss.lineup.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final UserRepository userRepository;

    @Autowired
    public LocationServiceImpl(LocationRepository locationRepository, UserRepository userRepository) {
        this.locationRepository = locationRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void log(UserBean userBean, double lat, double lng) {
        User user = userRepository.findByUsername(userBean.getUsername());
        RecentLocation recentLocation = locationRepository.findByUserId(userBean.getId());
        if(recentLocation == null){
            recentLocation = new RecentLocation();
            recentLocation.setUser(user);
            recentLocation.setLat(lat);
            recentLocation.setLng(lng);
            recentLocation.setLastUpdatedAt(Instant.now());
        } else{
            recentLocation.setLat(lat);
            recentLocation.setLng(lng);
            recentLocation.setLastUpdatedAt(Instant.now());
        }
        locationRepository.save(recentLocation);
    }

    @Override
    public List<LocationDto> getTeamLocation(UserBean userBean) {
        List<RecentLocation> recentLocations = locationRepository.findTeamMembersRecentLocation(userBean.getUsername());
        List<LocationDto> locationDtoList = new ArrayList<>();
        recentLocations.forEach(recentLocation -> locationDtoList.add(Mapper.getRecentLocationDto(recentLocation)));
        return locationDtoList;
    }

    @Override
    public List<LocationDto> getNearestNeighbours(UserBean userBean, double lat, double lng) {
        List<Neighbour> nearestNeighbours = locationRepository.findNearestNeighbours(userBean.getUsername(), lat, lng);

        return null;
    }
}
