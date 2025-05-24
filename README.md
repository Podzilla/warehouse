# Warehouse Management Service

## Overview

This service is a warehouse management system designed to handle order and inventory operations.

### Main Features

*   **Stock Management:** Keeps track of inventory levels, item locations, and stock movements.
*   **Order Processing:** Manages the entire order lifecycle, including:
    *   Order assignment to relevant personnel.
    *   Packaging of orders.
*   **User Role Management:** Defines and manages different user roles within the system, such as:
    *   Assigners: Responsible for assigning orders.
    *   Managers: Oversee warehouse operations.
    *   Packagers: Handle the packaging of orders.

### Technologies Used

*   **Programming Language:** Java 23
*   **Framework:** Spring Boot 3.4.5
*   **Build Tool:** Maven
*   **Containerization:** Docker
*   **Message Broker:** RabbitMQ
*   **Database:** PostgreSQL
*   **Caching:** Redis
*   **API Documentation:** Springdoc
*   **Utility Library:** Lombok

## Getting Started

### Prerequisites

- Java JDK 23 or later
- Maven 3.6.x or later
- Docker Engine and Docker Compose (for running with Docker)
- RabbitMQ server (if not using Docker)
- PostgreSQL server (if not using Docker)
- Redis server (if not using Docker)

### Building the Project

To build the project, navigate to the root directory and run the following Maven command:

```bash
./mvnw clean install
```

This command will compile the code, run tests, and package the application.

### Running the Project

#### With Docker Compose

The easiest way to run the project along with its dependencies (PostgreSQL, RabbitMQ, Redis) is by using the provided `docker-compose.yml` file.

Navigate to the root directory and run:

```bash
docker-compose up -d
```

The service will be available at `http://localhost:8080` (or the port configured in `docker-compose.yml` or your environment).

#### Locally (without Docker)

It is also possible to run the service locally without Docker. However, this requires you to set up and configure PostgreSQL, RabbitMQ, and Redis servers separately. You will need to update the connection details for these services in the `src/main/resources/application.yml` file.

Once the external services are running and configured, you can start the application using the following Maven command:

```bash
./mvnw spring-boot:run
```

## Project Structure

The main application code is located in `src/main/java/com/podzilla/warehouse`. Below is an overview of the key directories and their purposes:

-   **`Commands`**: Contains classes representing commands that can be executed within the application (e.g., `CreateStockCommand`, `AssignOrderCommand`). These are often used to encapsulate operations that modify the system's state.
-   **`Config`**: Holds configuration classes for various parts of the application. This includes beans for `RedisConfig`, `SecurityConfig`, message queues, and other infrastructure components.
-   **`Controllers`**: Includes Spring MVC controllers that handle incoming HTTP requests and define API endpoints. Examples include `StockController` for stock-related operations and `ManagerController` for manager-specific actions.
-   **`Events`**: Contains classes related to event handling, particularly for messages received from RabbitMQ. This includes event handlers like `OrderPlacedEventHandler` and listeners such as `RabbitMqEventListener`.
-   **`Models`**: Defines the data model entities (POJOs) used in the application, which are typically mapped to database tables. Examples are `Stock`, `AssignedOrders`, and `Packager`.
-   **`Repositories`**: Contains Spring Data JPA repositories that provide an abstraction layer for database interactions with the entities. Examples include `StockRepository` and `AssignedOrdersRepository`.
-   **`Services`**: Holds the core business logic of the application. Services mediate between controllers (or event handlers/commands) and repositories, orchestrating operations and ensuring data integrity. Examples are `StockService` and `AssignerService`.
-   **`WarehouseApplication.java`**: The main Spring Boot application class. This class contains the `main` method and is responsible for bootstrapping the entire service.

## API Documentation

This service utilizes Springdoc to automatically generate OpenAPI 3.0 documentation for the RESTful APIs.

Once the application is running, you can access the following:

