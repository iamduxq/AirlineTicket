package com.xdwolf.airlineticket.controller.auth;

import com.xdwolf.airlineticket.dto.UserDTO;
import com.xdwolf.airlineticket.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8888")
@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final IUserService userService;

    @GetMapping("/login")
    public String LoginPage(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "auth/login";
    }

    @PostMapping("/login")
    public String loginSubmit(@ModelAttribute("userDTO") UserDTO userDTO, Model model) {
        try {
            UserDTO result = userService.loginUser(userDTO);
            model.addAttribute("user", result);
            return "redirect:/";
        } catch (RuntimeException  e) {
            model.addAttribute("error", e.getMessage());
            return "auth/login";
        }
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerSubmit(@ModelAttribute("userDTO") UserDTO userDTO, Model model) {
        try {
            userService.registerUser(userDTO);
            model.addAttribute("message", "Đăng ký thành công");
            return "redirect:auth/login";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "auth/register";
        }
    }
}
