package com.xdwolf.airlineticket.service.admin;

import com.xdwolf.airlineticket.component.ServiceHelper;
import com.xdwolf.airlineticket.entity.BookingEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final ServiceHelper serviceHelper;
    private final FlightStatusService flightStatusService;

    public String formatCurrency(long amount) {
        NumberFormat format = NumberFormat.getInstance(new Locale("vi", "VN"));
        return format.format(amount) + " đ";
    }

    public long getTotalFlight() {
        return serviceHelper.flightRepository.count();
    }

    public long getCompletedFlights() {
        flightStatusService.updateFlightStatus();
        return serviceHelper.flightRepository.countByStatus(2);
    }

    public long getTotalBookings() {
        return serviceHelper.bookingRepository.count();
    }

    public long getTicketIssued() {
        return serviceHelper.ticketRepository.countByStatus(2);
    }

    public long getPriceByMonth() {
        LocalDate now = LocalDate.now();
        return serviceHelper.ticketRepository.getPriceByMonth(now.getMonthValue(), now.getYear());
    }


}
