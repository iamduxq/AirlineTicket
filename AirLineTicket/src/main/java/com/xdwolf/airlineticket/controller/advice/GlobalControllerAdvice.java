package com.xdwolf.airlineticket.controller.advice;

import com.xdwolf.airlineticket.config.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute
    public void addUserInfo(Model model) {
        System.out.println(">>> GlobalControllerAdvice RUN");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof CustomUserDetails userDetails) {
            System.out.println(">>> Advice fullname = " + userDetails.getFullname());
            model.addAttribute("fullname", userDetails.getFullname());
            model.addAttribute("username", userDetails.getUsername());
            model.addAttribute("role", userDetails.getRole());
        }
    }
}
