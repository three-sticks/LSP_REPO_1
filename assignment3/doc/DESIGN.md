# Assignment #3 Design Discussion

## Assignment #2 Design

My solution for Assignment #2 was more procedural, focused on the ETL process. The main program read the input file, parsed the CSV fields, validated the values, calculated payroll information and wrote the transformed CSV file to the input. The duties were mostly taken care of from the main execution flow of the pipeline, rather than by separate objects.

## Assignment #3 Design Changes

For Assignment #3 I broke the program into several classes with specific responsibilities:

- `ETLPipeline` is responsible for starting the program and providing the relative file paths.`ETLProcessor` is responsible for coordinating the Extract, Transform and Load steps and contains the run results.
- `EmployeeParser` reads a single CSV row, trims the fields, converts the employee name to uppercase, validates the numeric values and creates an `Employee` object.
- `Employee` holds the employee data and the transformed payroll results. It also returns employee's CSV representation.
- `PayrollCalculator` handles overtime pay, IT bonus, GrossPay rounding, PayLevel and EmploymentStatus
- `ETLResult` contains the run summary values and generates the necessary console summary.

## Why These Changes Are an Improvement

The main improvement is that each class has its own responsibility, instead of having the whole ETL process concentrated in one main program. That makes the code easier to read, understand, test, and maintain. For example, payroll calculations are separate from the CSV parsing, so changing the payroll rules doesn't mean you have to change the file processing logic.

The `Employee` class implements encapsulation by keeping employee fields private and exposing them via methods. The `ETLPipeline` class has a tiny main method that just wires the objects together and starts the process; the other classes do the work. This demonstrates a more meaningful object-oriented design, while maintaining all functional requirements and output from Assignment #2.

## AI / Internet Documentation

No Internet resources were used for the implementation.
