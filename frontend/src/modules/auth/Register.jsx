import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "./AuthContext";

export default function Register() {
  const nav = useNavigate();
  const { register } = useAuth();
  const [fullName, setFullName] = useState("");
  const [email, setEmail]     = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError]     = useState("");

  const submit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError("");
    try {
      await register(fullName.trim(), email.trim(), password);
      nav("/dashboard");
    } catch (e) { setError(e.message); }
    finally { setLoading(false); }
  };

  return (
    <div style={{ padding: 24, maxWidth: 480, margin: "40px auto", border: "1px solid #ddd" }}>
      <h2>Register</h2>
      {error && <div style={{ color: "red", marginBottom: 8 }}>{error}</div>}
      <form onSubmit={submit}>
        <div style={{ marginBottom: 8 }}>
          <label>Full name</label>
          <input value={fullName} onChange={e=>setFullName(e.target.value)} required style={{ width:"100%" }}/>
        </div>
        <div style={{ marginBottom: 8 }}>
          <label>Email</label>
          <input value={email} onChange={e=>setEmail(e.target.value)} type="email" required style={{ width:"100%" }}/>
        </div>
        <div style={{ marginBottom: 8 }}>
          <label>Password</label>
          <input value={password} onChange={e=>setPassword(e.target.value)} type="password" required style={{ width:"100%" }}/>
        </div>
        <button disabled={loading}>{loading ? "..." : "Create account"}</button>
      </form>
      <div style={{ marginTop: 8 }}>
        Have an account? <Link to="/login">Sign in</Link>
      </div>
    </div>
  );
}
