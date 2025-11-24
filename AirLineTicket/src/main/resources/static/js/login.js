document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("loginForm");
    if (!form) {
        console.log("Không tìm thấy form login");
        return;
    }

    form.addEventListener("submit", async (event) => {

        event.preventDefault();

        // Get data into form
        const username = document.getElementById("username").value.trim();
        const password = document.getElementById("password").value.trim();
        const rememberMe = document.getElementById("rememberMe").checked;

        if (!username || !password) {
            alert("Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        const data = {
            username, password
        };
        try {
            const res = await fetch("/api/user/login", {

                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(data),
            });

            if (res.ok) {
                const user = await res.json();
                console.log()

                // RememberME -> Save to localStorage
                if (rememberMe) {
                    localStorage.setItem("user", JSON.stringify(user));
                    localStorage.setItem("username", user.username);
                } else {
                    sessionStorage.setItem("user", JSON.stringify(user));
                    sessionStorage.setItem("username", user.username);
                }

                await new Promise(r => setTimeout(r, 200));
                showToast("Đăng nhập thành công!", "success");
                window.location.href = "/";
            } else {
                const err = await res.json();
                // alert("Đăng nhập thất bai: " + (err.error || "Sai thông tin đăng nhập"));
                showToast("Đăng nhập thất bai: " + (err.error || "Sai thông tin đăng nhập"), "error");
            }
        } catch (err) {
            console.error("Lỗi khi đăng nhập:", err);
            alert("Không thể kết nối tới server!");
        }
    })
});

function showToast(message, type = "success") {
    let bgColor;
    if (type === "success") {
        bgColor = "linear-gradient(to right, #00b09b, #96c93d)";
    } else if (type === "error") {
        bgColor = "linear-gradient(to right, #ff5f6d, #ffc371)";
    }
    Toastify({
        text: message,
        duration: 3000,
        gravity: "top",
        position: "right",
        style: {
            background: bgColor,
            borderRadius: "10px",
            boxShadow: "0 2px 6px rgba(0,0,0,0.2)",
            fontSize: "15px",
            padding: "10px 20px"
        },
    }).showToast();
}