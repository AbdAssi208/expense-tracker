import { useEffect, useState } from "react";
import { useAuth } from "../auth/AuthContext";
import { api } from "../../shared/api";
import { displayTitle } from "../../shared/title";

export default function TransactionsPage() {
  const { token } = useAuth();
  const [items, setItems] = useState([]);
  const [cats, setCats] = useState([]);
  const [loading, setLoading] = useState(false);
  const [err, setErr] = useState("");

  // form
  const [title, setTitle] = useState("");
  const [amount, setAmount] = useState("");
  const [date, setDate] = useState(() => new Date().toISOString().slice(0, 10));
  const [categoryId, setCategoryId] = useState("");

  const loadAll = async () => {
    setLoading(true);
    setErr("");
    try {
      const [tx, cs] = await Promise.all([
        api("/api/transactions", { token }),
        api("/api/categories", { token }),
      ]);
      setItems(tx || []);
      setCats(cs || []);
    } catch (e) {
      setErr(e.message);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    if (token) loadAll();
  }, [token]);

  const create = async (e) => {
    e.preventDefault();
    setErr("");

    // quick checkpoint before sending 
    if (!title.trim()) {
      setErr("Title is required");
      return;
    }
    if (!amount || Number.isNaN(Number(amount))) {
      setErr("Amount must be a number");
      return;
    }
    if (!date) {
      setErr("Date is required");
      return;
    }

    try {
      //title + description
  
      await api("/api/transactions", {
        method: "POST",
        token,
        body: {
          title: title,                 
          description: title,           
          amount: Number(amount),
          date,
          categoryId: categoryId ? Number(categoryId) : null,
        },
      });

      // clean and reload
      setTitle("");
      setAmount("");
      setCategoryId("");
      await loadAll();
    } catch (e) {
      setErr(e.message);
    }
  };

  const remove = async (id) => {
    if (!confirm("Delete transaction?")) return;
    try {
      await api(`/api/transactions/${id}`, { method: "DELETE", token });
      await loadAll();
    } catch (e) {
      alert(e.message);
    }
  };

  return (
    <div style={{ padding: 16 }}>
      <h2>Transactions</h2>
      {err && <div style={{ color: "red", marginBottom: 8 }}>{err}</div>}

      <form
        onSubmit={create}
        style={{
          display: "grid",
          gridTemplateColumns: "2fr 1fr 1fr 1fr auto",
          gap: 8,
          alignItems: "end",
          marginBottom: 16,
        }}
      >
        <div>
          <label>Title</label>
          <input
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            required
            placeholder="e.g. Grocery"
          />
        </div>

        <div>
          <label>Amount</label>
          <input
            type="number"
            step="0.01"
            value={amount}
            onChange={(e) => setAmount(e.target.value)}
            required
            placeholder="0.00"
          />
        </div>

        <div>
          <label>Date</label>
          <input
            type="date"
            value={date}
            onChange={(e) => setDate(e.target.value)}
            required
          />
        </div>

        <div>
          <label>Category</label>
          <select
            value={categoryId}
            onChange={(e) => setCategoryId(e.target.value)}
          >
            <option value="">(none)</option>
            {cats.map((c) => (
              <option key={c.id} value={c.id}>
                {c.name}
              </option>
            ))}
          </select>
        </div>

        <button>Add</button>
      </form>

      {loading ? (
        "Loading…"
      ) : (
        <table
          border="1"
          cellPadding="6"
          style={{ width: "100%", borderCollapse: "collapse" }}
        >
          <thead>
            <tr>
              <th>ID</th>
              <th>Title</th>
              <th>Amount</th>
              <th>Date</th>
              <th>Category</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            {items.map((t) => (
              <tr key={t.id}>
                <td>{t.id}</td>
                <td>{displayTitle(t)}</td>
                <td>{t.amount}</td>
                <td>{t.date}</td>
                <td>{t.categoryName ?? t.category?.name ?? ""}</td>
                <td>
                  <button onClick={() => remove(t.id)}>Delete</button>
                </td>
              </tr>
            ))}
            {!items.length && (
              <tr>
                <td colSpan={6} style={{ textAlign: "center" }}>
                  No transactions
                </td>
              </tr>
            )}
          </tbody>
        </table>
      )}
    </div>
  );
}
