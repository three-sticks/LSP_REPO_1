package org.howard.edu.lsp.midterm.inheritance;

/**
 * Abstract base class for all employee types.
 * 
 * An Employee knows the hours worked and the hourly rate; each concrete
 * subclass overrides {@link #calculatePay()} to apply its own pay rules.
 * Callers can hold any employee as an {@code Employee} and simply call
 * {@code calculatePay()}, so no conditional logic on the employee type would be needed.
 * 
 *
 * @author Robert Hardy
 */
public abstract class Employee {

    /** Hours worked in the pay period (assumed nonnegative). */
    private final double hoursWorked;

    /** Hourly pay rate (assumed nonnegative). */
    private final double hourlyRate;

    /**
     * Creates an employee.
     *
     * @param hoursWorked the hours worked in the pay period (nonnegative)
     * @param hourlyRate  the hourly rate of pay (nonnegative)
     */
    public Employee(double hoursWorked, double hourlyRate) {
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
    }

    /**
     * Returns the hours worked.
     *
     * @return the hours worked in the pay period
     */
    public double getHoursWorked() {
        return hoursWorked;
    }

    /**
     * Returns the hourly rate.
     *
     * @return the hourly rate of pay
     */
    public double getHourlyRate() {
        return hourlyRate;
    }

    /**
     * Calculates this employee's pay according to the rules of the employee type.
     *
     * @return the total pay for the period
     */
    public abstract double calculatePay();
}
