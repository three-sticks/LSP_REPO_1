# Question 2, Part 3 – Evolving Requirements and Design Rationale

## New requirements
1. Registration records may be stored in a **local file or a database**, depending on the deployment environment.
2. Confirmations may be sent by **email or text message**.

## How the Part 2 design accommodates the change

Both changes land on classes that already own that single concern. `RegistrationRepository` owns storage and `ConfirmationSender` owns delivery. Nothing in `Registration`, `TuitionPolicy`, `RegistrationView`, or `ActivityLogger` knows how records are stored or how messages are delivered, so none of them changes.

The two roles become abstractions, each with interchangeable implementations. `RegistrationService` only ever sends the same message (`save(registration)`, `send(registration)`) to whichever implementation it was given, so polymorphism replaces any `if (storage == FILE)` logic. Supporting a future option such as a cloud store or push notification means adding one new class and no edits to existing ones.

## Changes to the CRC cards

| Card | Change |
|---|---|
| `RegistrationRepository` | Becomes an abstraction ("save a registration record"). The Part 2 file behavior moves into a new `FileRegistrationRepository` card; a new `DatabaseRegistrationRepository` card handles database storage. |
| `ConfirmationSender` | Becomes an abstraction ("deliver a confirmation"). Email behavior moves into `EmailConfirmationSender`; a new `TextMessageConfirmationSender` card handles text messages. |
| `Student` | Gains one responsibility: store the student's phone number, which text messages require. |
| `RegistrationConfiguration` (new) | Reads the deployment environment settings, creates the chosen repository and sender, and builds `RegistrationService` with them. Collaborators: RegistrationService, RegistrationRepository, ConfirmationSender. |
| `RegistrationService` | Responsibilities unchanged. It now receives its repository and sender from outside instead of using fixed ones. |
| `Course`, `Registration`, `TuitionPolicy`, `RegistrationView`, `ActivityLogger` | Unchanged. |

Updated cards for the changed classes:

Class: FileRegistrationRepository
Responsibilities:
- Append registration records to a local file
- Close the file safely and report failures

Collaborators: Registration

Class: DatabaseRegistrationRepository
Responsibilities:
- Insert registration records into a database
- Report failures to the caller

Collaborators: Registration

Class: EmailConfirmationSender
Responsibilities:
- Send the confirmation to the student's email address

Collaborators: Registration, Student

Class: TextMessageConfirmationSender
Responsibilities:
- Send the confirmation to the student's phone number as a text message

Collaborators: Registration, Student

Class: RegistrationConfiguration
Responsibilities:
- Read deployment settings
- Choose and create the repository and confirmation sender implementations
- Build and provide a `RegistrationService` wired with those choices

Collaborators: RegistrationService, RegistrationRepository, ConfirmationSender

## Key decision: `RegistrationConfiguration` owns the choice of implementation, not `RegistrationService`

The question is who decides "file or database" and "email or text." I assigned that to `RegistrationConfiguration` and deliberately kept it out of `RegistrationService`, for four reasons:

- **One key abstraction per class (Riel 2.8).** `RegistrationService` has one job: coordinating the registration workflow. Reading deployment settings and instantiating infrastructure is a different concern that changes for different reasons.
- **No dependence on infrastructure (Riel 3.4, 2.7).** If the service checked the environment itself, it would know about every concrete storage and delivery class and depend on all of them. As designed, it depends only on the two abstractions.
- **It avoids recreating the original flaw.** Putting `if (env == DB) ... else ...` inside the service would bring back the explicit case analysis and mixed responsibilities that made `CourseRegistration` hard to extend. Adding a third option would again mean editing the service.
- **Testability and growth.** Because the service receives its collaborators from outside, a test can give it a fake repository and sender with no file, database, or network. A new deployment environment changes only `RegistrationConfiguration`.

If the university later lets each student choose email or text, `Student` would store that preference as data, but it still would not send anything. `ConfirmationSender` would stay the only class responsible for delivery.