-   **Swagger UI**: An interactive web UI that allows you to explore and test the API endpoints.
    It is available at `/swagger-ui.html`.
    For example, if the application is running locally on port 8080, you can access it at `http://localhost:8080/swagger-ui.html`.

-   **OpenAPI Specification (JSON)**: The raw OpenAPI 3.0 specification in JSON format.
    It is available at `/v3/api-docs`.
    For example, if the application is running locally on port 8080, you can access it at `http://localhost:8080/v3/api-docs`.

## Configuration

The primary configuration for the Warehouse Management Service is located in the `src/main/resources/application.yml` file.

Spring Boot's externalized configuration mechanism is used, which means that many properties defined in `application.yml` can be overridden using:
-   Environment variables (e.g., `SPRING_DATASOURCE_URL` overrides `spring.datasource.url`).
-   Command-line arguments (e.g., `--spring.datasource.url=jdbc:...`).
-   Other Spring Boot supported configuration sources.

Refer to the [Spring Boot Externalized Configuration documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.external-config) for more details.

### Key Configuration Sections

Below are some of the main configuration sections found in `application.yml`:

-   **`server.servlet.context-path`**: Sets the base path for the API (e.g., `/api`). If not set, the application will be served from the root context (`/`).
-   **`spring.datasource`**: Configures the connection to the primary PostgreSQL database.
    -   `url`: JDBC URL of the database.
    -   `username`: Database username.
    -   `password`: Database password.
    -   `driver-class-name`: The JDBC driver class (e.g., `org.postgresql.Driver`).
    *(The project includes commented-out settings for an H2 in-memory database which can be useful for local development or testing.)*
-   **`spring.jpa`**: Configures JPA (Java Persistence API) and Hibernate properties.
    -   `hibernate.ddl-auto`: Strategy for schema generation (e.g., `update`, `validate`, `create`, `create-drop`, `none`). **Use with caution in production.**
    -   `show-sql`: Enables logging of SQL statements generated by Hibernate.
    -   `properties.hibernate.dialect`: Specifies the SQL dialect (e.g., `org.hibernate.dialect.PostgreSQLDialect`).
-   **`spring.rabbitmq`**: Configures the connection to the RabbitMQ message broker.
    -   `host`: RabbitMQ server host.
    -   `port`: RabbitMQ server port (default is 5672).
    -   `username`: RabbitMQ username.
    -   `password`: RabbitMQ password.
-   **`spring.data.redis`**: Configures the connection to the Redis server for caching and other purposes.
    -   `host`: Redis server host.
    -   `port`: Redis server port (default is 6379).
    -   *(Other properties like password, SSL can also be configured here.)*
-   **`spring.cache.type`**: Specifies the default cache provider to be used. For this project, it's typically set to `redis` to leverage Redis for caching.
-   **`appconfig.cache.enabled`**: A custom application property that can be used to globally enable or disable caching features within the application. This allows for easily toggling caching without changing individual cache configurations.

For a complete list of configurable properties and their default values, please refer directly to the `src/main/resources/application.yml` file.

## Events

The Warehouse Management Service employs an event-driven architecture, using RabbitMQ for asynchronous communication with other services. This allows for decoupled and resilient interactions.

Incoming events are primarily consumed by the `RabbitMqEventListener` class located in the `com.podzilla.warehouse.Events` package. This listener acts as a central point for RabbitMQ messages and typically delegates the processing to specific event handler methods based on the type or routing key of the incoming event.

### Main Events Handled

The service listens for and processes the following key events:

-   **Order Placed Event**:
    -   **Trigger**: Received when a new order is successfully placed in an external service (e.g., an Order Management Service).
    -   **Purpose**: This event typically initiates processes within the warehouse, such as checking available stock for the ordered items, potentially reserving stock, and preparing for order assignment and packaging.
