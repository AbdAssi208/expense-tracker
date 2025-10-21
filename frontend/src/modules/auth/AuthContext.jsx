import { createContext, useContext, useEffect, useMemo, useState } from "react";
import { api } from "../../shared/api";

const AuthCtx = createContext(null);

export function AuthProvider({ children }) {
  const [token, setToken] = useState(() => localStorage.getItem("token") || "");
  const [user, setUser] = useState(null);
  const [ready, setReady] = useState(false);

  useEffect(() => {
    (async () => {
      setReady(false);
      if (!token) { setUser(null); setReady(true); return; }
      try {
        const me = await api("/api/auth/me", { token });
        setUser(me);
      } catch {
        localStorage.removeItem("token");
        setToken("");
        setUser(null);
      } finally {
        setReady(true);
      }
    })();
  }, [token]);

  const login = async (email, password) => {
    const r = await api("/api/auth/login", { method: "POST", body: { email, password } });
    localStorage.setItem("token", r.token);
    setToken(r.token);
  };

  const register = async (fullName, email, password) => {
    const r = await api("/api/auth/register", { method: "POST", body: { fullName, email, password } });
    localStorage.setItem("token", r.token);
    setToken(r.token);
  };

  const logout = () => {
    localStorage.removeItem("token");
    setToken("");
    setUser(null);
  };

  const value = useMemo(() => ({ token, user, ready, login, register, logout }), [token, user, ready]);
  return <AuthCtx.Provider value={value}>{children}</AuthCtx.Provider>;
}
export const useAuth = () => useContext(AuthCtx);
