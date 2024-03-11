### Building and running application

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