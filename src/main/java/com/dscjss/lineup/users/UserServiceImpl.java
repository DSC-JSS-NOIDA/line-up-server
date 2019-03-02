package com.dscjss.lineup.users;

import com.dscjss.lineup.users.dto.UserBean;
import com.dscjss.lineup.users.dto.UserDto;
import com.dscjss.lineup.users.exception.UserExistsException;
import com.dscjss.lineup.users.model.Role;
import com.dscjss.lineup.users.model.User;
import com.dscjss.lineup.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    private RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public UserBean registerNewUserAccount(UserDto userDto) throws UserExistsException {

        if (usernameExists(userDto.getUsername())) {
            throw new UserExistsException("There is an account with the given details: " + userDto.getUsername());
        }

        User user = createUser(userDto);
        userRepository.save(user);
        return Mapper.getUserBean(user);
    }

    private boolean usernameExists(String username) {
        User user = userRepository.findByUsername(username);
        return user != null;
    }

    private User createUser(UserDto userDto){
        User user = new User();
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setUsername(userDto.getUsername());
        user.setUniqueCode(generateUniqueToken(userDto));
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        Role userRole = roleRepository.findByName("ROLE_USER");
        user.setRoles(new HashSet<>(Arrays.asList(userRole)));
        user.setPhone(userDto.getPhone());
        user.setCreatedAt(Instant.now());
        return user;
    }

    private String generateUniqueToken(UserDto userDto) {
        return userDto.getUsername();
    }


    @Override
    @Transactional
    public void createAdmin() {
        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
        User admin = userRepository.findByUsername("admin");
        if(admin == null){
            User user = new User();
            user.setUsername("admin");
            user.setPassword(passwordEncoder.encode("admin123"));
            user.setRoles(new HashSet<>(Arrays.asList(adminRole)));
            userRepository.save(user);
        }
    }

    @Override
    public UserBean getUserByUsername(String username) {
        return Mapper.getUserBean(userRepository.findByUsername(username));
    }


}