package com.dscjss.lineup.game;

import com.dscjss.lineup.game.dto.Leaderboard;
import com.dscjss.lineup.game.exception.InvalidCodeException;
import com.dscjss.lineup.game.model.ScanHistory;
import com.dscjss.lineup.users.UserRepository;
import com.dscjss.lineup.users.dto.UserBean;
import com.dscjss.lineup.users.model.User;
import com.dscjss.lineup.util.Constants;
import com.dscjss.lineup.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

            if(scannedBy.getId() == optional.get().getId()){
                return Constants.SAME_CODE;
            }

            if(scanHistoryRepository.existsByScannedByIdAndTargetId(scannedBy.getId(), optional.get().getId())){
                return Constants.ALREADY_SCANNED_CODE;
            }
            ScanHistory scanHistory = new ScanHistory();
            scanHistory.setCode(code);
            scanHistory.setScannedBy(scannedBy);
            scanHistory.setTarget(optional.get());
            if(userRepository.areTeamMembers(scannedBy.getId(), optional.get().getId()).intValue() == 1){
                scanHistory.setCorrect(true);
                scannedBy.incrementScoreByOne();
                optional.get().incrementScoreByOne();
            }
            userRepository.save(scannedBy);
            userRepository.save(optional.get());
            scanHistoryRepository.save(scanHistory);
            return scanHistory.isCorrect() ? Constants.CORRECT_CODE : Constants.WRONG_CODE;
        }else{
            return Constants.INVALID_CODE;
        }
    }

    @Override
    public Leaderboard getLeaderboard(UserBean userBean, Pageable pageable) {
        Leaderboard leaderboard = new Leaderboard();
        Page<UserBean> page = userRepository.findAll(pageable).map(Mapper::getUserBean);
        return null;
    }
}
