import { useState } from "react";
import Image from "../assets/frontImg.jpeg";
import TextInput from "../components/common/TextInput";
export default function Login() {
  const [showPassword, setShowPassword] = useState(false);
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!username || !password) {
      setError("Please enter both username and password.");
      return;
    }
    setError("");
    alert(`Logged in as ${username}`);
    window.location.href = "/dashboard";
    // Add your authentication logic here
  };

  return (
    <div className="d-flex justify-content-center align-items-center vh-100 bg-light">
      <div
        className="card shadow d-flex flex-row overflow-hidden"
        style={{ width: "800px", height: "500px" }}
      >
        {/* Left side - Image */}
        <div className="col-6 p-0">
          <img
            src={Image}
            alt="Journal illustration"
            className="img-fluid h-100 w-100"
            style={{ objectFit: "cover" }}
          />
        </div>

        {/* Right side - Login form */}
        <div className="col-6 d-flex align-items-center justify-content-center p-4">
          <div className="w-100" style={{ maxWidth: "300px" }}>
            <h3 className="text-center mb-4">Login</h3>

            {error && (
              <div className="alert alert-danger py-2">{error}</div>
            )}

            <form onSubmit={handleSubmit}>
              {/* Username */}
              <TextInput
                label="Username"
                id="username"
                value={username}
                onChange={e => setUsername(e.target.value)}
                required
              />

              {/* Password with inside-eye button */}
              <div className="form-floating mb-3 position-relative">
                <TextInput
                  label="Password"
                  id="password"
                  type={showPassword ? "text" : "password"}
                  value={password}
                  onChange={e => setPassword(e.target.value)}
                  required
                  style={{ paddingRight: "2.5rem" }}
                />
                {/* Eye button inside input */}
                <span
                  onClick={() => setShowPassword(!showPassword)}
                  style={{
                    position: "absolute",
                    right: "12px",
                    top: "50%",
                    transform: "translateY(-50%)",
                    cursor: "pointer",
                    color: "gray"
                  }}
                >
                  {showPassword ? "🔓" : "🔒"}
                </span>
              </div>

              <button type="submit" className="btn btn-primary w-100">
                Login
              </button>
            </form>

            <div className="text-center mt-3">
              <small>
                Don’t have an account?{" "}
                <a href="/register" className="text-decoration-none">
                  Register here
                </a>
              </small>
            </div>
          </div>
        </div>
      </div>
    </div>
);
}