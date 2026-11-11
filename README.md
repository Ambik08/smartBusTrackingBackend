# 🚌 Smart Bus Tracking System – Backend

A Spring Boot-based backend application designed to manage buses, routes and location information for a Smart Bus Tracking System.

The backend provides RESTful APIs to handle bus and route management, process location updates and support communication with the React frontend.

## 🚀 Features

- 🚌 **Bus Management** – Manage bus information, credentials and details.
- 🛣️ **Route Management** – Create, retrieve and manage bus routes.
- 📍 **Location Tracking** – Handle bus latitude and longitude information.
- 🔐 **Authentication** – Support secure bus login and user authentication.
- 🔌 **RESTful APIs** – Provide endpoints for frontend communication.
- 🗄️ **Database Integration** – Persist application data using Spring Data JPA.
- ⚡ **Exception Handling** – Handle API and database-related errors.

## 🛠️ Tech Stack

| Technology | Purpose |
|---|---|
| Java | Backend programming |
| Spring Boot | Application framework |
| Spring Web | REST API development |
| Spring Data JPA | Database operations |
| Spring Security | Authentication and authorization |
| JWT | Token-based authentication |
| MySQL | Relational database |
| Maven | Dependency management |
| Postman | API testing |



*Note: Adjust the package structure above to match your actual source code.*

## ⚙️ Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/Ambik08/smartBusTrackingBackend.git
```

### 2. Navigate to the Project

```bash
cd smartBusTrackingBackend
```

### 3. Configure MySQL

Create a database:

```sql
CREATE DATABASE smart_bus_tracking;
```

### 4. Configure Application Properties

Update `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/smart_bus_tracking
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

Add any other required configuration for your application.

### 5. Run the Application

Using Maven:

```bash
mvn spring-boot:run
```

Or build the application:

```bash
mvn clean install
```

The backend will start on:

```text
http://localhost:8080
```

## 🔌 API Capabilities

The backend supports operations such as:

| Module | Operations |
|---|---|
| Bus | Login and manage bus information |
| Route | Create, retrieve and manage routes |
| Location | Handle latitude and longitude updates |
| Authentication | Authenticate users and protect endpoints |

Refer to the source code for the exact API endpoints and request formats.

## 🔗 Related Repository

**Frontend:** [SmartBusTrackingFrontend](https://github.com/Ambik08/smartBusTrackingFrontend)

Built using React.js, JavaScript, React Leaflet and OpenStreetMap.

## 👨‍💻 Developer

**Kasoju Ambik**

Java Backend Developer | Spring Boot Enthusiast

- GitHub: [Ambik08](https://github.com/Ambik08)
- LinkedIn: [Connect with me](https://www.linkedin.com/in/ambik-kasoju-234498295/)

---

<div align="center">

**🚌 Building smarter transportation through technology.**

</div>
