import React from "react";
import "../static/Navbar.css";
import logo from "../static/logo.png"; // pune imaginea acolo

export default function Navbar({ user, onLogout }) {
  return (
    <nav className="navbar">
      <div className="nav-left">
        <img src={logo} alt="TaskFlow Logo" className="logo" />
      </div>

      {user && (
        <div className="nav-right">
          <span className="user-info">{user.email} • {user.role}</span>
          <button onClick={onLogout} className="logout-btn">Logout</button>
        </div>
      )}
    </nav>
  );
}
