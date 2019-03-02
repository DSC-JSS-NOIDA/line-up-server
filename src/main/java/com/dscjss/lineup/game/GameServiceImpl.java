package com.dscjss.lineup.game;

import com.dscjss.lineup.game.dto.Leaderboard;
import com.dscjss.lineup.game.exception.InvalidCodeException;
import com.dscjss.lineup.game.model.ScanHistory;
import com.dscjss.lineup.users.UserRepository;
import com.dscjss.lineup.users.dto.UserBean;
import com.dscjss.lineup.users.model.User;
import com.dscjss.lineup.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class GameServiceImpl implements GameService {

    private final UserRepository userRepository;
    private final ScanHistoryRepository scanHistoryRepository;

    @Autowired
    public GameServiceImpl(UserRepository userRepository, ScanHistoryRepository scanHistoryRepository) {
        this.userRepository = userRepository;
        this.scanHistoryRepository = scanHistoryRepository;

    }

    @Override
    @Transactional
    public int isValidScan(String username, String code, double lat, double lng) {
        User scannedBy = userRepository.findByUsername(username);
        Optional<User> optional = userRepository.findByUniqueCode(code);
        if(optional.isPresent()){

            if(scanHistoryRepository.existsByScannedByIdAndTargetId(scannedBy.getId(), optional.get().getId())){
                return Constants.ALREADY_SCANNED_CODE;
            }

            ScanHistory scanHistory = new ScanHistory();
            scanHistory.setCode(code);
            scanHistory.setScannedBy(scannedBy);
            scanHistory.setTarget(optional.get());
            if(userRepository.areTeamMembers(scannedBy.getId(), optional.get().getId())){
                scanHistory.setCorrect(true);
                scannedBy.incrementScoreByOne();
                optional.get().incrementScoreByOne();
            }
            userRepository.save(scannedBy);
            userRepository.save(optional.get());
            scanHistoryRepository.save(scanHistory);
            return scanHistory.isCorrect() ? Constants.VALID_CODE : Constants.INVALID_CODE;
        }else{
            throw new InvalidCodeException("Invalid unique code.");
        }
    }

    @Override
    public Leaderboard getLeaderboard(UserBean userBean, Pageable pageable) {
        userRepository.findAll(pageable);
        return null;
    }
}
