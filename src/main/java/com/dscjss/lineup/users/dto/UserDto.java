package com.dscjss.lineup.users.dto;


import com.dscjss.lineup.validation.annotation.PasswordMatches;

import javax.validation.constraints.*;

@PasswordMatches
public class UserDto {


    @Pattern(regexp = "^[A-Za-z0-9_]+$", message = "{zeal_id.error.special_characters}")
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

    @NotNull
    @NotEmpty
    @Pattern(regexp = "^[1-9]{1}[0-9]{9}$", message = "{phone.error.invalid}")
    private String phone;

    @NotEmpty
    private String firstName;

    private String lastName;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
