package org.fonzhamilton.visualis.service;

import java.util.List;

import org.fonzhamilton.visualis.dto.DataInfoDTO;
import org.fonzhamilton.visualis.model.DataInfo;

public interface DataInfoService {
    public DataInfo createDataInfo(DataInfoDTO dataInfoDTO, Long dataId);
    public DataInfo findDataInfoByName(String name);
    public boolean existsByName(String name);
}
