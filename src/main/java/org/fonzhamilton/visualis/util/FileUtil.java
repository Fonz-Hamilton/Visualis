package org.fonzhamilton.visualis.util;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Component
public class FileUtil {
    private String newFileName;

    public String saveFile(MultipartFile file) throws IOException {
        // Get the absolute path to the storage directory

        Path storageDirectory = getStorageDirectory();

        // Generate a unique file name to avoid overwriting existing files
        String uniqueFileName = generateUniqueFileName(file.getOriginalFilename());
        setNewFileName(uniqueFileName);

        // Create the absolute path for the new file
        Path filePath = storageDirectory.resolve(uniqueFileName);

        // Save the file to the specified location
        Files.copy(file.getInputStream(), filePath);

        // Get the relative path to the saved file
        String relativePath = getRelativePath(filePath);

        // Return the relative path to the saved file
        return relativePath;
    }

    public String getFileType(MultipartFile file) {

        // super basic example. may need to enhance it based on file content or extension
        String originalFilename = file.getOriginalFilename();
        Objects.requireNonNull(originalFilename);

        int lastDotIndex = originalFilename.lastIndexOf('.');
        if (lastDotIndex != -1 && lastDotIndex < originalFilename.length() - 1) {
            return originalFilename.substring(lastDotIndex + 1).toLowerCase();
        }

        // Default to an unknown
        return "unknown";
    }

    private Path getStorageDirectory() {
        // Use the recommended code to get the real path within the project
        String relativePath = FileConstants.STORAGE_DIRECTORY;
        String realPath = System.getProperty("user.dir") + File.separator + relativePath;

        // Return the Path object representing the absolute path
        return Paths.get(realPath);
    }

    private String getRelativePath(Path filePath) {

        // Extract the substring starting from the "storage" directory to the end

        try {

            String storagePath = Paths.get(getProjectRoot(), FileConstants.STORAGE_DIRECTORY).toString();

            // Replace the file separator with "/"
            return storagePath.replace(File.separator, "/");
        } catch (Exception e){
            // Handle the case where storageIndex is out of bounds or gets messed up
            //TODO: take out swears
            System.err.println("Error: storage got all messed up and stuff");
            return null;
        }



    }
    private String generateUniqueFileName(String originalFilename) {
        // Extract the file extension from the original filename
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));

        // Generate a unique filename using UUID
        String uniqueFilename = UUID.randomUUID().toString() + fileExtension;

        return uniqueFilename;
    }

    public static String getProjectRoot() {
        return System.getProperty("user.dir");
    }

    public void setNewFileName(String newName) {
        newFileName = newName;
    }
    public String getNewFileName() {
        return this.newFileName;
    }


}
