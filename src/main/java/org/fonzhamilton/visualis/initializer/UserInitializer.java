package org.fonzhamilton.visualis.initializer;
import org.fonzhamilton.visualis.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.fonzhamilton.visualis.service.UserService;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.fonzhamilton.visualis.dto.UserDTO;
import org.fonzhamilton.visualis.service.RoleService;

import java.util.Collections;

/**
 * User Initializer. Initializes an admin user on startup if
 * one does not already exist
 */
@Component
@Slf4j
@DependsOn ("roleInitializer")
public class UserInitializer {

    private final UserService userService;
    private final RoleService roleService;
    @Autowired
    public BCryptPasswordEncoder encoder;

    @Autowired
    public UserInitializer(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void init() {

        createAdminUser();
    }

    private void createAdminUser() {
        // Check if the admin user already exists
        // this is mostly just for testing with create-drop
        User existingAdmin = userService.existsByUserName("admin");
        if (existingAdmin == null) {
            // Create an admin user
            UserDTO admin = new UserDTO();

            admin.setFirstName("admin");
            admin.setLastName("admin");
            admin.setUserName("admin");
            admin.setEmail("admin@visualis.com");

            admin.setPassword("admin"); // obfuscate somehow in real production
                                        // or make way for users to changes password

            // Assign role of admin to the admin user
            admin.setRoles(Collections.singletonList(roleService.findRoleByRoleName("ROLE_ADMIN")));

            // Save the admin user
            userService.create(admin);
        }
    }
}