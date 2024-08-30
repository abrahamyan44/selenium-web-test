# Selenium Web Test

This project contains Selenium WebDriver tests for interacting with the PicsArt search images page. The tests verify filter functionality, element interactions, and modal operations using JUnit and WebDriver.

## Project Setup

### Prerequisites

Before running the tests, ensure that you have the following installed:

- **Java Development Kit (JDK)**: Version 8 or above.
- **Maven**: For managing dependencies and running tests.
- **Docker**: For containerized setup using Selenium Grid (see Docker section below).

### Dependencies

This project uses the following dependencies:

- **Selenium WebDriver**
- **JUnit 5**
- **WebDriverManager**
- **Maven** for dependency management

### Installation

1. **Clone the repository**:

   ```bash
   git clone https://github.com/abrahamyan44/selenium-web-test
   cd main
2. Pull Selenium Hub and Node 
    ```bash
    docker pull selenium/hub
    docker pull selenium/node-chrome
    docker pull selenium/node-firefox

3. Start the Selenium Hub container:
   ```bash
   docker run -d -p 4444:4444 --name selenium-hub selenium/hub

4. Start Browser Nodes:
     ```bash
    docker run -d --link selenium-hub:hub -v /dev/shm:/dev/shm selenium/node-chrome
    docker run -d --link selenium-hub:hub -v /dev/shm:/dev/shm selenium/node-firefox

5. Build Maven Project
    ```bash
   mvn clean install

6. Run Maven Tests 
    ```bash
   mvn test
