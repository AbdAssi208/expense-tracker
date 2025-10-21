const BASE = import.meta.env.VITE_API_URL || "";

export async function api(path, { method = "GET", body, token } = {}) {
  const url = `${import.meta.env.VITE_API_URL || ""}${path}`;
  const res = await fetch(url, {
    method,
    headers: {
      "Content-Type": "application/json",
      ...(token ? { Authorization: `Bearer ${token}` } : {}),
    },
    body: body ? JSON.stringify(body) : undefined,
  });

  if (!res.ok) {
    let msg = `${res.status} ${res.statusText}`;
    try {
      const data = await res.json();
      if (data?.message) msg = data.message;
      if (data?.errors) msg += `: ${JSON.stringify(data.errors)}`;
    } catch (_) {}
    throw new Error(msg);
  }
  try {
    return await res.json();
  } catch {
    return null;
  }
}
