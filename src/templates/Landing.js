import React from "react";
import { Link } from "react-router-dom";
import "../static/Landing.css";

export default function Landing() {
  return (
    <div className="landing">
      <header className="hero">
        <h1>TaskFlow</h1>
        <p>Organize your appointment fast, secure and elegant</p>
        <div className="cta">
          <Link className="btn" to="/login">Login</Link>
          <Link className="btn outline" to="/register">Register</Link>
        </div>
      </header>
      <section className="features">
        <div className="card">JWT Security</div>
        <div className="card">Fast JSON Storage</div>
        <div className="card">Clean UI</div>
      </section>
    </div>
  );
}
