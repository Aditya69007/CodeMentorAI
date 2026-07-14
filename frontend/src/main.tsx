import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import { BrowserRouter } from "react-router-dom";
import { Toaster } from "react-hot-toast";

import "./index.css";

import App from "./App";
import { AuthProvider } from "./context/AuthContext";
import { ThemeProvider } from "./context/ThemeContext";

createRoot(
  document.getElementById("root")!
).render(
  <StrictMode>

    <BrowserRouter>

      <ThemeProvider>

        <AuthProvider>

          <App />

          <Toaster
            position="top-right"
            toastOptions={{
              duration: 4000,

              style: {
                background: "#161b22",
                color: "#f8fafc",
                border: "1px solid #30363d",
                borderRadius: "10px",
                padding: "12px 16px",
              },

              success: {
                duration: 3000,
              },

              error: {
                duration: 5000,
              },
            }}
          />

        </AuthProvider>

      </ThemeProvider>

    </BrowserRouter>

  </StrictMode>
);