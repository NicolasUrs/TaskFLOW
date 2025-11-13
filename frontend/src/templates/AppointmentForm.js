import React, { useState } from "react";

export default function AppointmentForm({ onSubmit, onClose }) {
  const [service, setService] = useState("");
  const [dateTime, setDateTime] = useState("");

  function handleSubmit(e) {
    e.preventDefault();
    onSubmit({ service, dateTime });
  }

  return (
    <div className="modal">
      <form className="modal-card" onSubmit={handleSubmit}>
        <h3>New appointment</h3>
        <input
          placeholder="Service (example: GYM , Barber, Meeting)"
          value={service}
          onChange={(e) => setService(e.target.value)}
          required
        />
        <input
          type="datetime-local"
          value={dateTime}
          onChange={(e) => setDateTime(e.target.value)}
          required
        />
        <div className="row">
          <button className="btn" type="submit">Save</button>
          <button className="btn outline" type="button" onClick={onClose}>
            Cancel
          </button>
        </div>
      </form>
    </div>
  );
}
