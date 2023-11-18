package org.fonzhamilton.visualis.service;

import lombok.extern.slf4j.Slf4j;
import org.fonzhamilton.visualis.dto.DataDTO;
import org.fonzhamilton.visualis.dto.DataInfoDTO;
import org.fonzhamilton.visualis.dto.UserDTO;
import org.fonzhamilton.visualis.model.DataInfo;
import org.fonzhamilton.visualis.model.User;
import org.fonzhamilton.visualis.repository.UserRepository;
import org.fonzhamilton.visualis.security.UserPrincipal;
import org.fonzhamilton.visualis.util.FileDuplicateChecker;
import org.fonzhamilton.visualis.util.FileUtil;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

@Service
@Slf4j
public class FileServiceImpl implements FileService {

    @Autowired
    private DataInfoService dataInfoService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DataService dataService;

    @Autowired
    private FileUtil fileUtil;  // Inject the FileUtil bean
    @Autowired
    private FileDuplicateChecker fileDuplicateChecker;

    public void handleFileUpload(MultipartFile file, String description, Authentication authentication) {
        // extract user information from authentication
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        User user = userPrincipal.getUser();

        user = userRepository.findById(user.getId()).orElse(null);
        try {
            // save the file using FileUtil
            String filePath = fileUtil.saveFile(file);
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            fileName = fileDuplicateChecker.checker(fileName);

            String realFileName = fileUtil.getNewFileName();    // gets the weird UID generated name, so it can be grabbed

            // create DataInfoDTO with file information
            DataInfoDTO dataInfoDTO = new DataInfoDTO();
            dataInfoDTO.setSourceLink(filePath + "\\" + realFileName);
            log.debug("SourcePath for file {}", dataInfoDTO.getSourceLink());
            dataInfoDTO.setName(fileName);
            dataInfoDTO.setDataType(fileUtil.getFileType(file));
            dataInfoDTO.setDescription(description);

            // Create DataInfo entity
            DataInfo savedDataInfoDTO = dataInfoService.createDataInfo(dataInfoDTO, user.getId());

            // Create Data entity
            DataDTO dataDTO = new DataDTO();
            dataDTO.setDataInfo(savedDataInfoDTO);
            dataDTO.setName(fileName);
            dataDTO.setUser(user);

            dataService.createData(dataDTO, user.getId());
        }
        catch (IOException e) {
            // Handle exception
            e.printStackTrace();
        }

    }

}
