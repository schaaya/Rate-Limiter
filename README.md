# Rate Limiter Implementation

## Introduction
Rate limiters are crucial for building reliable APIs and distributed systems. This challenge involves implementing three common rate limiting algorithms: Token Bucket, Fixed Window Counter, and Sliding Window Counter. Additionally, it explores distributed rate limiting using Redis across multiple servers.

![Rate Limiter](https://thealgoristsblob.blob.core.windows.net/thealgoristsimages/rate-limiter-sys-design-3.jpeg)
## Implementation Overview
The challenge is implemented using Java and Spring Boot. Each rate limiting algorithm is encapsulated in a separate class, and the Spring Boot application exposes endpoints for testing.

### Implemented Rate Limiting Algorithms
1. **Token Bucket Algorithm:** Bucket per IP address with a capacity of 10 tokens and a rate of 1 token per second.

2. **Fixed Window Counter Algorithm:** Tracks request rate within fixed time windows. Rejects requests exceeding the threshold.

3. **Sliding Window Log Algorithm:** Stores timestamped logs for each consumer request. Calculates the request rate based on logs.

4. **Sliding Window Counter Algorithm:** Hybrid approach combining the benefits of fixed window and sliding log algorithms.

### Learning Points
- Enhanced understanding of rate limiting strategies and their application in real-world scenarios.
- Implementation of rate limiting algorithms in Java.
- Integration of Spring Boot for building a RESTful API.
- Utilization of Redis for distributed rate limiting across multiple servers.

## Steps to Run the Application

### Prerequisites
- Java and Maven installed on your machine.
- Redis server running for distributed rate limiting.

### Step 1: Clone the Repository
```
git clone https://github.com/schaaya/rate-limiter.git
cd rate-limiter
```
### Step 2 : Step 2: Build the Application
```
mvn clean install
```
### Step 3: Run the Servers on Different Ports
```
# Run Server 1 on Port 8080
java -jar target/rate-limiter-0.0.1-SNAPSHOT.jar --server.port=8080

# Run Server 2 on Port 8081
java -jar target/rate-limiter-0.0.1-SNAPSHOT.jar --server.port=8081
```

### Step 4: Test Rate Limiter on Multi Servers
- Use Postman or curl to test rate limiting on both servers.
- Limit the client to 60 requests per minute and test the rate limiter's behavior.
## Writing Unit Tests
- Unit tests are implemented for each CRUD (GET) method.
- Ensure proper testing of rate limiting algorithms and API endpoints.
## Conclusion
This implementation demonstrates the effective use of rate limiting algorithms in a Java Spring Boot application. It covers the implementation of token bucket, fixed window counter, and sliding window counter algorithms, along with distributed rate limiting using Redis.

