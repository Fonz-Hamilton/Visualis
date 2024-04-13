package org.fonzhamilton.visualis.service;

import org.fonzhamilton.visualis.dto.DataInfoDTO;
import org.fonzhamilton.visualis.repository.DataInfoRepository;
import org.fonzhamilton.visualis.model.DataInfo;
import org.fonzhamilton.visualis.util.ModelDTOMapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataInfoServiceImpl implements DataInfoService {

    private final DataInfoRepository dataInfoRepository;

    @Autowired
    public DataInfoServiceImpl(DataInfoRepository dataInfoRepository) {
        this.dataInfoRepository = dataInfoRepository;
    }
    
    public DataInfo createDataInfo(DataInfoDTO dataInfoDTO) {

        DataInfo dataInfo = ModelDTOMapper.dataInfoDTOToDataInfo(dataInfoDTO);

        return dataInfoRepository.save(dataInfo);
    }

    public List<DataInfo> getAllDataInfo() {
        return dataInfoRepository.findAll();
    }

    public DataInfo findDataInfoByName(String name) {
        return dataInfoRepository.findDataInfoByName(name);
    }
    public boolean existsByName(String name) {
        if(dataInfoRepository.existsByName(name)) {
            return true;
        }
        else {
            return false;
        }
    }
}
