package org.fonzhamilton.visualis.service;

import org.fonzhamilton.visualis.dto.UserDTO;
import org.fonzhamilton.visualis.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    public UserDetails loadUserByUsername(String userName);
    public void create(UserDTO userDTO);
    public User findUserByEmail(String email);
    public User findUserByName(String name);
    public User existsByUserName(String name);
    //public void updateUser(UserDTO userDTO);
}

