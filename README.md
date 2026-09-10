# Online Book Store – Backend

A secure RESTful backend for an **Online Book Store** application built using Java and Spring Boot. The backend provides user authentication, book management, shopping cart functionality, order processing, and role-based access control.

## 🚀 Features

* User registration and login
* JWT-based authentication
* Password encryption using BCrypt
* Role-based authorization for USER and ADMIN
* Book listing and book details
* Admin book management
* Shopping cart management
* Add, update, and remove cart items
* Order placement
* User order history
* RESTful API architecture
* MySQL database integration
* CORS configuration for frontend integration
* Protected API endpoints using Spring Security

## 🛠️ Technologies Used

* Java 17
* Spring Boot 3.5.5
* Spring Security
* JWT
* Spring Data JPA
* Hibernate
* MySQL
* Maven
* REST APIs
* BCrypt
* Git & GitHub

## 📁 Project Structure

```text
src/
└── main/
    ├── java/
    │   └── com/bookstore/bookstore/
    │       ├── config/
    │       │   └── SecurityConfig.java
    │       │
    │       ├── controller/
    │       │   ├── AdminBookController.java
    │       │   ├── AdminController.java
    │       │   ├── BookController.java
    │       │   ├── CartController.java
    │       │   └── UserController.java
    │       │
    │       ├── dto/
    │       │   ├── LoginRequest.java
    │       │   ├── LoginResponse.java
    │       │   └── UserResponse.java
    │       │
    │       ├── entity/
    │       │   ├── Book.java
    │       │   ├── Cart.java
    │       │   ├── CartItem.java
    │       │   └── User.java
    │       │
    │       ├── repository/
    │       │   ├── BookRepository.java
    │       │   └── UserRepository.java
    │       │
    │       ├── security/
    │       │   ├── JwtAuthenticationFilter.java
    │       │   └── JwtService.java
    │       │
    │       └── service/
    │           ├── BookService.java
    │           ├── CartService.java
    │           └── UserService.java
    │
    └── resources/
        └── application.properties
```

## 🔐 Authentication & Authorization

The application uses **JWT-based authentication** with Spring Security.

### USER

Users can:

* Register and log in
* View available books
* Add books to their cart
* Update cart quantities
* Remove cart items
* Place orders
* View their order history

### ADMIN

Administrators can:

* Access the admin dashboard
* Add books
* Update book information
* Manage book stock
* Manage available books

Passwords are securely stored using **BCrypt hashing**.

## 📚 REST API Endpoints

### Authentication

| Method | Endpoint              | Description                        |
| ------ | --------------------- | ---------------------------------- |
| POST   | `/api/users/register` | Register a new user                |
| POST   | `/api/users/login`    | Authenticate user and generate JWT |

### Books

| Method | Endpoint          | Description         |
| ------ | ----------------- | ------------------- |
| GET    | `/api/books`      | Get available books |
| GET    | `/api/books/{id}` | Get book by ID      |

### Cart

| Method | Endpoint                      | Description          |
| ------ | ----------------------------- | -------------------- |
| GET    | `/api/cart/{userId}`          | Get user's cart      |
| POST   | `/api/cart/add`               | Add a book to cart   |
| PUT    | `/api/cart/item/{cartItemId}` | Update cart quantity |
| DELETE | `/api/cart/item/{cartItemId}` | Remove cart item     |

### Orders

| Method | Endpoint                    | Description       |
| ------ | --------------------------- | ----------------- |
| POST   | `/api/orders/place`         | Place an order    |
| GET    | `/api/orders/user/{userId}` | Get user's orders |

### Admin Book Management

| Method | Endpoint                | Description          |
| ------ | ----------------------- | -------------------- |
| GET    | `/api/admin/books`      | Get all books        |
| POST   | `/api/admin/books`      | Add a new book       |
| PUT    | `/api/admin/books/{id}` | Update a book        |
| DELETE | `/api/admin/books/{id}` | Delete/remove a book |

## 🗄️ Database

The application uses **MySQL** for persistent data storage.

Main entities include:

* User
* Book
* Cart
* CartItem
* Order
* OrderItem

The application uses **Spring Data JPA and Hibernate** for database operations and entity management.

## ⚙️ Configuration

Before running the application, configure your local MySQL database and update the database connection settings in:

```text
src/main/resources/application.properties
```

Do not commit real database passwords, JWT secrets, or other sensitive credentials to a public repository.

For production, these values should be supplied using environment variables or secure configuration.

## ▶️ Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/prithika8-16/online-book-store-backend.git
```

### 2. Navigate to the project

```bash
cd online-book-store-backend
```

### 3. Create the MySQL database

Create the database used by the application in MySQL.

### 4. Configure database credentials

Update the local database configuration in:

```text
src/main/resources/application.properties
```

### 5. Run the application

Using Maven:

```bash
mvn spring-boot:run
```

Or on Windows:

```bash
mvnw.cmd spring-boot:run
```

The backend runs on:

```text
http://localhost:8081
```

## 🔄 Application Flow

```text
User Registration
       ↓
User Login
       ↓
JWT Authentication
       ↓
Browse Books
       ↓
Add to Cart
       ↓
Checkout
       ↓
Place Order
       ↓
View Order History
```

## 🔗 Frontend

This backend is connected to the React frontend of the Online Book Store application.

**Frontend Repository:**

https://github.com/prithika8-16/online-book-store-frontend

## 👩‍💻 Author

**S Prithika**

B.Tech – Electronics and Communication Engineering

GitHub: https://github.com/prithika8-16
