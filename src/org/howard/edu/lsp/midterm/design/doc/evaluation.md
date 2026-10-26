# Question 2, Part 1 – Design Evaluation of `CourseRegistration`

## Overview

`CourseRegistration` looks like a small class, but it is really a procedural script wrapped in a class. A single method, `registerStudent()`, calculates tuition, applies a pricing rule, prints to the console, writes to a file, "sends" an email, and logs, all while reading unprotected public fields. Arthur Riel's object-oriented design heuristics flag nearly every part of it.

## Design Issues

### 1. Public data members break encapsulation
All five fields (`studentId`, `studentName`, `email`, `courseCode`, `creditHours`) are `public`.

- **Riel 2.1 – All data should be hidden within its class.**
- **Riel 2.2 – Users of a class must depend on its public interface, but a class should not depend on its users.**

Any code can set `creditHours = -5` or `email = null` and `registerStudent()` will happily run. The class cannot protect its own invariants, and every caller is coupled to the field layout, so renaming or retyping a field breaks everyone.

### 2. The class models more than one key abstraction
It mixes student data (`studentId`, `studentName`, `email`), course data (`courseCode`, `creditHours`), the registration itself, a pricing policy, persistence, notification, display, and logging.

- **Riel 2.8 – A class should capture one and only one key abstraction.**
- **Riel 2.10 – Spin off non-related information into another class.**

There is no `Student` or `Course` class at all. The real-world concepts are flattened into strings and an int inside one class (**Riel 3.5 – Model the real world whenever possible**).

### 3. God method and non-communicating behavior
Each block of `registerStudent()` uses only a subset of the fields: tuition uses `creditHours`; the file write uses `studentId`, `courseCode`, and `tuition`; the email uses `email`; the confirmation uses `studentName` and `courseCode`.

- **Riel 3.2 – Do not create god classes.**
- **Riel 3.3 – Beware of classes with too much non-communicating behavior.**
- **Riel 2.9 – Keep related data and behavior in one place.**

The pieces share only because they were pasted into one method. Tuition rules, storage, and messaging change for different reasons and at different times, yet here a change to any one of them means editing, retesting, and potentially breaking the same method.

### 4. Domain logic is tied to the user interface and infrastructure
The method calls `System.out.println`, `FileWriter`, and `new Date()` directly, and hard-codes the file name `"registrations.txt"`.

- **Riel 3.4 – The model should never be dependent on the interface; the interface should depend on the model.**
- **Riel 2.7 – A class should use another class only through its public interface (nil or export coupling).**

You cannot unit-test the tuition calculation without also writing to the console and a real file. You cannot reuse the logic in a web app or change where records are stored without rewriting the class.

### 5. Hard-coded business rules and no extension points
The rate (`750.0`), the heavy-load threshold (`12`), and the discount (`0.90`) are magic numbers buried in a method. Adding a database, text-message confirmations, or a new discount means modifying this class, so the design is not open for extension. Because it uses no abstractions or polymorphism, there is nowhere to plug in a new behavior.

### 6. Correctness problems
- **The discount rule is probably wrong.** The class represents one course (`courseCode`, `creditHours`), but "heavy course load" is a property of the student's total credit load across courses. A single course almost never has 12 or more credit hours, so the discount would almost never apply. The data model cannot express the rule it is trying to enforce.
- **Money is stored in `double`.** Binary floating point can produce rounding errors in currency; `BigDecimal` or integer cents should be used.
- **No input validation.** Null fields, negative credit hours, and blank IDs are all accepted.
- **Failures are swallowed.** `catch (Exception e) { e.printStackTrace(); }` hides a failed save, and the method continues to "send" the email and log "Registration processed" even though the record was never stored. The system reports success after a failure.
- **Resource handling is unsafe.** `writer.close()` is skipped if `write()` throws, leaking the file handle. try-with-resources should be used.
- **The email is not actually sent.** It is only a `println`, so the method claims behavior it does not perform.

## Summary

| Observed problem | Riel heuristic(s) | Consequence |
|---|---|---|
| Public fields | 2.1, 2.2 | No invariants; high coupling to data layout |
| Student + course + registration + pricing + I/O in one class | 2.8, 2.10, 3.5 | Poor cohesion; missing abstractions |
| One method does everything | 3.2, 3.3, 2.9 | God class; changes ripple everywhere |
| Console and file I/O inside the model | 3.4, 2.7 | Untestable; not reusable |
| Magic numbers, no abstractions | (open/closed principle) | New requirements force edits to existing code |
| Discount on one course's credits, `double` money, swallowed errors | (correctness) | Wrong results and silent failures |

The redesign in `design.md` fixes these by giving each key abstraction its own class, hiding all data, and moving pricing, persistence, notification, display, and logging into separate collaborators.

*Note: heuristic numbers follow Riel's* Object-Oriented Design Heuristics *(1996). Check them against your lecture notes in case the course uses a different numbering.*
