## Spring Security Configuration

This project provides a sample configuration for Spring Security in a web application. It ensures secure access to the application by configuring authentication mechanisms and authorization rules.
The configuration includes:

1. **HTTP Basic Authentication**: Users are authenticated using HTTP Basic authentication, ensuring secure access to the application.


2. **Embedded Database:** An embedded H2 database is configured to store user details. The database is initialized with default schema provided by Spring Security.

- **Access to H2 Console**: Access to the H2 database console is permitted, allowing to view and manage the embedded H2 database. Available at the following URL: `http://localhost:8080/h2-console`. No password is required to access the H2 console.
  ```bash
    JDBC URL: jdbc:h2:mem:devicedb
    Username: sa
    Password: (Leave blank)
  ```
- **Sample User Initialization**: Three sample users are initialized with encoded passwords in an embedded H2 database. These users used for testing purposes.
The created users are:
  ```bash
    - Username: user1, Password: user1, Roles: TESTER
    - Username: user2, Password: user2, Roles: TESTER
    - Username: user3, Password: user3, Roles: TESTER
  ```

