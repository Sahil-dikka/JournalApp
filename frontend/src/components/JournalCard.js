import { useNavigate } from "react-router-dom";

export default function JournalCard({title,content,id}) {
  const navigate = useNavigate();

  const handleEdit = () => {
   
    navigate(`/journal/${id}`); // SPA navigation
  };

  return (
    <div
      className="card m-3 shadow-sm"
      style={{
        borderRadius: "18px",
        background:
          "linear-gradient(135deg, #ece1f7ff 0%, #dde4ebff 100%)",
        border: "none",
        maxWidth: "340px",
      }}
    >
      <div className="card-body" style={{ padding: "1.5rem" }}>
        <h5
          className="card-title fw-bold text-primary mb-2"
          style={{ fontSize: "1.3rem" }}
        >
          
          {title}
        </h5>
        <p
          className="card-text text-muted mb-3"
          style={{ fontSize: "1rem" }}
        >
          {content}
        </p>
        <div className="d-flex justify-content-between gap-5">
          <button
            className="btn btn-primary"
            style={{
              borderRadius: "10px",
              fontWeight: 500,
              padding: "0.4rem 1.2rem",
            }}
            onClick={handleEdit}
          >
            Edit
          </button>
          <button
            className="btn btn-outline-danger"
            style={{
              borderRadius: "10px",
              fontWeight: 500,
              padding: "0.4rem 1.2rem",
            }}
          >
            Delete
          </button>
        </div>
      </div>
    </div>
  );
}