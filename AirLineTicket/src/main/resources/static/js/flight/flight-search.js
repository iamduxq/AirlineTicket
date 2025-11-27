import {apiGet} from "../helpers/api.js";
import {openBookingModal} from "../booking/booking-modal.js";

document.addEventListener("DOMContentLoaded", () => {
    const fromSelect = document.getElementById("from");
    const toSelect = document.getElementById("to");
    const swapBtn = document.getElementById("swapBtn");
    const form = document.getElementById("flightSearchForm");
    const results = document.getElementById("resultsContainer");
    const section = document.getElementById("flightResults");
    const tripTypeSelect = document.getElementById("tripType");
    const returnDateGroup = document.getElementById("returnDateGroup");

    if (tripTypeSelect && returnDateGroup) {
        if (tripTypeSelect.value === "oneway") {
            returnDateGroup.style.display = "none";
        }
        tripTypeSelect.addEventListener("change", () => {
            returnDateGroup.style.display = tripTypeSelect.value === "roundtrip" ? "block" : "none";
        });
    }

    // Load sân bay
    apiGet("/api/airport/find")
        .then(airports => {
            airports.forEach(a => {
                fromSelect.add(new Option(`${a.city} (${a.code}) - ${a.name}`, a.code));
                toSelect.add(new Option(`${a.city} (${a.code}) - ${a.name}`, a.code));
            });
        });

    // Swap điểm đi / điểm đến
    if (swapBtn) {
        swapBtn.addEventListener("click", () => {
            const tmp = fromSelect.value;
            fromSelect.value = toSelect.value;
            toSelect.value = tmp;
        });
    }

    // Submit tìm kiếm chuyến bay
    form.addEventListener("submit", async e => {
        e.preventDefault();

        const fromVal = fromSelect.value;
        const toVal = toSelect.value;
        const date = document.getElementById("departDate").value;

        if (!fromVal || !toVal || !date) {
            alert("Vui lòng chọn đủ thông tin!");
            return;
        }

        try {
            const flights = await apiGet(`/api/flight/search?from=${fromVal}&to=${toVal}&date=${date}`);

            results.innerHTML = "";
            section.style.display = "block";

            flights.forEach(flight => {
                const div = document.createElement("div");
                div.classList.add("flight-card");

                div.innerHTML = `
                    <div class="flight-info">
                        <h3>${flight.airline}</h3>
                        <p>Mã chuyến bay: <b>${flight.flightCode}</b></p>
                        <p>Đi: ${flight.departureAirportName.city} - ${flight.departureTime}</p>
                        <p>Đến: ${flight.arrivalAirportName.city} - ${flight.arrivalTime}</p>
                        <p>Giá: <strong>${flight.price.toLocaleString()} VNĐ</strong></p>
                        <button class="book-btn">Đặt vé</button>
                    </div>
                `;

                div.querySelector(".book-btn").addEventListener("click", () => {
                    openBookingModal(flight.id, flight.price);
                });

                results.appendChild(div);
            });

        } catch (err) {
            alert("Không thể tải chuyến bay!");
            console.error(err);
        }
    });
});
