package com.dscjss.lineup.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {


    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/settings")
    public ResponseEntity updateSettings(Principal principal, Settings settings) {
        adminService.updateSettings(settings);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/settings/reload")
    public ResponseEntity reloadSettings(Principal principal) {
        adminService.reloadSettings();
        return ResponseEntity.ok().build();
    }


    @GetMapping("/create_teams")
    public ResponseEntity createTeams(Principal principal, @RequestParam("member_count") int memberCount){
        adminService.createTeams(memberCount);
        return ResponseEntity.ok().build();
    }

}
