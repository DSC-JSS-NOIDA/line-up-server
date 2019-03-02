package com.dscjss.lineup.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @PostMapping("/settings")
    public ResponseEntity updateSettings(Principal principal, Settings settings) {
        return ResponseEntity.ok().build();
    }

}
