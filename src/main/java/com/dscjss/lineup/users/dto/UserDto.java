package com.dscjss.lineup.users.dto;


import com.dscjss.lineup.validation.annotation.PasswordMatches;

import javax.validation.constraints.*;

@PasswordMatches
public class UserDto {


    @Pattern(regexp = "^[A-Za-z0-9_]+$", message = "{username.error.special_characters}")
    @NotNull
    @NotEmpty(message = "{field.error.empty}")
    private String username;

    @Pattern(regexp = "^[^\\s]+$", message = "{password.error.white_spaces}")
    @NotNull(message = "{password.error.invalid}")
    @Size(min = 6, max = 18, message = "{password.error.size}")
    @NotEmpty(message = "{field.error.empty}")
    private String password;

    @NotNull
    @NotEmpty(message = "{field.error.empty}")
    private String matchingPassword;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

}
