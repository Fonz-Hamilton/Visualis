package org.fonzhamilton.visualis.util;

import org.fonzhamilton.visualis.service.DataInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
