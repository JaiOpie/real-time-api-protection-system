# Real-Time API Protection & Monitoring System

A distributed API protection platform built using **Spring Boot microservices, Kafka, Redis, PostgreSQL, and Vue.js**.

The system detects abusive API usage in real time, blocks malicious clients using distributed rate limiting, and provides a monitoring dashboard to visualize traffic and abuse patterns.

---

# Features

## API Protection

- Sliding window rate limiting
- Real-time abuse detection
- Distributed API key blocking using Redis
- TTL-based automatic cooldown recovery

## Event Driven Architecture

- API requests streamed using Kafka
- Analytics service consumes events asynchronously
- Abuse detection triggers block events

## Monitoring Dashboard

- Vue.js monitoring dashboard
- API traffic charts
- Blocked API keys visualization
- Auto-refresh every 5 seconds
- Scrollable traffic logs

---

# System Architecture

```
Client
   │
   ▼
API Gateway (Spring Cloud Gateway)
   │
   ├── API Key Validation
   ├── Redis (Blocked API Keys)
   │
   ▼
Kafka (Event Streaming)
   │
   ▼
Analytics Service
   ├── Sliding Window Abuse Detection
   ├── PostgreSQL Request Logs
   └── Publish Block Events
   │
   ▼
API Gateway consumes block events
   │
   ▼
Redis stores blocked API keys

Vue Dashboard
   │
   ▼
Analytics REST APIs
```

---

# Tech Stack

| Layer | Technology |
|------|------------|
| Backend | Spring Boot |
| Gateway | Spring Cloud Gateway |
| Messaging | Apache Kafka |
| Cache | Redis |
| Database | PostgreSQL |
| Frontend | Vue.js |
| Charts | Chart.js |
| Build Tool | Maven |
| Containers | Docker |

---

# Dashboard

The dashboard provides real-time monitoring of API traffic.

Displays:

- Total API requests
- Blocked requests
- Average response time
- API traffic charts
- Blocked API keys
- Recent API traffic logs

---

# Setup Instructions

## 1. Clone Repository

```bash
git clone https://github.com/YOUR_USERNAME/api-protection-system.git
cd api-protection-system
```

---

## 2. Start Infrastructure

Start Kafka, Redis and PostgreSQL using Docker.

```bash
docker-compose up -d
```

---

## 3. Run Backend Services

Start the services:

```
api-gateway
analytics-service
user-service
```

Example:

```bash
cd api-gateway
./mvnw spring-boot:run
```

---

## 4. Run Dashboard

```bash
cd dashboard-ui
npm install
npm run dev
```

Open:

```
http://localhost:5173
```

---

# Testing Rate Limiting

Send burst traffic:

```bash
for i in {1..30}
do
curl -H "X-API-KEY: 12345-SECRET-KEY" http://localhost:8080/api/users/profile
done
```

Expected response:

```
429 Too Many Requests
```

Blocked API keys will appear in the dashboard.

---

# Project Structure

```
real-time-api-protection-system
│
├── api-gateway
├── analytics-service
├── user-service
├── common-events
├── dashboard-ui
├── docker
│   └── docker-compose.yml
└── README.md
```

---

# Learning Outcomes

This project demonstrates:

- Microservices architecture
- Event-driven system design
- Distributed rate limiting
- Real-time analytics pipelines
- API security patterns
- Full-stack monitoring dashboards

---

# Future Improvements

- WebSocket-based real-time dashboard
- Prometheus metrics integration
- Distributed tracing
- Kubernetes deployment
- Multi-tenant API key policies

---

# Author

Jatin Jai
Java Backend Engineer | Spring Boot | Distributed Systems