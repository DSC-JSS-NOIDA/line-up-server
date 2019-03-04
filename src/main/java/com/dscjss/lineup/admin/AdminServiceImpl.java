package com.dscjss.lineup.admin;

import com.dscjss.lineup.exception.SettingsNotFoundException;
import com.dscjss.lineup.game.GameDetails;
import com.dscjss.lineup.teams.TeamRepository;
import com.dscjss.lineup.teams.model.Team;
import com.dscjss.lineup.users.UserRepository;
import com.dscjss.lineup.users.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private final SettingsRepository settingsRepository;

    private final GameDetails gameDetails;

    private final UserRepository userRepository;

    private final TeamRepository teamRepository;

    @PostConstruct
    public void initializeGameDetails(){
        gameDetails.setSettings(settingsRepository.getOne(1));
    }


    @Autowired
    public AdminServiceImpl(SettingsRepository settingsRepository, GameDetails gameDetails, UserRepository userRepository, TeamRepository teamRepository) {
        this.settingsRepository = settingsRepository;
        this.gameDetails = gameDetails;
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    public void reloadSettings(){
        gameDetails.setSettings(settingsRepository.getOne(1));
    }


    @Override
    public void updateSettings(Settings settings) {
        settingsRepository.save(settings);
    }


    @Override
    public void createTeams(int memberCount) {
        List<User> users = userRepository.findAll();
        int totalUserCount = users.size();
        int distinctTeamCount = totalUserCount / memberCount;
        for(int i = 0; i < distinctTeamCount; i++){
            Team team = new Team();
            teamRepository.save(team);
            for(int j = i; j < totalUserCount; j += memberCount){
                User user = users.get(j);
                user.setTeam(team);
                userRepository.save(user);
            }
        }

    }
}
