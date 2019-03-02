package com.dscjss.lineup.game;

import com.dscjss.lineup.game.dto.Leaderboard;
import com.dscjss.lineup.users.dto.UserBean;
import org.springframework.data.domain.Pageable;

public interface GameService {

    int isValidScan(String username, String code, double lat, double lng);

    Leaderboard getLeaderboard(UserBean userBean, Pageable pageable);
}
