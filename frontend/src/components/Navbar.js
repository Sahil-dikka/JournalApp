// src/components/Navbar.jsx
import { Link } from "react-router-dom";

export default function Navbar() {

  const handleLogout = () => {
    alert("Logged out!");
    window.location.href = "/";
    // Add your logout logic here
  }

  return (
    <nav className="navbar navbar-expand-lg navbar-dark bg-dark shadow-sm">
      <div className="container-fluid">
        {/* Brand / Logo */}
        <Link className="navbar-brand fw-bold" to="/dashboard">
          Journal App
        </Link>

        {/* Right-side options */}
        <div className="d-flex">
          <p className="text-white mb-0 me-3 mt-1">Welcome, User!</p>
          <button className="btn btn-outline-light" onClick={handleLogout}>Logout</button>
        </div>
      </div>
    </nav>
  );
}
