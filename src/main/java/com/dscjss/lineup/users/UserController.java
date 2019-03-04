package com.dscjss.lineup.users;


import com.dscjss.lineup.game.GameDetails;
import com.dscjss.lineup.location.LocationService;
import com.dscjss.lineup.users.dto.UserBean;
import com.dscjss.lineup.users.dto.UserDto;
import com.dscjss.lineup.users.exception.UserExistsException;
import com.dscjss.lineup.users.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.time.Instant;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {


    private final UserService userService;
    private final LocationService locationService;
    private final GameDetails gameDetails;

    @Autowired
    public UserController(UserService userService, MessageSource messageSource, LocationService locationService, GameDetails gameDetails) {
        this.userService = userService;
        this.locationService = locationService;
        this.gameDetails = gameDetails;
    }

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody() @Valid UserDto user, final BindingResult result) {

        Instant now = Instant.now();

        if(gameDetails.getSettings().getSignUpEndTime().isBefore(now)
            || gameDetails.getSettings().getSignUpStartTime().isAfter(now)){
            Map<String, String> map = new HashMap<>();
            map.put("error", "Sign Up request cannot be processed now, contact us through our facebook page for any queries.");
            return ResponseEntity.badRequest().body(map);
        }

        UserBean registered = null;
        boolean userExists = false;
        if (!result.hasErrors()) {
            try {
                registered = createUserAccount(user);
            } catch (UserExistsException e) {
                result.rejectValue("username", "user.error.registered", "User already registered.");
                userExists = true;
            }
        }
        Map<String, String> errors = new HashMap<>();
        for(FieldError error : result.getFieldErrors()){
            errors.put(error.getField(), error.getDefaultMessage());
        }

        if (userExists) {
            errors.clear();
            errors.put("error", "You have already registered for the event.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errors);
        }
        if (result.hasErrors()) {
            for (ObjectError objectError : result.getGlobalErrors()) {
                errors.put("error", objectError.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(registered);
    }

    private UserBean createUserAccount(UserDto userDto) throws UserExistsException {
        return userService.registerNewUserAccount(userDto);
    }

    @GetMapping("/users/me")
    public ResponseEntity user(Principal principal){
        UserBean userBean = userService.getUserByUsername(principal.getName(), false);
        return ResponseEntity.ok().body(userBean);
    }
}
