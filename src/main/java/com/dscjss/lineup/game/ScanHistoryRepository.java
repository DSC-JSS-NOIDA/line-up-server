package com.dscjss.lineup.game;

import com.dscjss.lineup.game.model.ScanHistory;
import com.dscjss.lineup.users.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScanHistoryRepository extends JpaRepository<ScanHistory, Integer> {


    boolean existsByScannedByIdAndTargetId(int scannedById, int targetId);


    @Query(value = "SELECT  first_name AS firstName, last_name AS lastName, username, unique_code AS uniqueCode, score, phone, duration, lat, lng FROM user JOIN " +
            "  (SELECT target_id AS id, lat, lng FROM scan_history where scanned_by_id = ?1 AND status = 1" +
            "  UNION SELECT scanned_by_id AS id, lat, lng FROM scan_history WHERE  target_id = ?1 AND status = 1) t " +
            "  ON user.id = t.id", nativeQuery = true)
    List<Player> getTeammatesScanned(int id);
}
