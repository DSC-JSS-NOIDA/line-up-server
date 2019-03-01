package com.dscjss.lineup.game;

import com.dscjss.lineup.users.UserRepository;
import com.dscjss.lineup.users.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameServiceImpl implements GameService {

    private final UserRepository userRepository;

    @Autowired
    public GameServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValidScan(String username, String code, double lat, double lng) {
        User scannedBy = userRepository.findByUsername(username);
        return false;
    }
}
