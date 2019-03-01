package com.dscjss.lineup.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/validate_scan")
    public ResponseEntity validateQR(Principal principal, @RequestParam String code, @RequestParam double lat, @RequestParam double lng){

        Map<String, String> map = new HashMap<>();
        if(principal != null){
            String username = principal.getName();
            map.put("valid", String.valueOf(gameService.isValidScan(username, code, lat, lng)));

        }
        return ResponseEntity.badRequest().build();
    }


}
