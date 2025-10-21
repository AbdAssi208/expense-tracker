import { useEffect, useState } from "react";
import { useAuth } from "../auth/AuthContext";
import { api } from "../../shared/api";

export default function IncomesPage() {
  const { token } = useAuth();
  const [items, setItems] = useState([]);
  const [err, setErr] = useState("");

  const [amount, setAmount] = useState("");
  const [source, setSource] = useState("");
  const [date, setDate] = useState(() => new Date().toISOString().slice(0,10));

  const load = async () => {
    try {
      const list = await api("/api/incomes", { token });
      setItems(list || []);
    } catch(e){ setErr(e.message); }
  };
  useEffect(()=>{ if (token) load(); }, [token]);

  const create = async (e) => {
    e.preventDefault();
    try {
      await api("/api/incomes", { method:"POST", token, body:{ amount:Number(amount), source, date } });
      setAmount(""); setSource("");
      await load();
    } catch(e){ alert(e.message); }
  };

  const remove = async (id) => {
    if (!confirm("Delete income?")) return;
    try {
      await api(`/api/incomes/${id}`, { method:"DELETE", token });
      await load();
    } catch(e){ alert(e.message); }
  };

  return (
    <div style={{ padding:16 }}>
      <h2>Incomes</h2>
      {err && <div style={{ color:"red" }}>{err}</div>}

      <form onSubmit={create} style={{ display:"grid", gridTemplateColumns:"1fr 2fr 1fr auto", gap:8, alignItems:"end", marginBottom:16 }}>
        <div>
          <label>Amount</label>
          <input type="number" step="0.01" value={amount} onChange={e=>setAmount(e.target.value)} required />
        </div>
        <div>
          <label>Source</label>
          <input value={source} onChange={e=>setSource(e.target.value)} />
        </div>
        <div>
          <label>Date</label>
          <input type="date" value={date} onChange={e=>setDate(e.target.value)} required />
        </div>
        <button>Add</button>
      </form>

      <table border="1" cellPadding="6" style={{ width:"100%", borderCollapse:"collapse" }}>
        <thead><tr><th>ID</th><th>Amount</th><th>Source</th><th>Date</th><th></th></tr></thead>
        <tbody>
          {items.map(i => (
            <tr key={i.id}>
              <td>{i.id}</td>
              <td>{i.amount}</td>
              <td>{i.source}</td>
              <td>{i.date}</td>
              <td><button onClick={()=>remove(i.id)}>Delete</button></td>
            </tr>
          ))}
          {!items.length && <tr><td colSpan={5} style={{ textAlign:"center" }}>No incomes</td></tr>}
        </tbody>
      </table>
    </div>
  );
}
