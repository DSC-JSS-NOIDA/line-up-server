package com.dscjss.lineup.users;


import com.dscjss.lineup.location.model.RecentLocation;
import com.dscjss.lineup.users.dto.UserBean;
import com.dscjss.lineup.users.dto.UserDto;
import com.dscjss.lineup.users.exception.UserExistsException;
import com.dscjss.lineup.users.model.User;

import java.util.List;

public interface UserService {

    UserBean registerNewUserAccount(UserDto userDto) throws UserExistsException;

    void createAdmin();

    UserBean getUserByUsername(String username, boolean onlySummary);
}
