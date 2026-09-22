package org.howard.edu.lsp.assignment3;

import java.math.BigDecimal;

public class Employee {
    private final int employeeId;
    private final String name;
    private final String department;
    private final BigDecimal hoursWorked;
    private final BigDecimal hourlyRate;
    private BigDecimal grossPay;
    private String payLevel;
    private String employmentStatus;

    public Employee(int employeeId, String name, String department,
                    BigDecimal hoursWorked, BigDecimal hourlyRate) {
        this.employeeId = employeeId;
        this.name = name;
        this.department = department;
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public BigDecimal getHoursWorked() {
        return hoursWorked;
    }

    public BigDecimal getHourlyRate() {
        return hourlyRate;
    }

    public BigDecimal getGrossPay() {
        return grossPay;
    }

    public String getPayLevel() {
        return payLevel;
    }

    public String getEmploymentStatus() {
        return employmentStatus;
    }

    public void setGrossPay(BigDecimal grossPay) {
        this.grossPay = grossPay;
    }

    public void setPayLevel(String payLevel) {
        this.payLevel = payLevel;
    }

    public void setEmploymentStatus(String employmentStatus) {
        this.employmentStatus = employmentStatus;
    }

    public String toCsvRow() {
        return String.format("%d,%s,%s,%s,%s,%s,%s,%s",
                employeeId,
                name,
                department,
                hoursWorked.setScale(2, java.math.RoundingMode.HALF_UP).toPlainString(),
                hourlyRate.setScale(2, java.math.RoundingMode.HALF_UP).toPlainString(),
                grossPay.setScale(2).toPlainString(),
                payLevel,
                employmentStatus);
    }
}
