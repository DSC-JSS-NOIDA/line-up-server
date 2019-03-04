package com.dscjss.lineup.game;

import com.dscjss.lineup.game.dto.SettingsDto;
import com.dscjss.lineup.game.dto.Leaderboard;
import com.dscjss.lineup.game.model.ScanHistory;
import com.dscjss.lineup.users.Player;
import com.dscjss.lineup.users.UserRepository;
import com.dscjss.lineup.users.dto.UserBean;
import com.dscjss.lineup.users.model.User;
import com.dscjss.lineup.util.Constants;
import com.dscjss.lineup.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {

    private final UserRepository userRepository;
    private final ScanHistoryRepository scanHistoryRepository;
    private final GameDetails gameDetails;

    @Autowired
    public GameServiceImpl(UserRepository userRepository, ScanHistoryRepository scanHistoryRepository, GameDetails gameDetails) {
        this.userRepository = userRepository;
        this.scanHistoryRepository = scanHistoryRepository;
        this.gameDetails = gameDetails;
    }

    @Override
    @Transactional
    public int isValidScan(String username, String code, double lat, double lng) {
        User scannedBy = userRepository.findByUsername(username);
        Optional<User> optional = userRepository.findByUniqueCode(code);
        int status = Constants.INVALID_CODE;
        ScanHistory scanHistory = new ScanHistory();
        scanHistory.setCode(code);
        scanHistory.setScannedBy(scannedBy);
        scanHistory.setScannedAt(Instant.now());
        scanHistory.setLat(lat);
        scanHistory.setLng(lng);
        if(optional.isPresent()){
            User target = optional.get();
            scanHistory.setTarget(target);
            if(scannedBy.getId() == target.getId()){
                status = Constants.SAME_CODE;
            } else if(scanHistoryRepository.existsByScannedByIdAndTargetId(scannedBy.getId(), target.getId())){
                status = Constants.ALREADY_SCANNED_CODE;
            } else  if(userRepository.areTeamMembers(scannedBy.getId(), target.getId()).intValue() == 1){
                if(scanHistoryRepository.existsByScannedByIdAndTargetId(target.getId(), scannedBy.getId())) {
                    status = Constants.TARGET_ALREADY_SCANNED_CODE;
                } else {
                    status = Constants.CORRECT_CODE;
                    scannedBy.incrementScoreByOne();
                    target.incrementScoreByOne();
                    Duration duration = Duration.between(scanHistory.getScannedAt(), gameDetails.getSettings().getStartTime());
                    scannedBy.setDuration(scannedBy.getDuration().plusMillis(duration.toMillis()));
                    target.setDuration(target.getDuration().plusMillis(duration.toMillis()));
                    userRepository.save(scannedBy);
                    userRepository.save(target);
                }
            } else{
                status = Constants.WRONG_CODE;
            }
        }
        scanHistory.setStatus(status);
        scanHistoryRepository.save(scanHistory);
        return scanHistory.getStatus();
    }

    @Override
    public Leaderboard getLeaderboard(UserBean userBean, Pageable pageable) {
        Leaderboard leaderboard = new Leaderboard();
        List<Player> playerList = userRepository.findAllTopRanks();
        List<UserBean> userBeanList = playerList.stream().map(player ->{
            UserBean temp = Mapper.getUserBean(player);
            temp.setPosition(player.getPosition());
            return temp;
        }).collect(Collectors.toList());
        leaderboard.setUserBeanList(userBeanList);
        leaderboard.setCurrentUser(getUserDetails(userBean.getUsername()));
        return leaderboard;
    }

    @Override
    public UserBean getUserDetails(String username) {
        Player player = userRepository.findUserDetails(username);
        UserBean userBean = Mapper.getUserBean(player);
        userBean.setPosition(player.getPosition());
        return userBean;
    }

    @Override
    public List<UserBean> getTeammatesFound(int id) {
        List<Player> playerList = scanHistoryRepository.getTeammatesScanned(id);
        return playerList.stream().map(player -> {
            UserBean userBean = Mapper.getUserBean(player);
            userBean.setLat(player.getLat());
            userBean.setLng(player.getLng());
            return userBean;
        }).collect(Collectors.toList());
    }

    @Override
    public SettingsDto getGameDetails() {
        return Mapper.getSettingsDto(gameDetails.getSettings());
    }
}
