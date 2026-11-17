# Banking Platform - API Gateway Path Rewriting & StripPrefix

## Overview
This project implements path rewriting and prefix stripping using **Spring Cloud Gateway** for a Banking Platform Account Service.

- **Account Service (Target Backend)**: Runs on isolated port `8081` and exposes internal routes at `/internal/accounts` (and `/api/v1/internal/accounts`).
- **API Gateway (Public Entry Point)**: Runs on port `8080`. Public clients call `http://localhost:8080/accounts/`.
- **Gateway Transformation**: The Gateway rewrites the incoming `/accounts` prefix to `/internal/accounts` before forwarding to the backend. The backend echoes the received URL path to prove the rewritten path was received.

---

## Tasks Overview

### Task 1: Build Target Service (`AccountService`)
- **Port**: `8081` (isolated port)
- **Controller**: `com.banking.account.AccountController`
- **Exposed Endpoint**: `@RequestMapping({"/internal/accounts", "/api/v1/internal/accounts"})`
- **Verification Response**: Returns `receivedPath` showing the exact URI received by the target service.

### Task 2: Apply StripPrefix / Rewrite in API Gateway (`BankingGateway`)
- **Port**: `8080`
- **Approach 1: `RewritePath` Filter (Recommended & Active)**
  ```yaml
  spring:
    cloud:
      gateway:
        routes:
          - id: account-service-rewrite
            uri: http://localhost:8081
            predicates:
              - Path=/accounts, /accounts/**
            filters:
              - RewritePath=/accounts(?<segment>/?.*), /internal/accounts${segment}
  ```
  *In `application.properties`:*
  ```properties
  spring.cloud.gateway.routes[0].id=account-service-rewrite
  spring.cloud.gateway.routes[0].uri=http://localhost:8081
  spring.cloud.gateway.routes[0].predicates[0]=Path=/accounts, /accounts/**
  spring.cloud.gateway.routes[0].filters[0]=RewritePath=/accounts(?<segment>/?.*), /internal/accounts${segment}
  ```

- **Approach 2: `StripPrefix` Filter (Alternative)**
  ```yaml
  spring:
    cloud:
      gateway:
        routes:
          - id: account-service-stripprefix
            uri: http://localhost:8081
            predicates:
              - Path=/accounts, /accounts/**
            filters:
              - StripPrefix=1
              - PrefixPath=/internal/accounts
  ```
  *In `application.properties`:*
  ```properties
  spring.cloud.gateway.routes[0].id=account-service-stripprefix
  spring.cloud.gateway.routes[0].uri=http://localhost:8081
  spring.cloud.gateway.routes[0].predicates[0]=Path=/accounts, /accounts/**
  spring.cloud.gateway.routes[0].filters[0]=StripPrefix=1
  spring.cloud.gateway.routes[0].filters[1]=PrefixPath=/internal/accounts
  ```

---

## Task 3: Verify Forwarding

### 1. Start Both Services in Spring Tool Suite (STS) / Terminal
1. Run `AccountServiceApplication.java` (Port `8081`)
2. Run `BankingGatewayApplication.java` (Port `8080`)

### 2. Verification Commands (cURL / Browser / PowerShell)

#### Test Direct Backend (Port 8081):
```bash
curl -i http://localhost:8081/internal/accounts
```
Expected output:
```json
{
  "service": "Account Service",
  "status": "SUCCESS",
  "port": 8081,
  "receivedPath": "/internal/accounts",
  "message": "Account Service: Request received at /internal/accounts"
}
```

#### Test Forwarding via Public Gateway (Port 8080):
```bash
curl -i http://localhost:8080/accounts/
```
Expected output:
```json
{
  "service": "Account Service",
  "status": "SUCCESS",
  "port": 8081,
  "receivedPath": "/internal/accounts/",
  "message": "Account Service: Request received at /internal/accounts/"
}
```

Notice that the client sent the request to `http://localhost:8080/accounts/` and the backend received and processed it at `/internal/accounts/`.
