document.addEventListener("DOMContentLoaded", function () {
    const user = JSON.parse(localStorage.getItem("user"))
        || JSON.parse(sessionStorage.getItem("user"));

    const loginMenu = document.getElementById("login-menu");
    const regisMenu = document.getElementById("register-menu");
    const userMenu = document.getElementById("user-menu");
    const userFullname = document.getElementById("userFullName");

    if (user) {
        loginMenu.classList.add("hidden");
        regisMenu.classList.add("hidden");
        userMenu.classList.remove("hidden");
        userFullname.textContent = user.fullName || "Người dùng";
    } else {
        loginMenu.classList.remove("hidden");
        regisMenu.classList.remove("hidden");
        userMenu.classList.add("hidden");
    }

    // Xử lý logout
    const logoutBtn = document.getElementById("logoutBtn");
    if (logoutBtn) {
        logoutBtn.addEventListener("click", function (e) {
            e.preventDefault();

            localStorage.clear();
            sessionStorage.clear();

            window.location.href = "/auth/login";
        });
    }
});
