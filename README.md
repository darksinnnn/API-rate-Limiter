# Smart API Rate Limiter

A production-ready API rate limiting service built with **Spring Boot** and **Redis**.

This project demonstrates how to protect backend services from traffic spikes, brute-force attacks, and API abuse using a distributed rate limiting architecture based on the **Token Bucket algorithm**.

---

## Overview

The system intercepts incoming HTTP requests before they reach the core business logic. It checks whether a user has available tokens and either allows the request or blocks it.

### Request Flow

```
Client Request
      |
Spring HandlerInterceptor (checks tokens)
      |
Redis (Token Store)
      |
If limit exceeded  -> HTTP 429 Too Many Requests
If within limit    -> REST Controller (Business Logic Executed)
```

---

## Features

* **Distributed and Scalable**
  Uses Redis to store rate-limit counters, allowing multiple application instances to share the same rate-limit state.

* **Token Bucket Algorithm**
  Implements a time-windowed token bucket strategy to smooth traffic while enforcing strict limits.

* **Interceptor-Based Enforcement**
  Uses Spring's `HandlerInterceptor` to ensure every request is validated before reaching controller methods.

* **Atomic Operations**
  Uses Redis atomic increment operations to prevent race conditions during concurrent requests.

---

## Tech Stack

* **Language:** Java 17+
* **Framework:** Spring Boot 3 (Spring Web, Spring Data Redis)
* **Database/Cache:** Redis
* **Build Tool:** Maven
* **Testing Tool:** Postman / cURL

---

## Prerequisites

* Java Development Kit (JDK) 17 or higher
* Maven installed
* Redis server running locally on port `6379`

---

## Installation and Running

### 1. Clone the Repository

```bash
git clone <repository-url>
cd rate-limiter
```

### 2. Run the Application

```bash
./mvnw spring-boot:run
```

The application will start at:

```
http://localhost:8080
```

---

## API Testing

The rate limit is configured to:

```
10 requests per 60 seconds per user
```

The user is identified via the `X-User-ID` header.

### Successful Request

```bash
curl -i -H "X-User-ID: user_123" http://localhost:8080/api/test
```

**Expected Response:**

```
HTTP/1.1 200 OK
```

### Exceeded Limit

Run the above command more than 10 times within 60 seconds (e.g., 11 times quickly).

```bash
curl -i -H "X-User-ID: user_123" http://localhost:8080/api/test
```

**Expected Response:**

```
HTTP/1.1 429 Too Many Requests
```

Response message:

```
Too many requests! Please wait.
```

---

## How It Works

* Each user is assigned a token bucket.
* Every request consumes one token.
* Tokens are replenished at a fixed rate.
* If no tokens are available, the request is rejected with HTTP 429.


* A more concise README version
* A version with architecture diagram (Mermaid)
* Production deployment notes (Docker + Redis setup)
* Configuration-based rate limit customization
