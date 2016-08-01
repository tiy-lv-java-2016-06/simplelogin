package com.theironyard.services;

import com.theironyard.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by jeff on 8/1/16.
 */
public interface UserRepository extends JpaRepository<User, Integer>{
    User findFirstByUsername(String username);
    User findFirstByToken(String token);
}
