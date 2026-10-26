package org.howard.edu.lsp.midterm.inheritance;

/**
 * A part-time employee, paid straight hourly pay for all hours worked
 * (no overtime premium).
 *
 * @author Robert Hardy
 */
public class PartTimeEmployee extends Employee {

    /**
     * Creates a part-time employee.
     *
     * @param hoursWorked the hours worked in the pay period (nonnegative)
     * @param hourlyRate  the hourly rate of pay (nonnegative)
     */
    public PartTimeEmployee(double hoursWorked, double hourlyRate) {
        super(hoursWorked, hourlyRate);
    }

    /**
     * Calculates part-time pay as hours worked times the hourly rate.
     *
     * @return the straight hourly pay
     */
    @Override
    public double calculatePay() {
        return getHoursWorked() * getHourlyRate();
    }
}
