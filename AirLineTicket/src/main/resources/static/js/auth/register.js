document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("registerForm");
    if (!form) {
        console.error("Không tìm thấy form đăng ký");
        return;
    }

    form.addEventListener("submit", async function (event) {
        event.preventDefault();
        // Get data from register form
        const firstName = document.getElementById("firstName").value.trim();

        const lastName = document.getElementById("lastName").value.trim();
        const email = document.getElementById("email").value.trim();
        const phone = document.getElementById("phone").value.trim();
        const username = document.getElementById("username").value.trim();
        const password = document.getElementById("password").value.trim();
        const confirmPassword = document.getElementById("confirmPassword").value.trim();
        const birth = document.getElementById("birthDate").value.trim();
        const gender = document.querySelector('input[name="gender"]:checked')?.value;
        const agreeTerm = document.getElementById("agreeTerms").checked;

        // Check data
        if (!agreeTerm) {
            alert("Vui lòng đồng ý với điều khoản sử dụng!");
            return;
        }
        if (password !== confirmPassword) {
            alert("Mật khẩu xác nhận không khớp!");
            return;
        }
        const data = {
            username: username,
            password: password,
            confirmPassword: confirmPassword,
            fullName: `${firstName} ${lastName}`.trim(),
            email: email,
            phone: phone,
            birth: birth,
            gender: gender
        };
        console.log("Dữ liệu gửi đi: ", data);

        // Request to API
        try {
            const res = await fetch("/api/user/register", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(data),
            });
            if (res.ok) {
                showToast("Tạo tài khoản thành công!", "success")
                form.reset();
            } else {
                const err = await res.json();
                showToast("❌ Đăng ký thất bai: " + (err.error || "Có lỗi xảy ra"), "error");
            }
        } catch (error) {
            console.error("Lỗi: ", error);
            alert("Không thể kết nối đến server!");
        }
    });
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