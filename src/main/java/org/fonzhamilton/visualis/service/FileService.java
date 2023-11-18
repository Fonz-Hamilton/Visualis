package org.fonzhamilton.visualis.service;
import java.util.List;

import org.fonzhamilton.visualis.dto.DataDTO;
import org.fonzhamilton.visualis.model.Data;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;


public interface FileService {
    public void handleFileUpload(MultipartFile file, String description, Authentication authentication);
}
