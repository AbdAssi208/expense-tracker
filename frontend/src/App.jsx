import { Routes, Route, Link, Navigate, useLocation } from "react-router-dom";
import PrivateRoute from "./shared/PrivateRoute";
import Login from "./modules/auth/Login";
import Register from "./modules/auth/Register";
import Dashboard from "./modules/dashboard/Dashboard";
import Transactions from "./modules/transactions/Transactions";
import Categories from "./modules/categories/Categories";
import Incomes from "./modules/incomes/Incomes"; // اختياري

function NavLink({ to, children }) {
  const loc = useLocation();
  const active = loc.pathname.startsWith(to);
  return (
    <Link to={to} style={{ fontWeight: active ? 700 : 400 }}>{children}</Link>
  );
}

export default function App() {
  return (
    <>
      <nav style={{ padding: 8, borderBottom: "1px solid #eee", display:"flex", gap:16 }}>
        <NavLink to="/dashboard">Dashboard</NavLink>
        <NavLink to="/transactions">Transactions</NavLink>
        <NavLink to="/categories">Categories</NavLink>
        <NavLink to="/incomes">Incomes</NavLink>
        <span style={{ marginLeft: "auto" }} />
        <NavLink to="/login">Login</NavLink>
        <NavLink to="/register">Register</NavLink>
      </nav>

      <Routes>
        <Route path="/" element={<Navigate to="/dashboard" replace />} />
        <Route path="/login" element={<Login/>} />
        <Route path="/register" element={<Register/>} />

        <Route path="/dashboard" element={<PrivateRoute><Dashboard/></PrivateRoute>} />
        <Route path="/transactions" element={<PrivateRoute><Transactions/></PrivateRoute>} />
        <Route path="/categories" element={<PrivateRoute><Categories/></PrivateRoute>} />
        <Route path="/incomes" element={<PrivateRoute><Incomes/></PrivateRoute>} />

        <Route path="*" element={<div style={{ padding:16 }}>Not found</div>} />
      </Routes>
    </>
  );
}
