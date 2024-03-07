package org.fonzhamilton.visualis.service;

import org.fonzhamilton.visualis.dto.DataDTO;
import org.fonzhamilton.visualis.model.User;
import org.fonzhamilton.visualis.repository.DataRepository;
import org.fonzhamilton.visualis.model.Data;
import org.fonzhamilton.visualis.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class DataServiceImpl implements DataService{
    private final DataRepository dataRepository;
    private final UserRepository userRepository;

    @Autowired
    public DataServiceImpl(DataRepository dataRepository, UserRepository userRepository) {
        this.dataRepository = dataRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Data createData(DataDTO dataDTO, long id) {

        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Data data = modelMapper.map(dataDTO, Data.class);

        // Set the user for the data
        data.setUser(user);

        return dataRepository.save(data);

    }
    @Override
    @Transactional
    public List<Data> getAllData() {
        return dataRepository.findAll();
    }

    @Override
    @Transactional
    public Data getDataByName(String name) {
        return dataRepository.findDataByName(name);
    }

    @Override
    @Transactional
    public List<Data> getAllDataByUserId(long id) {
        return dataRepository.getAllDataByUserId(id);
    }

}
