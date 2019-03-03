package com.dscjss.lineup.game;

import com.dscjss.lineup.game.dto.Leaderboard;
import com.dscjss.lineup.users.dto.UserBean;
import com.dscjss.lineup.util.Constants;
import com.dscjss.lineup.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
            int codeStatus = gameService.isValidScan(username, code, lat, lng);
            if(codeStatus == Constants.CORRECT_CODE){
                map.put("valid", String.valueOf(true));
                map.put("message", "You found your teammate.");
            }else if(codeStatus == Constants.WRONG_CODE){
                map.put("valid", String.valueOf(false));
                map.put("message", "Sorry, the person is not your teammate.");
            }else if(codeStatus == Constants.SAME_CODE) {
                map.put("valid", String.valueOf(false));
                map.put("message", "You obviously can't scan your code.");
            }else if(codeStatus == Constants.ALREADY_SCANNED_CODE){
                map.put("valid", String.valueOf(false));
                map.put("message", "You already scanned this code before.");
            }else if(codeStatus == Constants.TARGET_ALREADY_SCANNED_CODE){
                map.put("valid", String.valueOf(true));
                map.put("message", "You have received the score for this code.");
            }else{
                map.put("valid", String.valueOf(false));
                map.put("message", "You scanned an invalid code.");
            }
            return ResponseEntity.ok(map);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/leaderboard")
    public ResponseEntity leaderboard(Principal principal, @RequestParam(required = false, defaultValue = "1") int page, @RequestParam(name = "sort_by", required = false, defaultValue = "rank") String sort,
                                      @RequestParam(name = "sort_order", required = false, defaultValue = "asc") String order){
        if(principal != null){
            int pageSize = 20;
            Pageable pageable = Utility.createPageable(page, sort, order, pageSize);
            Map<String, Object> map = new HashMap<>();
            Leaderboard leaderboard = gameService.getLeaderboard(new UserBean(principal.getName()), pageable);
            return ResponseEntity.ok().body(leaderboard);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }




}
