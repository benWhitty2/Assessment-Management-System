# Assessment Management Platform
Our platform is a web application that helps teaching staff to manage, grade and release different types of exams and assignments to students in an easy-to-understand manner.

# JDK Version 17

# How to Run

### Windows
```powershell
# Terminal 1 - Backend
cd group_app; .\mvnw.cmd spring-boot:run

# Terminal 2 - Frontend
cd frontend; npm install
npm run dev
```

### Mac / Linux
```bash
# Terminal 1 - Backend
cd group_app && ./mvnw spring-boot:run

# Terminal 2 - Frontend
cd frontend && npm install
npm run dev
```

## Acesss Database: http://localhost:8080/h2-console/login
JDBC URL: jdbc:h2:file:./data/trackerdb
Username: sa
Password: somepassword

The H2 console is enabled for the benefit of the markers, so that they are able to see the inner workings of the database. We would not typically include this.

# Example Users

### Exams Officer

Username: admin
Password: password?

### Teaching Support Team

Username: support
Password: password?

### Academic Staff

Username: academic
Password: password?

### External Examiner

Username: external
Password: password?

# Testing

### Authentication & User Management

**LoginTests** (Integration Tests)
- `testValidLogin` - Validates successful login with correct credentials, returns JWT token and user details
- `testInvalidLogin` - Ensures unauthorized access with wrong password
- Uses `MockMvc` with HTTP Basic authentication to test `/auth/login` endpoint

**UserTests** (Unit & Integration Tests)
- `testNameIsNotNull` - Username validation
- `testUsernameIsUnique` - Ensures no duplicate usernames in database
- `testUsernameIsValid` - Username must be ≥3 characters
- `testEmailIsValid` - Email must contain "@" symbol
- `testPasswordIsNotNull` - Password presence validation
- `testPasswordIsHashedCorrectly` - Verifies bcrypt password encoding
- `testUserRoleAssignment` - Role assignment validation (ROLE_USER, ROLE_ADMIN)
- `testSettersAndGetters` - Accessor methods validation
- `endToEndCreateUser` - Full user signup flow with JWT token generation

### Module Management

**ModuleTests** (Unit Tests with Mocked Repository)
- `testModuleNameIsNotNull` - Name field validation
- `testModuleCodeIsUnique` - Duplicate ID constraint enforcement
- `testAssesmentsLoadForModule` - Lazy-loaded assessments relationship
- `testModuleSettersAndGetters` - Basic POJO validation
- `endToEndCreateAndDeleteModule` - Full CRUD lifecycle (create → verify → delete)

### Assessment Hierarchy

**AssessmentTests** (Abstract Base Class)
- Shared test logic for all assessment types (Coursework, Exam, Test)
- `testGeneralSettersAndGetters` - Common fields (id, name, releasedToStudents, checker, setter)
- `testAssessmentNameIsNotNull` - Name validation
- `testReleasedToStudentsIsNotNull` - Release status validation
- `testAssessmentIdIsUnique` - Primary key constraint
- `testModuleLoadForAssesments` - AssessmentModule linking table relationship
- `deleteAssessment` - Soft/hard delete verification

**CourseworkTests** (Extends AssessmentTests)
- `testSpecificSettersAndGetters` - Deadline and specification fields
- `testDeadlineIsNotNull` - Deadline required validation
- `testSpecificationIsNotNull` - Specification document validation

**ExamTests** (Extends AssessmentTests)
- `testSpecificSettersAndGetters` - Duration, startTime, ongoing status
- `testDurationIsNotNull` - Exam duration validation
- `testStartTimeIsNotNull` - Start time validation
- `testOngoingIsNotNull` - Active exam flag validation

**TestTests** (Extends AssessmentTests)
- `testSpecificSettersAndGetters` - Duration, startTime, ongoing, autograded
- `testDurationIsNotNull` - Test duration validation
- `testStartTimeIsNotNull` - Start time validation
- `testOngoingIsNotNull` - Active test flag validation
- `testAutogradedIsNotNull` - Auto-grading capability flag

### Linking Tables

**AssessmentModuleTests** (Many-to-Many Relationship)
- `testAssessmentModuleCodeIsUnique` - Composite key uniqueness
- `testAssessmentLoads` - Assessment side of relationship
- `testModuleLoads` - Module side of relationship
- `testAssessmentModuleSettersAndGetters` - Join table fields
- `endToEndCreateAndDeleteModule` - Full relationship lifecycle

**ModuleStaffTests** (Many-to-Many Relationship)
- `testModuleStaffCodeIsUnique` - Composite key uniqueness
- `testUserLoads` - User (staff) side of relationship
- `testModuleLoads` - Module side of relationship
- `testModuleStaffSettersAndGetters` - Join table fields
- `endToEndCreateAndDeleteModule` - Full relationship lifecycle

