package com.xdwolf.airlineticket.service;

import com.xdwolf.airlineticket.dto.UserDTO;

import java.util.List;

public interface IUserService {
    List<UserDTO> findByUsername(String username);
    List<UserDTO> findByEmail(String email);
    UserDTO registerUser(UserDTO user);

    UserDTO loginUser(UserDTO userDTO);
}
