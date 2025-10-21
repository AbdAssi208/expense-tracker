import { useEffect, useState } from "react";
import { useAuth } from "../auth/AuthContext";
import { api } from "../../shared/api";
import { displayTitle } from "../../shared/title";

export default function DashboardPage() {
  const { user, token, logout } = useAuth();
  const [recent, setRecent] = useState([]);
  const [counts, setCounts] = useState({ tx: 0, cats: 0, incomes: 0 });
  const [err, setErr] = useState("");

  useEffect(() => {
    if (!token) return;
    (async () => {
      try {
        setErr("");
        const [tx, cats, incomes] = await Promise.all([
          api("/api/transactions", { token }),
          api("/api/categories",   { token }),
          // incomes
          api("/api/incomes",      { token }).catch(() => []),
        ]);
        setCounts({ tx: tx?.length || 0, cats: cats?.length || 0, incomes: incomes?.length || 0 });
        setRecent((tx || []).slice(0, 5));
      } catch (e) {
        setErr(e.message);
      }
    })();
  }, [token]);

  const name = user?.fullName || user?.name || user?.email || "(user)";

  return (
    <div style={{ padding: 16 }}>
      <header style={{ display:"flex", justifyContent:"space-between", alignItems:"center" }}>
        <h1>Dashboard</h1>
        <div>
          <span style={{ marginRight: 12 }}>{name}</span>
          <button onClick={logout}>Logout</button>
        </div>
      </header>

      {err && <div style={{ color:"red", marginTop:8 }}>{err}</div>}

      <section style={{ display:"grid", gridTemplateColumns:"repeat(3, 1fr)", gap:12, marginTop:16 }}>
        <Card title="Transactions" value={counts.tx} />
        <Card title="Categories" value={counts.cats} />
        <Card title="Incomes" value={counts.incomes} />
      </section>

      <section style={{ marginTop: 20 }}>
        <h3>Recent Transactions</h3>
        <table border="1" cellPadding="6" style={{ width:"100%", borderCollapse:"collapse" }}>
          <thead>
            <tr><th>ID</th><th>Title</th><th>Amount</th><th>Date</th><th>Category</th></tr>
          </thead>
          <tbody>
          {recent.map(t => (
            <tr key={t.id}>
              <td>{t.id}</td>
              <td>{displayTitle(t)}</td>
              <td>{t.amount}</td>
              <td>{t.date}</td>
              <td>{t.categoryName ?? t.category?.name ?? ""}</td>
            </tr>
          ))}
          {!recent.length && <tr><td colSpan={5} style={{ textAlign:"center" }}>No data yet</td></tr>}
          </tbody>
        </table>
      </section>
    </div>
  );
}

function Card({ title, value }) {
  return (
    <div style={{ border:"1px solid #ddd", padding:12, borderRadius:8 }}>
      <div style={{ color:"#555" }}>{title}</div>
      <div style={{ fontSize:24, fontWeight:700 }}>{value}</div>
    </div>
  );
}
