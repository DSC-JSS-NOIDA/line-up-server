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


    @Query(value = "SELECT (SELECT ROUND((" +
            "                   6371 * acos (" +
            "                         cos ( radians(?2) ) " +
            "                         * cos( radians( lat ) ) " +
            "                         * cos( radians( lng ) - radians(?3) ) " +
            "                       + sin ( radians(?2) ) " +
            "                           * sin( radians( lat ) ) " +
            "                   ) " +
            "                 )*1000,0)) AS distance, " +
            " user_id AS userId, lat, lng, last_updated_at AS lastUpdatedAt FROM recent_user_location JOIN user u on recent_user_location.user_id = u.id " +
            "    WHERE u.username != ?1 ORDER BY  distance ASC LIMIT 5;", nativeQuery = true)
    List<Neighbour> findNearestParticipants(String username, double lat, double lng);
}
