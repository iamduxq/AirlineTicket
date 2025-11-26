// Iframe booking modal
let selectedFlightId = null;
let selectedFlightPrice = null;


// Booking Modal Handle
function openBookingModal(flightId, price) {
    fetch("/api/booking/user/me")
        .then(res => {
            if (res.status === 401 || res.status === 403) {
                alert("Vui lòng đăng nhập trước khi đặt vé!");
                window.location.href = "/auth/login";
                return;
            }
            selectedFlightId = flightId;
            selectedFlightPrice = price;
            document.getElementById("bookingModal").style.display = "block";
        }) .catch(err => {
        console.error("Lỗi kiểm tra đăng nhập: ", err);
        alert("Không thể kiểm tra trạng thái đăng nhập!");
    });
}


function closeBookingModal() {
    const modal = document.getElementById("bookingModal");
    const form = document.getElementById('bookingForm');
    modal.style.display = "none";
    document.body.style.overflow = "auto";
    form.reset();
}

window.addEventListener("click", (e) => {
    const modal = document.getElementById("bookingModal");
    if (e.target === modal) {
        closeBookingModal();
    }
})


// Main function
document.addEventListener("DOMContentLoaded", () => {
    window.scroll({top: 0, behavior: "smooth"});
    const fromSelect = document.getElementById("from");
    const toSelect = document.getElementById("to");
    const swapBtn = document.getElementById("swapBtn");
    const flightSearchForm = document.getElementById("flightSearchForm");
    const resultsContainer = document.getElementById("resultsContainer");
    const flightResultsSection = document.getElementById("flightResults");
    const tripTypeSelect = document.getElementById("tripType");
    const returnDateGroup = document.getElementById("returnDateGroup");
    // const closeModal = document.querySelector(".close");

    // Hide/show returnDate
    if (tripTypeSelect.value === "oneway") {
        returnDateGroup.style.display = "none";
    }
    tripTypeSelect.addEventListener("change", () => {
        returnDateGroup.style.display = tripTypeSelect.value === "roundtrip" ? "block" : "none";
        if (tripTypeSelect.value !== "roundtrip") {
            document.getElementById("returnDate").value = "";
        }
    });

    // Load api airport
    fetch("/api/airport/find")
        .then(res => res.json())
        .then(airports => {
            airports.forEach(a => {
                fromSelect.add(new Option(`${a.city} (${a.code}) - ${a.name}`, a.code));
                toSelect.add(new Option(`${a.city} (${a.code}) - ${a.name}`, a.code));
            });
        });

    // Swap btn
    if (swapBtn) {
        swapBtn.addEventListener("click", () => {
            const temp = fromSelect.value;
            fromSelect.value = toSelect.value;
            toSelect.value = temp;
        });
    }

    // Search flight Handle
    if (flightSearchForm) {
        flightSearchForm.addEventListener("submit", event => {
            event.preventDefault();

            const from = fromSelect.value;
            const to = toSelect.value;
            const departDate = document.getElementById("departDate").value;

            if (!from || !to || !departDate) {
                alert("Vui lòng chọn đầy đủ thông tin chuyến bay!");
                return;
            }

            fetch(`/api/flight/search?from=${from}&to=${to}&date=${departDate}`)
                .then(res => res.json())
                .then(flights => {
                    resultsContainer.innerHTML = "";
                    flightResultsSection.style.display = "block";

                    if (!flights.length) {
                        resultsContainer.innerHTML = `<p>Không tìm thấy chuyến bay phù hợp!</p>`;
                        return;
                    }
                    flights.forEach(flight => {
                        const card = document.createElement("div");
                        card.classList.add("flight-card");
                        card.innerHTML = `
                            <div class="flight-info">
                                <h3>${flight.airline}</h3>
                                <p>Mã chuyến bay: <b>${flight.flightCode}</b></p>
                                <p>Đi: ${flight.departureAirportName.city} - ${flight.departureTime}</p>
                                <p>Đến: ${flight.arrivalAirportName.city} - ${flight.arrivalTime}</p>
                                <p>Giá: <strong>${flight.price.toLocaleString()} VNĐ</strong></p>
                                <button class="book-btn" onclick="openBookingModal(${flight.id}, ${flight.price})">
                                    Đặt vé
                                </button>
                            </div>
                        `;
                        resultsContainer.appendChild(card);
                    });
                })
                .catch(err => {
                    console.error("Lỗi tìm chuyến bay: ", err);
                    alert("Không thể tải danh sách chuyến bay!");
                });
        });
    }
});
