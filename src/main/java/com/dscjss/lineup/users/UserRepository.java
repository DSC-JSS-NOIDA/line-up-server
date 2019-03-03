package com.dscjss.lineup.users;


import com.dscjss.lineup.users.dto.UserBean;
import com.dscjss.lineup.users.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Integer>, PagingAndSortingRepository<User, Integer> {

    User findByUsername(String user);

    Optional<User> findByUniqueCode(String code);

    @Query(value = "SELECT IF((SELECT team_id FROM user WHERE user.id = ?1) = (SELECT team_id FROM user WHERE user.id = ?2), TRUE, FALSE) AS ans", nativeQuery = true)
    BigInteger areTeamMembers(int userId1, int userId2);


    @Query(value = "SELECT id, first_name AS firstName, last_name AS lastName, username, unique_code AS uniqueCode, score, phone, duration, " +
            "@seq\\:=@seq + 1, @rn\\:=IF(@last_score = u.score AND @last_duration = u.duration,  @rn, @seq) AS position," +
            "        @last_score\\:=u.score, @last_duration\\:=u.duration FROM user u CROSS JOIN " +
            "          (SELECT @last_score\\:=-1, @last_duration\\:=-1, @rn\\:=0, @seq\\:=0) AS params" +
            "      ORDER BY u.score DESC , u.duration ASC LIMIT 15" , nativeQuery = true)
    List<Player> findAllTopRanks();


    @Query(value = "SELECT id, first_name AS firstName, last_name AS lastName, username, unique_code AS uniqueCode, score, phone, duration, position FROM " +
            "(SELECT id, first_name, last_name, username, unique_code, score, phone, duration, @seq \\:= @seq + 1,\n" +
            "       @rn \\:= IF(@last_score = u.score AND @last_duration = u.duration,  @rn, @seq) AS position,\n" +
            "       @last_score \\:= u.score, @last_duration \\:= u.duration\n" +
            "FROM user u CROSS JOIN\n" +
            "     (SELECT @last_score \\:= -1, @last_duration \\:= -1, @rn \\:= 0, @seq \\:= 0) AS params\n" +
            "ORDER BY u.score DESC , u.duration ASC) AS ranks WHERE username = 'ZO_123'",nativeQuery = true)
    Player findUserDetails(String username);


    //List<Player> findAllByIdTeamId(int id);
}