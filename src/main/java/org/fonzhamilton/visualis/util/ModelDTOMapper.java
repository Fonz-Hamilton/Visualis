package org.fonzhamilton.visualis.util;
import org.fonzhamilton.visualis.model.User;
import org.fonzhamilton.visualis.model.Data;
import org.fonzhamilton.visualis.model.DataInfo;
import org.fonzhamilton.visualis.dto.UserDTO;
import org.fonzhamilton.visualis.dto.DataDTO;
import org.fonzhamilton.visualis.dto.DataInfoDTO;


import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

public class ModelDTOMapper {
    private static ModelMapper modelMapper = new ModelMapper();

    static {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }
    private ModelDTOMapper() {
        // no args constructor
    }

    public static User userDTOToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    public static UserDTO userToUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public static Data dataDTOToData(DataDTO dataDTO) {
        return modelMapper.map(dataDTO, Data.class);
    }

    public static DataDTO dataToDataDTO(Data data) {
        return modelMapper.map(data, DataDTO.class);
    }

    public static DataInfo dataInfoDTOToDataInfo(DataInfoDTO dataInfoDTO) {
        return modelMapper.map(dataInfoDTO, DataInfo.class);
    }

    public static DataInfoDTO dataInfoToDataInfoDTO(DataInfo dataInfo) {
        return modelMapper.map(dataInfo, DataInfoDTO.class);
    }
}
