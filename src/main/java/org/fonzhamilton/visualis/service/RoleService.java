package org.fonzhamilton.visualis.service;

import java.util.List;
import org.fonzhamilton.visualis.model.Role;

public interface RoleService {
    public void saveRole(Role role);
    public Role findRoleByRoleName(String name);
    public List<Role> getAllRoles();
    public List<Role> getRolesByUser(long id);
    public void createRoles();
    public void createRoleIfNotExists(String roleName);
}
