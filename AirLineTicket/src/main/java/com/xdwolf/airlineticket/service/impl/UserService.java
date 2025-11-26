package com.xdwolf.airlineticket.service.impl;

import com.xdwolf.airlineticket.component.ServiceHelper;
import com.xdwolf.airlineticket.dto.UserDTO;
import com.xdwolf.airlineticket.entity.UserEntity;
import com.xdwolf.airlineticket.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final ServiceHelper service;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDTO registerUser(UserDTO user) {

        // Check username
        if (service.userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("USER");
        }
        user.setStatus((byte) 1);

        UserEntity saved = service.userRepository.save(service.userConverter.convertToEntity(user));
        return service.userConverter.convertToDTO(saved);
    }

    @Override
    public UserDTO loginUser(UserDTO userDTO) {
        List<UserEntity> user = service.userRepository.findByUsername(userDTO.getUsername());
        if (user.isEmpty()) {
                throw new RuntimeException("Tài khoản không tồn tại");
        }
        UserEntity entity = user.get(0);


        if (!passwordEncoder.matches(userDTO.getPassword(), entity.getPassword())) {
            throw new RuntimeException("Mật khẩu không chính xác!");
        }
        return service.userConverter.convertToDTO(entity);
    }

    @Override
    public List<UserDTO> findByUsername(String username) {
        List<UserEntity> userEntity = service.userRepository.findByUsername(username);
        return service.userConverter.convertToDTOList(userEntity);
    }

    @Override
    public List<UserDTO> findByEmail(String email) {
        List<UserEntity> userEntity = service.userRepository.findByEmail(email);
        return service.userConverter.convertToDTOList(userEntity);
    }

}
