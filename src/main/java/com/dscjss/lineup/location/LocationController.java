package com.dscjss.lineup.location;


import com.dscjss.lineup.game.GameDetails;
import com.dscjss.lineup.users.dto.UserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class LocationController {

    private final LocationService locationService;
    private final GameDetails gameDetails;

    @Autowired
    public LocationController(LocationService locationService, GameDetails gameDetails) {
        this.locationService = locationService;
        this.gameDetails = gameDetails;
    }

    @PutMapping("/location")
    public ResponseEntity logLocation(Principal principal, @RequestParam double lat, @RequestParam double lng){

        if(principal != null){
            locationService.log(new UserBean(principal.getName()), lat, lng);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/nearest_participants")
    public ResponseEntity nearestParticipants(Principal principal, @RequestParam double lat, @RequestParam double lng){

        if(principal != null){
            if(gameDetails.getSettings().getEndTime().isBefore(Instant.now())){
                Map<String, String> map = new HashMap<>();
                map.put("error", "Event has ended. Winners will be announced shortly.");
                return ResponseEntity.badRequest().body(map);
            }

            return ResponseEntity.ok().body(locationService.getNearestParticipants(new UserBean(principal.getName()), lat, lng));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
