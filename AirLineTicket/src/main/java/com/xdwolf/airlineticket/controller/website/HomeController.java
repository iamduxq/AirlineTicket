package com.xdwolf.airlineticket.controller.website;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@CrossOrigin(origins = "http://localhost:8888")
@Controller
public class HomeController {
    @GetMapping({"/", "/index"})
    public String index(Model model) {
        model.addAttribute("title", "SkyTravel - Đặt vé máy bay giá rẻ");
        return "pages/index";
    }

    @GetMapping("/services")
    public String service() {
        return "pages/services";
    }

    @GetMapping("/about")
    public String about() {
        return "pages/about";
    }

    @GetMapping("/contact")
    public String contact() {
        return "pages/contact";
    }

    @GetMapping("/booking-management")
    public String bookingManagement() {
        return "pages/booking-management";
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String register() {
        return "auth/register";
    }
}
