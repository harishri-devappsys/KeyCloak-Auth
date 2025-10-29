```markdown
# Secure Microservices with Spring Boot & Keycloak

This repository contains a **Spring Boot multi-module project** demonstrating secure, authenticated communication between microservices using **Keycloak**.

---

## 🧩 Project Overview

This project implements a **microservice architecture** where services are secured using Keycloak for server-to-server authentication.  
The primary mechanism used is the **OAuth 2.0 Client Credentials Flow**, which is the standard for **machine-to-machine communication**.

---

## ⚙️ Backend Architecture

The backend follows a **modular, microservice pattern** to ensure separation of concerns and maintainability.

### Core Modules

- **`/ServiceA`**  
  An example microservice that acts as an **OAuth2 Client**.  
  It initiates requests to other services (like ServiceB) by first obtaining a **JWT** from Keycloak.

- **`/ServiceB`**  
  An example microservice that acts as an **OAuth2 Resource Server**.  
  It protects its endpoints and only grants access to requests that present a valid JWT issued by Keycloak.

- **`/common-security`**  
  A reusable security module containing shared **Spring Security configurations**.  
  Any service needing to act as a Resource Server (like ServiceB) can simply include this module as a dependency to be instantly secured with a consistent policy.

- **`/docker-compose.yml`**  
  A Docker configuration file that launches a local **Keycloak instance**, which acts as the central **Authentication Server**.

---

## 🔐 Authentication Flow (Client Credentials)

The security model for **server-to-server communication** works as follows:

1. **Request Initiation**  
   Service A needs to call a protected API endpoint on Service B.

2. **Token Request**  
   Service A contacts the **Keycloak token endpoint**, authenticating itself using its unique  
   `client-id` and `client-secret` (stored in `application.yml`).

3. **Token Issuance**  
   Keycloak validates the credentials, confirms that Service A is a registered **service account**, and issues a **short-lived JWT** back to Service A.

4. **Secure API Call**  
   Service A calls the protected endpoint on Service B, including the token in the header:  
```

Authorization: Bearer <token>

````

5. **Token Validation**  
Service B (using the `common-security` module) validates the incoming JWT by:
- Checking its **digital signature** against Keycloak’s public key.  
- Verifying the **issuer realm** is correct.  
- Ensuring the token is **not expired**.

6. **Access Granted**  
Once the token is validated, Service B trusts the request and grants access, returning secure data to Service A.

---

## 🧰 Core Technologies

| Category | Technologies |
|-----------|--------------|
| **Backend** | Java 21, Spring Boot 3, Spring Security (OAuth2 Client & Resource Server), Gradle |
| **Authentication** | Keycloak |
| **Containerization** | Docker (for Keycloak) |

---

## 🚀 How to Run

### 1. Start Keycloak

First, launch the **Keycloak authentication server** using Docker:

```bash
docker-compose up -d
````

Access the **Keycloak Admin Console** at [http://localhost:8080](http://localhost:8080).

Then, perform the following setup:

1. Create a new **realm** — e.g., `valura-services`.
2. Create a **client for ServiceA** — e.g., `relay-service`.

   * Enable **Client Authentication** and **Service Accounts Roles**.
3. Create a **client for ServiceB** — e.g., `connector-service`.
4. Copy the **client secret** from `relay-service` and place it into `ServiceA`’s `application.yml`.

---

### 2. Run the Backend Services

From the root directory, run each Spring Boot application:

```bash
# Run ServiceA (the client)
./gradlew :ServiceA:bootRun
```

```bash
# In a new terminal, run ServiceB (the resource server)
./gradlew :ServiceB:bootRun
```

---

## ✅ Summary

This setup demonstrates **secure microservice communication** using **Spring Boot** and **Keycloak**, leveraging **OAuth2 Client Credentials Flow** for machine-to-machine trust.
It ensures robust authentication, centralized authorization, and reusable security configurations across all microservices.

```
