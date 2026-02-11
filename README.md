# Restaurant POS Microservices 🍕

This project is a microservice-based Restaurant Point of Sale (POS) system. It allows for menu management and order processing, utilizing **RabbitMQ** for asynchronous notifications and **MongoDB** for flexible data storage.

---

## 🏗️ Architecture

The system consists of two primary services:

* **Menu Service:** Manages the catalog of food items, prices, and descriptions.
* **Order Service:** Handles customer orders, calculates totals by communicating with the Menu Service, and manages order status transitions.
* **Message Broker (RabbitMQ):** When an order status changes, a message is published. The Order Service consumes these messages to simulate sending notifications via system logs.

---

## 🛠️ Tech Stack

* **Language:** Java 21 (Spring Boot 3.x)
* **Database:** MongoDB
* **Messaging:** RabbitMQ
* **Containerization:** Docker & Docker Compose

---

## 🚀 Getting Started

### Prerequisites
* Docker and Docker Compose installed
* Java 21 (if running services locally without Docker)
* Maven

### Running with Docker (Recommended)
The easiest way to start the entire infrastructure (Services, DB, and Queue) is using Docker Compose:

1. **Clone the repository:**
   ```bash
   git clone [https://github.com/GustavoK101/restaurant-pos.git](https://github.com/GustavoK101/restaurant-pos.git)
   cd restaurant-pos-challenge

   ### Build and Start

The easiest way to start the entire infrastructure (Services, DB, and Queue) is using Docker Compose:

```bash
docker-compose up --build

```


## 📑 API Endpoints

### 🟢 Menu Service (`localhost:8081`)
```
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| **POST** | `/menu-items` | Create a new menu item |
| **GET** | `/menu-items` | List all items (Paginated: `?limit=10&offset=0`) |
| **PUT** | `/menu-items/{id}` | Update item details |
| **DELETE** | `/menu-items/{id}` | Remove an item |

**Example Request (POST /menu-items):**
```json
{
  "name": "Pizza",
  "description": "Delicious cheese pizza",
  "price": 9.99
}

```
### 🔵 Order Service (`localhost:8080`)

The Order Service manages customer purchases and integrates with the Menu Service to validate items and prices. It also triggers asynchronous notifications via RabbitMQ.
```
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| **POST** | `/orders` | Create a new order |
| **GET** | `/orders` | List order history (Paginated: `?limit=10&offset=0`) |
| **GET** | `/orders/{orderId}` | Get details of a specific order |
| **PATCH** | `/orders/{orderId}/status` | Update status (triggers RabbitMQ notification) |

#### 📝 Request Examples

**1. Create Order (`POST /orders`)**
```json
{
  "customer": {
    "fullName": "John Doe",
    "address": "123 Main St",
    "email": "john@example.com"
  },
  "orderItems": [
    { "productId": "abc123", "quantity": 2 },
    { "productId": "xyz456", "quantity": 1 }
  ]
}
