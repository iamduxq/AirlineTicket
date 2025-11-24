package com.xdwolf.airlineticket.repository;

import com.xdwolf.airlineticket.dto.UserDTO;
import com.xdwolf.airlineticket.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    List<UserEntity> findByUsername(String username);
    List<UserEntity> findByEmail(String email);
    boolean existsByUsername(String username);
    Optional<UserEntity> findFirstByUsername(String username);
}
