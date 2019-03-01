package com.dscjss.lineup.location;

import com.dscjss.lineup.location.model.RecentLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LocationRepository extends JpaRepository<RecentLocation, Integer> {

    RecentLocation findByUserId(int id);

    @Query(value = "SELECT * FROM recent_user_location AS rul JOIN user ON user.id = rul.user_id" +
            " WHERE user.team_id = (SELECT team_id FROM user WHERE username = ?) ORDER BY user.id", nativeQuery = true)
    List<RecentLocation> findTeamMembersRecentLocation(String user);
}
