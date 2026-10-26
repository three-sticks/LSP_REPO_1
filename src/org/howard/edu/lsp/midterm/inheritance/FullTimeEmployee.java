package org.howard.edu.lsp.midterm.inheritance;

/**
 * A full-time employee. Hours up to 40 per week are paid at the hourly rate;
 * hours beyond 40 are paid as overtime at 1.5 times the hourly rate.
 *
 * @author Robert Hardy
 */
public class FullTimeEmployee extends Employee {

    /** Weekly hours paid at the regular rate. */
    private static final double OVERTIME_THRESHOLD = 40.0;

    /** Multiplier applied to the hourly rate for overtime hours. */
    private static final double OVERTIME_MULTIPLIER = 1.5;

    /**
     * Creates a full-time employee.
     *
     * @param hoursWorked the hours worked in the week (nonnegative)
     * @param hourlyRate  the hourly rate of pay (nonnegative)
     */
    public FullTimeEmployee(double hoursWorked, double hourlyRate) {
        super(hoursWorked, hourlyRate);
    }

    /**
     * Calculates full-time pay: regular pay for the first 40 hours plus
     * overtime pay (1.5 times the rate) for every hour beyond 40.
     *
     * @return the total pay including any overtime
     */
    @Override
    public double calculatePay() {
        double hours = getHoursWorked();
        double rate = getHourlyRate();
        double regularHours = Math.min(hours, OVERTIME_THRESHOLD);
        double overtimeHours = Math.max(0.0, hours - OVERTIME_THRESHOLD);
        return regularHours * rate + overtimeHours * rate * OVERTIME_MULTIPLIER;
    }
}
