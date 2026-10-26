# Question 2, Part 2 – Improved Design Using CRC Cards

The redesign gives each key abstraction its own class (Riel 2.8), keeps data private with its behavior (Riel 2.1, 2.9), and keeps the model independent of console, file, and email details (Riel 3.4). `RegistrationService` only coordinates; it does not compute tuition, format records, or print anything itself, so it is not a god class (Riel 3.2).

Design note: `RegistrationRepository` and `ConfirmationSender` are named for their *role*, not their technology, because the first version writes to a local file and sends email but the role should not be tied to either.

---

Class: Student
Responsibilities:
- Store the student's ID, name, and email
- Provide that information to other classes
- Validate that required values are present

Collaborators: None

---

Class: Course
Responsibilities:
- Store the course code and credit hours
- Provide that information to other classes
- Validate that the credit hours are positive

Collaborators: None

---

Class: Registration
Responsibilities:
- Represent one student's enrollment in one course
- Hold the student, the course, the tuition charged, and the time of registration
- Provide read-only access to this information

Collaborators: Student, Course

---

Class: TuitionPolicy
Responsibilities:
- Own the pricing rules: rate per credit hour, heavy-load threshold (12 credit hours), and discount (10%)
- Calculate tuition for a given number of credit hours
- Apply the heavy-load discount when the threshold is met

Collaborators: Course

---

Class: RegistrationService
Responsibilities:
- Coordinate the registration workflow for a student and a course
- Create the Registration and ask TuitionPolicy for the tuition
- Ask the repository to save the record, then the sender, view, and logger to report it
- Stop the workflow and report an error if saving fails, so success is never reported after a failure

Collaborators: Student, Course, Registration, TuitionPolicy, RegistrationRepository, ConfirmationSender, RegistrationView, ActivityLogger

---

Class: RegistrationRepository
Responsibilities:
- Save a registration record
- Hide all storage details (file name, record format, resource handling) from other classes
- Report storage failures to the caller instead of swallowing them
- First version: append records to a local file

Collaborators: Registration

---

Class: ConfirmationSender
Responsibilities:
- Deliver a confirmation of a registration to the student
- Hide all delivery details from other classes
- First version: send the confirmation by email

Collaborators: Registration, Student

---

Class: RegistrationView
Responsibilities:
- Display the registration summary (student name, course, tuition) to the user
- Own all console formatting so the model never prints

Collaborators: Registration

---

Class: ActivityLogger
Responsibilities:
- Record that a registration was processed, with a timestamp

Collaborators: Registration
