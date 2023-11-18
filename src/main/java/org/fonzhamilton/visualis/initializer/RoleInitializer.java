package org.fonzhamilton.visualis.initializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.fonzhamilton.visualis.service.RoleService;
import org.fonzhamilton.visualis.service.RoleServiceImpl;
import jakarta.annotation.PostConstruct;

/**
 * Role Initializer. Creates Roles ROLE_USER
 * and ROLE_ADMIN on startup if not already created
 */
@Component
public class RoleInitializer {

    private RoleService roleService;

    @Autowired
    public RoleInitializer(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostConstruct
    public void init() {
        createRoles();
    }

    private void createRoles() {
        roleService.createRoleIfNotExists("ROLE_ADMIN");
        roleService.createRoleIfNotExists("ROLE_USER");

    }
}
