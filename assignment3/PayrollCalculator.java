package org.howard.edu.lsp.assignment3;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PayrollCalculator {
    private static final BigDecimal FORTY = new BigDecimal("40.00");
    private static final BigDecimal ONE_POINT_FIVE = new BigDecimal("1.5");
    private static final BigDecimal BONUS = new BigDecimal("1.05");

    public void calculate(Employee employee) {
        BigDecimal hours = employee.getHoursWorked();
        BigDecimal rate = employee.getHourlyRate();

        BigDecimal pay;
        if (hours.compareTo(FORTY) <= 0) {
            pay = hours.multiply(rate);
        } else {
            BigDecimal regularPay = FORTY.multiply(rate);
            BigDecimal overtimeHours = hours.subtract(FORTY);
            BigDecimal overtimePay = overtimeHours.multiply(rate).multiply(ONE_POINT_FIVE);
            pay = regularPay.add(overtimePay);
        }

        if (employee.getDepartment().equals("IT")) {
            pay = pay.multiply(BONUS);
        }

        BigDecimal grossPay = pay.setScale(2, RoundingMode.HALF_UP);
        employee.setGrossPay(grossPay);
        employee.setPayLevel(determinePayLevel(grossPay));
        employee.setEmploymentStatus(determineEmploymentStatus(hours));
    }

    private String determinePayLevel(BigDecimal grossPay) {
        BigDecimal fiveHundred = new BigDecimal("500.00");
        BigDecimal oneThousand = new BigDecimal("1000.00");
        BigDecimal twoThousand = new BigDecimal("2000.00");

        if (grossPay.compareTo(fiveHundred) < 0) {
            return "Low";
        }
        if (grossPay.compareTo(oneThousand) < 0) {
            return "Standard";
        }
        if (grossPay.compareTo(twoThousand) < 0) {
            return "High";
        }
        return "Executive";
    }

    private String determineEmploymentStatus(BigDecimal hoursWorked) {
        return hoursWorked.compareTo(new BigDecimal("30.00")) < 0
                ? "Part-Time"
                : "Full-Time";
    }
}
