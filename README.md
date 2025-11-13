# 🗂️ TaskFlow — Full-Stack Appointments Manager

TaskFlow este o aplicație **full-stack** realizată cu **Spring Boot (backend)** și **React (frontend)**, care permite utilizatorilor să își gestioneze programările într-un mod rapid și intuitiv.  
Aplicația include **autentificare cu JWT**, **validări**, **persistență în fișiere JSON** și un dashboard modern pentru gestionarea programărilor.

---

## 🚀 Funcționalități principale

### 👤 Autentificare & Autorizare
- Înregistrare utilizator
- Login utilizator
- Generare **JWT Token**
- Parole criptate cu **BCrypt**
- Rute protejate (doar userii logați pot accesa)

### 📅 Gestionarea programărilor
- Creare programări (cu formular)
- Editare dată/ora
- Ștergere programări
- Vizualizare programări:
  - **Admin:** vede toate programările
  - **User:** vede doar programările sale

### 💾 Persistență
- Toate datele (useri + programări) sunt salvate în **fișiere JSON**
- La pornirea backend-ului, datele sunt încărcate în memorie

---

## 🛠️ Tehnologii folosite

### Backend (Java + Spring Boot)
- Spring Web
- Spring Security
- JWT (jjwt)
- BCrypt PasswordEncoder
- Quartz (optional)
- Gson pentru fișiere JSON

### Frontend (React)
- React + Hooks
- react-router-dom
- Fetch API
- CSS custom pentru UI

---

## 📁 Arhitectură proiect

