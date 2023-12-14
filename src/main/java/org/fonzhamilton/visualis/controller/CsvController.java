package org.fonzhamilton.visualis.controller;
import lombok.extern.slf4j.Slf4j;
import org.fonzhamilton.visualis.processor.CsvProcessor;
import org.fonzhamilton.visualis.service.DataInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class CsvController {

    private final CsvProcessor csvProcessor;
    @Autowired
    private DataInfoService dataInfoService;
    @Autowired
    private DataController dataController;

    public CsvController(CsvProcessor csvProcessor) {
        this.csvProcessor = csvProcessor;
    }

    @GetMapping("/csvUpload")
    public String showUploadForm() {
        return "csvUpload";
    }

    @PostMapping("/processCsv")
    public String processCsvFile(@RequestParam("file") MultipartFile file,
                                 @RequestParam("name") String name,
                                 @RequestParam("xAxis") String xAxis,
                                 @RequestParam("yAxis") String yAxis,
                                 Model model, Authentication authentication) {

        try {
            // Process the CSV file
            String fileLocation = dataInfoService.findDataInfoByName(name).getSourceLink();

            // Use the result directly without calling getHeaders and getData separately
            List<Map<String, String>> csvData = csvProcessor.processCsvFile(fileLocation);

            // headers extracted from the first row of csvData
            List<String> csvHeaders = !csvData.isEmpty() ? new ArrayList<>(csvData.get(0).keySet()) : Collections.emptyList();

            // Add data to the model for Thymeleaf
            // I dont think this even gets called things have gotten pretty spaghetti 'round here
            model.addAttribute("csvHeaders", csvHeaders);
            log.debug("CSV Data: {}", csvData); // Log the data
            model.addAttribute("csvData", csvData);

            // Add the selected headers to the model
            model.addAttribute("xAxis", xAxis);
            model.addAttribute("yAxis", yAxis);

            List<String> nameList = dataController.getNames(authentication);
            model.addAttribute("nameList", nameList);
            return "csvTable"; // Thymeleaf template name
        } catch (IOException e) {
            // Handle exception
            e.printStackTrace();
            model.addAttribute("errorMessage", "Error processing CSV file");
            return "data"; // Thymeleaf template for error handling
        }
    }
}
