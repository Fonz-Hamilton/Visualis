package org.fonzhamilton.visualis;

import lombok.extern.slf4j.Slf4j;
import org.fonzhamilton.visualis.model.Role;
import org.fonzhamilton.visualis.repository.DataInfoRepository;
import org.fonzhamilton.visualis.repository.DataRepository;
import org.fonzhamilton.visualis.repository.RoleRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@SpringBootTest

class VisualisApplicationTests {

    @Autowired
    DataInfoRepository dataInfoRepository;
    @Autowired
    RoleRepository roleRepository;

    @BeforeAll
    public static void initializeStuff() {


    }
/*
    @Test
    public void testDataInfoRepositoryExistsByName() {
        log.warn("tesssst");

        if(dataInfoRepository.existsByName("test")) {
            log.warn("This shouldnt work");
        }
        else {
            log.warn("this should work");
        }



        String name = "testName";


        when(dataInfoRepository.existsByName(name)).thenReturn(true);


        boolean exists = dataInfoServiceMock.existsByName(name);


        assertTrue(exists, "Data of name should exist");

        verify(dataInfoRepositoryMock, times(1)).existsByName(name);


    }

 */
    /*

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

        Data testData1 = new Data();
        testData1.setUser(new User()); // Assuming Data has a User association
        testData1.setName("TestName1");
        dataRepository.save(testData1);

        Data testData2 = new Data();
        testData2.setUser(new User()); // Assuming Data has a User association
        testData2.setName("TestName2");
        dataRepository.save(testData2);


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
*/
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
        long roleId = 1l;
        // roles are list in case users with multiple roles are needed
        List<Role> foundRole = roleRepository.findRoleByUser(roleId);

        assertEquals(foundRole.get(0).getName(), "ROLE_ADMIN", "Role should match");

    }

/*
    @Test
    public void testFindUserByEmail() {

        String email = "test@example.com";
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

        String existingEmail = "test@example.com";
        User testUser = new User();
        testUser.setEmail(existingEmail);
        userRepository.save(testUser);


        boolean exists = userRepository.existsByEmail(existingEmail);


        assertTrue(exists, "User should exist with that email");

    }

    @Test
    public void testDataInfoServiceExistsByName() {

        String nonExistentName = "nonexistentName";
        when(dataInfoRepositoryMock.existsByName(nonExistentName)).thenReturn(false);


        boolean exists = dataInfoServiceMock.existsByName(nonExistentName);


        assertFalse(exists, "Data should not exist with the specified name");

        verify(dataInfoRepositoryMock, times(1)).existsByName(nonExistentName);

    }

    @Test
    public void testDataServiceGetDataByName() {

        String existingName = "testName";

        Data testData = new Data();
        testData.setName(existingName);
        when(dataRepositoryMock.findDataByName(existingName)).thenReturn(testData);


        Data result = dataServiceMock.getDataByName(existingName);


        assertNotNull(result, "Data should be found with the specified name");
        assertEquals(existingName, result.getName(), "Data names should match");

        verify(dataRepositoryMock, times(1)).findDataByName(existingName);

    }

    @Test
    public void testRoleServiceFindRoleByRoleName() {

        String roleName = "testRole";



        Role testRole = new Role();
        testRole.setName(roleName);
        when(roleRepositoryMock.findRoleByName(roleName)).thenReturn(testRole);


        Role result = roleServiceMock.findRoleByRoleName(roleName);


        assertNotNull(result, "Role should be found with the specified name");
        assertEquals(roleName, result.getName(), "Role names should match");

        verify(roleRepositoryMock, times(1)).findRoleByName(roleName);

    }



 */


}


