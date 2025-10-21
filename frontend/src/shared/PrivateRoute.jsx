import { Navigate } from "react-router-dom";
import { useAuth } from "../modules/auth/AuthContext";

export default function PrivateRoute({ children }) {
  const { token, ready } = useAuth();
  if (!ready) return <div style={{ padding: 16 }}>Loading…</div>;
  if (!token) return <Navigate to="/login" replace />;
  return children;
}
