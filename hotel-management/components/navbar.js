document.addEventListener("DOMContentLoaded", () => {
    const username = sessionStorage.getItem("username");
  
    const navHTML = `
      <nav>
        <div class="logo">🏨 Hotel System</div>
        <ul>
          <li><a href="home.html">Home</a></li>
          <li><a href="rooms.html">Rooms</a></li>
          <li><a href="dashboard.html">Dashboard</a></li>
          <li><a href="about.html">About</a></li>
          ${username ? `
            <li><a href="#">👤 ${username}</a></li>
            <li><a href="#" onclick="logout()">Logout</a></li>
          ` : `
            <li><a href="index.html" onclick="sessionStorage.setItem('returnTo', location.pathname)">Login</a></li>
            <li><a href="register.html">Register</a></li>
          `}
        </ul>
      </nav>
    `;
  
    const header = document.createElement("header");
    header.innerHTML = navHTML;
    document.body.insertBefore(header, document.body.firstChild);
  });
  
  function logout() {
    sessionStorage.clear();
    window.location.href = "index.html";
  }
  