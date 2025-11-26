package com.xdwolf.airlineticket.config;

import com.xdwolf.airlineticket.entity.UserEntity;
import com.xdwolf.airlineticket.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findFirstByUsername(username)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng!"));
        return new CustomUserDetails(user);
    }
}
