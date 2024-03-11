## Technologies Used

The Test Device Manager application is built using various technologies and frameworks. Below is an overview of the key technologies and dependencies utilized in the project.


### Spring Boot
- **Description**: A powerful and flexible framework for building Java applications, particularly well-suited for microservices and web applications.

### Spring Boot Starters

### 1. spring-boot-starter-data-jdbc   
- **Description**: Provides support for JDBC-based data access, enabling seamless interaction with the underlying database.

### 2. **spring-boot-starter-security**
  - **Description**: Integrates security features into the application, facilitating authentication and authorization mechanisms.

### 3. **spring-boot-starter-web**
  - **Description**: Simplifies the setup of Spring MVC and RESTful web services, allowing for the development of web-based endpoints.

### 4. **spring-boot-starter-test**
- **Description**: Provides support for testing Spring Boot applications, including utilities for unit testing, integration testing, and mocking dependencies.

### Liquibase

- **Description**: A database schema version control tool that enables tracking and managing database changes across different environments. It ensures consistency and reliability in database schema management.

### H2 Database

- **Description**: A lightweight, in-memory database engine used primarily for development and testing purposes. It provides fast database access without the need for external dependencies.

## Getting Started

### Maven

To get started with the Test Device Manager application, follow these steps:

1. **Download**: Begin by downloading the project to your local machine.

2. **Build the Project**: Navigate to the project directory and build the application using Maven.

    ```bash
    cd test-device-manager
    mvn clean install
    ```

3. **Run the Application**: Once the project is built successfully, run the application using the Spring Boot Maven plugin.

    ```bash
    mvn spring-boot:run
    ```

### Docker
1. **Build Docker Image**: Run the following command in the terminal to build Docker image
   ```bash
    docker build -t test-device-manager .
   ```
2. **Run Docker Container**: Once the image is built, run a Docker container using the following command:
   ```bash
    docker run -p 8080:8080 test-device-manager
   ```

This command initiates a Docker container that runs `test-device-manager` application,
making it accessible via http://localhost:8080.

### Access the Application  
Once the application is running, access it through API client (Ex: Postman) using the appropriate endpoints.


### Access the Database 
Access the H2 in-memory database using the provided H2 console. The console can typically be accessed at http://localhost:8080/h2-console when the application is running.
   ```bash
    JDBC URL: jdbc:h2:mem:devicedb
    Username: sa
    Password: (Leave blank)
   ```
You can use the H2 console to query the database, view tables, and perform other administrative tasks.

By following these steps, you can quickly set up and start using the Test Device Manager application for managing device bookings and waitlists effectively.



