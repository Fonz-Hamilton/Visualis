package org.fonzhamilton.visualis.controller;

import org.fonzhamilton.visualis.model.Data;
import org.fonzhamilton.visualis.processor.CsvProcessor;
import org.fonzhamilton.visualis.security.UserPrincipal;
import org.fonzhamilton.visualis.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class DataController {
    @Autowired
    private FileService fileService;

    @Autowired
    private DataService dataService;
    @Autowired
    private DataController dataController;

    @Autowired
    private DataInfoService dataInfoService;

    @Autowired
    private CsvProcessor csvProcessor;

    @PostMapping("/upload")
    public String fileUpload(@RequestParam("file") MultipartFile file,
                                   @RequestParam("description") String description, Model model,
                                   Authentication authentication) {

        try {
            fileService.handleFileUpload(file, description, authentication);
            model.addAttribute("successMessage", "File uploaded successfully!");
        } catch (Exception e) {

            e.printStackTrace();
            model.addAttribute("errorMessage", "Error uploading file");
            log.debug(e.getMessage());
        }

        List<String> nameList = getNames(authentication);
        model.addAttribute("nameList", nameList);

        return "data";
    }


    @GetMapping("/fetchNames")
    @ResponseBody
    public List<String> getNames(Authentication authentication) {

        Long userId = getCurrentUserId(authentication);
        List<Data> data = dataService.getAllDataByUserId(userId);
        List<String> fileNames = new ArrayList<>();
        for(int i = 0; i < data.size(); i++) {
            fileNames.add((data.get(i).getDataInfo().getName()));
        }


        log.debug("File names: {}", fileNames);

        return fileNames;

    }

    @PostMapping("/fetchFileLocation")
    public String getFileLocation(@RequestParam(name = "name", required = false) String name, Model model, Authentication authentication) {
        try {
            if (name == null || name.trim().isEmpty()) {
                // Handle the case where 'name' is empty
                model.addAttribute("errorMessage", "Name cannot be empty");
                return "redirect:/visualize";
            }

            String fileLocation = dataInfoService.findDataInfoByName(name).getSourceLink();

            // Reset the existing data in the model attribute
            model.addAttribute("csvData", null);

            // Process the CSV file using CsvProcessor
            try {
                List<Map<String, String>> csvData = csvProcessor.processCsvFile(fileLocation);
                log.warn("This is after csvProcessor is called");
                // Add headers and data to the model
                model.addAttribute("csvHeaders", !csvData.isEmpty() ? new ArrayList<>(csvData.get(0).keySet()) : Collections.emptyList());
                model.addAttribute("csvData", csvData);
                log.debug("CSV Data: {}", csvData); // Log the data

            }
            catch (IOException e) {
                model.addAttribute("errorMessage", "Error processing CSV file: " + e.getMessage());
                return "data";
            }

            model.addAttribute("fileLocation", fileLocation);  // Set the file location
            model.addAttribute("successMessage", "File location fetched successfully!");
            List<String> nameList = dataController.getNames(authentication);
            model.addAttribute("nameList", nameList);
            model.addAttribute("clearTable", true);

            return "data";
        }
        catch (IncorrectResultSizeDataAccessException e) {
            return "redirect:/visualize";
        }

    }


    private Long getCurrentUserId(Authentication authentication) {
        try {
            if (authentication != null && authentication.getPrincipal() instanceof UserPrincipal) {
                UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
                return userPrincipal.getUser().getId();
            }
        }
        catch(Exception e) {
            return null;
        }
        return null; // im bad at exceptions
    }


}

