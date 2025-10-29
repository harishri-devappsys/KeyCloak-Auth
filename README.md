# 🔒 Secure Microservices with Spring Boot & Keycloak

This repository contains a **Spring Boot multi-module project** demonstrating secure, authenticated communication between microservices using **Keycloak**.

---

## 🧭 Project Overview

This project implements a **microservice architecture** where services are secured using **Keycloak** for server-to-server authentication.  
The primary mechanism used is the **OAuth 2.0 Client Credentials Flow**, which is the standard for **machine-to-machine** communication.

---

## 🏗️ Backend Architecture

The backend follows a **modular microservice pattern** to ensure separation of concerns and maintainability.

### **Core Modules**

| Module | Description |
|:--------|:-------------|
| `/ServiceA` | Example microservice acting as an **OAuth2 Client**. Responsible for initiating requests to other services (like ServiceB) by first obtaining a JWT from Keycloak. |
| `/ServiceB` | Example microservice acting as an **OAuth2 Resource Server**. Protects its endpoints and only grants access to requests presenting a valid JWT issued by Keycloak. |
| `/common-security` | A reusable **security module** containing common Spring Security configuration. Any service that needs to act as a Resource Server (like ServiceB) can include this module to be instantly secured with a consistent policy. |
| `docker-compose.yml` | Docker configuration that launches a local **Keycloak** instance, serving as the central Authentication Server. |

---

## 🔐 Authentication Flow (Client Credentials)

The **security model** for server-to-server communication works as follows:

1. **Request Initiation:**  
   Service A needs to call a protected API endpoint on Service B.

2. **Token Request:**  
   Service A contacts the Keycloak **token endpoint**, authenticating itself using its unique `client-id` and `client-secret` (stored in `application.yml`).

3. **Token Issuance:**  
   Keycloak validates the credentials, confirms that Service A is a registered service account, and issues a short-lived **JWT**.

4. **Secure API Call:**  
   Service A calls Service B’s protected endpoint, including the JWT in the `Authorization: Bearer <token>` header.

5. **Token Validation:**  
   Service B (via `common-security`) automatically validates the JWT by:
   - Checking its **digital signature** using Keycloak’s public key.  
   - Verifying the **issuer realm**.  
   - Ensuring the **token is not expired**.

6. **Access Granted:**  
   Once validated, Service B trusts the request and returns the secure data to Service A.

---

## 🧰 Core Technologies

- **Backend:** Java 21, Spring Boot 3, Spring Security (OAuth2 Client & Resource Server), Gradle  
- **Authentication:** Keycloak  
- **Containerization:** Docker (for Keycloak)

---

## 🚀 How to Run

### **1. Start Keycloak**

Launch the Keycloak authentication server using Docker:

```bash
docker-compose up -d
