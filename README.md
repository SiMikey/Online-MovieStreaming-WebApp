# 📺 Online Streaming Services

A full-stack **Online Streaming Platform** built using **Spring Boot Microservices** for the backend and **React** for the frontend. The application allows users to explore, stream, and manage multimedia content through a secure, scalable, and modular architecture.

---

## 🧩 Microservices Architecture (Backend)

The backend is developed using **Spring Boot** and follows a microservices pattern with the following modules:

### 🔗 Microservices Overview

| Service        | Description |
|----------------|-------------|
| **API Gateway** | Central entry point for routing requests to services |
| **Config Server** | Centralized configuration management |
| **Catalog Service** | Manages movie/show metadata, categories, etc. |
| **Streaming Service** | Handles video/audio streaming functionality |
| **User Service** | Manages user authentication, roles, and profiles |
| **Discovery Server (Eureka)** | Registers all microservices for discovery and load balancing |

---

## 🖥️ Frontend (React)

- Built using **React + Vite**
- Communicates with backend services via REST APIs
- Fully responsive and dynamic UI
- Functionalities include:
  - Browse and search movies/shows
  - Stream selected media content
  - View personalized content
  - User login/signup/logout

---

## ⚙️ Tech Stack

### 🚀 Backend

- Java 17
- Spring Boot
- Spring Cloud Gateway
- Spring Cloud Config
- Eureka Discovery Server
- MongoDB / MySQL
- JWT Authentication
- Maven

### 🖼 Frontend

- React (with Vite)
- Tailwind CSS
- Axios
- React Router

---

