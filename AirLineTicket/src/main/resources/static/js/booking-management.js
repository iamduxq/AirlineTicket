document.addEventListener("DOMContentLoaded", () => {

    const username = localStorage.getItem("username") || sessionStorage.getItem("username");
    const bookingsContainer = document.getElementById("userBookings");

    if (!username) {
        bookingsContainer.innerHTML = `<p>Vui lòng đăng nhập để xem danh sách vé.</p>`;
        return;
    }

    fetch(`/api/booking/user/${username}`)
        .then(res => res.json())
        .then(bookings => {

            if (!bookings || bookings.length === 0) {
                bookingsContainer.innerHTML = `<p>Bạn chưa có đặt vé nào!</p>`;
                return;
            }

            bookingsContainer.innerHTML = "";

            bookings.forEach(b => {
                console.log("Booking item → ", b);
                const ticket = b.ticket;
                const flight = ticket.flight;
                const passenger = b.passengers?.[0]?.fullname || "Không rõ";

                const depAirport = flight.departureAirportName?.city || "?";
                const arrAirport = flight.arrivalAirportName?.city || "?";

                const airline = flight.airline || "?";
                const flightCode = flight.flightCode || flight.fightCode || "?";

                const departDate = new Date(flight.departureTime).toLocaleDateString("vi-VN");
                const returnDate = new Date(flight.arrivalTime).toLocaleDateString("vi-VN");

                const price = ticket.totalPrice?.toLocaleString("vi-VN") || "0";

                const status = b.status;
                const statusClass =
                    status === "CONFIRMED" ? "confirmed" :
                        status === "COMPLETED" ? "completed" : "cancelled";

                const statusText =
                    status === "CONFIRMED" ? "Đã xác nhận" :
                        status === "COMPLETED" ? "Đã bay" : "Đã hủy";

                const card = `
                    <div class="booking-card">

                        <!-- Header -->
                        <div class="booking-header">
                            <div class="booking-code">${ticket.ticketCode || ("SKY" + ticket.id)}</div>
                            <div class="booking-status ${statusClass}">${statusText}</div>
                        </div>

                        <!-- Chuyến đi -->
                        <div class="flight-route">
                            <h4>${depAirport} → ${arrAirport}</h4>
                            <p>${airline} ${flightCode}</p>
                        </div>

                        <!-- Chuyến về (nếu có roundtrip) -->
                        <div class="flight-route" style="margin-top:10px;">
                            <h4>${arrAirport} → ${depAirport}</h4>
                            <p>${airline} ${flightCode}</p>
                        </div>

                        <!-- Chi tiết -->
                        <div class="booking-details">
                            <p><b>Ngày đi:</b> ${departDate}</p>
                            <p><b>Ngày về:</b> ${returnDate}</p>
                            <p><b>Hành khách:</b> ${passenger}</p>
                            <p><b>Tổng tiền:</b>
                                <span class="price">${price} VNĐ</span>
                            </p>
                        </div>

                        <!-- Actions -->
                        <div class="booking-actions">
                            <button class="action-btn view" onclick="viewBooking(${b.id})">
                                <i class="fas fa-eye"></i> Xem chi tiết
                            </button>
                            <button class="action-btn edit" onclick="editBookingModal(${b.id})">
                                <i class="fas fa-edit"></i> Sửa thông tin
                            </button>
                            ${status === "CONFIRMED" ? `
                            <button class="action-btn cancel" onclick="cancelBooking(${b.id})">
                                <i class="fas fa-times"></i> Hủy vé
                            </button>
                            ` : ""}
                        </div>
                    </div>
                `;

                bookingsContainer.innerHTML += card;
            });
        })
        .catch(err => {
            console.error("Lỗi khi tải danh sách vé:", err);
            bookingsContainer.innerHTML = `<p>Không thể tải danh sách vé</p>`;
        });
});

function closeEditBookingModal() {
    const modal = document.getElementById("editModal");
    const form = document.getElementById('editForm');
    modal.style.display = "none";
    document.body.style.overflow = "auto";
    form.reset();
}

function closeDetailsBookingModal() {
    const modal = document.getElementById("detailsModal");
    modal.style.display = "none";
    document.body.style.overflow = "auto";
    form.reset();
}

window.addEventListener("click", (e) => {
    const modal = document.getElementById("editModal");
    if (e.target === modal) {
        closeBookingModal();
    }
})

