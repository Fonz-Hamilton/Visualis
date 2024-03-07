package org.fonzhamilton.visualis;

import lombok.extern.slf4j.Slf4j;
import org.fonzhamilton.visualis.dto.DataDTO;
import org.fonzhamilton.visualis.dto.UserDTO;
import org.fonzhamilton.visualis.model.Data;
import org.fonzhamilton.visualis.model.DataInfo;
import org.fonzhamilton.visualis.model.Role;
import org.fonzhamilton.visualis.model.User;
import org.fonzhamilton.visualis.repository.DataInfoRepository;
import org.fonzhamilton.visualis.repository.DataRepository;
import org.fonzhamilton.visualis.repository.RoleRepository;
import org.fonzhamilton.visualis.repository.UserRepository;
import org.fonzhamilton.visualis.security.UserPrincipal;
import org.fonzhamilton.visualis.service.DataInfoService;
import org.fonzhamilton.visualis.service.DataService;
import org.fonzhamilton.visualis.service.RoleService;
import org.fonzhamilton.visualis.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest

class VisualisApplicationTests {

    @Autowired
    DataInfoRepository dataInfoRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    DataRepository dataRepository;
    @Autowired
    DataService dataService;
    @Autowired
    DataInfoService dataInfoService;
    @Autowired
    RoleService roleService;
    @Autowired
    UserService userService;


    @BeforeAll
    public static void initializeStuff() {


    }

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


    @ParameterizedTest
    @MethodSource("userIdProvider")
    public void testGetAllDataByUserId(Long userId) {
        User user = new User();
        user.setId(userId);
        user.setPassword("testpass");
        user.setFirstName("testFirst");
        user.setLastName("testLast");
        user.setUserName("DataTest" + userId.toString());
        user.setEmail("DataTest" + userId.toString() + "test.com");

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);

        // Persist the user entity
        userRepository.save(user);

        // Retrieve the persisted user entity
        user = userRepository.findUserById(userId);

        // Create a DataDTO object
        DataDTO dataDTO = new DataDTO();
        dataDTO.setName("testdata");
        dataDTO.setUser(user);

        // Persist the DataDTO object
        dataService.createData(dataDTO, user.getId());

        // Create a DataDTO object
        DataDTO dataDTO2 = new DataDTO();
        dataDTO2.setName("testdata");
        dataDTO2.setUser(user);

        // Persist the DataDTO object
        dataService.createData(dataDTO2, user.getId());

        List<Data> foundDataList = dataRepository.getAllDataByUserId(userId);

        assertEquals(2, foundDataList.size(), "Two data entries should be found");

    }

    // Helper method
    private static Stream<Arguments> userIdProvider() {
        return Stream.of(
                Arguments.of(1L),
                Arguments.of(2L),
                Arguments.of(3L)
        );
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

    @Test
    public void testDataInfoServiceExistsByName() {

        String name = "nonexistentName";

        boolean exists = dataInfoService.existsByName(name);

        assertFalse(exists, "Data should not exist with the specified name");

        name = "existingName";
        DataInfo dataInfo = new DataInfo();
        dataInfo.setName(name);
        dataInfoRepository.save(dataInfo);

        exists = dataInfoService.existsByName(name);

        assertTrue(exists, "Data should exist by that name");

    }


    @Test
    public void testDataServiceGetDataByName() {

        String existingName = "testName2";

        Data testData = new Data();
        testData.setName(existingName);
        dataRepository.save(testData);

        Data result = dataService.getDataByName(existingName);

        assertNotNull(result, "Data should be found with the specified name");
        assertEquals(existingName, result.getName(), "Data names should match");


    }

    @Test
    public void testRoleServiceFindRoleByRoleName() {

        String roleName = "ROLE_ADMIN";

        Role result = roleService.findRoleByRoleName(roleName);

        assertNotNull(result, "Role should be found with the specified name");
        assertEquals(roleName, result.getName(), "Role names should match");

    }

}
