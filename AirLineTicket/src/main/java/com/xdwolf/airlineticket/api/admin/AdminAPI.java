package com.xdwolf.airlineticket.api.admin;

import com.xdwolf.airlineticket.component.ServiceHelper;
import com.xdwolf.airlineticket.config.CustomUserDetails;
import com.xdwolf.airlineticket.entity.BookingEntity;
import com.xdwolf.airlineticket.entity.FlightEntity;
import com.xdwolf.airlineticket.paging.PagingService;
import com.xdwolf.airlineticket.service.admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminAPI {
    private final AdminService adminService;
    private final ServiceHelper serviceHelper;
    private final PagingService pagingService;
    private final List<String> airlines = List.of(
            "Vietnam Airlines",
            "VietJet Air",
            "Bamboo Airways",
            "Pacific Airlines",
            "Vietravel Airlines"
    );

    @GetMapping("/dashboard")
    public String viewDashboard(@RequestParam(defaultValue = "0") int page,
                                 Model model,
                                 Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        model.addAttribute("fullname", userDetails.getFullname());
        model.addAttribute("completedFlights", adminService.getCompletedFlights());
        model.addAttribute("totalBookings", adminService.getTotalBookings());
        model.addAttribute("issuedTickets", adminService.getTicketIssued());
        model.addAttribute("totalPrice", adminService.formatCurrency(adminService.getPriceByMonth()));
        Page<BookingEntity> bookingPage = pagingService.getPage(serviceHelper.bookingRepository, page, 5, "bookingDate");
        model.addAttribute("pageData", bookingPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageUrl", "/admin/dashboard");
        model.addAttribute("tableFragment", "admin/fragment/table/booking-table");
        return "admin/dashboard";
    }

    @GetMapping("/flight")
    public String viewFlight(@RequestParam(defaultValue = "0") int page,
                             @RequestParam(required = false) Long editId,
                             @RequestParam(required = false) boolean create,
                             Model model) {
        Page<FlightEntity> flightPage = pagingService.getPage(serviceHelper.flightRepository, page, 5, "createdDate");
        model.addAttribute("pageData", flightPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageUrl", "/admin/flight");
        model.addAttribute("createMode", false);
        model.addAttribute("editFlight", null);
        model.addAttribute("airlines", airlines);
        model.addAttribute("airports", serviceHelper.airportRepository.findAll());
        if (create) {
            model.addAttribute("createMode", true);
        } if (editId != null) {
            FlightEntity flight = serviceHelper.flightRepository.findById(editId).orElseThrow(null);
            model.addAttribute("editFlight", flight);
        }
        model.addAttribute("tableFragment", "admin/fragment/table/flight-table");
        return "admin/dashboard";
    }
}
