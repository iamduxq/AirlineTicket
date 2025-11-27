import {apiRequest} from "../helpers/http.js";
let selectedFlightId = null;
let selectedFlightPrice = null;

export function openBookingModal(flightId, price) {
    apiRequest("/api/booking/user/me")
        .then(() => {
            selectedFlightId = flightId;
            selectedFlightPrice = price;
            document.getElementById("bookingModal").style.display ="block";
        })
        .catch(() => {
            alert("Vui lòng đăng nhập trước khi đặt vé!");
            window.location.href = "/auth/login";
        });
}

export function closeBookingModal() {
    document.getElementById("bookingModal").style.display = "none";
    document.getElementById("bookingForm").reset();
}

window.closeBookingModal = closeBookingModal;

window.addEventListener("click", e => {
    const modal = document.getElementById("bookingModal");
    if (e.target === modal) {
        closeBookingModal();
    }
});

export function getSelectedFlight() {
    return {selectedFlightId, selectedFlightPrice};
}