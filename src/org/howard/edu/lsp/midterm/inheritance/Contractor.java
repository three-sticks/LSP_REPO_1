package org.howard.edu.lsp.midterm.inheritance;

/**
 * A contractor, paid a 15% premium on all hours worked.
 *
 * @author Robert Hardy
 */
public class Contractor extends Employee {

    /** Premium multiplier applied to every hour worked. */
    private static final double CONTRACTOR_PREMIUM = 1.15;

    /**
     * Creates a contractor.
     *
     * @param hoursWorked the hours worked in the pay period (nonnegative)
     * @param hourlyRate  the hourly rate of pay (nonnegative)
     */
    public Contractor(double hoursWorked, double hourlyRate) {
        super(hoursWorked, hourlyRate);
    }

    /**
     * Calculates contractor pay: hours worked times the hourly rate times 1.15.
     *
     * @return the pay including the 15% premium
     */
    @Override
    public double calculatePay() {
        return getHoursWorked() * getHourlyRate() * CONTRACTOR_PREMIUM;
    }
}
