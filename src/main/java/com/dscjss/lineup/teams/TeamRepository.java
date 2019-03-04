package com.dscjss.lineup.teams;

import com.dscjss.lineup.teams.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Integer> {
}
