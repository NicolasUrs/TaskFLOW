# 🗂️ TaskFlow — Appointments Manager

TaskFlow is a **full-stack application** built using **Spring Boot (backend)** and **React (frontend)** for managing appointments efficiently.  
The system provides **JWT authentication**, **input validation**, **JSON-based persistence**, and a modern dashboard for users and administrators.

---

## 🚀 Main Features

### 👤 Authentication & Authorization
- User registration  
- User login  
- **JWT Token** generation  
- Password hashing with **BCrypt**  
- Protected routes (only authenticated users can access the app)

### 📅 Appointment Management
- Create appointments  
- Edit date/time  
- Delete appointments  
- View appointments:
  - **Admin** → can see all appointments  
  - **User** → can only see their own  

### 💾 Persistence
- All data (users + appointments) is stored in **JSON files**
- Data is loaded into memory at backend startup

---

## 🛠️ Technologies Used

### Backend (Java + Spring Boot)
- Spring Web  
- Spring Security  
- JWT (jjwt)  
- BCrypt PasswordEncoder  
- Gson (JSON storage)  
- Quartz (optional)

### Frontend (React)
- React + Hooks  
- react-router-dom  
- Fetch API  
- Custom CSS styling  

---
