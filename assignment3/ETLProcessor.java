package org.howard.edu.lsp.assignment3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ETLProcessor {
    private static final String HEADER =
            "EmployeeID,Name,Department,HoursWorked,HourlyRate,GrossPay,PayLevel,EmploymentStatus";

    private final EmployeeParser parser;
    private final PayrollCalculator calculator;

    public ETLProcessor(EmployeeParser parser, PayrollCalculator calculator) {
        this.parser = parser;
        this.calculator = calculator;
    }

    public ETLResult process(Path inputPath, Path outputPath) throws IOException {
        List<Employee> employees = new ArrayList<>();
        int rowsRead = 0;
        int rowsSkipped = 0;

        try (BufferedReader reader = Files.newBufferedReader(inputPath)) {
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                rowsRead++;
                Employee employee = parser.parse(line);

                if (employee == null) {
                    rowsSkipped++;
                    continue;
                }

                calculator.calculate(employee);
                employees.add(employee);
            }
        }

        writeOutput(outputPath, employees);
        return new ETLResult(rowsRead, employees.size(), rowsSkipped, outputPath.toString());
    }

    private void writeOutput(Path outputPath, List<Employee> employees) throws IOException {
        Path parent = outputPath.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }

        try (BufferedWriter writer = Files.newBufferedWriter(outputPath)) {
            writer.write(HEADER);
            writer.newLine();

            for (Employee employee : employees) {
                writer.write(employee.toCsvRow());
                writer.newLine();
            }
        }
    }
}
