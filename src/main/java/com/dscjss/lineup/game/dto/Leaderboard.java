package com.dscjss.lineup.game.dto;

import com.dscjss.lineup.users.dto.UserBean;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.Page;

public class Leaderboard {

    Page<UserBean> page;

    @JsonProperty("current_user")
    UserBean currentUser;

    public Page<UserBean> getPage() {
        return page;
    }

    public void setPage(Page<UserBean> page) {
        this.page = page;
    }

    public UserBean getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(UserBean currentUser) {
        this.currentUser = currentUser;
    }
}
