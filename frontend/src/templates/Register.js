import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import "../static/Form.css";

export default function Register() {
  const nav = useNavigate();
  const [form, setForm] = useState({ name: "", email: "", password: "" });
  const [msg, setMsg] = useState("");
  const [err, setErr] = useState("");

  async function onSubmit(e) {
    e.preventDefault();
    setErr("");
    setMsg("");

    try {
      const res = await fetch("http://localhost:8080/api/auth/register", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ ...form, role: "USER" }),
      });

      if (!res.ok) throw new Error("Register failed");
      const text = await res.text();

      setMsg(text || "Registered successfully!");
      setTimeout(() => nav("/login"), 600);
    } catch (e2) {
      setErr("Register failed");
    }
  }

  return (
    <div className="form-wrap">
      <h2>Register</h2>
      <form onSubmit={onSubmit} className="form-card">
        <input
          placeholder="Name"
          value={form.name}
          onChange={(e) => setForm({ ...form, name: e.target.value })}
          required
        />
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
        {msg && <div className="ok">{msg}</div>}
        <button className="btn" type="submit">Register</button>
      </form>
      <p className="muted">
        Already have an account? <Link to="/login">Login</Link>
      </p>
    </div>
  );
}
