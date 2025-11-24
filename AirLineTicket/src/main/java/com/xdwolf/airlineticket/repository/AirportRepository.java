package com.xdwolf.airlineticket.repository;

import com.xdwolf.airlineticket.entity.AirportEntity;
import com.xdwolf.airlineticket.entity.FlightEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AirportRepository extends JpaRepository<AirportEntity, Long> {
    boolean existsByCode(String code); // Check code sân bay tồn tại?

    AirportEntity findAirportEntityById(Long id); // Cách 1

    Optional<AirportEntity> findById(Long id); // Cách 2

    List<AirportEntity> findAll();
}
