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


    @Query(value = "  SELECT * FROM (SELECT ROUND((6371 * acos (cos ( radians(?2) ) * cos( radians( rcu.lat ) )  * cos( radians( rcu.lng ) - radians(?3) )" +
            "                         + sin ( radians(?2) )* sin( radians( rcu.lat ) )" +
            "                     ))*1000,0) AS distance , " +
            "                        ((360.0 + degrees(atan2( sin(radians(rcu.lng-?3))*cos(radians(rcu.lat))," +
            "                           cos(radians(?2))*sin(radians(rcu.lat))-sin(radians(?2))*cos(radians(rcu.lat))*" +
            "                            cos(radians(rcu.lng-?3))))) % 360.0) AS bearing," +
            "    user_id AS userId, lat, lng, last_updated_at AS lastUpdatedAt FROM recent_user_location rcu JOIN user u on rcu.user_id = u.id" +
            "      WHERE u.username != ?1 ORDER BY distance ASC) AS t  WHERE t.distance < 500000000  LIMIT 5", nativeQuery = true)
    List<Neighbour> findNearestParticipants(String username, double lat, double lng);
}
