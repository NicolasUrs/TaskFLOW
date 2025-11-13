import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { saveAuth } from "../auth";
import "../static/Form.css";

export default function Login() {
  const nav = useNavigate();
  const [form, setForm] = useState({ email: "", password: "" });
  const [err, setErr] = useState("");

  async function onSubmit(e) {
    e.preventDefault();
    setErr("");

    try {
      const res = await fetch("http://localhost:8080/api/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(form),
      });

      if (!res.ok) throw new Error("Login failed");

      const data = await res.json(); // aici e important: trebuie json, nu data.token

      // backend returnează { token, role, email }
      saveAuth(data.token, data.email, data.role);
      nav("/app");
    } catch (e2) {
      setErr("Login failed");
    }
  }

  return (
    <div className="form-wrap">
      <h2>Login</h2>
      <form onSubmit={onSubmit} className="form-card">
        <input
          placeholder="Email"
          value={form.email}
          onChange={(e) => setForm({ ...form, email: e.target.value })}
          type="email"
          required
        />
        <input
          placeholder="Password"
          value={form.password}
          onChange={(e) => setForm({ ...form, password: e.target.value })}
          type="password"
          required
        />
        {err && <div className="error">{err}</div>}
        <button className="btn" type="submit">Login</button>
      </form>
      <p className="muted">
        Don’t have an account? <Link to="/register">Register</Link>
      </p>
    </div>
  );
}
