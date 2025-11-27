import {apiRequest} from "../helpers/http.js";
import {getSelectedFlight, closeBookingModal} from "./booking-modal.js";

document.addEventListener("DOMContentLoaded", () => {
   const form = document.getElementById("bookingForm");
   if (!form) return;

   form.addEventListener("submit", async e => {
       e.preventDefault();
       const { selectedFlightId, selectedFlightPrice } = getSelectedFlight();
       if (!selectedFlightPrice) {
           alert("Không tìm thấy chuyến bay!");
           return;
       }
       const passenger = {
           fullname: document.getElementById("passengerName").value,
           dayOfBirth: document.getElementById("passengerBirth").value + "T00:00:00",
           gender: document.getElementById("passengerGender").value,
           passportNumber: document.getElementById("citizenId").value,
           nationality: document.getElementById("nationality").value
       };

       const payload = {
           flightId: selectedFlightId,
           seatNumber: 1,
           totalPrice: selectedFlightPrice,
           passengers: [passenger]
       };

       try {
           await apiRequest("/api/booking/book-ticket", "POST", payload);
           alert("Đặt vé thành công!");
           window.location.href = "booking-management";
       } catch (err) {
           alert("Không thể đặt vé: " + err.message);
       }
   });
});