function cancelBooking(id) {
    if (!confirm("Bạn có chắc muốn hủy vé không?")) return;

    fetch(`/api/booking/cancel/${id}`, { method: "PUT" })
        .then(res => {
            if (res.ok) {
                alert("Hủy vé thành công!");
                location.reload();
            } else {
                alert("Không thể hủy vé!");
            }
        });
}

function viewBooking(id) {
    // alert("Tính năng xem chi tiết vé đang được phát triển");
    fetch(`/api/booking/${id}`)
        .then(res => res.json())
        .then(b => {
            const ticket = b.ticket;
            const flight = ticket.flight;

            const passengerList = b.ticket.passengers.map(p => `
                <li>
                    <b>${p.fullname}</b> - ${p.gender == 1 ? 'Nam' : 'Nữ'}
                    <br>Ngày sinh: ${p.dayOfBirth}
                    <br>Passport: ${p.passportNumber}
                    <br>Quốc tịch: ${p.nationality}
                </li>
            `).join("");

            const html = `

            <p><b>Mã Booking:</b> ${b.id}</p>
                <p><b>Trạng thái:</b> ${b.status}</p>
                <hr>              
                <h3>Thông tin vé</h3>
                <p><b>Mã vé:</b> ${ticket.ticketCode}</p>
                <p><b>Tổng tiền:</b> ${ticket.totalPrice.toLocaleString()} VNĐ</p>
                <p><b>Số ghế:</b> ${ticket.seatNumber}</p>
                <hr>
                <h3>Thông tin chuyến bay</h3>
                <p><b>Mã chuyến:</b> ${flight.flightCode}</p>
                <p><b>Hãng bay:</b> ${flight.airline}</p>
                <p><b>Đi:</b> ${flight.departureAirportName.city} – ${flight.departureTime}</p>
                <p><b>Đến:</b> ${flight.arrivalAirportName.city} – ${flight.arrivalTime}</p>
                <hr>
                <h3>Hành khách</h3>
                <ul style="line-height: 1.6;">${passengerList}</ul>
            `;
            document.getElementById("details-content").innerHTML = html;
            document.getElementById("detailsModal").style.display = "block";
        })
        .catch(err => {
            console.error("Lỗi xem chi tiết: ", err);
            alert("Không thể tải thông tin vé!");
        });
}

let selectedBookingId = null
function editBookingModal(bookingId) {
    selectedBookingId = bookingId;
    document.getElementById("editModal").style.display = "block";
}

function submitEdit() {
    const payload = {
        fullname: document.getElementById("editPassengerName").value,
        dayOfBirth: document.getElementById("editPassengerBirth").value + "T00:00:00",
        gender: document.getElementById("editPassengerGender").value,
        passportNumber: document.getElementById("editCitizenId").value,
        nationality: document.getElementById("editNationality").value
    };
    fetch(`/api/booking/${selectedBookingId}/update-passenger`, {
        method: "PUT",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(payload)
    })
        .then(res => res.json())
        .then(date => {
            alert("Cập nhật thành công!");
            location.reload();
        })
        .catch(err => {
            console.error("Không thể cập nhật");
        });
}

// Xử lý đặt vé
document.getElementById("bookingForm")?.addEventListener("submit", function (e) {
    e.preventDefault();

    const fullname = document.getElementById("passengerName").value;
    const birth = document.getElementById("passengerBirth").value;
    const gender = document.getElementById("passengerGender").value;
    const passportNumber = document.getElementById("citizenId").value;
    const nationality = document.getElementById("nationality").value;

    const passenger = {
        fullname: fullname,
        dayOfBirth: birth + "T00:00:00",
        gender: gender,
        passportNumber: passportNumber,
        nationality: nationality
    };

    const username = localStorage.getItem("username") || sessionStorage.getItem("username");

    const payload = {
        username: username,
        flightId: selectedFlightId,
        seatNumber: 1,
        totalPrice: selectedFlightPrice,
        passengers: [passenger]
    };

    fetch("/api/booking/book-ticket", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(payload)
    })
        .then(res => res.json())
        .then(data => {
            alert("Đặt vé thành công!");
            closeBookingModal();
            window.location.href = "/booking-management";
        })
        .catch(err => {
            console.error("Lỗi đặt vé:", err);
            alert("Không thể đặt vé, vui lòng thử lại!");
        });
});