-   **Order Cancelled Event**:
    -   **Trigger**: Received when an existing order is cancelled in an external service.
    -   **Purpose**: This event may involve releasing any stock that was previously reserved for the cancelled order, updating inventory records, and potentially halting any ongoing processes related to that order.
-   **Order Stock Reservation Event**:
    -   **Trigger**: This event is likely related to the outcome or status of a stock reservation attempt made for an order, possibly originating from this service or another.
    -   **Purpose**: It could confirm that stock has been successfully reserved, indicate a failure to reserve stock (e.g., due to insufficient inventory), or update the status of a reservation.

### Configuration

The connection details for the RabbitMQ server (such as host, port, username, and password) are configured in the `src/main/resources/application.yml` file under the `spring.rabbitmq` section.

Specific queue names, exchange names, and routing keys are defined within the event listener classes (e.g., using `@RabbitListener` annotations) or in dedicated RabbitMQ configuration classes within the `com.podzilla.warehouse.Config` package. For precise details on these, refer to the source code.

## Database

The Warehouse Management Service utilizes a relational database to persist its core data. While the primary target database is PostgreSQL, the application is often configured with an H2 in-memory database for development and testing purposes.

Interaction with the database is managed through Spring Data JPA, which simplifies data access layers. The JPA entities, representing the database tables, are defined in the `com.podzilla.warehouse.Models` package.

### Main Entities

-   **`Stock`**: Represents products or items available in the warehouse. This entity typically includes details such as item name, quantity on hand, location, and other relevant product information.
-   **`Assigner`**: Represents users within the system who have the role and responsibility of assigning incoming orders to available packagers.
-   **`Packager`**: Represents users who are responsible for the physical act of picking items from stock and packaging them for shipment according to order details.
-   **`Manager`**: Represents users who have administrative or oversight roles within the warehouse system. They might have permissions to manage users, view reports, or configure system settings.
-   **`AssignedOrders`**: Tracks orders that have been assigned to a specific packager and are currently in the process of being prepared. This entity likely links order identifiers (which may originate from external events) to `Packager` entities and potentially to the `Stock` items involved.
-   **`PackagedOrders`**: Represents orders that have been fully packaged and are ready for the next stage in the fulfillment process, such as shipment or dispatch.

### Configuration and Schema Management

Database connection details (URL, username, password, driver class) are configured in the `src/main/resources/application.yml` file under the `spring.datasource` section.

Schema management, including the creation and updating of database tables based on the JPA entities, is handled by Hibernate (the default JPA provider in Spring Boot) through the `spring.jpa.hibernate.ddl-auto` property.
-   For **development and testing**, this is often set to `create` (tables are created at startup, dropping existing ones) or `create-drop` (tables are dropped when the application context closes).
-   For **production environments**, it is typically set to `update` (attempts to update the schema to match entities, adding new columns/tables but not removing existing ones) or `validate` (validates that the schema matches the entities and throws an error if not). It's crucial to use `ddl-auto` with caution in production and prefer managed database migration tools for significant changes.

## Contributing

We welcome contributions to the Warehouse Management Service! If you're interested in helping to improve the project, please follow these general guidelines:

1.  **Fork the repository** on GitHub.
2.  **Create a new branch** for your feature or bug fix from the `main` branch.
    ```bash
    git checkout -b feature/your-feature-name
    # or
    git checkout -b bugfix/issue-number
    ```
3.  **Make your changes**, adhering to the existing code style and conventions.
4.  **Add or update tests** as appropriate to cover your changes.
5.  **Ensure all tests pass** locally before pushing.
6.  **Commit your changes** with clear and descriptive commit messages.
7.  **Push your branch** to your forked repository.
8.  **Create a pull request** against the `main` branch of the original repository.

The project maintainers will review your pull request, provide feedback, and merge it if everything looks good. We appreciate your contributions!

## License

This project is licensed under the Apache License 2.0. You can find the full license text here:
[Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0)

See the `LICENSE` file for more details.
