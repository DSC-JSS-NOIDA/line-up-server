package com.dscjss.lineup.admin;

import com.dscjss.lineup.exception.SettingsNotFoundException;
import com.dscjss.lineup.game.GameDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class AdminServiceImpl implements AdminService {

    private final SettingsRepository settingsRepository;

    private final GameDetails gameDetails;

    @PostConstruct
    public void initializeGameDetails(){
        gameDetails.setSettings(settingsRepository.getOne(1));
    }


    @Autowired
    public AdminServiceImpl(SettingsRepository settingsRepository, GameDetails gameDetails) {
        this.settingsRepository = settingsRepository;
        this.gameDetails = gameDetails;
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
    public void createTeams() {

    }
}
