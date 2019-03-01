package com.dscjss.lineup.location;


import com.dscjss.lineup.users.dto.UserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api")
public class LocationController {

    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PutMapping("/location")
    public ResponseEntity logLocation(Principal principal, @RequestParam double lat, @RequestParam double lng){

        if(principal != null){
            locationService.log(new UserBean(principal.getName()), lat, lng);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }



}
