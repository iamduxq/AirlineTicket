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
    public String LoginPage(Model model, @RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout) {
        model.addAttribute("userDTO", new UserDTO());
        if (error != null) {
            model.addAttribute("error", "Sai tài khoản hoặc mật khẩu!");
        }
        if (logout != null) {
            model.addAttribute("message", "Bạn đã đăng xuất!");
        }
        return "auth/login";
    }

//    @PostMapping("/login")
//    public String loginSubmit(@ModelAttribute("userDTO") UserDTO userDTO, Model model) {
//        return "redirect:/auth/login";
//    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerSubmit(@ModelAttribute("userDTO") UserDTO userDTO, Model model) {
        try {
            userService.registerUser(userDTO);
            return "redirect:auth/login?registered=true";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "auth/register";
        }
    }
}
