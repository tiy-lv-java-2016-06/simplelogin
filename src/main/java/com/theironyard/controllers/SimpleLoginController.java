package com.theironyard.controllers;

import com.theironyard.command.UserCommand;
import com.theironyard.entities.User;
import com.theironyard.exceptions.LoginFailedException;
import com.theironyard.exceptions.UserExistsException;
import com.theironyard.exceptions.UserNotFoundException;
import com.theironyard.services.UserRepository;
import com.theironyard.utilities.PasswordStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * Created by jeff on 8/1/16.
 */
@RestController
public class SimpleLoginController {

    @Autowired
    UserRepository userRepository;

    public User checkLogin(UserCommand command) throws Exception{
        User storedUser = userRepository.findFirstByUsername(command.getUsername());
        if(storedUser == null){
            throw new UserNotFoundException();
        }
        else if(!PasswordStorage.verifyPassword(command.getPassword(), storedUser.getPassword())){
            throw new LoginFailedException();
        }

        return storedUser;
    }

    @RequestMapping(path = "/token", method = RequestMethod.POST)
    public String getToken(@RequestBody UserCommand command) throws Exception {
        User user = checkLogin(command);

        return user.getToken();
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public User createUser(@RequestBody UserCommand command) throws PasswordStorage.CannotPerformOperationException {
        User user = userRepository.findFirstByUsername(command.getUsername());

        if(user != null){
            throw new UserExistsException();
        }

        user = new User(command.getUsername(), PasswordStorage.createHash(command.getPassword()));
        userRepository.save(user);

        return user;
    }

    @RequestMapping(path = "/user", method = RequestMethod.GET)
    public User getUser(@RequestHeader(value = "Authorization") String auth){
        String token = auth.split(" ")[1];
        return userRepository.findFirstByToken(token);
    }

}
