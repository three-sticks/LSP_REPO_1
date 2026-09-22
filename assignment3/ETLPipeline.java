package org.howard.edu.lsp.assignment3;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ETLPipeline {
    public static void main(String[] args) {
        Path inputPath = Paths.get("data/employees.csv");
        Path outputPath = Paths.get("data/transformed_employees.csv");

        try {
            ETLProcessor processor = new ETLProcessor(
                    new EmployeeParser(),
                    new PayrollCalculator());

            ETLResult result = processor.process(inputPath, outputPath);
            result.printSummary();
        } catch (Exception e) {
            System.err.println("ETL pipeline failed: " + e.getMessage());
        }
    }
}
