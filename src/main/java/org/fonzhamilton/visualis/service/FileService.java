package org.fonzhamilton.visualis.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;


public interface FileService {
    public void handleFileUpload(MultipartFile file, String description, Authentication authentication);
}
