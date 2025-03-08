# 🧾 Receipt Processor API 🚀

A RESTful Spring Boot API that processes receipts and calculates points based on predefined rules.

## 📌 Features
- Accepts receipts via a `POST` request and assigns a unique ID.
- Returns points for a given receipt ID based on custom rules.
- Designed for **containerized execution via Docker**.
- Full **unit tests & logging** implemented.
- API documentation via **Swagger UI**.

---

## 🔧 Installation & Setup

### **Run Locally**
1. Clone the repository:
   ```sh
   git clone https://github.com/your-username/receipt-processor.git
   cd receipt-processor
   ```
2. **Build & Run the Application:**
   ```sh
   mvn clean package
   mvn spring-boot:run
   ```
3. **Access API Documentation:**
    - Swagger UI: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

---

## 🐳 **Run with Docker**
### **1️⃣ Build the Docker Image**
```sh
docker build -t receipt-processor .
```
### **2️⃣ Run the Docker Container**
```sh
docker run -p 8080:8080 receipt-processor
```
### **3️⃣ Stop the Container**
```sh
docker ps  # Find container ID
docker stop <container_id>
```

---

## 🔗 **API Endpoints**
| Method | Endpoint | Description |
|--------|---------|-------------|
| `POST` | `/receipts/process` | Submits a receipt for processing |
| `GET`  | `/receipts/{id}/points` | Retrieves the awarded points for a receipt |

---

## 📜 **Example API Requests**
### **📌 1. Submit a Receipt**
#### **Request (POST `/receipts/process`)**
```json
{
    "retailer": "Target",
    "purchaseDate": "2022-01-02",
    "purchaseTime": "13:13",
    "total": "1.25",
    "items": [
        {"shortDescription": "Pepsi - 12-oz", "price": "1.25"}
    ]
}
```
#### **Response**
```json
{
    "id": "7fb1377b-b223-49d9-a31a-5a02701dd310"
}
```

---

### **📌 2. Get Points for a Receipt**
#### **Request (GET `/receipts/{id}/points`)**
```sh
curl -X GET "http://localhost:8080/receipts/7fb1377b-b223-49d9-a31a-5a02701dd310/points"
```
#### **Response**
```json
{
    "points": 31
}
```

---

## 🛠 **Testing the Application**
### **1️⃣ Run Unit Tests**
```sh
mvn test
```
### **2️⃣ Check Docker Logs for Errors**
```sh
docker logs <container_id>
```

---

## 📜 **Project Structure**
```plaintext
receipt-processor/
│── src/
│   ├── main/
│   │   ├── java/com/bhavesh/receiptprocessor/
│   │   │   ├── config/
│   │   │   ├── controller/
│   │   │   ├── dto/
│   │   │   ├── model/
│   │   │   ├── repository/
│   │   │   ├── service/
│   │   │   ├── ReceiptProcessorApplication.java
│   │   ├── resources/
│   │   │   ├── application.properties  # Spring Boot config
│   │   │   ├── api.yml  # API Specification
│── test/
│── target/  # Contains the built JAR
│── .mvn/
│── .gitignore
│── .dockerignore
│── Dockerfile  # Docker setup
│── pom.xml  # Maven dependencies
│── README.md  # 📌 This file
```

---

## 📜 **License**
MIT License - Feel free to use and modify this project.

---
