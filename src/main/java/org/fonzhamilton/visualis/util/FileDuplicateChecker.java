package org.fonzhamilton.visualis.util;

import org.fonzhamilton.visualis.model.DataInfo;
import org.fonzhamilton.visualis.service.DataInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
@Component
public class FileDuplicateChecker {
    private String fileName;

    @Autowired
    DataInfoService dataInfoService;
    public String checker (String name) {
        this.fileName = name;
        if(dataInfoService.existsByName(fileName)) {
            fileName += "_copy";
            checker(fileName);
        }
        else {
            return fileName;
        }
        return fileName;
    }
}
