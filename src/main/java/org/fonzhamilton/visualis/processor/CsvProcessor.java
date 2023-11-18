package org.fonzhamilton.visualis.processor;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Processes the CSV data
 *
 */
@Component
public class CsvProcessor {

    public List<Map<String, String>> processCsvFile(String fileLocation) throws IOException {
        List<Map<String, String>> csvData = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileLocation))) {
            String[] headers = reader.readLine().split(",");

            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                Map<String, String> row = new LinkedHashMap<>();

                for (int i = 0; i < headers.length; i++) {
                    row.put(headers[i], values[i]);
                }

                csvData.add(row);
            }
        }

        return csvData;
    }
}
