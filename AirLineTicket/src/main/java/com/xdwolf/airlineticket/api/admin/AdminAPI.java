package com.xdwolf.airlineticket.api.admin;

import com.xdwolf.airlineticket.config.CustomUserDetails;
import com.xdwolf.airlineticket.service.admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.NumberFormat;
import java.util.Locale;

@Controller
@RequestMapping("/admin/")
@RequiredArgsConstructor
public class AdminAPI {
    private final AdminService adminService;

    @GetMapping("/dashboard")
    public String adminDashboard(Model model, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        model.addAttribute("fullname", userDetails.getFullname());
        model.addAttribute("completedFlights", adminService.getCompletedFlights());
        model.addAttribute("totalBookings", adminService.getTotalBookings());
        model.addAttribute("issuedTickets", adminService.getTicketIssued());
        model.addAttribute("totalPrice", adminService.formatCurrency(adminService.getPriceByMonth()));
        return "admin/dashboard";
    }
}
