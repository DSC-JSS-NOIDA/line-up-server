package com.dscjss.lineup.game.dto;

import com.dscjss.lineup.users.dto.UserBean;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.Page;

import java.util.List;

public class Leaderboard {

    @JsonProperty("users")
    private List<UserBean> userBeanList;

    @JsonProperty("current_user")
    private UserBean currentUser;

    public List<UserBean> getUserBeanList() {
        return userBeanList;
    }

    public void setUserBeanList(List<UserBean> userBeanList) {
        this.userBeanList = userBeanList;
    }

    public UserBean getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(UserBean currentUser) {
        this.currentUser = currentUser;
    }
}
