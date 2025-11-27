import { apiGet, apiPut } from "../helpers/api.js";

const STATUS_MAP = {
    "CONFIRMED": { text: "Đã xác nhận", css: "confirmed" },
    "CANCELLED": { text: "Đã hủy", css: "cancelled" },
    "COMPLETED": { text: "Đã bay", css: "completed" }
};

function renderEmpty(container) {
    container.innerHTML = `<p>Bạn chưa đặt vé nào!</p>`;
}

function renderCard(b) {
    const ticket = b.ticket;
    const flight = ticket.flight;
    const passenger = b.passengers?.[0]?.fullname || "Không rõ";
    const statusObj = STATUS_MAP[b.status] ?? STATUS_MAP["CONFIRMED"];

    return `
        <div class="booking-card">

            <div class="booking-header">
                <div class="booking-code">${ticket.ticketCode || ("SKY" + ticket.id)}</div>
                <div class="booking-status ${statusObj.css}">${statusObj.text}</div>
            </div>

            <div class="flight-route">
                <h4>${flight.departureAirportName.city} → ${flight.arrivalAirportName.city}</h4>
                <p>${flight.airline} ${flight.flightCode}</p>
            </div>

            <div class="flight-route" style="margin-top:10px;">
                <h4>${flight.arrivalAirportName.city} → ${flight.departureAirportName.city}</h4>
                <p>${flight.airline} ${flight.flightCode}</p>
            </div>

            <div class="booking-details">
                <p><b>Ngày đi:</b> ${new Date(flight.departureTime).toLocaleDateString("vi-VN")}</p>
                <p><b>Ngày về:</b> ${new Date(flight.arrivalTime).toLocaleDateString("vi-VN")}</p>
                <p><b>Hành khách:</b> ${passenger}</p>
                <p><b>Tổng tiền:</b> <span class="price">${ticket.totalPrice.toLocaleString("vi-VN")} VNĐ</span></p>
            </div>

            <div class="booking-actions">
                <button class="action-btn view" onclick="viewBooking(${b.id})">
                    <i class="fas fa-eye"></i> Xem chi tiết
                </button>

                ${b.status === "CONFIRMED" ? `
                    <button class="action-btn edit" onclick="editBookingModal(${b.id})">
                        <i class="fas fa-edit"></i> Sửa thông tin
                    </button>

                    <button class="action-btn cancel" onclick="cancelBooking(${b.id})">
                        <i class="fas fa-times"></i> Hủy vé
                    </button>
                ` : ""}
            </div>
        </div>
    `;
}

function renderBookingList(bookings, container) {
    container.innerHTML = bookings.map(renderCard).join("");
}

document.addEventListener("DOMContentLoaded", async () => {
    const container = document.getElementById("userBookings");

    try {
        const bookings = await apiGet("/api/booking/user/me");

        if (!bookings || bookings.length === 0) {
            renderEmpty(container);
            return;
        }

        renderBookingList(bookings, container);

    } catch (err) {
        console.error("Lỗi khi tải danh sách vé:", err);
        container.innerHTML = `<p>Không thể tải danh sách</p>`;
    }
});

window.cancelBooking = async function (id) {
    if (!confirm("Bạn có chắc muốn hủy vé không?")) return;

    try {
        await apiPut(`/api/booking/cancel/${id}`);
        alert("Hủy vé thành công!");
        location.reload();

    } catch (err) {
        alert("Không thể hủy vé!");
        console.error(err);
    }
};

window.restoreBooking = async function (id) {
    if (!confirm("Bạn muốn thu hồi hủy vé?")) return;

    try {
        await apiPut(`/api/booking/restore/${id}`);
        alert("Thu hồi thành công!");
        location.reload();

    } catch (err) {
        alert("Không thể thu hồi!");
        console.error(err);
    }
};

window.viewBooking = async function (id) {
    try {
        const b = await apiGet(`/api/booking/${id}`);
        const ticket = b.ticket;
        const flight = ticket.flight;
        const statusObj = STATUS_MAP[b.status];

        const passengerList = ticket.passengers.map(p => `
            <li>
                <b>${p.fullname}</b> - ${p.gender === 1 ? "Nam" : "Nữ"}
                <br>Ngày sinh: ${p.dayOfBirth}
                <br>Passport: ${p.passportNumber}
                <br>Quốc tịch: ${p.nationality}
            </li>
        `).join("");

        const pdfBtn = (b.status === "CONFIRMED") ? `
            <button class="action-btn pdf" onclick="downloadPdf(${b.id})">
                <i class="fa-solid fa-file-pdf"></i> Tải vé điện tử
            </button>
        ` : "";

        const restoreBtn = b.status === "CANCELLED" ? `
            <button class="action-btn restore" onclick="restoreBooking(${b.id})">
                <i class="fa-solid fa-rotate-left"></i> Thu hồi hủy vé
            </button>
        ` : "";

        const html = `
            <p><b>Mã Booking:</b> ${b.id}</p>
            <p><b>Trạng thái:</b> ${statusObj.text}</p>
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
            <ul style="line-height:1.6;">${passengerList}</ul>

            ${pdfBtn}
            ${restoreBtn}
        `;

        document.getElementById("details-content").innerHTML = html;
        document.getElementById("detailsModal").style.display = "block";

    } catch (err) {
        console.error("Lỗi xem chi tiết:", err);
        alert("Không thể tải thông tin vé!");
    }
};

window.downloadPdf = function (id) {
    window.open(`/api/booking/${id}/ticket-pdf`, "_blank");
};

let selectedBookingId = null;
window.editBookingModal = function (id) {
    selectedBookingId = id;
    document.getElementById("editModal").style.display = "block";
};

window.closeEditBookingModal = function () {
    document.getElementById("editModal").style.display = "none";
    document.getElementById("editForm").reset();
};

window.submitEdit = async function () {
    const payload = {
        fullname: document.getElementById("editPassengerName").value,
        dayOfBirth: document.getElementById("editPassengerBirth").value + "T00:00:00",
        gender: document.getElementById("editPassengerGender").value,
        passportNumber: document.getElementById("editCitizenId").value,
        nationality: document.getElementById("editNationality").value
    };

    try {
        await apiPut(`/api/booking/${selectedBookingId}/update-passenger`, payload);
        alert("Cập nhật thành công!");
        location.reload();

    } catch (err) {
        console.error("Không thể cập nhật:", err);
        alert("Cập nhật thất bại!");
    }
};

window.closeDetailsBookingModal = function () {
    document.getElementById("detailsModal").style.display = "none";
};
