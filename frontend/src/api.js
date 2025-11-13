export async function api(url, opts = {}) {
  const token = localStorage.getItem("token");

  // Setăm headerele (inclusiv Authorization dacă există token)
  const headers = {
    "Content-Type": "application/json",
    ...(token ? { Authorization: "Bearer " + token } : {}),
    ...opts.headers, // permite adăugarea de headere suplimentare
  };

  // Asigurăm metoda și body-ul corect
  const options = {
    method: opts.method || "GET",
    headers,
    body: opts.body ? JSON.stringify(opts.body) : undefined,
  };

  // Prefixăm automat URL-urile care nu au domeniu complet
  const fullUrl = url.startsWith("http")
    ? url
    : `http://localhost:8080${url}`;

  // Executăm cererea
  const res = await fetch(fullUrl, options);

  // Dacă nu e OK (ex: 404, 500 etc.)
  if (!res.ok) {
    const text = await res.text();
    throw new Error(text || res.statusText);
  }

  // Returnăm JSON-ul dacă există, altfel null
  try {
    return await res.json();
  } catch {
    return null;
  }
}
