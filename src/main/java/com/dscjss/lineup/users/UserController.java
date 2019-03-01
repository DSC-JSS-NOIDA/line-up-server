package com.dscjss.lineup.users;


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
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {


    private final UserService userService;
    private final LocationService locationService;

    @Autowired
    public UserController(UserService userService, MessageSource messageSource, LocationService locationService) {
        this.userService = userService;
        this.locationService = locationService;
    }

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody() @Valid UserDto user, final BindingResult result){
        UserBean registered = null;
        if (!result.hasErrors()) {
            try {
                registered = createUserAccount(user);
            } catch (UserExistsException e) {
                result.rejectValue("username","user.error.registered", "User already registered.");
            }
        }

        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for(FieldError error : result.getFieldErrors()){
                errors.put(error.getField(), error.getDefaultMessage());
            }
            for(ObjectError objectError : result.getGlobalErrors()){
                errors.put("user", objectError.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }
        return ResponseEntity.ok().body(registered);
    }

    private UserBean createUserAccount(UserDto userDto) throws UserExistsException {
        return userService.registerNewUserAccount(userDto);
    }

    @GetMapping("/location/my_team")
    public ResponseEntity teamLocation(Principal principal){

        if(principal != null){
            return ResponseEntity.ok().body(locationService.getTeamLocation(new UserBean(principal.getName())));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
