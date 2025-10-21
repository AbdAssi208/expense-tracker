import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "./AuthContext";

export default function Login() {
  const nav = useNavigate();
  const { login } = useAuth();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const submit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError("");
    try {
      await login(email.trim(), password);
      nav("/dashboard");
    } catch (e) {
      setError(e.message);
    } finally { setLoading(false); }
  };

  return (
    <div style={{ padding: 24, maxWidth: 480, margin: "40px auto", border: "1px solid #ddd" }}>
      <h2>Login</h2>
      {error && <div style={{ color: "red", marginBottom: 8 }}>{error}</div>}
      <form onSubmit={submit}>
        <div style={{ marginBottom: 8 }}>
          <label>Email</label>
          <input value={email} onChange={e => setEmail(e.target.value)} type="email" required style={{ width: "100%" }} />
        </div>
        <div style={{ marginBottom: 8 }}>
          <label>Password</label>
          <input value={password} onChange={e => setPassword(e.target.value)} type="password" required style={{ width: "100%" }} />
        </div>
        <button disabled={loading}>{loading ? "..." : "Sign In"}</button>
      </form>
      <div style={{ marginTop: 8 }}>
        No account? <Link to="/register">Create one</Link>
      </div>
    </div>
  );
}
