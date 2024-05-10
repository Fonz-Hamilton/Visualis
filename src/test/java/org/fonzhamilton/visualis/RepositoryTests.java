package org.fonzhamilton.visualis;

import lombok.extern.slf4j.Slf4j;
import org.fonzhamilton.visualis.model.Data;
import org.fonzhamilton.visualis.model.DataInfo;
import org.fonzhamilton.visualis.model.Role;
import org.fonzhamilton.visualis.model.User;
import org.fonzhamilton.visualis.repository.DataInfoRepository;
import org.fonzhamilton.visualis.repository.DataRepository;
import org.fonzhamilton.visualis.repository.RoleRepository;
import org.fonzhamilton.visualis.repository.UserRepository;
import org.fonzhamilton.visualis.service.RoleService;
import org.fonzhamilton.visualis.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest

class RepositoryTests {

    @Autowired
    DataInfoRepository dataInfoRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    DataRepository dataRepository;
    @Autowired
    RoleService roleService;
    @Autowired
    UserService userService;

    @Test
    public void testDataInfoRepositoryExistsByName() {

        String name = "testName";

        DataInfo dataInfo = new DataInfo();
        dataInfo.setName(name);
        dataInfoRepository.save(dataInfo);
        boolean exists = dataInfoRepository.existsByName(name);

        assertTrue(exists, "Data of name should exist");
    }

    @Test
    public void testFindDataByName() {
        String nameToSearch = "YourDataName";
        Data testData = new Data();
        testData.setName(nameToSearch);
        dataRepository.save(testData);


        Data foundData = dataRepository.findDataByName(nameToSearch);

        assertNotNull(foundData, "Data should be found");
        assertEquals(nameToSearch, foundData.getName(), "Names should match");
    }

    @Test
    public void testFindDataById() {

        Data testData = new Data();
        testData.setId(1L); // setting the id as a long for testing
        // Future me asks why wouldnt it be a long?
        testData.setName("TestName");
        dataRepository.save(testData);


        List<Data> foundDataList = dataRepository.findDataById(1L);


        assertFalse(foundDataList.isEmpty(), "Data should be found");
        assertEquals(1, foundDataList.size(), "Only one data entry should be found");
        assertEquals("TestName", foundDataList.get(0).getName(), "Names should match");
    }

    @Test
    public void testFindRoleByName() {

        String roleName = "test";
        Role testRole = new Role();
        testRole.setName(roleName);
        roleRepository.save(testRole);

        Role foundRole = roleRepository.findRoleByName(roleName);

        assertNotNull(foundRole, "Role is found");
        assertEquals(roleName, foundRole.getName(), "Role names should match");
    }

    @Test
    public void testFindRoleByUser() {

        // id 1 should be admin
        long roleId = 1L;
        // roles are list in case users with multiple roles are needed
        List<Role> foundRole = roleRepository.findRoleByUser(roleId);

        assertEquals(foundRole.get(0).getName(), "ROLE_ADMIN", "Role should match");
    }

    @Test
    public void testFindUserByEmail() {

        String email = "testFind@example.com";
        User testUser = new User();
        testUser.setEmail(email);
        userRepository.save(testUser);


        User foundUser = userRepository.findUserByEmail(email);

        assertNotNull(foundUser, "User should be found");
        assertEquals(email, foundUser.getEmail(), "User emails should match");
    }

    @Test
    public void testFindUserByUserName() {

        String userName = "testUser";
        User testUser = new User();
        testUser.setUserName(userName);
        userRepository.save(testUser);

        User foundUser = userRepository.findUserByUserName(userName);

        assertNotNull(foundUser, "User should be found");
        assertEquals(userName, foundUser.getUserName(), "User names are supposed to match!!");
    }

    @Test
    public void testUserExistsByEmail() {

        String email = "test@example.com";
        User testUser = new User();
        testUser.setEmail(email);
        userRepository.save(testUser);

        boolean exists = userRepository.existsByEmail(email);

        assertTrue(exists, "User should exist with that email");
    }

    @Test
    public void testExistsByUserName() {

        String existingEmail = "test2@example.com";
        User testUser = new User();
        testUser.setEmail(existingEmail);
        userRepository.save(testUser);

        boolean exists = userRepository.existsByEmail(existingEmail);

        assertTrue(exists, "User should exist with that email");
    }
}