package org.fonzhamilton.visualis.util;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Component
public class FileUtil {
    private String newFileName;

    public String saveFile(MultipartFile file) throws IOException {
        // Get the absolute path to the storage directory
        Path storageDirectory = getStorageDirectory();

        // Generate a unique file name to avoid overwriting existing files
        String uniqueFileName = generateUniqueFileName(Objects.requireNonNull(file.getOriginalFilename()));
        setNewFileName(uniqueFileName);

        // Create the absolute path for the new file
        Path filePath = storageDirectory.resolve(uniqueFileName);

        // Save the file to the specified location
        try (InputStream inputStream = file.getInputStream()) {
            FileUtils.copyInputStreamToFile(inputStream, filePath.toFile());
        }

        // Get and Return the relative path to the saved file
        return getRelativePath(filePath);
    }

    /**
     * Get file type using MIME
     * @param file Multipart file that user uploads
     * @return String containing what the type is
     */
    public String getFileType(MultipartFile file) {
        String fileType = file.getContentType();

        // return unknown if empty or null
        if (fileType == null || fileType.isEmpty()) {
            return "unkown";
        }

        // get file type from content type
        String[] parts = fileType.split("/");
        if(parts.length == 2) {
            return parts[1].toLowerCase();
        }

        // Default to an unknown
        return "unknown";
    }

    private Path getStorageDirectory() {
        // get the real path
        String relativePath = FileConstants.STORAGE_DIRECTORY;
        String realPath = System.getProperty("user.dir") + File.separator + relativePath;

        // Return the Path object representing the absolute path
        return Paths.get(realPath);
    }

    private String getRelativePath(Path filePath) {
        // Extract the substring starting from the "storage" directory to the end
        try {
            return Paths.get(getProjectRoot(), FileConstants.STORAGE_DIRECTORY).toString();
        }
        catch (Exception e){
            // Handle the case where storageIndex is out of bounds or gets messed up
            System.err.println("Error: storage got all messed up and stuff");
            return null;
        }
    }
    private String generateUniqueFileName(String originalFilename) {
        // Extract the file extension from the original filename
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));

        // Generate and return a unique filename using UUID
        return UUID.randomUUID().toString() + fileExtension;
    }

    public static String getProjectRoot() {
        return System.getProperty("user.dir");
    }

}
