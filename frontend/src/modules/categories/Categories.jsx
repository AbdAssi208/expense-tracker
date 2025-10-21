import { useEffect, useState } from "react";
import { useAuth } from "../auth/AuthContext";
import { api } from "../../shared/api";

export default function CategoriesPage() {
  const { token } = useAuth();
  const [items, setItems] = useState([]);
  const [err, setErr] = useState("");

  // form
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");

  // edit
  const [editing, setEditing] = useState(null);
  const [eName, setEName] = useState("");
  const [eDesc, setEDesc] = useState("");

  const load = async () => {
    setErr("");
    try {
      const list = await api("/api/categories", { token });
      setItems(list || []);
    } catch (e) { setErr(e.message); }
  };
  useEffect(() => { if (token) load(); }, [token]);

  const create = async (e) => {
    e.preventDefault();
    try {
      await api("/api/categories", { method:"POST", token, body:{ name, description } });
      setName(""); setDescription("");
      await load();
    } catch(e){ alert(e.message); }
  };

  const startEdit = (c) => {
    setEditing(c.id);
    setEName(c.name);
    setEDesc(c.description || "");
  };

  const saveEdit = async (id) => {
    try {
      await api(`/api/categories/${id}`, { method:"PUT", token, body:{ name:eName, description:eDesc } });
      setEditing(null);
      await load();
    } catch(e){ alert(e.message); }
  };

  const remove = async (id) => {
    if (!confirm("Delete category?")) return;
    try {
      await api(`/api/categories/${id}`, { method:"DELETE", token });
      await load();
    } catch(e){ alert(e.message); }
  };

  return (
    <div style={{ padding:16 }}>
      <h2>Categories</h2>
      {err && <div style={{ color:"red" }}>{err}</div>}

      <form onSubmit={create} style={{ display:"grid", gridTemplateColumns:"2fr 3fr auto", gap:8, alignItems:"end", marginBottom:16 }}>
        <div>
          <label>Name</label>
          <input value={name} onChange={e=>setName(e.target.value)} required />
        </div>
        <div>
          <label>Description</label>
          <input value={description} onChange={e=>setDescription(e.target.value)} />
        </div>
        <button>Add</button>
      </form>

      <table border="1" cellPadding="6" style={{ width:"100%", borderCollapse:"collapse" }}>
        <thead><tr><th>ID</th><th>Name</th><th>Description</th><th></th></tr></thead>
        <tbody>
          {items.map(c => (
            <tr key={c.id}>
              <td>{c.id}</td>
              <td>
                {editing===c.id
                  ? <input value={eName} onChange={e=>setEName(e.target.value)} />
                  : c.name}
              </td>
              <td>
                {editing===c.id
                  ? <input value={eDesc} onChange={e=>setEDesc(e.target.value)} />
                  : (c.description || "")}
              </td>
              <td style={{ whiteSpace:"nowrap" }}>
                {editing===c.id ? (
                  <>
                    <button onClick={()=>saveEdit(c.id)}>Save</button>{" "}
                    <button onClick={()=>setEditing(null)}>Cancel</button>
                  </>
                ) : (
                  <>
                    <button onClick={()=>startEdit(c)}>Edit</button>{" "}
                    <button onClick={()=>remove(c.id)}>Delete</button>
                  </>
                )}
              </td>
            </tr>
          ))}
          {!items.length && <tr><td colSpan={4} style={{ textAlign:"center" }}>No categories</td></tr>}
        </tbody>
      </table>
    </div>
  );
}
