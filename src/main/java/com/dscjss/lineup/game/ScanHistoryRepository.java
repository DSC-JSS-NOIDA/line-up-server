package com.dscjss.lineup.game;

import com.dscjss.lineup.game.model.ScanHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScanHistoryRepository extends JpaRepository<ScanHistory, Integer> {


    boolean existsByScannedByIdAndTargetId(int scannedById, int targetId);
}
