// Handle Login
// Login Handler
document.addEventListener("DOMContentLoaded", () => {
    const loginForm = document.getElementById("loginForm");
  
    if (loginForm) {
      loginForm.addEventListener("submit", async (e) => {
        e.preventDefault();
        const username = document.getElementById("username").value;
        const password = document.getElementById("password").value;
  
        const response = await fetch("http://localhost:8080/api/users/login", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({ username, password })
        });
  
        const text = await response.text();
        if (text.includes("successful")) {
          sessionStorage.setItem("username", username);
  
          // ✅ Stay on current page or redirect to where user came from
          const returnTo = sessionStorage.getItem("returnTo") || "home.html";
          sessionStorage.removeItem("returnTo");
          sessionStorage.setItem("username", username);
          window.location.replace("home.html"); // NOT window.location.href
          } else {
          document.getElementById("loginMessage").innerText = "Invalid credentials";
        }
      });  
    }
  
    // Handle Registration
    if (registerForm) {
      registerForm.addEventListener("submit", async (e) => {
        e.preventDefault();
        const fullName = document.getElementById("fullName").value;
        const username = document.getElementById("username").value;
        const email = document.getElementById("email").value;
        const password = document.getElementById("password").value;
  
        const response = await fetch("http://localhost:8080/api/users/register", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({ fullName, username, email, password })
        });
  
        const data = await response.json();
        document.getElementById("registerMessage").innerText = "Registered Successfully!";
        setTimeout(() => window.location.href = "index.html", 1500);
      });
    }
  });
  