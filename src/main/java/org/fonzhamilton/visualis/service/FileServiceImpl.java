package org.fonzhamilton.visualis.service;
import org.fonzhamilton.visualis.dto.DataDTO;
import org.fonzhamilton.visualis.dto.DataInfoDTO;
import org.fonzhamilton.visualis.model.DataInfo;
import org.fonzhamilton.visualis.model.User;
import org.fonzhamilton.visualis.repository.UserRepository;
import org.fonzhamilton.visualis.security.UserPrincipal;
import org.fonzhamilton.visualis.util.FileDuplicateChecker;
import org.fonzhamilton.visualis.util.FileUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

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
    private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    public void handleFileUpload(MultipartFile file, String description, Authentication authentication) {
        // extract user information from authentication
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        User user = userPrincipal.getUser();

        user = userRepository.findById(user.getId()).orElse(null);  // defaults to null if Optional<User> is null
        try {
            // save the file using FileUtil
            String filePath = fileUtil.saveFile(file);
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            fileName = fileDuplicateChecker.checker(fileName);

            String realFileName = fileUtil.getNewFileName();    // gets the weird UID generated name, so it can be grabbed

            // create DataInfoDTO with file information
            DataInfoDTO dataInfoDTO = new DataInfoDTO();
            dataInfoDTO.setSourceLink(filePath + File.separator + realFileName);
            log.debug("SourcePath for file {}", dataInfoDTO.getSourceLink());
            dataInfoDTO.setName(fileName);
            dataInfoDTO.setDataType(fileUtil.getFileType(file));
            dataInfoDTO.setDescription(description);

            // Create DataInfo entity
            DataInfo savedDataInfoDTO = dataInfoService.createDataInfo(dataInfoDTO);

            // Create Data entity
            DataDTO dataDTO = new DataDTO();
            dataDTO.setDataInfo(savedDataInfoDTO);
            dataDTO.setName(fileName);
            dataDTO.setUser(user);

            // User null shouldn't happen but just in case it does
            if(user != null) {
                dataService.createData(dataDTO, user.getId());
            }
            else {
                throw new NullPointerException("User is null, that's bad");
            }

        }
        catch (IOException e) {
            // Handle exception
            logger.error("An error occurred while handling file I/O", e);
        }

    }

}
