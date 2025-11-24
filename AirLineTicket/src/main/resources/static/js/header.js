document.addEventListener("DOMContentLoaded", function() {
    const user = JSON.parse(localStorage.getItem("user")) || JSON.parse(sessionStorage.getItem("user"));
    const loginMenu = document.getElementById("login-menu");
    const regisMenu = document.getElementById("register-menu");
    const userMenu = document.getElementById("user-menu");
    const userFullname = document.getElementById("userFullName");

    if (user) {
        loginMenu.style.display = "none";
        regisMenu.style.display = "none";
        userMenu.style.display = "block";
        userFullname.textContent = user.fullName || "Người dùng";
    } else {
        loginMenu.style.display = "block";
        regisMenu.style.display = "block";
        userMenu.style.display = "none";
    }

    const logoutBtn = document.getElementById("logoutBtn");
    if (logoutBtn) {
        logoutBtn.addEventListener("click", function (e) {
            e.preventDefault();
            localStorage.removeItem("username");
            sessionStorage.removeItem("username");

            localStorage.clear();
            sessionStorage.clear();
            window.location.href = "/auth/login";
        })
    }
});