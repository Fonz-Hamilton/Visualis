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
import org.fonzhamilton.visualis.repository.UserRepository;
import org.fonzhamilton.visualis.service.DataInfoService;
import org.fonzhamilton.visualis.service.DataService;
import org.fonzhamilton.visualis.service.RoleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest

class ServiceTests {

    @Autowired
    DataInfoRepository dataInfoRepository;
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