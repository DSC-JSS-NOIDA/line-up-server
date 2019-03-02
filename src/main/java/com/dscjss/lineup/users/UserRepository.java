package com.dscjss.lineup.users;


import com.dscjss.lineup.users.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Integer>, PagingAndSortingRepository<User, Integer> {
    User findByUsername(String user);

    Optional<User> findByUniqueCode(String code);

    @Query(value = "SELECT IF((SELECT team_id FROM user WHERE user.id = ?1) = (SELECT team_id FROM user WHERE user.id = ?2), TRUE, FALSE) AS ans", nativeQuery = true)
    boolean areTeamMembers(int userId1, int userId2);


    /*@Query(value = "")
    void updateScores();*/
}