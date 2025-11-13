import React from "react";
import { Routes, Route, Navigate } from "react-router-dom";
import Landing from "./templates/Landing";
import Login from "./templates/Login";
import Register from "./templates/Register";
import Dashboard from "./templates/Dashboard";
import { isLoggedIn } from "./auth";

function Protected({ children }) {
  return isLoggedIn() ? children : <Navigate to="/login" replace />;
}

export default function App() {
  return (
    <Routes>
      <Route path="/" element={<Landing />} />
      <Route path="/login" element={<Login />} />
      <Route path="/register" element={<Register />} />
      <Route
        path="/app"
        element={
          <Protected>
            <Dashboard />
          </Protected>
        }
      />
      <Route path="*" element={<Navigate to="/" replace />} />
    </Routes>
  );
}
