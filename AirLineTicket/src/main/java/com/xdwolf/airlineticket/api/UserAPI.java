package com.xdwolf.airlineticket.api;

import com.xdwolf.airlineticket.dto.UserDTO;
import com.xdwolf.airlineticket.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserAPI {
    private final IUserService userService;

    @GetMapping(value = "/search/by-username")
//    public List<UserDTO> listUsername(@RequestBody UserDTO model) { Cách 1: Sử dụng @RequestBody
    public List<UserDTO> listUsername(@RequestParam("username") String username) { // -> Cách 2: truyền tham số
        return userService.findByUsername(username); // -> Cách 2
//        return userService.findByUsername(model.getUsername()); -> Cách 1
    }

    @GetMapping(value = "/search/by-email")
    public List<UserDTO> listEmail(@RequestBody UserDTO model) {
        return userService.findByEmail(model.getEmail());
    }

    @PostMapping("/register")
    public UserDTO registerUser(@RequestBody UserDTO userDTO) {
        return userService.registerUser(userDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO) {
        try {
            UserDTO result = userService.loginUser(userDTO);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", e.getMessage()));
        }
    }
}
