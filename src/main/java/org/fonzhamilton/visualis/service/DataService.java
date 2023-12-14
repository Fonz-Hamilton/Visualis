package org.fonzhamilton.visualis.service;

import java.util.List;

import org.fonzhamilton.visualis.dto.DataDTO;
import org.fonzhamilton.visualis.model.Data;

public interface DataService {

    public Data createData(DataDTO data, long id);
    public List<Data> getAllData();
    public Data getDataByName(String name);
    public List<Data> getAllDataByUserId(long id);
}
