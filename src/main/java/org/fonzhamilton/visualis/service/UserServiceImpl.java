package org.fonzhamilton.visualis.service;

import org.fonzhamilton.visualis.exception.DuplicateUserException;
import org.fonzhamilton.visualis.repository.UserRepository;
import org.fonzhamilton.visualis.dto.UserDTO;
import org.fonzhamilton.visualis.model.Role;
import org.fonzhamilton.visualis.security.UserPrincipal;
import org.fonzhamilton.visualis.model.User;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;


@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    public BCryptPasswordEncoder encoder;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findUserByEmail(email);

        if (user == null) {
            log.debug("Invalid username or password {}", email);

            throw new UsernameNotFoundException("Invalid username or password.");
        }

        return new UserPrincipal(user, roleService.getRolesByUser(user.getId()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }


    /**
     * @param userDTO
     */
    @Transactional
    public void create(UserDTO userDTO) {
        String email = userDTO.getEmail();
        String userName = userDTO.getUserName();

        if (userRepository.existsByEmail(email) && userRepository.existsByUserName(userName)) {
            throw new DuplicateUserException("Email already exists", "Username already exists");
        }
        else if (userRepository.existsByEmail(email)) {
            throw new DuplicateUserException("Email already exists", null);
        }
        else if (userRepository.existsByUserName(userName)) {
            throw new DuplicateUserException(null, "Username already exists");
        }

        else {

            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            User user = modelMapper.map(userDTO, User.class);

            // check to see if email already exists

            user.setPassword(encoder.encode(user.getPassword()));
            if(userDTO.getEmail().equals("admin@visualis.com")) {
                user.setRoles(Arrays.asList(roleService.findRoleByRoleName("ROLE_ADMIN")));
            }
            else {
                user.setRoles(Arrays.asList(roleService.findRoleByRoleName("ROLE_USER")));
            }


            log.debug("Roles assigned to user: {}", user.getRoles());
            System.out.println("Roles assigned to user: {}" + user.getRoles());

            userRepository.save(user);
        }
    }

    public User findUserByEmail(String email)
    {
        return userRepository.findUserByEmail(email);
    }

    public User findUserByName(String name)
    {
        return userRepository.findUserByUserName(name);
    }
    public User existsByUserName(String name) {
        return userRepository.findUserByUserName(name);
    }

}
