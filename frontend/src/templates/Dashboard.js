import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { api } from "../api";
import { clearAuth, getAuth } from "../auth";
import AppointmentForm from "./AppointmentForm";
import "../static/Dashboard.css";
import Navbar from "../templates/Navbar";

export default function Dashboard() {
  const nav = useNavigate();
  const { email, role } = getAuth();

  const [items, setItems] = useState([]);
  const [showNew, setShowNew] = useState(false);
  const [loading, setLoading] = useState(true);
  const [err, setErr] = useState("");
  const [message, setMessage] = useState("");

  //Load appointments from backend
  async function load() {
    setLoading(true);
    setErr("");
    try {
      const data = await api("http://localhost:8080/api/appointments");
      console.log("Appointments:", data);
      setItems(data);
    } catch (e) {
      console.error("Load error:", e);
      setErr("Could not load appointments");
    } finally {
      setLoading(false);
    }
  }

  //Add new appointment
  async function addAppointment(payload) {
    try {
      await api("/api/appointments", {
        method: "POST",
        body: {
          user: { id: 0, name: "N/A", email },
          service: payload.service,
          dateTime: payload.dateTime,
        },
      });
      setShowNew(false);
      await load();
      showTempMessage("✅ Appointment added successfully!");
    } catch {
      alert("Failed to add appointment");
    }
  }

  //Delete appointment
  async function remove(id) {
    try {
      await api(`/api/appointments/${id}`, { method: "DELETE" });
      setItems((s) => s.filter((x) => x.id !== id));
      showTempMessage("🗑️ Appointment deleted!");
    } catch {
      alert("Delete failed (permission?)");
    }
  }

  //Update appointment
  async function updateAppointment(id, newDateTime) {
    try {
      await api(`/api/appointments/${id}`, {
        method: "PUT",
        body: { dateTime: newDateTime },
      });
      await load();
      showTempMessage("✏️ Appointment updated successfully!");
    } catch {
      alert("Update failed (permission?)");
    }
  }

  //Temporary success message
  function showTempMessage(msg) {
    setMessage(msg);
    setTimeout(() => setMessage(""), 2500);
  }

  //Logout
  function logout() {
    clearAuth();
    nav("/login");
  }

  // 🔹 Load appointments on mount
  useEffect(() => {
    load();
  }, []);

  return (
    <div className="dash">
      <Navbar user={{ email, role }} onLogout={logout} />

      <main className="content">
        <div className="header-row">
          <h2>Appointments</h2>
          <button className="btn" onClick={() => setShowNew(true)}>
            + Add
          </button>
        </div>

        {message && <div className="success">{message}</div>}
        {loading && <div className="muted">Loading…</div>}
        {err && <div className="error">{err}</div>}

        <div className="grid">
          {items.map((a) => (
            <div className="card" key={a.id}>
              <div className="title">{a.service || "—"}</div>
              <div className="meta">{a.dateTime}</div>
              <div className="small">
                by: {a.createdBy || a?.user?.email || "unknown"}
              </div>

              <div className="row">
                {!a.editing ? (
                  <>
                    <button
                      className="btn outline"
                      onClick={() => {
                        a.editing = true;
                        setItems([...items]);
                      }}
                    >
                      Edit
                    </button>
                    <button
                      className="btn danger"
                      onClick={() => remove(a.id)}
                    >
                      Delete
                    </button>
                  </>
                ) : (
                  <>
                    <input
                      type="datetime-local"
                      defaultValue={a.dateTime?.slice(0, 16)}
                      onChange={(e) => (a.newDateTime = e.target.value)}
                    />
                    <button
                      className="btn"
                      onClick={() => {
                        if (!a.newDateTime)
                          return alert("Please select a valid date/time");
                        updateAppointment(a.id, a.newDateTime);
                        a.editing = false;
                        setItems([...items]);
                      }}
                    >
                      Save
                    </button>
                    <button
                      className="btn outline"
                      onClick={() => {
                        a.editing = false;
                        setItems([...items]);
                      }}
                    >
                      Cancel
                    </button>
                  </>
                )}
              </div>
            </div>
          ))}
        </div>
      </main>

      {showNew && (
        <AppointmentForm
          onSubmit={addAppointment}
          onClose={() => setShowNew(false)}
        />
      )}
    </div>
  );
}
