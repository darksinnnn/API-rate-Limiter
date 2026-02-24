🚀 Smart API Rate Limiter
A robust, production-ready API rate limiting service built with Spring Boot and Redis.
This project demonstrates how to protect backend services from traffic spikes, brute-force attacks, and API abuse by implementing a distributed rate limiting architecture using the Token Bucket algorithm.
🧠 System Architecture
The system intercepts incoming HTTP requests before they reach the core business logic. It queries a centralized Redis cache to check the user's current token allocation.
Client Request 
   │
   ▼
[ Spring Interceptor ] ──(Checks tokens)──> [ Redis (Token Store) ]
   │
   ├──> IF EXCEEDED: Returns 429 Too Many Requests
   │
   ▼
[ REST Controller ] (Business Logic Executed)


✨ Features
Distributed & Scalable: Uses Redis to store rate-limit counters, allowing multiple instances of this Spring Boot app to share the same rate-limit state seamlessly.
Token Bucket Algorithm: Implements a time-windowed token bucket approach to allow for smooth traffic handling while capping absolute maximums.
Zero-Bypass Architecture: Utilizes Spring's HandlerInterceptor to ensure every request is evaluated before controller execution.
Atomic Operations: Uses Redis increment operations to prevent race conditions during concurrent requests.
🛠️ Tech Stack
Language: Java 17+
Framework: Spring Boot 3 (Spring Web, Spring Data Redis)
Database/Cache: Redis
Tools: Maven, Postman (Testing)
🚦 Getting Started
Prerequisites
Java Development Kit (JDK) 17 or higher installed.
Maven installed.
Redis server running locally on port 6379.
Installation & Run
Clone the repository:
use git clone 


Navigate to the directory:
cd rate-limiter


Run the Spring Boot application:
./mvnw spring-boot:run

The server will start on http://localhost:8080.
🧪 Testing the API
To test the rate limiter, send multiple requests using Postman or cURL. The limit is configured to 10 requests per 60 seconds per user.
Success Request (Tokens Available):
curl -i -H "X-User-ID: user_123" http://localhost:8080/api/test


Expected Output: HTTP 200 OK
Blocked Request (Limit Exceeded):
curl -i -H "X-User-ID: user_123" http://localhost:8080/api/test


(Run this 11 times quickly)
Expected Output: HTTP 429 Too Many Requests ("Too many requests! Please wait.")

