# Secure Microservices with Spring Boot & Keycloak

This repository contains a **Spring Boot multi-module project** demonstrating secure, authenticated communication between microservices using **Keycloak**.

---

## 🚀 Project Overview

This project implements a **microservice architecture** where services are secured using **Keycloak** for server-to-server authentication.  
The primary mechanism used is the **OAuth 2.0 Client Credentials Flow**, which is the standard for **machine-to-machine communication**.

---

## 🧩 Backend Architecture

The backend follows a **modular microservice pattern** to ensure **separation of concerns** and **maintainability**.

### **Core Modules**

#### `/ServiceA`
- Acts as an **OAuth2 Client**.
- Responsible for **initiating requests** to other services (like ServiceB).
- Obtains a **JWT** from Keycloak before calling another microservice.

#### `/ServiceB`
- Acts as an **OAuth2 Resource Server**.
- Protects its endpoints using JWT validation.
- Grants access only to requests that carry a **valid Keycloak-issued JWT**.

#### `/common-security`
- A **shared Spring Security module**.
- Contains **reusable security configuration**.
- Any service can include this module to instantly apply consistent **OAuth2 resource server security**.

#### `/docker-compose.yml`
- Configures and launches a **local Keycloak instance**.
- Acts as the **central Authentication Server** for all microservices.

---

## 🔐 Authentication Flow (Client Credentials)

The security model for **server-to-server communication** works as follows:

1. **Request Initiation**  
   - Service A wants to call a protected API on Service B.

2. **Token Request**  
   - Service A contacts **Keycloak’s token endpoint** using its `client-id` and `client-secret` (defined in `application.yml`).

3. **Token Issuance**  
   - Keycloak verifies credentials and issues a short-lived **JWT (JSON Web Token)** to Service A.

4. **Secure API Call**  
   - Service A calls Service B’s protected endpoint with the **JWT** in the header:  
     ```
     Authorization: Bearer <token>
     ```

5. **Token Validation**  
   - Service B automatically validates the JWT by:
     - Checking its **digital signature** using Keycloak’s public key.
     - Verifying the **issuer realm** is correct.
     - Ensuring the **token is not expired**.

6. **Access Granted**  
   - Upon successful validation, Service B **trusts the request** and returns the secure data to Service A.

---

## ⚙️ Core Technologies

| Component | Technology |
|------------|-------------|
| **Language** | Java 21 |
| **Framework** | Spring Boot 3 |
| **Security** | Spring Security (OAuth2 Client & Resource Server) |
| **Authentication** | Keycloak |
| **Build Tool** | Gradle |
| **Containerization** | Docker |

---

## 🧱 How to Run

### **1️⃣ Start Keycloak**

First, launch the **Keycloak authentication server** using Docker:

```bash
docker-compose up -d
````

Then, access the **Keycloak Admin Console**:

👉 [http://localhost:8080](http://localhost:8080)

#### Inside Keycloak:

* Create a new **Realm** — e.g., `valura-services`
* Create the following **Clients**:

  * **relay-service** → (for `ServiceA`)

    * Enable:

      * ✅ *Client authentication*
      * ✅ *Service accounts roles*
  * **connector-service** → (for `ServiceB`)
* Copy the **client secret** from `relay-service` into `ServiceA`’s `application.yml`

---

### **2️⃣ Run the Backend Services**

From the **root directory**, launch each Spring Boot service.

#### Run ServiceA (OAuth2 Client)

```bash
./gradlew :ServiceA:bootRun
```

#### Run ServiceB (Resource Server)

```bash
./gradlew :ServiceB:bootRun
```

---

## 🧠 Summary

This setup demonstrates a **secure, maintainable microservice system** where:

* **ServiceA** acts as an **OAuth2 client**, retrieving tokens.
* **ServiceB** acts as a **resource server**, validating tokens.
* **Keycloak** serves as the **centralized identity provider**.
* **common-security** ensures a unified and reusable security configuration.

Together, these components provide a robust **end-to-end secure communication framework** for enterprise-grade microservices.

---

## 📂 Directory Structure

```
secure-microservices-keycloak/
├── ServiceA/
│   └── src/main/java/... (OAuth2 Client)
├── ServiceB/
│   └── src/main/java/... (Resource Server)
├── common-security/
│   └── src/main/java/... (Shared Security Config)
├── docker-compose.yml
└── build.gradle
```

---

## 🧾 License

This project is provided for **educational and demonstration purposes**.
You can freely adapt it to fit your organization’s security needs.

```
```
