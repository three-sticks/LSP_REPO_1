package org.howard.edu.lsp.assignment3;

import java.math.BigDecimal;
import java.util.Locale;

public class EmployeeParser {

    public Employee parse(String line) {
        if (line == null || line.trim().isEmpty()) {
            return null;
        }

        String[] fields = line.split(",", -1);
        if (fields.length != 5) {
            return null;
        }

        String employeeIdField = fields[0].trim();
        String name = fields[1].trim().toUpperCase(Locale.ROOT);
        String department = fields[2].trim();
        String hoursField = fields[3].trim();
        String rateField = fields[4].trim();

        try {
            int employeeId = Integer.parseInt(employeeIdField);
            BigDecimal hoursWorked = new BigDecimal(hoursField);
            BigDecimal hourlyRate = new BigDecimal(rateField);

            if (hoursWorked.compareTo(BigDecimal.ZERO) < 0 ||
                    hourlyRate.compareTo(BigDecimal.ZERO) < 0) {
                return null;
            }

            return new Employee(employeeId, name, department, hoursWorked, hourlyRate);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