## Running Tests
```bash
cd group_app
./mvnw test
``` 

# File Structure

## Frontend (React + Vite)
```
frontend/
├── src/
│   ├── App.jsx
│   ├── AuthedRoutes.jsx
│   ├── main.jsx
│   ├── components/
│   │   ├── AssessmentCard.jsx
│   │   ├── ButtonCompartment.jsx
│   │   ├── Header.jsx
│   │   ├── ModuleBar.jsx
│   │   └── ModuleCard.jsx
│   ├── screens/
│   │   ├── AddAssessmentPage.jsx
│   │   ├── AddModulePage.jsx
│   │   ├── AddUserPage.jsx
│   │   ├── AssessmentProgresserPage.jsx
│   │   ├── InsideModulePage.jsx
│   │   ├── ListOfModulesPage.jsx
│   │   ├── LoginPage.jsx
│   │   └── ProgressPage.jsx
│   └── styles/
│       ├── custom.css
│       └── variables.css
├── index.html
├── package.json
└── vite.config.js
```

## Backend (Spring Boot)
```
group_app/src/
├── main/
│   ├── java/team10/group_app/
│   │   ├── config/
│   │   │   ├── RsaKeyProperties.java
│   │   │   └── SecurityConfig.java
│   │   ├── controller/
│   │   │   ├── AssessmentController.java
│   │   │   ├── AssessmentModuleController.java
│   │   │   ├── AuthController.java
│   │   │   ├── CourseworkController.java
│   │   │   ├── ExamController.java
│   │   │   ├── ModuleController.java
│   │   │   ├── ModuleStaffController.java
│   │   │   ├── TestController.java
│   │   │   └── UserController.java
│   │   ├── domain/
│   │   │   ├── Assessment.java
│   │   │   ├── AssessmentModule.java
│   │   │   ├── Coursework.java
│   │   │   ├── Exam.java
│   │   │   ├── Module.java
│   │   │   ├── ModuleStaff.java
│   │   │   ├── SecurityUser.java
│   │   │   ├── Stage.java
│   │   │   ├── Test.java
│   │   │   └── User.java
│   │   ├── dto/
│   │   │   ├── AssessmentDTO.java
│   │   │   ├── AssessmentModuleDTO.java
│   │   │   ├── CourseworkDTO.java
│   │   │   ├── ExamDTO.java
│   │   │   ├── ModuleDTO.java
│   │   │   ├── ModuleStaffDTO.java
│   │   │   ├── ProgressStepDTO.java
│   │   │   ├── TestDTO.java
│   │   │   ├── TokenDTO.java
│   │   │   ├── UserDTO.java
│   │   │   └── UserSignupDTO.java
│   │   ├── repository/
│   │   │   ├── AssessmentModuleRepository.java
│   │   │   ├── AssessmentRepository.java
│   │   │   ├── CourseworkRepository.java
│   │   │   ├── ExamRepository.java
│   │   │   ├── ModuleRepository.java
│   │   │   ├── ModuleStaffRepository.java
│   │   │   ├── TestRepository.java
│   │   │   └── UserRepository.java
│   │   ├── service/
│   │   │   ├── AssessmentModuleService.java
│   │   │   ├── AssessmentService.java
│   │   │   ├── CourseworkService.java
│   │   │   ├── ExamService.java
│   │   │   ├── ModuleService.java
│   │   │   ├── ModuleStaffService.java
│   │   │   ├── TestService.java
│   │   │   └── UserService.java
│   │   └── GroupAppApplication.java
│   └── resources/
│       ├── application.properties
│       └── certs/
└── test/java/team10/group_app/
    ├── AssessmentModuleTests.java
    ├── AssessmentTests.java
    ├── CourseworkTests.java
    ├── ExamTests.java
    ├── GroupAppApplicationTests.java
    ├── LoginTests.java
    ├── ModuleStaffTests.java
    ├── ModuleTests.java
    ├── TestTests.java
    └── UserTests.java

* File strucutre generated by Claude Sonnet 4.5
* Prompt: Display the frontend and backend as separate strucutures, and only include the src for group app. don't add comments expalining what the files do.
```
# My contributions

I contributed to team 10s work on this project by developing the back end specifically the database and the corresponding data transfer objects as well as many of the tests for these. 

# Development

I created the database schema found in the domain folder and did much of the development on the corresponding dto’s. I considered the applications needs for security, data integrity, data availability,  as well as what data would actually be necessary for this application. 

# Testing
I implemented the tests for the database domain classes ensuring reliable function over any areas essential to operation of the site.

