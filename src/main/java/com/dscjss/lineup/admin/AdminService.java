package com.dscjss.lineup.admin;

import java.time.Instant;

public interface AdminService {

    void reloadSettings();

    void updateSettings(Settings settings);

    void createTeams();


}
